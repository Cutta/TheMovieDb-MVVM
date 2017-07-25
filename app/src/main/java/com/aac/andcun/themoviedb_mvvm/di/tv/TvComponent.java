package com.aac.andcun.themoviedb_mvvm.di.tv;

import com.aac.andcun.themoviedb_mvvm.di.PerFragment;
import com.aac.andcun.themoviedb_mvvm.di.app.AppComponent;
import com.aac.andcun.themoviedb_mvvm.ui.tv.TvPageFragment;

import dagger.Component;

/**
 * Created by cuneytcarikci on 25/07/2017.
 */

@Component(modules = TvModule.class,dependencies = AppComponent.class)
@PerFragment
public interface TvComponent {

    void inject(TvPageFragment tvPageFragment);

}
