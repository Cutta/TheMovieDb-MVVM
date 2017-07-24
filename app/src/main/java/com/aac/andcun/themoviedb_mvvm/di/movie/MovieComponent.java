package com.aac.andcun.themoviedb_mvvm.di.movie;

import com.aac.andcun.themoviedb_mvvm.di.PerFragment;
import com.aac.andcun.themoviedb_mvvm.di.app.AppComponent;
import com.aac.andcun.themoviedb_mvvm.ui.movie.MoviePageFragment;

import dagger.Component;

/**
 * Created by cuneytcarikci on 25/07/2017.
 */

@PerFragment
@Component(modules = MovieModule.class, dependencies = AppComponent.class)
public interface MovieComponent {

    void inject(MoviePageFragment fragment);

}
