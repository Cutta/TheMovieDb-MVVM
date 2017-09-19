package com.aac.andcun.themoviedb_mvvm.ui.base;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aac.andcun.themoviedb_mvvm.TMDBApp;
import com.aac.andcun.themoviedb_mvvm.di.app.AppComponent;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    protected T binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resolveDaggerDependency();
        setUpUiComponents();
    }

    protected void resolveDaggerDependency() {

    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void setUpUiComponents();


}
