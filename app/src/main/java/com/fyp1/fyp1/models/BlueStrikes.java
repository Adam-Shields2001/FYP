package com.fyp1.fyp1.models;

public class BlueStrikes extends Strikes {
    private int blueStrike;

    public BlueStrikes() {

    }

    public BlueStrikes(float elapsedTimeInSeconds, int round, String fighterNames, String uid, int blueStrike) {
        super(elapsedTimeInSeconds, round, fighterNames, uid);
        this.blueStrike = blueStrike;
    }

    public int getBlueStrike() {
        return blueStrike;
    }

    public void setBlueStrike(int blueStrike) {
        this.blueStrike = blueStrike;
    }
}
