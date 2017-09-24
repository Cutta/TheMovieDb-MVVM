package com.aac.andcun.themoviedb_mvvm.di.app;

import com.aac.andcun.themoviedb_mvvm.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by andani on 19.09.2017.
 */

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MainActivity mainActivity();

    /*@ContributesAndroidInjector(modules = FragmentBuilderModule.class)
    abstract MovieDetailActivity movieDetailActivity();*/

}