package com.aac.andcun.themoviedb_mvvm.di.app;

import android.app.Application;
import android.content.Context;

import com.aac.andcun.themoviedb_mvvm.TMDBApp;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                AndroidInjectionModule.class,
                ActivityBuilderModule.class
        }
)
public interface AppComponent {

    @Component.Builder
    interface Builder {

        AppComponent build();

    }

    void inject(TMDBApp app);

}
