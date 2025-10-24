package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service){
        this.service = service;
    }

    @GetMapping("/wish/{wishID}")
    public String showWish (@PathVariable int wishID, Model model){
        Wish wish = service.findWishByID(wishID);

        model.addAttribute("wish", wish);

        return "wish";
    }
}
