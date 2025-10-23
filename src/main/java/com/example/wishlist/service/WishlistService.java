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

    public List<Wish> showWishlist() {
        return repository.showWishlist();
    }

    public Wishlist findWishlistByID(int wishlistID) {
        return repository.findWishlistByID(wishlistID);
    }
}
