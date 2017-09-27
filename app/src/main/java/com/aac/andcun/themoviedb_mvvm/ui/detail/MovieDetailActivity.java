package com.aac.andcun.themoviedb_mvvm.ui.detail;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityMovieDetailBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.Status;

import javax.inject.Inject;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieRepository.getMovie(getMovieId()).observe(this, new Observer<Resource<Movie>>() {
            @Override
            public void onChanged(@Nullable Resource<Movie> movieResource) {
                binding.setLoading(movieResource.status == Status.LOADING);
                if (movieResource.data != null) {
                    binding.setMovie(movieResource.data);
                }
                binding.executePendingBindings();
                Log.d("TAG", "onChanged: " + movieResource.message);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpUiComponents() {

    }

    public int getMovieId() {
        return getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    }

}