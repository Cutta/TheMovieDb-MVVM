package com.aac.andcun.themoviedb_mvvm.ui.person;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aac.andcun.themoviedb_mvvm.R;
import com.aac.andcun.themoviedb_mvvm.databinding.ActivityPersonDetailBinding;
import com.aac.andcun.themoviedb_mvvm.repository.PersonRepository;
import com.aac.andcun.themoviedb_mvvm.ui.base.BaseActivity;
import com.aac.andcun.themoviedb_mvvm.vo.Person;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;

import javax.inject.Inject;

/**
 * Created by cuneytcarikci on 28/09/2017.
 */

public class PersonDetailActivity extends BaseActivity<ActivityPersonDetailBinding> {

    @Inject
    PersonRepository personRepository;

    private static final String EXTRA_PERSON_ID = PersonDetailActivity.class.getSimpleName() + ".extra_person_id";

    public static Intent newIntent(Context context, int personId) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personRepository.getPerson(getPersonId()).observe(this, new Observer<Resource<Person>>() {
            @Override
            public void onChanged(@Nullable Resource<Person> personResource) {
                if (personResource != null && personResource.data != null) {
                    binding.setPerson(personResource.data);
                    binding.executePendingBindings();
                }


            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_detail;
    }

    public int getPersonId() {
        return getIntent().getIntExtra(EXTRA_PERSON_ID, 0);
    }
}
