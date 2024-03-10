
package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.*;

import org.jetbrains.annotations.*;

public class UserModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UserData userData;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() +
                " {\"status\":\""+status+ "\"," +
                " \"message:\""+message+"\"," +
                " \"data:\""+userData+"\"}";
    }

}
