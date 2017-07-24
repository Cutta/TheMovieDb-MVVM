package com.aac.andcun.themoviedb_mvvm.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by cuneytcarikci on 23/05/2017.
 */

public class ResponseSessionId {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}