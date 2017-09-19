package com.aac.andcun.themoviedb_mvvm.di.app;

import android.app.Application;
import android.content.Context;

import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.repository.MovieRepository;
import com.aac.andcun.themoviedb_mvvm.repository.TvRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
public class AppModule {

   // private Application app;

   // public AppModule(Application app) {
    //    this.app = app;
   // }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Singleton
    @Provides
    TMDBService provideService(Retrofit retrofit) {
        return retrofit.create(TMDBService.class);
    }

    @Singleton
    @Provides
    MovieRepository provideMovieRepository(TMDBService service) {
        return new MovieRepository(service);
    }

    @Singleton
    @Provides
    TvRepository provideTvRepository(TMDBService service) {
        return new TvRepository(service);
    }

}