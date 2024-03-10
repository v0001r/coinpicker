package com.devanshi.tambola.coinpicker.models;

import com.google.gson.annotations.*;

public class GameData {
    @SerializedName("game_id")
    @Expose
    private Integer gameId;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public GameData withGameId(Integer gameId) {
        this.gameId = gameId;
        return this;
    }
}
