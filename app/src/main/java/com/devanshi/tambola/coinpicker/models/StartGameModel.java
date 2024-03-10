package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.*;

public class StartGameModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private GameData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public StartGameModel withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StartGameModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public GameData getData() {
        return data;
    }

    public void setData(GameData data) {
        this.data = data;
    }

    public StartGameModel withData(GameData data) {
        this.data = data;
        return this;
    }

}
