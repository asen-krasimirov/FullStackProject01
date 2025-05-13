package com.example.crypto_demo.controller;

import com.example.crypto_demo.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "http://localhost:3000")
public class MarketDataController {

    @Autowired
    private MarketDataService marketDataService;

    @GetMapping("/prices")
    public ResponseEntity<Map<String, BigDecimal>> getPrices() {
        Map<String, BigDecimal> prices = marketDataService.getCurrentPrices();
        System.out.println("Returning prices: " + prices);
        return ResponseEntity.ok(prices);
    }
    @GetMapping("/supportedPairs")
    public ResponseEntity<List<String>> getSupportedPairs() {
        List<String> supportedPairs = marketDataService.getSupportedPairs();
        return ResponseEntity.ok(supportedPairs);
    }
}
