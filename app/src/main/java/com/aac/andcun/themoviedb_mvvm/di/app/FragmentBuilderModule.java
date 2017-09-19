package com.aac.andcun.themoviedb_mvvm.di.app;

import com.aac.andcun.themoviedb_mvvm.ui.movie.MovieFragment;
import com.aac.andcun.themoviedb_mvvm.ui.movie.MoviePageFragment;
import com.aac.andcun.themoviedb_mvvm.ui.tv.TvFragment;
import com.aac.andcun.themoviedb_mvvm.ui.tv.TvPageFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by andani on 19.09.2017.
 */

@Module
public abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract MovieFragment movieFragment();

    @ContributesAndroidInjector
    abstract MoviePageFragment moviePageFragment();

    @ContributesAndroidInjector
    abstract TvFragment tvFragment();

    @ContributesAndroidInjector
    abstract TvPageFragment tvPageFragment();

}