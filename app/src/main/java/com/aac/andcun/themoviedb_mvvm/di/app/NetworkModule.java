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


    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    @Provides
    @Singleton
    TMDBService provideService(Retrofit retrofit) {

        return retrofit.create(TMDBService.class);

    }


    @Provides
    @Singleton
    MovieRepository provideMovieRepository(TMDBService service){
        return  new MovieRepository(service);
    }

    @Provides
    @Singleton
    TvRepository provideTvRepository(TMDBService service){
        return  new TvRepository(service);
    }
/*
    @Provides
    @Singleton
    ApiSource provideApiSource(Retrofit retrofit) {
        return new ApiSourceImpl(retrofit);
    }
    */
}
