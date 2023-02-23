package com.fyp1.fyp1.models;

public class PredictionModel {

    private String name, firstName, lastName, wins, losses, moneyline;

    public PredictionModel(String name, String firstName, String lastName, String wins, String losses, String moneyline) {
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.losses = losses;
        this.moneyline = moneyline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLosses() {
        return losses;
    }

    public void setLosses(String losses) {
        this.losses = losses;
    }

    public String getMoneyline() {
        return moneyline;
    }

    public void setMoneyline(String moneyline) {
        this.moneyline = moneyline;
    }
}
