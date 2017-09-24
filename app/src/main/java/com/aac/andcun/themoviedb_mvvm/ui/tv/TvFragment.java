package com.aac.andcun.themoviedb_mvvm.ui.tv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.FragmentTvBinding;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseFragment;

/**
 * Created by cuneytcarikci on 24/07/2017.
 */

public class TvFragment extends BaseFragment<FragmentTvBinding> {

    TvPagerAdapter adapter;
    TvRepository.TvListType[] tvListTypes;

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
        tvListTypes = TvRepository.TvListType.values();

        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(tvListTypes.length);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private class TvPagerAdapter extends FragmentStatePagerAdapter {


        TvPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return TvPageFragment.newInstance(tvListTypes[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tvListTypes[position].getTitle();
        }

        @Override
        public int getCount() {
            return tvListTypes.length;
        }

    }
}