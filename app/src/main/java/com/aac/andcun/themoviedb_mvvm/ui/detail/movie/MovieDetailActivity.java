package com.aac.andcun.themoviedb_mvvm.ui.detail.movie;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityMovieDetailBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.ui.detail.PeopleAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.person.PersonDetailActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Cast;
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

    PeopleAdapter<Cast> castPeopleAdapter;
    PeopleAdapter<Crew> crewPeopleAdapter;

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
                if (movieResource != null) {
                    binding.setLoading(movieResource.status == Status.LOADING);
                    if (movieResource.data != null) {
                        binding.setMovie(movieResource.data);
                    }
                    binding.executePendingBindings();
                }

            }
        });

        movieRepository.getCredits(getMovieId()).observe(this, new Observer<Resource<Credits>>() {
            @Override
            public void onChanged(@Nullable Resource<Credits> creditsResource) {
                if (creditsResource != null && creditsResource.data != null) {
                    castPeopleAdapter.setPeoples(creditsResource.data.casts);
                    crewPeopleAdapter.setPeoples(creditsResource.data.crews);
                    binding.executePendingBindings();
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void setUpUiComponents() {

        castPeopleAdapter = new PeopleAdapter<>();
        crewPeopleAdapter = new PeopleAdapter<>();

        binding.rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCrew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.rvCast.setAdapter(castPeopleAdapter);
        binding.rvCrew.setAdapter(crewPeopleAdapter);


        castPeopleAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener<Cast>() {
            @Override
            public void onItemClick(int position, Cast item) {
                startActivity(PersonDetailActivity.newIntent(MovieDetailActivity.this, item.getId()));
            }
        });

        crewPeopleAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener<Crew>() {
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