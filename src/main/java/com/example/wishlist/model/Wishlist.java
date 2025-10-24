package com.example.wishlist.model;

import java.util.List;

public class Wishlist {
    private String wishlistName;
    private Integer wishlistID;
    private Integer userID;
    private List<Wish> wishes;

    public Wishlist(String wishlistName, Integer userID){
        this.wishlistName = wishlistName;
        this.userID = userID;
    }

    public Wishlist() {}

    public int getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public Integer getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(Integer wishlistID) {
        this.wishlistID = wishlistID;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }
}