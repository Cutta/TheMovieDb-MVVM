package com.aac.andcun.themoviedb_mvvm.ui.detail;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityMovieDetailBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credit;
import com.aac.andcun.themoviedb_mvvm.vo.Credits;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;
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

    MoviePeopleAdapter<Cast> castMoviePeopleAdapter;
    MoviePeopleAdapter<Crew> crewMoviePeopleAdapter;

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

        movieRepository.getCredits(getMovieId()).observe(this, new Observer<Resource<Credits>>() {
            @Override
            public void onChanged(@Nullable Resource<Credits> creditsResource) {
                if (creditsResource.data != null) {
                    castMoviePeopleAdapter.setPeoples(creditsResource.data.casts);
                    crewMoviePeopleAdapter.setPeoples(creditsResource.data.crews);
                    binding.executePendingBindings();
                }
                Log.i("", "");
            }
        });

        /*movieRepository.getCredits(getMovieId()).observe(this, new Observer<Resource<Credit>>() {
            @Override
            public void onChanged(@Nullable Resource<Credit> creditResource) {
                Log.d("onChanged", "onChanged: ");
            }
        });*/
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpUiComponents() {

        castMoviePeopleAdapter = new MoviePeopleAdapter<>();
        crewMoviePeopleAdapter = new MoviePeopleAdapter<>();

        binding.rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCrew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.rvCast.setAdapter(castMoviePeopleAdapter);
        binding.rvCrew.setAdapter(crewMoviePeopleAdapter);


        castMoviePeopleAdapter.setOnItemClickListener(new MoviePeopleAdapter.OnItemClickListener<Cast>() {
            @Override
            public void onItemClick(int position, Cast item) {
                Toast.makeText(MovieDetailActivity.this, item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        crewMoviePeopleAdapter.setOnItemClickListener(new MoviePeopleAdapter.OnItemClickListener<Crew>() {
            @Override
            public void onItemClick(int position, Crew item) {
                Toast.makeText(MovieDetailActivity.this, item.getName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public int getMovieId() {
        return getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    }

}