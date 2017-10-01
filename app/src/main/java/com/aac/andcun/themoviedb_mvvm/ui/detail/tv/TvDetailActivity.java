package com.aac.andcun.themoviedb_mvvm.ui.detail.tv;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityTvDetailBinding;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.ui.detail.PeopleAdapter;
import com.aac.andcun.themoviedb_mvvm.ui.person.PersonDetailActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credits;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.Status;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 29.09.2017.
 */

public class TvDetailActivity extends BaseActivity<ActivityTvDetailBinding> {

    @Inject
    TvRepository tvRepository;

    PeopleAdapter<Cast> castPeopleAdapter;
    PeopleAdapter<Crew> crewPeopleAdapter;

    private static final String EXTRA_TV_ID = TvDetailActivity.class.getSimpleName() + ".extra_tv_id";

    public static Intent newIntent(Context context, int tvId) {
        Intent intent = new Intent(context, TvDetailActivity.class);
        intent.putExtra(EXTRA_TV_ID, tvId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvRepository.getTv(getTvId()).observe(this, new Observer<Resource<Tv>>() {
            @Override
            public void onChanged(@Nullable Resource<Tv> tvResource) {
                if (tvResource != null) {
                    binding.setLoading(tvResource.status == Status.LOADING);
                    if (tvResource.data != null) {
                        binding.setTv(tvResource.data);
                    }
                    binding.executePendingBindings();
                }

            }
        });

        tvRepository.getCredits(getTvId()).observe(this, new Observer<Resource<Credits>>() {
            @Override
            public void onChanged(@Nullable Resource<Credits> creditsResource) {
                if (creditsResource != null && creditsResource.data != null) {
                    castPeopleAdapter.setPeoples(creditsResource.data.casts);
                    crewPeopleAdapter.setPeoples(creditsResource.data.crews);
                    binding.executePendingBindings();
                }
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tv_detail;
    }

    @Override
    protected void setUpUiComponents() {

        castPeopleAdapter = new PeopleAdapter<>();
        crewPeopleAdapter = new PeopleAdapter<>();

        binding.rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCrew.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        binding.rvCast.setAdapter(castPeopleAdapter);
        binding.rvCrew.setAdapter(crewPeopleAdapter);


        castPeopleAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener<Cast>() {
            @Override
            public void onItemClick(int position, Cast item) {
                startActivity(PersonDetailActivity.newIntent(TvDetailActivity.this, item.getId()));
            }
        });

        crewPeopleAdapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener<Crew>() {
            @Override
            public void onItemClick(int position, Crew item) {
                startActivity(PersonDetailActivity.newIntent(TvDetailActivity.this, item.getId()));
            }
        });


    }

    public int getTvId() {
        return getIntent().getIntExtra(EXTRA_TV_ID, 0);
    }

}