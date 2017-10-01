package com.aac.andcun.themoviedb_mvvm.ui.detail.tv;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 29.09.2017.
 */

@Module
public class TvDetailModule {

    @Provides
    public int provideMovieId(TvDetailActivity tvDetailActivity) {
        return tvDetailActivity.getTvId();
    }

}