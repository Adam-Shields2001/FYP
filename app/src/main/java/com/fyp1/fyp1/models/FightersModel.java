package com.fyp1.fyp1.models;

public class FightersModel {

    private String firstName, lastName, nickname;
    private Double wins, losses, draws;

    public FightersModel(String firstName, String lastName, String nickname, Double wins, Double losses, Double draws) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Double getWins() {
        return wins;
    }

    public void setWins(Double wins) {
        this.wins = wins;
    }

    public Double getLosses() {
        return losses;
    }

    public void setLosses(Double losses) {
        this.losses = losses;
    }

    public Double getDraws() {
        return draws;
    }

    public void setDraws(Double draws) {
        this.draws = draws;
    }
}
