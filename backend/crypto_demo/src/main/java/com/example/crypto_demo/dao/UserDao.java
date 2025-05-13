package com.example.crypto_demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbc;

    public BigDecimal getBalance(int userId) {
        String sql = "SELECT balance FROM users WHERE id = ?";
        return jdbc.queryForObject(sql, new Object[]{userId}, BigDecimal.class);
    }

    public void updateBalance(int userId, BigDecimal newBalance) {
        String sql = "UPDATE users SET balance = ? WHERE id = ?";
        jdbc.update(sql, newBalance, userId);
    }
}
