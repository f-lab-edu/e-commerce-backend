package com.kichen.creation.commerce.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SuccessResponse<T> {
    private final int statusCode;
    private final T data;
}
