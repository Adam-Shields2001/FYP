package com.fyp1.fyp1.models;

public class RedStrikes extends Strikes {
    private int redStrike;

    public RedStrikes() {
        // Default constructor required for calls to DataSnapshot.getValue(RedStrikes.class)
    }

    public RedStrikes(float elapsedTimeInSeconds, int round, String fighterNames, String uid, int redStrike) {
        super(elapsedTimeInSeconds, round, fighterNames, uid);
        this.redStrike = redStrike;
    }

    public int getRedStrike() {
        return redStrike;
    }

    public void setRedStrike(int redStrike) {
        this.redStrike = redStrike;
    }
}
