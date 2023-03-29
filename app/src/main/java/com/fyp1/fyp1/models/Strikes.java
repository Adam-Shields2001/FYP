package com.fyp1.fyp1.models;

public class Strikes {

    private float elapsedTimeInSeconds;
    private int headStrike, round;
    private String uid;

    public Strikes(String uid, float elapsedTimeInSeconds, int headStrike, int round) {
        this.uid = uid;
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.headStrike = headStrike;
        this.round = round;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public float getElapsedTimeInSeconds() {
        return elapsedTimeInSeconds;
    }

    public void setElapsedTimeInSeconds(float elapsedTimeInSeconds) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
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
