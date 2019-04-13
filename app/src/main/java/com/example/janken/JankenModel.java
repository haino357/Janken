package com.example.janken;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class JankenModel extends RealmObject {
    @PrimaryKey
    private long id;
    private Date date;
    private int gameCount;
    private int gameWinCount;
    private int gameDrawCount;
    private int gameLoseCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public int getGameWinCount() {
        return gameWinCount;
    }

    public void setGameWinCount(int gameWinCount) {
        this.gameWinCount = gameWinCount;
    }

    public int getGameDrawCount() {
        return gameDrawCount;
    }

    public void setGameDrawCount(int gameDrawCount) {
        this.gameDrawCount = gameDrawCount;
    }

    public int getGameLoseCount() {
        return gameLoseCount;
    }

    public void setGameLoseCount(int gameLoseCount) {
        this.gameLoseCount = gameLoseCount;
    }
}
