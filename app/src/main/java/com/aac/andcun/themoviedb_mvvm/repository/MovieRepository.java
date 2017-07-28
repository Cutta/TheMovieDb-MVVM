package com.aac.andcun.themoviedb_mvvm.repository;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseCredits;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;
import com.aac.andcun.themoviedb_mvvm.vo.ResultMovie;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MovieRepository {

    private TMDBService service;

    @Inject
    public MovieRepository(TMDBService service) {
        this.service = service;
    }

    public Observable<List<ResultMovie>> getPopularMovies(int page) {

        return service.getPopularMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new Function<ResponseResultList<ResultMovie>, List<ResultMovie>>() {
                    @Override
                    public List<ResultMovie> apply(ResponseResultList<ResultMovie> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<List<ResultMovie>> getNowPlayingMovies(int page) {

        return service.getNowPlayingMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new Function<ResponseResultList<ResultMovie>, List<ResultMovie>>() {
                    @Override
                    public List<ResultMovie> apply(ResponseResultList<ResultMovie> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<List<ResultMovie>> getUpcomingMovies(int page) {

        return service.getUpcomingMovie(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new Function<ResponseResultList<ResultMovie>, List<ResultMovie>>() {
                    @Override
                    public List<ResultMovie> apply(ResponseResultList<ResultMovie> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<ResultMovie> getMovieDetail(int movieId) {

        return service.getMovieDetail(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());

    }

    public Observable<ResponseCredits> getCredits(int movieId){
        return service.getMovieCredit(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

    public Observable<ResponseResultList<ResultMovie>> getSimilars(int movieId) {
        return service.getSimilarMovies(movieId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
    }

}