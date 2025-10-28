package com.example.wishlist.controller;

import com.example.wishlist.model.User;
import com.example.wishlist.model.User;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    //viser forsiden
    @GetMapping()
    public String getForside() {
        return "Frontpage";
    }

    //går til admin siden efter succefuldt login
    @PostMapping("/userFrontpage")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

        boolean valid = service.validateUser(username, password);

        if (valid) {
            return "userFrontpage"; // viser side efter login
        } else {
            model.addAttribute("error", true);
            return "Frontpage"; // viser login igen med fejl
        }
    }

    //viser createUser html siden
    @GetMapping("/createUser")
    public String getCreateUser(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        service.addUser(user.getUserName(), user.getPassword());

        // Tilføj en succesbesked, som sendes med i redirect
        redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! You can now log in.");

        // Redirecter tilbage til forsiden (login-siden)
        return "redirect:/";
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
    public String addWishlist(@PathVariable Integer userID, Model model) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUserID(userID);
        model.addAttribute("wishlist", wishlist);
        return "addWishlist";
    }

    @PostMapping("/saveWishlist")
    public String saveWishlist(@ModelAttribute Wishlist wishlist, @RequestParam("userID") Integer userID, RedirectAttributes redirectAttributes) {
        service.addWishlist(wishlist.getWishlistName(), userID);
        redirectAttributes.addAttribute("userID", userID);
        return "redirect:/Wishlists/{userID}";

    }

    @GetMapping("/wishlists")
    public String showUsersWishlists(@RequestParam Integer userID, Model model){
        List<Wishlist> wishlists = service.showWishlists(userID);
        model.addAttribute("wishlists", wishlists);
        return "brugerForside";
    }
}