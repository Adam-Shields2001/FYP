package com.fyp1.fyp1.models;

public class Strikes {

    private float elapsedMinutes, elapsedSeconds;
    private int headStrike, round;
    private String uid;

    public Strikes(String uid, float elapsedMinutes, float elapsedSeconds, int headStrike, int round) {
        this.uid = uid;
        this.elapsedMinutes = elapsedMinutes;
        this.elapsedSeconds = elapsedSeconds;
        this.headStrike = headStrike;
        this.round = round;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getElapsedMinutes() {
        return elapsedMinutes;
    }

    public void setElapsedMinutes(float elapsedMinutes) {
        this.elapsedMinutes = elapsedMinutes;
    }

    public float getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(float elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public int getHeadStrike() {
        return headStrike;
    }

    public void setHeadStrike(int headStrike) {
        this.headStrike = headStrike;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
