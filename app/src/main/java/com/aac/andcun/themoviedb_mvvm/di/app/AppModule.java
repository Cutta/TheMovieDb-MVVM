package com.aac.andcun.themoviedb_mvvm.di.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.aac.andcun.themoviedb_mvvm.api.TMDBService;
import com.aac.andcun.themoviedb_mvvm.db.DateTypeAdapter;
import com.aac.andcun.themoviedb_mvvm.db.MovieDao;
import com.aac.andcun.themoviedb_mvvm.db.TMDBDb;
import com.aac.andcun.themoviedb_mvvm.db.TvDao;
import com.aac.andcun.themoviedb_mvvm.util.LiveDataCallAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.aac.andcun.themoviedb_mvvm.api.ApiConstants.BASE_URL;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

@Module
public class AppModule {

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
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
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
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
    TMDBDb provideDb(Application app) {
        return Room.databaseBuilder(app, TMDBDb.class, "tmdb.db").build();
    }

    @Singleton
    @Provides
    MovieDao provideMovieDao(TMDBDb db) {
        return db.movieDao();
    }

    @Singleton
    @Provides
    TvDao provideTvDao(TMDBDb db) {
        return db.tvDao();
    }

}