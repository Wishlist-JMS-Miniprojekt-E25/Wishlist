package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {
    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public List<Wishlist> showWishlists(Integer userID) {
        return repository.showWishlists(userID);
    }

    public List<Wish> showWishlist(Integer wishlistID) {
        return repository.showWishlist(wishlistID);
    }

    public Wishlist findWishlistByID(Integer wishlistID) {
        return repository.findWishlistByID(wishlistID);
    }

    public Wish addWish(Integer wishlistID, String wishName, String description, String link, int price) {
        return repository.addWish(wishlistID, wishName, description, link, price);
    }

    public Wishlist addWishlist(String wishlistName, Integer userID){
        return repository.addWishlist(wishlistName, userID);
    }
}
