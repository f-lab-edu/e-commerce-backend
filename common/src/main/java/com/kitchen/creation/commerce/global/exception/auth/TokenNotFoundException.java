package com.kitchen.creation.commerce.global.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends RuntimeException {
    @Getter
    private final int httpStatusCode = HttpStatus.UNAUTHORIZED.value();

    public TokenNotFoundException(String message) {
        super(message);
    }
}
