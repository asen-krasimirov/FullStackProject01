package com.example.crypto_demo.service;

import com.example.crypto_demo.dao.UserDao;
import com.example.crypto_demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public BigDecimal getUserBalance(int userId) {
        return userDao.getBalance(userId);
    }

    public void resetBalance(int userId) {
        userDao.updateBalance(userId, User.INITIAL_BALANCE);
    }
}
