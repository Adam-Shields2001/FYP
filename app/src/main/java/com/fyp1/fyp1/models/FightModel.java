package com.fyp1.fyp1.models;

public class FightModel {

    private String shortName, name, firstName, lastName;
    private double wins, losses, draws, noContests, moneyline;

    public FightModel(String shortName, String name, String firstName, String lastName, double wins, double losses, double draws, double noContests, double moneyline) {
        this.shortName = shortName;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.noContests = noContests;
        this.moneyline = moneyline;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public double getWins() {
        return wins;
    }

    public void setWins(double wins) {
        this.wins = wins;
    }

    public double getLosses() {
        return losses;
    }

    public void setLosses(double losses) {
        this.losses = losses;
    }

    public double getDraws() {
        return draws;
    }

    public void setDraws(double draws) {
        this.draws = draws;
    }

    public double getNoContests() {
        return noContests;
    }

    public void setNoContests(double noContests) {
        this.noContests = noContests;
    }

    public double getMoneyline() {
        return moneyline;
    }

    public void setMoneyline(double moneyline) {
        this.moneyline = moneyline;
    }
}
