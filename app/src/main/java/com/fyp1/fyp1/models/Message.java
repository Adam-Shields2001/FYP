package com.fyp1.fyp1.models;

public class Message {
    private String userId;
    private String text;
    private String userName;

    public Message() {
        // Required empty constructor for Firebase
    }

    public Message(String userId, String text, String userName) {
        this.userId = userId;
        this.text = text;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}