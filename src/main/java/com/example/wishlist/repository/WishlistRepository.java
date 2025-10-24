package com.example.wishlist.repository;

import com.example.wishlist.model.User;
import com.example.wishlist.model.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> {
        Wish wish = new Wish();
        wish.setName(rs.getString("name"));
        wish.setDescription(rs.getString("description"));
        wish.setLink(rs.getString("link"));
        wish.setPrice(rs.getInt("price"));
        wish.setReserved(rs.getBoolean("isReserved"));
        return wish;
    };

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // Tilføjer en ny bruger til databasen
    public User addUser(String userName, String password) {
        String sql = "INSERT INTO wishlist.`user` (userName, password) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userName);
            ps.setString(2, password);
            return ps;
        }, keyHolder);

        User user = new User();
        user.setUserID(keyHolder.getKey().intValue());
        user.setUserName(userName);
        user.setPassword(password);
        return user;
    }

    public boolean validateUser(String userName, String password) {
        String sql = "SELECT COUNT(*) FROM wishlist.`user` WHERE userName = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userName, password);
        return count != null && count > 0;
    }

}
