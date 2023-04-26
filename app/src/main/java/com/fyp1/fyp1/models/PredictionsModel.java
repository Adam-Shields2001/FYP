package com.fyp1.fyp1.models;

public class PredictionsModel {

    private String userId;
    private String fighter;
    private String method;
    private String round;

    public PredictionsModel() {}

    public PredictionsModel(String userId, String fighter, String method, String round) {
        this.userId = userId;
        this.fighter = fighter;
        this.method = method;
        this.round = round;
    }

    public String getUserId() {
        return userId;
    }

    public String getFighter() {
        return fighter;
    }

    public String getMethod() {
        return method;
    }

    public String getRound() {
        return round;
    }
}
