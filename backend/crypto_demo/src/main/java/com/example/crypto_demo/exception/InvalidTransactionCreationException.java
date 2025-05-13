package com.example.crypto_demo.exception;

public class InvalidTransactionCreationException extends RuntimeException {
    public InvalidTransactionCreationException() {
        super("Failed to create transaction.");
    }

    public InvalidTransactionCreationException(String message) {
        super(message);
    }

    public InvalidTransactionCreationException(String message, Throwable cause) {
        super(message, cause);
    }

}