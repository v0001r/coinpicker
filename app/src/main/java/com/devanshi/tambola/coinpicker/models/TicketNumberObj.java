
package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.*;

public class TicketNumberObj {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("meta_checked")
    @Expose
    private Integer metaChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TicketNumberObj withId(String id) {
        this.id = id;
        return this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TicketNumberObj withValue(String value) {
        this.value = value;
        return this;
    }

    public Integer getMetaChecked() {
        return metaChecked;
    }

    public void setMetaChecked(Integer metaChecked) {
        this.metaChecked = metaChecked;
    }

    public TicketNumberObj withMetaChecked(Integer metaChecked) {
        this.metaChecked = metaChecked;
        return this;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() +
                " {\"id\":\"" + id + "\"," +
                " \"value:\"" + value + "\"," +
                " \"meta_checked:\"" + metaChecked + "\"}";
    }
}
