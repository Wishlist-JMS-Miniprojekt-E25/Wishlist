package com.example.wishlist;

import com.example.wishlist.controller.WishlistController;
import com.example.wishlist.model.Wish;
import com.example.wishlist.service.WishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(WishlistController.class)
class WishlistApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WishlistService service;

    @Test
    void getFrontpage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("Frontpage"));
    }
    @Test
    void deleteWishlist() throws Exception {
        mockMvc.perform(post("/deleteWishlist/{wishlistID}", 5)
                        .sessionAttr("userID", 3))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/userFrontpage"));

        verify(service, times(1)).deleteWishlistByID(5);
    }

    @Test
    void editWish() throws Exception{
        Wish wish = new Wish(1, 1, "Wish test", "description test", "test.dk", 20);
        when(service.findWishByID(1)).thenReturn(wish);

        mockMvc.perform(get("/edit/{wishID}", 1)
                .sessionAttr("userID", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("editWish"));
    }

}
