package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.aac.andcun.themoviedb_mvvm.vo.Person;

import java.util.List;

/**
 * Created by cuneytcarikci on 28/09/2017.
 */

@Dao
public abstract class PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(Person person);

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //public abstract void insert(List<Person> persons);

    @Query("SELECT * FROM Person WHERE mId = :id")
    public abstract LiveData<Person> find(int id);


}
