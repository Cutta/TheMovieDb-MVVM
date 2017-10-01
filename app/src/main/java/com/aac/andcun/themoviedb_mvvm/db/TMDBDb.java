package com.aac.andcun.themoviedb_mvvm.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.aac.andcun.themoviedb_mvvm.vo.Cast;
import com.aac.andcun.themoviedb_mvvm.vo.Credit;
import com.aac.andcun.themoviedb_mvvm.vo.Crew;
import com.aac.andcun.themoviedb_mvvm.vo.Movie;
import com.aac.andcun.themoviedb_mvvm.vo.PaginationResult;
import com.aac.andcun.themoviedb_mvvm.vo.Person;
import com.aac.andcun.themoviedb_mvvm.vo.Tv;

/**
 * Created by andani on 20.09.2017.
 */

@Database(entities = {Movie.class, Tv.class, Credit.class, Crew.class, Cast.class, Person.class, PaginationResult.class}, version = 1)
public abstract class TMDBDb extends RoomDatabase {

    abstract public MovieDao movieDao();

    abstract public TvDao tvDao();

    abstract public CreditDao creditDao();

    abstract public PersonDao personDao();

}