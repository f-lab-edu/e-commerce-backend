package com.kichen.creation.commerce.order.exception;

public class OrderFailureException extends RuntimeException {
    public OrderFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
