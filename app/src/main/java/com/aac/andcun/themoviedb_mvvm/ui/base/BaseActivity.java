package com.aac.andcun.themoviedb_mvvm.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity implements HasSupportFragmentInjector {

    protected T binding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        resolveDaggerDependency();
        setUpUiComponents();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }

    protected void resolveDaggerDependency() {
    }

    protected void setUpUiComponents() {
    }

    @LayoutRes
    protected abstract int getLayoutId();

}
