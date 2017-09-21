package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.MovieDao;
import com.aac.andcun.themoviedb_mvvm.db.TMDBDb;
import com.aac.andcun.themoviedb_mvvm.util.AbsentLiveData;
import com.aac.andcun.themoviedb_mvvm.util.AppExecutors;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;


/**
 * Created by cuneytcarikci on 24/07/2017.
 */

@Singleton
public class MovieRepository {

    private TMDBDb db;
    private TMDBService service;
    private MovieDao movieDao;
    private AppExecutors appExecutors;
    public static final String type = "popular_movies";

    @Inject
    public MovieRepository(TMDBService service, TMDBDb db, MovieDao movieDao, AppExecutors appExecutors) {
        this.service = service;
        this.db = db;
        this.movieDao = movieDao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Movie>>> getPopularMovies() {

        return new NetworkBoundResource<List<Movie>, ResponseResultList>(appExecutors) {


            @Override
            protected void saveCallResult(@NonNull ResponseResultList item) {
                List<Integer> movieIds = item.getMovieIds();
                PaginationResult paginationResult = new PaginationResult(type, movieIds, item.getTotalResults(), item.getNextPage());
                db.beginTransaction();

                try {

                    movieDao.insertMovies(item.getResults());
                    movieDao.insert(paginationResult);
                    db.setTransactionSuccessful();

                } finally {
                    db.endTransaction();
                }

            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                return Transformations.switchMap(movieDao.search(type), new Function<PaginationResult, LiveData<List<Movie>>>() {
                    @Override
                    public LiveData<List<Movie>> apply(PaginationResult input) {
                        if (input == null)
                            return AbsentLiveData.create();
                        else
                            return movieDao.loadById(input.ids);

                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseResultList>> createCall() {
                return service.getPopularM(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), 1);
            }

        }.asLiveData();
    }


    public Observable<List<Movie>> getNowPlayingMovies(int page) {

        return service.getNowPlayingMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new io.reactivex.functions.Function<ResponseResultList, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ResponseResultList result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<List<Movie>> getUpcomingMovies(int page) {

        return service.getUpcomingMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new io.reactivex.functions.Function<ResponseResultList, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ResponseResultList result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<Movie> getMovieDetail(int movieId) {

        return service.getMovieDetail(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());

    }

    public Observable<ResponseCredits> getCredits(int movieId) {
        return service.getMovieCredit(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

    public Observable<ResponseResultList> getSimilars(int movieId) {
        return service.getSimilarMovies(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

}