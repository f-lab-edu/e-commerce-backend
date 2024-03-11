package com.kitchen.creation.commerce.auth;

import com.kitchen.creation.commerce.auth.dto.TokenDto;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class Token {
    @Id
    @Getter
    private final String id;

    @Getter
    private final String refreshToken;

    private String accessToken;

    public Token(
            @NonNull String id,
            @NonNull String accessToken,
            @NonNull String refreshToken
    ) {
        validateId(id);
        validateTokens(accessToken, refreshToken);

        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenDto toTokenDto() {
        return new TokenDto(
                this.refreshToken,
                this.accessToken
        );
    }

    public void refresh(String accessToken) {
        this.accessToken = accessToken;
    }

    private void validateTokens(String accessToken, String refreshToken) {
        if (accessToken.isBlank() || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Access token is Invalid!");
        }

        if (refreshToken.isBlank() || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is Invalid!");
        }
    }

    private void validateId(String id) {
        if (id.isBlank() || id.isEmpty()) {
            throw new IllegalArgumentException("ID is Invalid!");
        }
    }
}
