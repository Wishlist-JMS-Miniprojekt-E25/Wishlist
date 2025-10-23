package com.example.wishlist.model;

public class Wish {
    private String name;
    private String description;
    private String link;
    private int price;
    private int wishID;
    private int wishlistID;
    private boolean isReserved;

    public Wish(String name, String description, String link, int price, int wishlistID) {
        this.name = name;
        this.description = description;
        this.link = link;
        this.price = price;
        this.wishlistID = wishlistID;
        isReserved = false;
    }

    public Wish() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}