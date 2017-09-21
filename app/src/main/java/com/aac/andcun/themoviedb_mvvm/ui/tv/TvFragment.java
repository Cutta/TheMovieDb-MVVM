package com.aac.andcun.themoviedb_mvvm.ui.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentTvBinding;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvFragment extends BaseFragment<FragmentTvBinding> {

    TvPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tv;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpViewPager();
    }

    private void setUpViewPager() {
        adapter = new TvPagerAdapter(getFragmentManager());

        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private class TvPagerAdapter extends FragmentStatePagerAdapter {

        private static final int TAB_COUNT = 2;

        TvPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return TvPageFragment.newInstance(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = "POPULAR";
                    break;
                case 1:
                    title = "ON THE AIR";
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
