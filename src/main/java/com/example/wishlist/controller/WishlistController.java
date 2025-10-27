package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping()
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    //Viser siden der viser den enkelte ønskeliste.
    @GetMapping("/wishlist/{wishlistID}")
    public String showWishlist(@PathVariable Integer wishlistID, Model model) {

        List<Wish> wishes = service.showWishlist(wishlistID);
        model.addAttribute("wishes", wishes);

        return "wishlist";
    }

    //Viser siden med formen for at tilføje et ønske til en ønskeliste.
    @GetMapping("/addWish/{userID}")
    public String addWish(@PathVariable Integer userID, Model model) {
        Wish wish = new Wish();
        List<Wishlist> wishlists = service.showWishlists(userID);
        model.addAttribute("wish", wish);
        model.addAttribute("wishlists", wishlists);
        return "addWish";
    }

    @PostMapping("/saveWish")
    public String saveWish(@ModelAttribute Wish wish, RedirectAttributes redirectAttributes) {
        service.addWish(wish.getWishlistID(), wish.getWishName(), wish.getDescription(), wish.getLink(), wish.getPrice());
        redirectAttributes.addAttribute("wishlistID", wish.getWishlistID());
        return "redirect:/wishlist/{wishlistID}";
    }

    @GetMapping("/addWishlist/{userID}")
    public String addWishlist(@PathVariable Integer userID,Model model) {
        Wishlist wishlist = new Wishlist();
        model.addAttribute("wishlist", wishlist);
        return "addWishlist";
    }

    @PostMapping("/saveWishlist")
    public String saveWishlist(@ModelAttribute Wishlist wishlist, RedirectAttributes redirectAttributes) {
        service.addWishlist(wishlist.getWishlistName(), wishlist.getUserID());
        redirectAttributes.addAttribute("userID", wishlist.getUserID());
        return "redirect:/Wishlists/{userID}";
    }
}