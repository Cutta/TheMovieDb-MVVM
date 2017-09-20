package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;

import java.util.List;

/**
 * Created by andani on 20.09.2017.
 */

@Dao
public abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Movie... movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMovies(List<Movie> movies);

    @Query("SELECT * FROM PaginationResult WHERE type = :type")
    public abstract LiveData<PaginationResult> search(String type);

    @Query("SELECT * FROM PaginationResult WHERE type = :type")
    public abstract PaginationResult findSearchResult(String type);

    @Query("SELECT * FROM Movie WHERE id in (:movieIds)")
    public abstract LiveData<List<Movie>> loadById(List<Integer> movieIds);

}