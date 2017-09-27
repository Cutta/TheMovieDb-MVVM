package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;

import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;

import java.util.Collections;
import java.util.Comparator;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(PaginationResult result);

    @Query("SELECT * FROM PaginationResult WHERE type = :type")
    public abstract LiveData<PaginationResult> search(String type);

    @Query("SELECT * FROM PaginationResult WHERE type = :type")
    public abstract PaginationResult findPaginationResult(String type);

    @Query("SELECT * FROM Movie WHERE mId = :movieId")
    public abstract LiveData<Movie> loadById(Integer movieId);

    @Query("SELECT * FROM Movie WHERE mId in (:movieIds)")
    public abstract LiveData<List<Movie>> loadByIds(List<Integer> movieIds);

    public LiveData<List<Movie>> loadOrdered(List<Integer> movieIds) {
        final SparseIntArray order = new SparseIntArray();
        int index = 0;
        for (Integer movieId : movieIds)
            order.put(movieId, index++);
        return Transformations.map(loadByIds(movieIds), new Function<List<Movie>, List<Movie>>() {
            @Override
            public List<Movie> apply(List<Movie> movies) {
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie m1, Movie m2) {
                        int pos1 = order.get(m1.getId());
                        int pos2 = order.get(m2.getId());
                        return pos1 - pos2;
                    }
                });
                return movies;
            }
        });
    }

}