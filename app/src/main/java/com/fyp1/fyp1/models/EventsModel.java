package com.fyp1.fyp1.models;

public class EventsModel {

    private String shortName, name, season, dateTime;

    public EventsModel(String shortName, String name, String season, String dateTime) {
        this.shortName = shortName;
        this.name = name;
        this.season = season;
        this.dateTime = dateTime;
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

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
