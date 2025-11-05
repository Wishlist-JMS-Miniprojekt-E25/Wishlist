package com.example.wishlist;

import com.example.wishlist.model.User;
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
    void insertAndReadBack() {
        User saved = repository.addUser("Test User", "1234");
        saved.setUserID(1);
        User found = repository.findUserByID(saved.getUserID());

        assertThat(found).isNotNull();
        assertThat(found.getUserName()).isEqualTo("Test User");
        assertThat(found.getPassword()).isEqualTo("1234");
    }
}
