package com.aac.andcun.themoviedb_mvvm.repository;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.vo.ResponseResultList;
import com.aac.andcun.themoviedb_mvvm.vo.ResultTv;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvRepository {

    private TMDBService service;

    @Inject
    public TvRepository(TMDBService service) {
        this.service = service;
    }

    public Observable<List<ResultTv>> getPopularTvs(int page) {

        return service.getPopularTv(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new io.reactivex.functions.Function<ResponseResultList<ResultTv>, List<ResultTv>>() {
                    @Override
                    public List<ResultTv> apply(ResponseResultList<ResultTv> result) throws Exception {
                        return result.getResults();
                    }
                });

    }

    public Observable<List<ResultTv>> getOnTheAirTvs(int page) {

        return service.getTvOnTheAir(ApiConstants.API_KEY, Locale.getDefault().getLanguage(), page)
                .map(new io.reactivex.functions.Function<ResponseResultList<ResultTv>, List<ResultTv>>() {
                    @Override
                    public List<ResultTv> apply(ResponseResultList<ResultTv> result) throws Exception {
                        return result.getResults();
                    }
                });

    }
}
