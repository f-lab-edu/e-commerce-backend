package com.kichen.creation.commerce.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int statusCode;

    private final String message;

    private final LocalDateTime requestDateTime;
}
