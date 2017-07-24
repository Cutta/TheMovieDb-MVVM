package com.aac.andcun.themoviedb_mvvm.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}