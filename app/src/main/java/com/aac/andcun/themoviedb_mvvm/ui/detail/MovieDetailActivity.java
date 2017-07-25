package com.aac.andcun.themoviedb_mvvm.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityMovieDetailBinding;
import com.aac.andcun.themoviedb_mvvm.di.detail.DaggerDetailComponent;
import com.aac.andcun.themoviedb_mvvm.di.detail.DetailModule;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.util.RxTransformer;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;
import com.aac.andcun.themoviedb_mvvm.vo.ResultMovie;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * Created by cuneytcarikci on 25/07/2017.
 */

public class MovieDetailActivity extends BaseActivity<ActivityMovieDetailBinding> {

    @Inject
    MovieRepository repository;

    public static final String EXTRA_MOVIE_ID = "movieId";

    private int movieId;

    public static Intent newIntent(Context context, int movieId) {

        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);

        //getDetail();
        //getMovieCredits();
        getSimilarMovies();
    }

    private void getDetail() {
        repository.getMovieDetail(movieId).compose(RxTransformer.<ResultMovie>applyIOSchedulers())
                .subscribe(new Consumer<ResultMovie>() {
                    @Override
                    public void accept(ResultMovie resultMovie) throws Exception {

                        Log.d("accept", "accept: " + resultMovie.getOriginalTitle());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MovieDetailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMovieCredits() {
        repository.getCredits(movieId).compose(RxTransformer.<ResponseCredits>applyIOSchedulers())
                .subscribe(new Consumer<ResponseCredits>() {
                    @Override
                    public void accept(ResponseCredits responseCredits) throws Exception {
                        Log.d("accept", "accept: " + responseCredits.getCast().size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MovieDetailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getSimilarMovies() {
        repository.getSimilars(movieId).compose(RxTransformer.<ResponseResultList<ResultMovie>>applyIOSchedulers())
                .subscribe(new Consumer<ResponseResultList<ResultMovie>>() {
                    @Override
                    public void accept(ResponseResultList<ResultMovie> resultMovieResponseResultList) throws Exception {
                        Log.i("", "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i("", "");
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

    @Override
    protected void resolveDaggerDependency() {
        DaggerDetailComponent.builder()
                .appComponent(getApplicationComponent())
                .detailModule(new DetailModule())
                .build()
                .inject(this);
    }
}
