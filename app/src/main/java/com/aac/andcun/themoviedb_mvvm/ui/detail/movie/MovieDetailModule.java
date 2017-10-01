package com.aac.andcun.themoviedb_mvvm.ui.detail.movie;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andani on 19.09.2017.
 */

@Module
public class MovieDetailModule {

    @Provides
    public int provideMovieId(MovieDetailActivity movieDetailActivity) {
        return movieDetailActivity.getMovieId();
    }

}