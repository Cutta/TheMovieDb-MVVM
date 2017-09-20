package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;

/**
 * Created by andani on 20.09.2017.
 */

@Database(entities = {Movie.class, PaginationResult.class}, version = 1)
public abstract class TMDBDb extends RoomDatabase {

    abstract public MovieDao movieDao();

}