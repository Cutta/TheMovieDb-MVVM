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

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MovieFragment extends BaseFragment<FragmentMovieBinding> {

    MoviePagerAdapter adapter;
    MovieRepository.MovieListType[] movieListTypes;

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
        movieListTypes = MovieRepository.MovieListType.values();

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(movieListTypes.length);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private class MoviePagerAdapter extends FragmentStatePagerAdapter {

        MoviePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return MoviePageFragment.newInstance(movieListTypes[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return movieListTypes[position].getTitle();
        }

        @Override
        public int getCount() {
            return movieListTypes.length;
        }

    }

}