package com.aac.andcun.themoviedb_mvvm.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import java.util.List;

/**
 * Created by cuneytcarikci on 27/09/2017.
 */

@Entity(primaryKeys = {"id"})
public class Credit {
    @Ignore
    private List<Cast> casts;
    @Ignore
    private List<Crew> crews;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }
}
