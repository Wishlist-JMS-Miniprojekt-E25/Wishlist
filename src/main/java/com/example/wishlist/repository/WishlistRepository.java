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
        wish.setName(rs.getString("name"));
        wish.setDescription(rs.getString("description"));
        wish.setLink(rs.getString("link"));
        wish.setPrice(rs.getInt("price"));
        wish.setReserved(rs.getBoolean("isReserved"));
        wish.setWishID(rs.getInt("wishID"));
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

    public List<Wish> showWishlist() {

        String sql = """
        SELECT
        w.wishName,
        w.price,
        w.isReserved
        FROM wish w
        """;

        return jdbcTemplate.query(sql,wishRowMapper);
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