package com.example.crypto_demo.exception;

public class NotSupportedCurrencyException extends RuntimeException {
    public NotSupportedCurrencyException() {
        super("Currency not supported.");
    }

    public NotSupportedCurrencyException(String message) {
        super(message);
    }

    public NotSupportedCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

}