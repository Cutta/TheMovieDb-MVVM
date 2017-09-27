package com.aac.andcun.themoviedb_mvvm.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuneytcarikci on 27/09/2017.
 */

public class People {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    private int movieTvId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public int getMovieTvId() {
        return movieTvId;
    }

    public void setMovieTvId(int movieTvId) {
        this.movieTvId = movieTvId;
    }
}
