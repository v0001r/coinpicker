package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.*;

import org.jetbrains.annotations.*;

public class DeclaredNumberModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DeclaredNumberData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public DeclaredNumberModel withStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeclaredNumberModel withMessage(String message) {
        this.message = message;
        return this;
    }

    public DeclaredNumberData getData() {
        return data;
    }

    public void setData(DeclaredNumberData data) {
        this.data = data;
    }

    public DeclaredNumberModel withData(DeclaredNumberData data) {
        this.data = data;
        return this;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() +
                " {\"status\":\"" + status + "\"," +
                " \"message:\"" + message + "\"," +
                " \"data:\"" + data + "\"}";
    }
}
