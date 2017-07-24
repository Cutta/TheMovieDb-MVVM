package com.aac.andcun.themoviedb_mvvm.di.movie;

import com.aac.andcun.themoviedb_mvvm.di.PerFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cuneytcarikci on 25/07/2017.
 */

@Module
public class MovieModule {

    public MovieModule() {
    }

    @Provides
    @PerFragment
    String provideDummyString(){
        return "";
    }

}
