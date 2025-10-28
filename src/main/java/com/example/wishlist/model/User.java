package com.example.wishlist.model;

import java.util.List;

public class User {
    private String userName;
    private String password;
    private Integer userID;

    public User(String userName, String password, Integer userID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
    }

    public User() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}