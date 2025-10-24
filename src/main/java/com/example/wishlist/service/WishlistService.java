package com.example.wishlist.service;

import com.example.wishlist.model.User;
import com.example.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {
    private final WishlistRepository repository;

    public WishlistService(WishlistRepository repository) {
        this.repository = repository;
    }

    // Tilføjer en bruger (videresender bare til repository)
    public User addUser(String userName, String password) {
        return repository.addUser(userName, password);
    }

    public boolean validateUser(String userName, String password) {
        return repository.validateUser(userName, password);
    }

}
