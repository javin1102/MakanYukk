package com.example.makanyukk.util;

import android.app.Application;

import com.example.makanyukk.model.User;

public class UsersAPI extends Application {
    private static UsersAPI instance;
    private String email;
    private String password;
    private String username;
    private String userID;
    private Boolean hasRes;
    private String phoneNumber;



    public UsersAPI(){}
    public static UsersAPI getInstance(){
        if(instance == null)
        {
            instance = new UsersAPI();
        }
        return instance;
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

    public void setUser(User user){
        user.setUserID(userID);
        user.setHasRes(hasRes);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);
    }
}
