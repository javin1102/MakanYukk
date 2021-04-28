package com.example.makanyukk.util;

import android.app.Application;

public class UsersAPI extends Application {
    private static UsersAPI instance;
    private String username,userId;
    private String phoneNumber;
    private boolean hasRes;




    public UsersAPI(){}
    public static UsersAPI getInstance(){
        if(instance == null)
        {
            instance = new UsersAPI();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isHasRes() {
        return hasRes;
    }

    public void setHasRes(boolean hasRes) {
        this.hasRes = hasRes;
    }
}
