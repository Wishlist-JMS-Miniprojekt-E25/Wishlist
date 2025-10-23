package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping()
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service){
        this.service = service;
    }

    //Viser siden der viser den enkelte ønskeliste.
    @GetMapping("/wishlist/{wishlistID}/{userID}")
    public String showWishlist(@PathVariable int wishlistID,@PathVariable int userID, Model model) {

        List<Wish> wishes = service.showWishlist();
        model.addAttribute("wishes", wishes);

        return "wishlist";
    }

}
