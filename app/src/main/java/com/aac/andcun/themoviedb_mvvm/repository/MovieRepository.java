package com.aac.andcun.themoviedb_mvvm.repository;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;
import com.aac.andcun.themoviedb_mvvm.vo.ResultMovie;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MovieRepository {

    private TMDBService service;

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
}
