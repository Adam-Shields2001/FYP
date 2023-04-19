package com.fyp1.fyp1.models;

public class Strikes {

    private float elapsedTimeInSeconds;
    private int round;
    private String fighterNames;
    private String uid;

    public Strikes() {
    }

    public Strikes(float elapsedTimeInSeconds, int round, String fighterNames, String uid) {
        this.elapsedTimeInSeconds = elapsedTimeInSeconds;
        this.round = round;
        this.fighterNames = fighterNames;
        this.uid = uid;
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

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getFighterNames() {
        return fighterNames;
    }

    public void setFighterNames(String fighterNames) {
        this.fighterNames = fighterNames;
    }
}
