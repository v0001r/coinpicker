
package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.*;

import org.jetbrains.annotations.*;

public class UserData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("fname")
    @Expose
    private String fname;
    @SerializedName("lname")
    @Expose
    private String lname;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no_tickets")
    @Expose
    private Integer noTickets;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ref_id")
    @Expose
    private String refId;
    @SerializedName("is_admin")
    @Expose
    private Integer isAdmin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserData withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UserData withTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserData withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public UserData withFname(String fname) {
        this.fname = fname;
        return this;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public UserData withLname(String lname) {
        this.lname = lname;
        return this;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserData withRole(String role) {
        this.role = role;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserData withEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getNoTickets() {
        return noTickets;
    }

    public void setNoTickets(Integer noTickets) {
        this.noTickets = noTickets;
    }

    public UserData withNoTickets(Integer noTickets) {
        this.noTickets = noTickets;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserData withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public UserData withRefId(String refId) {
        this.refId = refId;
        return this;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public UserData withIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() +
                " {\"timestamp\":\"" + timestamp + "\"," +
                " \"username:\"" + username + "\"," +
                " \"fname:\"" + fname + "\"," +
                " \"lname:\"" + lname + "\"," +
                " \"role:\"" + role + "\"," +
                " \"email:\"" + email + "\"," +
                " \"noTickets:\"" + noTickets + "\"," +
                " \"status:\"" + status + "\"," +
                " \"refId:\"" + refId + "\"}";
    }

}
