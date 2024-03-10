package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.*;

public class DeclaredNumberData {
    @SerializedName("total_declare_number")
    @Expose
    private String totalDeclareNumber;
    @SerializedName("current")
    @Expose
    private String current;
    @SerializedName("first")
    @Expose
    private String first;
    @SerializedName("second")
    @Expose
    private String second;
    @SerializedName("third")
    @Expose
    private String third;
    @SerializedName("fourth")
    @Expose
    private String fourth;
    @SerializedName("fifth")
    @Expose
    private String fifth;

    public String getTotalDeclareNumber() {
        return totalDeclareNumber;
    }

    public void setTotalDeclareNumber(String totalDeclareNumber) {
        this.totalDeclareNumber = totalDeclareNumber;
    }

    public DeclaredNumberData withTotalDeclareNumber(String totalDeclareNumber) {
        this.totalDeclareNumber = totalDeclareNumber;
        return this;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public DeclaredNumberData withCurrent(String current) {
        this.current = current;
        return this;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public DeclaredNumberData withFirst(String first) {
        this.first = first;
        return this;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public DeclaredNumberData withSecond(String second) {
        this.second = second;
        return this;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public DeclaredNumberData withThird(String third) {
        this.third = third;
        return this;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    public DeclaredNumberData withFourth(String fourth) {
        this.fourth = fourth;
        return this;
    }

    public String getFifth() {
        return fifth;
    }

    public void setFifth(String fifth) {
        this.fifth = fifth;
    }

    public DeclaredNumberData withFifth(String fifth) {
        this.fifth = fifth;
        return this;
    }
}
