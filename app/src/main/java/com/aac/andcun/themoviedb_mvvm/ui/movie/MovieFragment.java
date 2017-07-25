package com.aac.andcun.themoviedb_mvvm.ui.movie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentMovieBinding;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class MovieFragment extends BaseFragment<FragmentMovieBinding> {

    MoviePagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_movie;
    }

    @Override
    protected void setUpUiComponents() {

        adapter = new MoviePagerAdapter(getFragmentManager());

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    private class MoviePagerAdapter extends FragmentStatePagerAdapter {

        private static final int TAB_COUNT = 3;

        MoviePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return MoviePageFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "POPULAR";
                    break;
                case 1:
                    title = "NOW PLAYING";
                    break;

                case 2:
                    title = "UPCOMING";
                    break;
            }
            return title;
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}
