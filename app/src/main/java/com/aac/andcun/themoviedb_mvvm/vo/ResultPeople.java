
package com.aac.andcun.themoviedb_mvvm.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultPeople {

    @SerializedName("profile_path")
    @Expose
    private String profilePath;
    @SerializedName("adult")
    @Expose
    private boolean adult;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("known_for")
    @Expose
    private List<Movie> knownFor = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Double popularity;

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Movie> getKnownFor() {
        return knownFor;
    }

    public void setKnownFor(List<Movie> knownFor) {
        this.knownFor = knownFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

}
