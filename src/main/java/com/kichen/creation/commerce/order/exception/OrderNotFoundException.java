package com.kichen.creation.commerce.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
