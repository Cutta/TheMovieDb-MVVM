package com.aac.andcun.themoviedb_mvvm.di.tv;

import com.aac.andcun.themoviedb_mvvm.di.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 25/07/2017.
 */

@Module
public class TvModule {

    public TvModule() {
    }

    @Provides
    @PerFragment
    String provideDummyString(){
        return "";
    }
}
