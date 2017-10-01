package com.aac.andcun.themoviedb_mvvm.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.TypeConverters;


import com.aac.andcun.themoviedb_mvvm.db.EntityTypeConverters;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuneytcarikci on 28/09/2017.
 */
@Entity(primaryKeys = {"mId"})
@TypeConverters(EntityTypeConverters.class)
public class Person extends Id{

    @SerializedName("adult")
    @Expose
    private boolean adult;
  //  @SerializedName("also_known_as")
   // @Expose
   // private List<Object> alsoKnownAs = null;
    @SerializedName("biography")
    @Expose
    private String biography;
    @SerializedName("birthday")
    @Expose
    private String birthday;
    @SerializedName("deathday")
    @Expose
    private String deathday;
    @SerializedName("gender")
    @Expose
    private Integer gender;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("place_of_birth")
    @Expose
    private String placeOfBirth;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("profile_path")
    @Expose
    private String profilePath;

    public boolean getAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    //public List<Object> getAlsoKnownAs() {
     //   return alsoKnownAs;
    //}

    //public void setAlsoKnownAs(List<Object> alsoKnownAs) {
      //  this.alsoKnownAs = alsoKnownAs;
   // }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

}