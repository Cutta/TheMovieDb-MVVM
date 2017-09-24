package com.aac.andcun.themoviedb_mvvm.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andani on 9/24/2017.
 */

public class Id {

    @SerializedName("id")
    @Expose
    private int mId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

}