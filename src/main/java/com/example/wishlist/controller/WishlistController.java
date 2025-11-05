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

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    //viser forsiden
    @GetMapping()
    public String getForside() {
        return "Frontpage";
    }

    //går til brugerforsiden ved redirecters
    @GetMapping("/userFrontpage")
    public String showUserFrontpage(HttpSession session, Model model) {
        Integer userID = (Integer) session.getAttribute("userID");
        String username = (String) session.getAttribute("username");

        // Hvis ingen er logget ind → send tilbage til login
        if (userID == null) {
            return "redirect:/";
        }

        // Hent alle brugerens egne og delte ønskelister
        List<Wishlist> wishlists = service.showWishlists(userID);
        List<Wishlist> sharedWishlists = service.getSharedWishlists(userID);

        // Fjern ønskelister hvor ejeren er den samme som den loggede bruger
        sharedWishlists.removeIf(shared -> shared.getUserID() == userID);

        // Send data videre til viewet
        model.addAttribute("userID", userID);
        model.addAttribute("username", username);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("sharedWishlists", sharedWishlists);
        model.addAttribute("showAddWish", true);

        return "userFrontpage";
    }



    //går til brugerforsiden fra forsiden efter succefuldt login
    @PostMapping("/userFrontpage")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        User loggedInUser = service.findUserByCredentials(username, password);

        if (loggedInUser != null) {
            // Gem login-info i session
            session.setAttribute("userID", loggedInUser.getUserID());
            session.setAttribute("username", loggedInUser.getUserName());

            Integer userID = loggedInUser.getUserID();

            // Hent egne og delte ønskelister
            List<Wishlist> userWishlists = service.showWishlists(userID);
            List<Wishlist> sharedWishlists = service.getSharedWishlists(userID);

            // Fjern ønskelister som brugeren selv ejer
            sharedWishlists.removeIf(shared -> shared.getUserID() == userID);

            // Tilføj data til view
            model.addAttribute("userID", userID);
            model.addAttribute("username", loggedInUser.getUserName());
            model.addAttribute("wishlists", userWishlists);
            model.addAttribute("sharedWishlists", sharedWishlists);
            model.addAttribute("showAddWish", true);

            // Viser userFrontpage efter login
            return "userFrontpage";
        } else {
            model.addAttribute("error", true);
            return "Frontpage"; // viser login igen med fejl
        }
    }

    //viser createUser html siden
    @GetMapping("/createUser")
    public String getCreateUser(Model model) {
        model.addAttribute("user", new User());

        model.addAttribute("pageTitle", "Create an account");
        model.addAttribute("showAddWish", true);
        model.addAttribute("showBack", true);

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
    public String showWish(@PathVariable int wishID, HttpSession session, Model model) {
        Integer currentUserID = (Integer) session.getAttribute("userID");

        if (currentUserID == null) {
            return "redirect:/";
        }

        Wish wish = service.findWishByID(wishID);
        Wishlist wishlist = service.findWishlistByID(wish.getWishlistID());

        boolean isOwner = wishlist.getUserID() == currentUserID;

        model.addAttribute("wish", wish);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("userID", currentUserID);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("pageTitle", "Wish");
        model.addAttribute("showAddWish", true);
        model.addAttribute("showBack", true);
        model.addAttribute("backLink", "/wishlist/" + wishlist.getWishlistID());


        if (!isOwner) {
            User owner = service.findUserByID(wishlist.getUserID());
            model.addAttribute("ownerName", owner.getUserName());
        }

        return "wish";
    }


    //Viser siden der viser den enkelte ønskeliste.
    @GetMapping("/wishlist/{wishlistID}")
    public String showWishlist(@PathVariable int wishlistID, HttpSession session, Model model) {
        Integer currentUserID = (Integer) session.getAttribute("userID");

        if (currentUserID == null) {
            return "redirect:/";
        }

        Wishlist wishlist = service.findWishlistByID(wishlistID);
        List<Wish> wishes = service.showWishlist(wishlistID);

        boolean isOwner = wishlist.getUserID() == currentUserID;

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("wishes", wishes);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("userID", currentUserID);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("showBack", true);
        model.addAttribute("backLink", "/userFrontpage");
        model.addAttribute("showAddWish", true);


        return "wishlist";
    }


    //Viser siden med formen for at tilføje et ønske til en ønskeliste.
    @GetMapping("/addWishFromUserFrontpage")
    public String addWishFromUserFrontpage(HttpSession session, Model model) {
        Integer currentUserID = (Integer) session.getAttribute("userID");

        if (currentUserID == null) {
            return "redirect:/";
        }

        Wish wish = new Wish();
        List<Wishlist> wishlists = service.showWishlists(currentUserID);


        model.addAttribute("wish", wish);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("showBack", true);
        model.addAttribute("backLink", "/userFrontpage");
        model.addAttribute("userID", currentUserID);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("showAddWish", true);

        return "addWish_fromUserFrontpage";
    }

    @GetMapping("/addWishFromWishlist/{wishlistID}")
    public String addWishFromWishlist(@PathVariable Integer wishlistID, Model model, HttpSession session) {
        Integer currentUserID = (Integer) session.getAttribute("userID");

        if (currentUserID == null) {
            return "redirect:/";
        }

        Wish wish = new Wish();
        wish.setWishlistID(wishlistID);
        model.addAttribute("wish", wish);
        model.addAttribute("showBack", true);
        model.addAttribute("userID", currentUserID);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("showAddWish", true);
        model.addAttribute("backLink", "/userFrontpage");
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

        if (userID == null) {
            return "redirect:/";
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUserID(userID);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("userID", userID);
        model.addAttribute("username", session.getAttribute("username"));
        return "addWishlist";
    }

    @PostMapping("/saveWishlist")
    public String saveWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            return "redirect:/";
        }

        service.addWishlist(wishlist.getWishlistName(), userID);
        return "redirect:/userFrontpage";

    }


    @GetMapping("edit/{wishID}")
    public String editWish(@PathVariable int wishID, Model model, HttpSession session) {
        Integer currentUserID = (Integer) session.getAttribute("userID");

        if (currentUserID == null) {
            return "redirect:/";
        }

        Wish wish = service.findWishByID(wishID);

        model.addAttribute("wish", wish);

        model.addAttribute("pageTitle", "Edit wish");
        model.addAttribute("showAddWish", true);
        model.addAttribute("showBack", true);
        model.addAttribute("backLink", "/userFrontpage");
        model.addAttribute("userID", currentUserID);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("showAddWish", true);
        model.addAttribute("backLink", "/userFrontpage");

        return "editWish";
    }

    @PostMapping("/update")
    public String updateWish(@ModelAttribute Wish wish, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
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

        if (userID == null) {
            return "redirect:/";
        }

        Wishlist wishlist = service.findWishlistByID(wishlistID);
        service.deleteWishlistByID(wishlistID);

        return "redirect:/userFrontpage";
    }

    @GetMapping("/shareWishlist/{wishlistID}")
    public String shareWishlist(@PathVariable int wishlistID, HttpSession session, Model model) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            return "redirect:/";
        }

        model.addAttribute("pageTitle", "Share Wishlist");
        model.addAttribute("showAddWish", true);

        List<Wishlist> wishlists = service.showWishlists(userID);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("wishlistID", wishlistID);
        model.addAttribute("userID",userID);
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("wishlist", service.findWishlistByID(wishlistID));
        model.addAttribute("showBack", true);
        model.addAttribute("backLink", "/userFrontpage");
        model.addAttribute("showAddWish", true);

        return "share";
    }

    @PostMapping("/saveShare/{id}")
    public String saveShare(@PathVariable("id") int wishlistID,
                            @RequestParam("targetUserID") int targetUserID,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        Integer currentUserID = (Integer) session.getAttribute("userID");

        if (currentUserID == null) {
            return "redirect:/";
        }

        if (currentUserID == targetUserID) {
            redirectAttributes.addFlashAttribute("errorMessage", "You can't share a wishlist with yourself.");
            return "redirect:/shareWishlist/" + wishlistID;
        }

        // Tjek om ønskelisten allerede er delt med brugeren
        if (service.isWishlistAlreadyShared(wishlistID, targetUserID)) {
            redirectAttributes.addFlashAttribute("errorMessage", "This wishlist is already shared with that user.");
            return "redirect:/shareWishlist/" + wishlistID;
        }

        // Ellers del ønskelisten
        service.shareWishlist(wishlistID, targetUserID);
        redirectAttributes.addFlashAttribute("successMessage", "Wishlist shared successfully!");
        return "redirect:/userFrontpage";
    }

    @PostMapping("/unshareWishlist/{wishlistID}")
    public String unshareWishlist(@PathVariable int wishlistID, HttpSession session) {
        Integer userID = (Integer) session.getAttribute("userID");

        if (userID == null) {
            return "redirect:/";
        }

        service.unshareWishlist(wishlistID, userID);

        return "redirect:/userFrontpage";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Sletter sessionen
        return "redirect:/";  // Sender brugeren tilbage til forsiden (login)
    }


}