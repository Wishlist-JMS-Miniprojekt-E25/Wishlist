package com.example.wishlist.model;

import java.util.List;

public class User {
    private String userName;
    private String password;
    private int userID;
    private List<Wishlist> wishlists;

    public User(String userName, String password, int userID) {
        this.userName = userName;
        this.password = password;
        this.userID = userID;
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

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }
}