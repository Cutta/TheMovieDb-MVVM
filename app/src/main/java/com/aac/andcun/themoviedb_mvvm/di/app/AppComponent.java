package com.aac.andcun.themoviedb_mvvm.di.app;

import android.app.Application;

import com.aac.andcun.themoviedb_mvvm.TMDBApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuilderModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

    void inject(TMDBApp aaApp);
}