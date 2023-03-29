package com.fyp1.fyp1.models;

import com.google.gson.annotations.SerializedName;

public class Fight {
    String firstName;
    String lastName;
    String opponentFirstName;
    String opponentLastName;
    String preFightWins;
    String preFightLosses;
    String opponentPreFightWins;
    String opponentPreFightLosses;
    String moneyLine;
    String opponentMoneyLine;

    public Fight(String firstName, String lastName, String opponentFirstName, String opponentLastName,
                 String preFightWins, String preFightLosses, String opponentPreFightWins,
                 String opponentPreFightLosses, String moneyLine, String opponentMoneyLine) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.opponentFirstName = opponentFirstName;
        this.opponentLastName = opponentLastName;
        this.preFightWins = preFightWins;
        this.preFightLosses = preFightLosses;
        this.opponentPreFightWins = opponentPreFightWins;
        this.opponentPreFightLosses = opponentPreFightLosses;
        this.moneyLine = moneyLine;
        this.opponentMoneyLine = opponentMoneyLine;
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

    public String getOpponentFirstName() {
        return opponentFirstName;
    }

    public void setOpponentFirstName(String opponentFirstName) {
        this.opponentFirstName = opponentFirstName;
    }

    public String getOpponentLastName() {
        return opponentLastName;
    }

    public void setOpponentLastName(String opponentLastName) {
        this.opponentLastName = opponentLastName;
    }

    public String getPreFightWins() {
        return preFightWins;
    }

    public void setPreFightWins(String preFightWins) {
        this.preFightWins = preFightWins;
    }

    public String getPreFightLosses() {
        return preFightLosses;
    }

    public void setPreFightLosses(String preFightLosses) {
        this.preFightLosses = preFightLosses;
    }

    public String getOpponentPreFightWins() {
        return opponentPreFightWins;
    }

    public void setOpponentPreFightWins(String opponentPreFightWins) {
        this.opponentPreFightWins = opponentPreFightWins;
    }

    public String getOpponentPreFightLosses() {
        return opponentPreFightLosses;
    }

    public void setOpponentPreFightLosses(String opponentPreFightLosses) {
        this.opponentPreFightLosses = opponentPreFightLosses;
    }

    public String getMoneyLine() {
        return moneyLine;
    }

    public void setMoneyLine(String moneyLine) {
        this.moneyLine = moneyLine;
    }

    public String getOpponentMoneyLine() {
        return opponentMoneyLine;
    }

    public void setOpponentMoneyLine(String opponentMoneyLine) {
        this.opponentMoneyLine = opponentMoneyLine;
    }

    public String getFighters() {
        return firstName + " " + lastName + " vs. " + opponentFirstName + " " + opponentLastName;
    }

    public String getRecords() {
        return preFightWins + "-" + preFightLosses + " (UFC) vs. " + opponentPreFightWins + "-" + opponentPreFightLosses + " (UFC)";
    }

}



