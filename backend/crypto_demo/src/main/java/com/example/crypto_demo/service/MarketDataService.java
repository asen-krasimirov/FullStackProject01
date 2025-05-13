package com.example.crypto_demo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class MarketDataService extends TextWebSocketHandler {

    private WebSocketSession session;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, BigDecimal> currentPrices = new HashMap<>();
    private final List<String> top20Pairs = List.of(
        "BTC/USD", "ETH/USD", "XRP/USD", "LTC/USD", "BCH/USD", "ADA/USD", "DOT/USD", "BNB/USD",
        "LINK/USD", "XLM/USD", "USDC/USD", "DOGE/USD", "EOS/USD", "TRX/USD", "UNI/USD",
        "AAVE/USD", "XTZ/USD", "MKR/USD", "SNX/USD", "WBTC/USD"
    );

    private static final String KRAKEN_WEBSOCKET_URL = "wss://ws.kraken.com";

    @PostConstruct
    public void connect() {
        WebSocketClient client = new StandardWebSocketClient();
        try {
            session = client.execute(this, KRAKEN_WEBSOCKET_URL).get();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error connecting to Kraken WebSocket API: " + e.getMessage());
        }
    }

    private void subscribeToTicker(List<String> pairs) throws IOException {
        String subscribeMessage = String.format(
            "{ \"event\": \"subscribe\", \"subscription\": { \"name\": \"ticker\" }, \"pair\": [ %s ] }",
            String.join(", ", pairs.stream().map(s -> "\"" + s + "\"").toArray(String[]::new))
        );
        session.sendMessage(new TextMessage(subscribeMessage));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());

        if (jsonNode.isObject() && jsonNode.has("event")) {
            // String event = jsonNode.get("event").asText();    // debug
            // System.out.println("Received event: " + event);   // debug
            return;
        }

        if (jsonNode.isArray() && jsonNode.size() >= 4) {
            JsonNode data = jsonNode.get(1);
            String pair = jsonNode.get(3).asText();

            if (data != null && data.has("c") && data.get("c").isArray()) {
                String lastPrice = data.get("c").get(0).asText();
                if (lastPrice != null) {
                    BigDecimal price = new BigDecimal(lastPrice);
                    currentPrices.put(pair.split("/")[0], price);
                    // System.out.println("Price of " + pair + ": " + price);    // debug
                }
            }
        } else {
            System.out.println("Skipping non-ticker message: " + jsonNode.toString());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connected to Kraken WebSocket API");

        this.session = session;

        try {
            subscribeToTicker(top20Pairs);
        } catch (IOException e) {
            System.err.println("Failed to subscribe: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("Disconnected from Kraken WebSocket API: " + status);
        this.session = null;
    }

    @PreDestroy
    public void disconnect() throws IOException {
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    public Map<String, BigDecimal> getCurrentPrices() {
        return currentPrices;
    }

    public List<String> getSupportedPairs() {
        return this.top20Pairs;
    }
}
