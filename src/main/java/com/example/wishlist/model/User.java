package com.example.wishlist.model;

public class User {
    private String userName;
    private String password;
    private int userID;
    private int wishlistID;

    public User(String userName, String password, int userID, int wishlistID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
        this.wishlistID = wishlistID;
    }

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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }
}