package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> {
        Wish wish = new Wish();
        wish.setWishID(rs.getInt("wishID"));
        wish.setWishlistID(rs.getInt("wishlistID"));
        wish.setName(rs.getString("wishName"));
        wish.setDescription(rs.getString("description"));
        wish.setLink(rs.getString("link"));
        wish.setPrice(rs.getInt("price"));
        wish.setIsReserved(rs.getBoolean("isReserved"));
        return wish;
    };

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
      Wishlist wishlist = new Wishlist();
      wishlist.setName(rs.getString("wishlistName"));
      wishlist.setWishlistID(rs.getInt("wishlistID"));
      return wishlist;
    };

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> showWishlist(int wishlistID) {
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

    public Wishlist findWishlistByID(int wishlistID) {
        String sql = """
                SELECT
                wl.wishlistID
                FROM wishlist wl
                WHERE wishlistID = ?
                """;

        return jdbcTemplate.queryForObject(sql, wishlistRowMapper,wishlistID);
    }
}