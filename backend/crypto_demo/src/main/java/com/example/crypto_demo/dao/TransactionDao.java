package com.example.crypto_demo.dao;

import com.example.crypto_demo.exception.InvalidTransactionCreationException;
import com.example.crypto_demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, transaction_type, currency, amount, price, created_at, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(sql, transaction.getUserId(), transaction.getTransactionType(), transaction.getCurrency(), transaction.getAmount(), transaction.getPrice(), transaction.getTimestamp(), transaction.getTotalCost());
        } catch (DataAccessException e) {
            // System.err.println("Error creating transaction: " + e.getMessage());  // debug
            throw new InvalidTransactionCreationException("Failed to create transaction", e);
        }
    }

    public List<Transaction> getTransactionsByUserId(int userId) {
        String sql = "SELECT * FROM transactions WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, new TransactionRowMapper());
    }

    private static final class TransactionRowMapper implements RowMapper<Transaction> {
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            Transaction transaction = new Transaction();
            transaction.setId(rs.getInt("id"));
            transaction.setUserId(rs.getInt("user_id"));
            transaction.setTransactionType(rs.getString("transaction_type"));
            transaction.setCurrency(rs.getString("currency"));
            transaction.setAmount(rs.getBigDecimal("amount"));
            transaction.setPrice(rs.getBigDecimal("price"));
            transaction.setTimestamp(rs.getTimestamp("created_at").toLocalDateTime());
            transaction.setTotalCost(rs.getBigDecimal("total_cost"));
            return transaction;
        }
    }

    public void deleteTransactionsByUserId(int userId) {
        String sql = "DELETE FROM transactions WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
