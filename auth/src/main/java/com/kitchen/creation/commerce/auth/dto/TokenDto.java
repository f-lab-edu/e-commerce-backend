package com.kitchen.creation.commerce.auth.dto;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class TokenDto {
    private final String accessToken;
    private final String refreshToken;

    public TokenDto(
            @NonNull String accessToken,
            @NonNull String refreshToken
    ) {
        validateTokens(accessToken, refreshToken);

        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private void validateTokens(String accessToken, String refreshToken) {
        if (accessToken.isBlank() || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token is Invalid!");
        }

        if (refreshToken.isBlank() || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is Invalid!");
        }
    }
}
