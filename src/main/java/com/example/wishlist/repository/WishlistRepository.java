package com.example.wishlist.repository;

import com.example.wishlist.model.User;
import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> {
        Wish wish = new Wish();
        wish.setWishID(rs.getInt("wishID"));
        wish.setWishlistID(rs.getInt("wishlistID"));
        wish.setWishName(rs.getString("wishName"));
        wish.setDescription(rs.getString("description"));
        wish.setLink(rs.getString("link"));
        wish.setPrice(rs.getInt("price"));
        wish.setIsReserved(rs.getBoolean("isReserved"));
        wish.setReserved(rs.getBoolean("isReserved"));
        wish.setWishID(rs.getInt("wishID"));
        return wish;
    };

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistName(rs.getString("wishlistName"));
        wishlist.setWishlistID(rs.getInt("wishlistID"));
        wishlist.setUserID(rs.getInt("userID"));
        return wishlist;
    };

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
      User user = new User();
      user.setUserID(rs.getInt("userID"));
      user.setUserName(rs.getString("userName"));
      user.setPassword(rs.getString("password"));
      return user;
      Wishlist wishlist = new Wishlist();
      wishlist.setName(rs.getString("wishlistName"));
      wishlist.setWishlistID(rs.getInt("wishlistID"));
      return wishlist;
    };

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
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


    public List<Wishlist> showWishlists(Integer userID) {
        String sql = """
                SELECT
                wl.wishlistID,
                wl.wishlistName,
                wl.userID
                FROM wishlist wl
                WHERE wl.userID = ?
                """;

        return jdbcTemplate.query(sql, wishlistRowMapper, userID);
    }

    public List<Wish> showWishlist(Integer wishlistID) {
        String sql = """
                SELECT
                w.wishID,
                w.wishlistID,
                w.wishName,
                w.description,
                w.link,
                w.price,
                w.isReserved
                FROM wish w
                WHERE w.wishlistID = ?
                """;

        return jdbcTemplate.query(sql, wishRowMapper, wishlistID);
    }

    public Wishlist findWishlistByID(Integer wishlistID) {
        String sql = """
                SELECT
                wl.wishlistID
                FROM wishlist wl
                WHERE wishlistID = ?
                """;

        return jdbcTemplate.queryForObject(sql, wishlistRowMapper, wishlistID);
    }

    public User findUserByID(Integer userID) {
        String sql = """
                SELECT
                u.userID,
                u.userName,
                u.password
                FROM user u
                WHERE userID = ?
                """;

        return jdbcTemplate.queryForObject(sql, userRowMapper,userID);
    }

    public Wish addWish(Integer wishlistID, String wishName, String description, String link, int price) {
        String sql = "INSERT INTO wish (wishlistID, wishName, description, link, price) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, wishlistID);
            ps.setString(2, wishName);
            ps.setString(3, description);
            ps.setString(4, link);
            ps.setInt(5, price);
            return ps;
        }, keyHolder);

        int wishID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if (wishID != -1) {
            return new Wish(wishID, wishlistID, wishName, description, link, price);
        } else {
            throw new RuntimeException("Could not add wish");
        }
    }

    public Wishlist addWishlist(String wishlistName, Integer userID) {
        String sql = "INSERT INTO wishlist (wishlistName, userID) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, wishlistName);
            ps.setInt(2, userID);
            return ps;
        }, keyHolder);

        int wishlistID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

        if (wishlistID != -1) {
            return new Wishlist(wishlistID, wishlistName, userID);
        } else {
            throw new RuntimeException("Could not add wishlist");
        }
    public List<Wishlist> showAllWishlists(int userID){
       String sql = """
       SELECT
       wl.wishlistID,
       wl.wishlistName
       FROM wishlist wl
       WHERE wl.user_id = ?
       """;

        return jdbcTemplate.query(sql, wishlistRowMapper, userID);
    }
}