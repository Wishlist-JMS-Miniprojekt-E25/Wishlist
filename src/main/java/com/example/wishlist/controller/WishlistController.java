package com.example.wishlist.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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
                        HttpSession session,
                        Model model) {

        User loggedInUser = service.findUserByCredentials(username, password);

        if (loggedInUser != null){
            session.setAttribute("userID", loggedInUser.getUserID());
            session.setAttribute("username", loggedInUser.getUserName());

            List<Wishlist> userWishlists = service.showWishlists(loggedInUser.getUserID());
            model.addAttribute("wishlists", userWishlists);
            model.addAttribute("username", loggedInUser.getUserName());


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

    @GetMapping("/wish/{wishID}")
    public String showWish (@PathVariable int wishID, Model model){
        Wish wish = service.findWishByID(wishID);

        model.addAttribute("wish", wish);

        return "wish";
    }

    //Viser siden der viser den enkelte ønskeliste.
    @GetMapping("/wishlist/{wishlistID}")
    public String showWishlist(@PathVariable int wishlistID, Model model, HttpSession session) {

        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        Wishlist wishlist = service.findWishlistByID(wishlistID);
    public String showWishlist(@PathVariable int wishlistID, Model model) {
        Wishlist wishlist = service.findWishlistByID(wishlistID);
        List<Wish> wishes = service.showWishlist(wishlistID);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wishes", wishes);

        // Sørger for at man kommer til gæst view, hvis det ikke er ens egen ønskeliste man besøger
        if(userID == null || wishlist.getUserID() != userID){
            return "wishlistGuest";
        }

        return "wishlist";
    }

    //Viser siden med formen for at tilføje et ønske til en ønskeliste.
    @GetMapping("/addWishFromUserFrontpage")
    public String addWishFromUserFrontpage(HttpSession session, Model model) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        Wish wish = new Wish();
        List<Wishlist> wishlists = service.showWishlists(userID);
        model.addAttribute("wish", wish);
        model.addAttribute("wishlists", wishlists);
        return "addWish_fromUserFrontpage";
    }

    @GetMapping("/addWishFromWishlist/{wishlistID}")
    public String addWishFromWishlist(@PathVariable Integer wishlistID, Model model) {
        Wish wish = new Wish();
        wish.setWishlistID(wishlistID);
        model.addAttribute("wish", wish);
        return "addWish_fromWishlist";
    }

    @PostMapping("/saveWish")
    public String saveWish(@ModelAttribute Wish wish, RedirectAttributes redirectAttributes) {
        service.addWish(wish.getWishlistID(), wish.getWishName(), wish.getDescription(), wish.getLink(), wish.getPrice());
        redirectAttributes.addAttribute("wishlistID", wish.getWishlistID());
        return "redirect:/wishlist/{wishlistID}";
    }

    @GetMapping("/addWishlist")
    public String addWishlist(HttpSession session, Model model) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUserID(userID);
        model.addAttribute("wishlist", wishlist);
        return "addWishlist";
    }

    @PostMapping("/saveWishlist")
    public String saveWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        service.addWishlist(wishlist.getWishlistName(), userID);
        return "redirect:/wishlists";

    }

    @GetMapping("/wishlists")
    public String showUsersWishlists(HttpSession session, Model model){
        Integer userID = (Integer) session.getAttribute("userID");
        String username = (String) session.getAttribute("username");

        if (userID == null){
            return "redirect:/";
        }

        List<Wishlist> wishlists = service.showWishlists(userID);
        List<Wishlist> sharedWishlists = service.getSharedWishlists(userID);



        model.addAttribute("wishlists", wishlists);
        model.addAttribute("sharedWishlists", sharedWishlists);
        model.addAttribute("username", username);
        return "userFrontpage";
    }

    @GetMapping("edit/{wishID}")
    public String editWish(@PathVariable int wishID, Model model){
        Wish wish = service.findWishByID(wishID);

        model.addAttribute("wish", wish);

        return "editWish";
    }

    @PostMapping("/update")
    public String updateWish(@ModelAttribute Wish wish, RedirectAttributes redirectAttributes, HttpSession session){

        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        service.updateWish(wish);

        redirectAttributes.addAttribute("wishlistID", wish.getWishlistID());
        return "redirect:/wishlist/{wishlistID}";
    }

    @PostMapping("/deleteWish/{wishID}")
    public String deleteWish(@PathVariable int wishID, RedirectAttributes redirectAttributes) {
        Wish wish = service.findWishByID(wishID);
        service.deleteWishByID(wishID);
        redirectAttributes.addAttribute("wishlistID", wish.getWishlistID());
        return "redirect:/wishlist/{wishlistID}";
    }

    @PostMapping("/deleteWishlist/{wishlistID}")
    public String deleteWishlist(@PathVariable int wishlistID, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        Wishlist wishlist = service.findWishlistByID(wishlistID);
        service.deleteWishlistByID(wishlistID);

        return "redirect:/wishlists";
    }

    @GetMapping("/shareWishlist/{wishlistID}")
    public String shareWishlist (@PathVariable int wishlistID, HttpSession session, Model model) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        List<Wishlist> wishlists = service.showWishlists(userID);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("wishlistID", wishlistID);
        return "share";
    }

    @PostMapping("/saveShare/{wishlistID}")
    public String saveShare (@PathVariable int wishlistID, @RequestParam ("username") String username,
                             HttpSession session){
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null){
            return "redirect:/";
        }

        Wishlist wishlist = service.findWishlistByID(wishlistID);
        User targetUser = service.findUserByID(userID);

        service.shareWishlist(wishlistID, targetUser.getUserID());
        return "redirect:/userFrontpage";
    }
}