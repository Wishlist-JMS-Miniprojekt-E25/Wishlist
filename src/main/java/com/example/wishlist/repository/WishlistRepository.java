package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
        return wish;
    };

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
      Wishlist wishlist = new Wishlist();
      wishlist.setWishlistName(rs.getString("wishlistName"));
      wishlist.setWishlistID(rs.getInt("wishlistID"));
      wishlist.setWishlistID(rs.getInt("userID"));
      return wishlist;
    };

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
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

        return jdbcTemplate.query(sql,wishlistRowMapper, userID);
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

        return jdbcTemplate.query(sql,wishRowMapper, wishlistID);
    }

    public Wishlist findWishlistByID(Integer wishlistID) {
        String sql = """
                SELECT
                wl.wishlistID
                FROM wishlist wl
                WHERE wishlistID = ?
                """;

        return jdbcTemplate.queryForObject(sql, wishlistRowMapper,wishlistID);
    }

    public Wish addWish(Integer wishlistID , String wishName, String description, String link, int price) {
            String sql = "INSERT INTO wish (wishlistID, wishName, description, link, price) VALUES (?, ?, ?, ?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, wishlistID);
                ps.setString(2, wishName);
                ps.setString(3, description);
                ps.setString(4, link);
                ps.setInt(5,price);
                return ps;
            }, keyHolder);

            int wishID = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;

            if (wishID != -1) {
                return new Wish(wishID, wishlistID, wishName, description, link, price);
            } else {
                throw new RuntimeException("Could not add wish");
            }
        }
    }