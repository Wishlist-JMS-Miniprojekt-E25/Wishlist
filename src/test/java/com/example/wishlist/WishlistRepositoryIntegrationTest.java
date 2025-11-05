package com.example.wishlist;

import com.example.wishlist.model.User;
import com.example.wishlist.model.Wishlist;
import com.example.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class WishlistRepositoryIntegrationTest {
    @Autowired
    private WishlistRepository repository;

    @Test
    void testReadWishlist() {
        List<Wishlist> all = repository.showWishlists(1);

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);
        assertThat(all.get(0).getWishlistName()).isEqualTo("Test wishlist");
    }

    @Test
    void insertAndReadBack() {
        Wishlist wishlist = repository.addWishlist("Test wishlist", 1);
        Wishlist foundWishlist = repository.findWishlistByID(wishlist.getWishlistID());

        assertThat(foundWishlist).isNotNull();
        assertThat(foundWishlist.getWishlistName()).isEqualTo("Test wishlist");
        assertThat(foundWishlist.getUserID()).isEqualTo(1);
    }
}
