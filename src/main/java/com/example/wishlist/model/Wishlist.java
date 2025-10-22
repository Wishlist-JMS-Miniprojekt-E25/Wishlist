package com.example.wishlist.model;

import java.util.List;

public class Wishlist {
    private String name;
    private int wishlistID;
    private int userID;
    private List<Wish> wishes;

    public Wishlist(String name, int userID){
        this.name = name;
        this.userID = userID;
    }

    public Wishlist() {}

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }
}