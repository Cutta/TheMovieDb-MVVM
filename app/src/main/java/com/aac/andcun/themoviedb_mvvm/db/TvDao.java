package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.util.SparseIntArray;

import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by andani on 9/24/2017.
 */

@Dao
public abstract class TvDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Tv... tvs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTvs(List<Tv> tvs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(PaginationResult result);

    @Query("SELECT * FROM PaginationResult WHERE type = :type")
    public abstract LiveData<PaginationResult> search(String type);

    @Query("SELECT * FROM PaginationResult WHERE type = :type")
    public abstract PaginationResult findPaginationResult(String type);

    @Query("SELECT * FROM Tv WHERE mId in (:tvIds)")
    public abstract LiveData<List<Tv>> loadById(List<Integer> tvIds);

    public LiveData<List<Tv>> loadOrdered(List<Integer> tvIds) {
        final SparseIntArray order = new SparseIntArray();
        int index = 0;
        for (Integer tvId : tvIds)
            order.put(tvId, index++);
        return Transformations.map(loadById(tvIds), new Function<List<Tv>, List<Tv>>() {
            @Override
            public List<Tv> apply(List<Tv> movies) {
                Collections.sort(movies, new Comparator<Tv>() {
                    @Override
                    public int compare(Tv m1, Tv m2) {
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