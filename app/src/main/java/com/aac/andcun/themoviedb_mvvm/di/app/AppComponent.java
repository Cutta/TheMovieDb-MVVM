package com.aac.andcun.themoviedb_mvvm.di.app;

import android.content.Context;
import com.aac.andcun.themoviedb_mvvm.TMDBApp;

import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    Context context();

    void inject(TMDBApp app);

}
