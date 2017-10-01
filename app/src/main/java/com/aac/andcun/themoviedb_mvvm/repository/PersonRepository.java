package com.aac.andcun.themoviedb_mvvm.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aac.andcun.themoviedb_mvvm.api.ApiConstants;
import com.aac.andcun.themoviedb_mvvm.api.ApiResponse;
import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.PersonDao;
import com.aac.andcun.themoviedb_mvvm.util.AppExecutors;
import com.aac.andcun.themoviedb_mvvm.vo.Resource;
import com.aac.andcun.themoviedb_mvvm.vo.Person;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by cuneytcarikci on 28/09/2017.
 */

@Singleton
public class PersonRepository {


  private PersonDao personDao;
  private TMDBService service;
  private AppExecutors appExecutors;


  @Inject
  public PersonRepository(TMDBService service, PersonDao personDao, AppExecutors appExecutors) {
    this.service = service;
    this.personDao = personDao;
    this.appExecutors = appExecutors;
  }

  public LiveData<Resource<Person>> getPerson(final int personId) {

    return new NetworkBoundResource<Person, Person>(appExecutors) {
      @Override
      protected void saveCallResult(@NonNull Person item) {
        personDao.insert(item);
      }

      @Override
      protected boolean shouldFetch(@Nullable Person data) {
        return data == null;
      }

      @NonNull
      @Override
      protected LiveData<Person> loadFromDb() {
        return personDao.find(personId);
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<Person>> createCall() {
        return service.getPerson(personId, ApiConstants.API_KEY, Locale.getDefault().getLanguage());
      }
    }.asLiveData();


  }


}
