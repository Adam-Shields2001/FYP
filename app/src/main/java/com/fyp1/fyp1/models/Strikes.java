package com.fyp1.fyp1.models;

public class Strikes {

    private float elapsedMinutes, elapsedSeconds;
    private int headStrike;

    public Strikes(float elapsedMinutes, float elapsedSeconds, int headStrike) {
        this.elapsedMinutes = elapsedMinutes;
        this.elapsedSeconds = elapsedSeconds;
        this.headStrike = headStrike;
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
}
