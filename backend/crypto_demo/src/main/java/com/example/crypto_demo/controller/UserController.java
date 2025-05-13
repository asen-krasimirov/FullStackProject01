package com.example.crypto_demo.controller;

import com.example.crypto_demo.model.User;
import com.example.crypto_demo.service.TransactionService;
import com.example.crypto_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000") // for React frontend
public class UserController {

    @Autowired
    private UserService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getBalance(@RequestParam int userId) {
        return ResponseEntity.ok(accountService.getUserBalance(userId));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestParam int userId) {
        accountService.resetBalance(userId);
        transactionService.resetUserTransactions(userId);
        return ResponseEntity.ok("Account reset to $" + User.INITIAL_BALANCE + ".");
    }
}
