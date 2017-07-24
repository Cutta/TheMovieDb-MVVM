package com.aac.andcun.themoviedb_mvvm;

import android.app.Application;

import com.aac.andcun.themoviedb_mvvm.di.app.AppComponent;
import com.aac.andcun.themoviedb_mvvm.di.app.AppModule;
import com.aac.andcun.themoviedb_mvvm.di.app.DaggerAppComponent;
import com.aac.andcun.themoviedb_mvvm.di.app.NetworkModule;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TMDBApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }


    private void initAppComponent() {

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule()).build();

        appComponent.inject(this);

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
