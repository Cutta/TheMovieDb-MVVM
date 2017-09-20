package com.aac.andcun.themoviedb_mvvm.repository;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.MovieDao;
import com.aac.andcun.themoviedb_mvvm.util.AppExecutors;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

@Singleton
public class MovieRepository {

    private TMDBService service;
    private MovieDao movieDao;
    private AppExecutors appExecutors;

    @Inject
    public MovieRepository(TMDBService service, MovieDao movieDao, AppExecutors appExecutors) {
        this.service = service;
        this.movieDao = movieDao;
        this.appExecutors = appExecutors;
    }

    public Observable<List<Movie>> getPopularMovies(int page) {

        return service.getPopularMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new Function<ResponseResultList<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ResponseResultList<Movie> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<List<Movie>> getNowPlayingMovies(int page) {

        return service.getNowPlayingMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new Function<ResponseResultList<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ResponseResultList<Movie> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<List<Movie>> getUpcomingMovies(int page) {

        return service.getUpcomingMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new Function<ResponseResultList<Movie>, List<Movie>>() {
                    @Override
                    public List<Movie> apply(ResponseResultList<Movie> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<Movie> getMovieDetail(int movieId) {

        return service.getMovieDetail(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());

    }

    public Observable<ResponseCredits> getCredits(int movieId){
        return service.getMovieCredit(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

    public Observable<ResponseResultList<Movie>> getSimilars(int movieId) {
        return service.getSimilarMovies(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

}