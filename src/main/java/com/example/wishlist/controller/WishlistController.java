package com.example.wishlist.controller;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping()
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service){
        this.service = service;
    }

    @GetMapping("/wishlists")
    public String showUsersWishlists(@RequestParam int userID, Model model){
        List<Wishlist> wishlists = service.showAllWishlists(userID);
        model.addAttribute("wishlists", wishlists);
        return "brugerForside";
    }
}
