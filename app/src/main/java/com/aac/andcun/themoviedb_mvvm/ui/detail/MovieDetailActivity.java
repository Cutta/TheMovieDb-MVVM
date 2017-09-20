package com.aac.andcun.themoviedb_mvvm.ui.detail;

import android.content.Context;
import android.content.Intent;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityMovieDetailBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.util.RxTransformer;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by andani on 19.09.2017.
 */

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {

    @Inject
    MovieRepository movieRepository;

    private static final String EXTRA_MOVIE_ID = MovieDetailActivity.class.getSimpleName() + ".extra_movie_id";

    public static Intent newIntent(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpUiComponents() {
        movieRepository.getMovieDetail(getMovieId())
                .compose(RxTransformer.<Movie>applyIOSchedulers())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    public int getMovieId() {
        return getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    }

}