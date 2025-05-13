package com.example.crypto_demo.controller;

import com.example.crypto_demo.exception.NotSupportedCurrencyException;
import com.example.crypto_demo.model.Transaction;
import com.example.crypto_demo.service.MarketDataService;
import com.example.crypto_demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "http://localhost:3000")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MarketDataService marketDataService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyCrypto(
        @RequestParam int userId,
        @RequestParam String currency,
        @RequestParam BigDecimal amount
    ) {
        try {
            if (!marketDataService.getSupportedPairs().contains(currency + "/USD")) {
                throw new NotSupportedCurrencyException("Currency not supported.");
            }

            BigDecimal price = marketDataService.getCurrentPrices().get(currency);
            transactionService.buyCrypto(userId, currency, amount, price);

            return ResponseEntity.ok("Transaction successful.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellCrypto(
        @RequestParam int userId,
        @RequestParam String currency,
        @RequestParam BigDecimal amount
    ) {
        BigDecimal price = marketDataService.getCurrentPrices().get(currency);
        transactionService.sellCrypto(userId, currency, amount, price);
        return ResponseEntity.ok("Transaction successful.");
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getTransactions(@RequestParam int userId) {
        List<Transaction> transactions = transactionService.getTransactions(userId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/returns")
    public ResponseEntity<Map<String, BigDecimal>> getInvestmentReturns(@RequestParam int userId) {
        Map<String, BigDecimal> returns = transactionService.calculateReturns(userId, marketDataService.getCurrentPrices());
        return ResponseEntity.ok(returns);
    }
}
