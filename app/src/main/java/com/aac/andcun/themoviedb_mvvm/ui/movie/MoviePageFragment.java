package com.aac.andcun.themoviedb_mvvm.ui.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentMoviePageBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.repository.NextPageHandler;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;
import com.aac.andcun.themoviedb_mvvm.ui.common.MovieAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.common.decoration.GridSpacingItemDecoration;
import com.aac.andcun.themoviedb_mvvm.ui.detail.MovieDetailActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MoviePageFragment extends BaseFragment<FragmentMoviePageBinding> {

    private static final String ARG_POSITION = "position";

    @Inject
    MovieRepository repository;

    MovieAdapter mAdapter;
    NextPageHandler mNextPageHandler;

    int position;

    public static MoviePageFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        MoviePageFragment fragment = new MoviePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpRecyclerView();
        setUpNextPageHandler();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_page;
    }

    private void setUpRecyclerView() {
        LiveData<Resource<List<Movie>>> resourceLiveData;

        switch (position) {
            case 0:
                resourceLiveData = repository.getPopularMovies();
                break;

            case 1:
                resourceLiveData = repository.getPopularMovies();
                break;

            case 2:
                resourceLiveData = repository.getPopularMovies();
                break;

            default:
                resourceLiveData = repository.getPopularMovies();
                break;
        }

        resourceLiveData.observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                if (listResource != null && listResource.data != null) {
                    mAdapter.addMovieList(listResource.data);
                    binding.executePendingBindings();
                }
            }
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPosition = layoutManager.findLastVisibleItemPosition();
                if (lastPosition == mAdapter.getItemCount() - 1)
                    mNextPageHandler.loadNextPage();
            }
        });

        mAdapter = new MovieAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.movie_tv_item_margin)));
        binding.recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Movie item) {
                startActivity(MovieDetailActivity.newIntent(getActivity(), item.getId()));
            }
        });
    }

    private void setUpNextPageHandler() {
        mNextPageHandler = new NextPageHandler(repository);

        mNextPageHandler.getLoadMoreState().observe(this, new Observer<NextPageHandler.LoadMoreState>() {
            @Override
            public void onChanged(@Nullable NextPageHandler.LoadMoreState loadMoreState) {
                if (loadMoreState == null)
                    binding.setLoadingMore(false);
                else {
                    binding.setLoadingMore(loadMoreState.isRunning());
                    String error = loadMoreState.getErrorMessage();
                    if (error != null)
                        Snackbar.make(binding.loadMoreBar, error, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}