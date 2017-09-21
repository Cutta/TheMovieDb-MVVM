package com.aac.andcun.themoviedb_mvvm.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentMovieBinding;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MovieFragment extends BaseFragment<FragmentMovieBinding> {

    @Inject
    MovieRepository movieRepository;

    MoviePagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager();
    }

    private void setUpViewPager() {
        adapter = new MoviePagerAdapter(getFragmentManager());

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private class MoviePagerAdapter extends FragmentStatePagerAdapter {

        private String[] mTitles = new String[] {
                "POPULAR",
                "NOW PLAYING",
                "UPCOMING"
        };

        MoviePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return MoviePageFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return 1;
        }

    }

}