package com.example.wishlist.controller;

import com.example.wishlist.model.User;
import com.example.wishlist.service.WishlistService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping()
public class WishlistController {
    private final WishlistService service;
    private final WishlistService wishlistService;

    public WishlistController(WishlistService service, WishlistService wishlistService){
        this.service = service;
        this.wishlistService = wishlistService;
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

        boolean valid = wishlistService.validateUser(username, password);

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
        wishlistService.addUser(user.getUserName(), user.getPassword());

        // Tilføj en succesbesked, som sendes med i redirect
        redirectAttributes.addFlashAttribute("successMessage", "Account created successfully! You can now log in.");

        // Redirecter tilbage til forsiden (login-siden)
        return "redirect:/";
    }




}
