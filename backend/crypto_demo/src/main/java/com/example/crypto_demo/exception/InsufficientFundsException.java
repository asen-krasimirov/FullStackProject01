package com.example.crypto_demo.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds.");
    }

    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

}