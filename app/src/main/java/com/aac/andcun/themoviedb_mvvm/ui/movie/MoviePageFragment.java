package com.aac.andcun.themoviedb_mvvm.ui.movie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentMoviePageBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.repository.NextPageHandler;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;
import com.aac.andcun.themoviedb_mvvm.ui.common.MovieAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.common.decoration.GridSpacingItemDecoration;
import com.aac.andcun.themoviedb_mvvm.ui.detail.movie.MovieDetailActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MoviePageFragment extends BaseFragment<FragmentMoviePageBinding> {

    private static final String ARG_MOVIE_LIST_TYPE_ORDINAL = MoviePageFragment.class.getSimpleName() + ".arg_movie_list_type_ordinal";

    @Inject
    MovieRepository movieRepository;

    private MovieAdapter adapter;
    private NextPageHandler nextPageHandler;
    private MovieRepository.MovieListType movieListType;

    public static MoviePageFragment newInstance(MovieRepository.MovieListType movieListType) {
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_LIST_TYPE_ORDINAL, movieListType.ordinal());
        MoviePageFragment fragment = new MoviePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieListType = MovieRepository.MovieListType.values()[getArguments().getInt(ARG_MOVIE_LIST_TYPE_ORDINAL, 0)];
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
        LiveData<Resource<List<Movie>>> resourceLiveData = movieRepository.getMovies(movieListType);

        resourceLiveData.observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                if (listResource != null && listResource.data != null) {
                    adapter.updateMovieList(listResource.data);
                    binding.executePendingBindings();
                }
            }
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisiblePosition == adapter.getItemCount() - 1)
                    nextPageHandler.loadNextPage();
            }
        });

        adapter = new MovieAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerView.addItemDecoration(new GridSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.movie_tv_item_margin)));
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Movie item) {
                startActivity(MovieDetailActivity.newIntent(getActivity(), item.getId()));
            }
        });
    }

    private void setUpNextPageHandler() {
        nextPageHandler = new NextPageHandler() {
            @Override
            public LiveData<Resource<Boolean>> createNextPageCall() {
                return movieRepository.fetchNextPage(movieListType);
            }
        };

        nextPageHandler.getLoadMoreState().observe(this, new Observer<NextPageHandler.LoadMoreState>() {
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