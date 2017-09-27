package com.aac.andcun.themoviedb_mvvm.vo;

import android.arch.persistence.room.Entity;

/**
 * Created by cuneytcarikci on 27/09/2017.
 */

@Entity(primaryKeys = {"mId"})
public class Credit {

    private int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

}