package com.aac.andcun.themoviedb_mvvm.di.app;

import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aac.andcun.themoviedb_mvvm.api.ApiConstants.BASE_URL;


/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Module
public class NetworkModule {



}
