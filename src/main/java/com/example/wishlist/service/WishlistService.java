package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    public Wish findWishByID(int wishID){
        return repository.findWishByID(wishID);
    }
}
