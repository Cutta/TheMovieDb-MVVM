package com.aac.andcun.themoviedb_mvvm.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aac.andcun.themoviedb_mvvm.TMDBApp;
import com.aac.andcun.themoviedb_mvvm.di.app.AppComponent;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    protected T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,getLayoutId());
        resolveDaggerDependency();
        setUpUiComponents();
    }

    protected AppComponent getApplicationComponent() {
        return ((TMDBApp) (getApplication())).getAppComponent();
    }

    protected void resolveDaggerDependency() {

    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void setUpUiComponents();
}
