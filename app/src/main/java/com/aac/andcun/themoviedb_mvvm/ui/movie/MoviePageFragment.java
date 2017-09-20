package com.aac.andcun.themoviedb_mvvm.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentMoviePageBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;
import com.aac.andcun.themoviedb_mvvm.ui.common.MovieAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.common.decoration.GridSpacingItemDecoration;
import com.aac.andcun.themoviedb_mvvm.ui.detail.MovieDetailActivity;
import com.aac.andcun.themoviedb_mvvm.util.RxTransformer;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MoviePageFragment extends BaseFragment<FragmentMoviePageBinding> {

    private static final String ARG_POSITION = "position";

    @Inject
    MovieRepository repository;

    MovieAdapter adapter;

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

        Observable<List<Movie>> observable = null;

        switch (position) {
            case 0:
                observable = repository.getPopularMovies(1);
                break;

            case 1:
                observable = repository.getNowPlayingMovies(1);
                break;

            case 2:
                observable = repository.getUpcomingMovies(1);
                break;
        }


        observable
                .compose(RxTransformer.<List<Movie>>applyIOSchedulers())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        Log.e("MoviePageFragment", "Count: " + movies.size());
                        adapter.addMovieList(movies);
                        binding.executePendingBindings();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void resolveDaggerDependency() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie_page;
    }

    @Override
    protected void setUpUiComponents() {
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

}