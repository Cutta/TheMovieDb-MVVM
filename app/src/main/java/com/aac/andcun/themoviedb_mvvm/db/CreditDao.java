package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credit;
import com.aac.andcun.themoviedb_mvvm.vo.Credits;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by cuneytcarikci on 27/09/2017.
 */

@Dao
public abstract class CreditDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCredit(Credit credit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCrews(List<Crew> crews);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCasts(List<Cast> casts);

    @Query("SELECT * FROM Credit WHERE mId = :id")
    public abstract LiveData<Credit> find(int id);

    @Query("SELECT * FROM Crew WHERE movieTvId = :id")
    public abstract List<Crew> findCrews(int id);

    @Query("SELECT * FROM Cast WHERE movieTvId = :id")
    public abstract List<Cast> findCasts(int id);

    @Query("SELECT * FROM Credit WHERE mId = :id")
    public abstract LiveData<Credits> findCredit(int id);

    public LiveData<Credits> loadCredits(int id) {
        return Transformations.map(findCredit(id), new Function<Credits, Credits>() {
            @Override
            public Credits apply(Credits input) {
                Collections.sort(input.casts, new Comparator<Cast>() {
                    @Override
                    public int compare(Cast t0, Cast t1) {
                        return t0.getListOrder() - t1.getListOrder();
                    }
                });
                Collections.sort(input.crews, new Comparator<Crew>() {
                    @Override
                    public int compare(Crew t0, Crew t1) {
                        return t0.getListOrder() - t1.getListOrder();
                    }
                });
                return input;
            }
        });
    }

}
