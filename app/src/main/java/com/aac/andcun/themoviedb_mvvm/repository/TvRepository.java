package com.aac.andcun.themoviedb_mvvm.repository;

import com.aac.andcun.themoviedb_mvvm.api.TMDBService;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvRepository {

    TMDBService service;

    public TvRepository(TMDBService service) {
        this.service = service;
    }
}
