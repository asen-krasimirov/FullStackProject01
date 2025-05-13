package com.example.crypto_demo.model;

import java.math.BigDecimal;

public class User {
    public static final BigDecimal INITIAL_BALANCE = new BigDecimal("10000");

    private int id;
    private String username;
    private BigDecimal balance;
}
