package com.example.makanyukk.model;

public class User {
    private String email;
    private String password;
    private String username;
    private String userID;
    private Boolean hasRes;
    private String phoneNumber;



    public User(){}

    public User(String email, String password, String username, String userID) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.userID = userID;
    }

    public Boolean getHasRes() {
        return hasRes;
    }

    public void setHasRes(Boolean hasRes) {
        this.hasRes = hasRes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
