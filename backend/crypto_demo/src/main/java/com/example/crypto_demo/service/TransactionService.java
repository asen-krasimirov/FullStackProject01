package com.example.crypto_demo.service;

import com.example.crypto_demo.dao.TransactionDao;
import com.example.crypto_demo.dao.UserDao;
import com.example.crypto_demo.exception.InsufficientFundsException;
import com.example.crypto_demo.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionDao transactionDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Transactional
    public void buyCrypto(int userId, String currency, BigDecimal amount, BigDecimal price) {
        BigDecimal userBalance = userService.getUserBalance(userId);
        BigDecimal totalCost = amount.multiply(price);

        if (userBalance.compareTo(totalCost) < 0) {
            throw new InsufficientFundsException();
        }

        BigDecimal newBalance = userBalance.subtract(totalCost);
        userDao.updateBalance(userId, newBalance);

        Transaction transaction = new Transaction(userId, "buy", currency, amount, price, LocalDateTime.now(), totalCost);
        transactionDao.createTransaction(transaction);
    }

    @Transactional
    public void sellCrypto(int userId, String currency, BigDecimal amount, BigDecimal price) {
        BigDecimal userBalance = userService.getUserBalance(userId);
        BigDecimal totalRevenue = amount.multiply(price);

        BigDecimal newBalance = userBalance.add(totalRevenue);
        userDao.updateBalance(userId, newBalance);

        Transaction transaction = new Transaction(userId, "sell", currency, amount, price, LocalDateTime.now(), totalRevenue);
        transactionDao.createTransaction(transaction);
    }

    public List<Transaction> getTransactions(int userId) {
        return transactionDao.getTransactionsByUserId(userId);
    }

    public Map<String, BigDecimal> calculateReturns(int userId, Map<String, BigDecimal> currentPrices) {
        List<Transaction> transactions = transactionDao.getTransactionsByUserId(userId);

        Map<String, BigDecimal> totalBought = new HashMap<>();
        Map<String, BigDecimal> totalSpent = new HashMap<>();
        Map<String, BigDecimal> totalSold = new HashMap<>();

        for (Transaction tx : transactions) {
            String currency = tx.getCurrency();
            BigDecimal amount = tx.getAmount();
            BigDecimal totalCost = tx.getTotalCost();

            if (tx.getTransactionType().equalsIgnoreCase("buy")) {
                totalBought.merge(currency, amount, BigDecimal::add);
                totalSpent.merge(currency, totalCost, BigDecimal::add);
            } else if (tx.getTransactionType().equalsIgnoreCase("sell")) {
                totalSold.merge(currency, amount, BigDecimal::add);
            }
        }

        System.out.println(totalSold);

        Map<String, BigDecimal> returns = new HashMap<>();

        for (String currency : totalBought.keySet()) {
            BigDecimal bought = totalBought.getOrDefault(currency, BigDecimal.ZERO);
            BigDecimal sold = totalSold.getOrDefault(currency, BigDecimal.ZERO);
            BigDecimal netHolding = bought.subtract(sold);
            BigDecimal currentPrice = currentPrices.get(currency);

            if (!currentPrices.containsKey(currency)) {
                continue;
            }

            if (netHolding.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal currentValue = currentPrice.multiply(netHolding);
                BigDecimal totalInvested = totalSpent.getOrDefault(currency, BigDecimal.ZERO);

                BigDecimal profitOrLoss = currentValue.subtract(totalInvested);
                returns.put(currency, profitOrLoss);
            }
        }

        return returns;
    }

    @Transactional
    public void resetUserTransactions(int userId) {
        transactionDao.deleteTransactionsByUserId(userId);
    }
}
