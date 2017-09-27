package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credit;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;

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

    @Query("SELECT * FROM Credit WHERE id = :id")
    public abstract LiveData<Credit> find(int id);

    @Query("SELECT * FROM Crew WHERE movieTvId = :id")
    public abstract List<Crew> findCrews(int id);

    @Query("SELECT * FROM Cast WHERE movieTvId = :id")
    public abstract List<Cast> findCasts(int id);


}
