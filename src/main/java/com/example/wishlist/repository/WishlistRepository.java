package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> {
      Wishlist wishlist = new Wishlist();
      wishlist.setName(rs.getString("wishlistName"));
      return wishlist;
    };

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> showWishList() {

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
                """;

        return jdbcTemplate.query(sql,);
    }
}