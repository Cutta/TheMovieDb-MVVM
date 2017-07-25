package com.aac.andcun.themoviedb_mvvm.di.detail;

import com.aac.andcun.themoviedb_mvvm.di.PerActivity;
import com.aac.andcun.themoviedb_mvvm.di.app.AppComponent;
import com.aac.andcun.themoviedb_mvvm.ui.detail.MovieDetailActivity;

import dagger.Component;

/**
 * Created by cuneytcarikci on 25/07/2017.
 */

@Component(modules = DetailModule.class, dependencies = AppComponent.class)
@PerActivity
public interface DetailComponent {

    void inject(MovieDetailActivity activity);

}
