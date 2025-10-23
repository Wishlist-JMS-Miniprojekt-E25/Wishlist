package com.example.wishlist.controller;

import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service){
        this.service = service;
    }
// returnerer "forside", da det er brugerens egen forside
    @GetMapping("/wishlists")
    public String showUsersWishlists(Model model){
        List<Wishlist> wishlists = service.showAllWishlists();
        model.addAttribute("wishlists", wishlists);
        return "forside";
    }
}
