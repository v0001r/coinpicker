package com.devanshi.tambola.coinpicker.models;

import org.jetbrains.annotations.*;

public class Menu {
    private int menu_id;
    private int icon_id;
    private String icon_path;
    private String menu_name;
    private boolean isSelected;

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getIcon_path() {
        return icon_path;
    }

    public void setIcon_path(String icon_path) {
        this.icon_path = icon_path;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() +
                " {\"menu_id\":\"" + menu_id + "\"," +
                " \"icon_id:\"" + icon_id + "\"," +
                " \"icon_path:\"" + icon_path + "\"," +
                " \"menu_name:\"" + menu_name + "\"," +
                " \"isSelected:\"" + isSelected + "\"}";
    }
}
