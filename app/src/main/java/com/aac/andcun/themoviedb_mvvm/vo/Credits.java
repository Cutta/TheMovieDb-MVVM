package com.aac.andcun.themoviedb_mvvm.vo;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

/**
 * Created by andani on 28.09.2017.
 */

public class Credits {

    @Embedded
    public Credit credit;

    @Relation(parentColumn = "mId", entityColumn = "movieTvId", entity = Cast.class)
    public List<Cast> casts;

    @Relation(parentColumn = "mId", entityColumn = "movieTvId", entity = Crew.class)
    public List<Crew> crews;

}