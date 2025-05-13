package com.example.crypto_demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private int userId;
    private String transactionType;
    private String currency;
    private BigDecimal amount;
    private BigDecimal price;
    private LocalDateTime timestamp;
    private BigDecimal totalCost;

    public Transaction() {
    }

    public Transaction(int userId, String transactionType, String currency, BigDecimal amount, BigDecimal price, LocalDateTime timestamp, BigDecimal totalCost) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.currency = currency;
        this.amount = amount;
        this.price = price;
        this.timestamp = timestamp;
        this.totalCost = totalCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }
}
