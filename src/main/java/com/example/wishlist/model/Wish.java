package com.example.wishlist.model;

public class Wish {
    private int wishID;
    private int wishlistID;
    private String wishName;
    private String description;
    private String link;
    private int price;
    private boolean isReserved;

    public Wish(int wishlistID, String wishName, String description, String link, int price) {
        this.wishlistID = wishlistID;
        this.wishName = wishName;
        this.description = description;
        this.link = link;
        this.price = price;
        isReserved = false;
    }

    public Wish() {}

    public String getWishName() {
        return wishName;
    }

    public void setName(String wishName) {
        this.wishName = wishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getWishID() {
        return wishID;
    }

    public void setWishID(int wishID) {
        this.wishID = wishID;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
}