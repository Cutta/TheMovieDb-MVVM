package com.aac.andcun.themoviedb_mvvm.ui.main;

import android.os.Bundle;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityMainBinding;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     //   startActivity(MovieDetailActivity.newIntent(MainActivity.this,550));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpUiComponents() {


    }
}
