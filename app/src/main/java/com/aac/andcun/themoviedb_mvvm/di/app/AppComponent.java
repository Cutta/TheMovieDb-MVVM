package com.aac.andcun.themoviedb_mvvm.di.app;

import android.content.Context;
import com.aac.andcun.themoviedb_mvvm.TMDBApp;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    Context context();

    MovieRepository movieRepository();

    TvRepository tvRepository();

    void inject(TMDBApp app);

}
