package com.aac.andcun.themoviedb_mvvm.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import com.aac.andcun.themoviedb_mvvm.db.EntityTypeConverters;

import java.util.List;

/**
 * Created by andani on 20.09.2017.
 */

@Entity(primaryKeys = {"type"})
@TypeConverters(EntityTypeConverters.class)
public class PaginationResult {

    public final String type;
    public final List<Integer> ids;
    public final int totalCount;
    @Nullable
    public final Integer next;

    public PaginationResult(String type, List<Integer> ids, int totalCount,
                            Integer next) {
        this.type = type;
        this.ids = ids;
        this.totalCount = totalCount;
        this.next = next;
    }

}