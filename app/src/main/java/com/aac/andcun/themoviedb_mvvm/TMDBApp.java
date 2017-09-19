package com.aac.andcun.themoviedb_mvvm;

import android.app.Activity;
import android.app.Application;


import com.aac.andcun.themoviedb_mvvm.di.app.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TMDBApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }


    private void initAppComponent() {

        DaggerAppComponent
                .builder().build().inject(this);

        /*DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build()
                .inject(this);*/

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

}
