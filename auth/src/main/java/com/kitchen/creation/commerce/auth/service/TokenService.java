package com.kitchen.creation.commerce.auth.service;

import com.kitchen.creation.commerce.auth.AuthRole;
import com.kitchen.creation.commerce.auth.Token;
import com.kitchen.creation.commerce.auth.dto.TokenDto;
import com.kitchen.creation.commerce.auth.repository.TokenRepository;
import com.kitchen.creation.commerce.auth.util.JwtUtil;
import com.kitchen.creation.commerce.global.exception.auth.InvalidTokenException;
import com.kitchen.creation.commerce.global.exception.auth.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;

    private final TokenRepository tokenRepository;

    @Transactional
    public TokenDto logInAsAdmin(String userId) {
        String accessToken = jwtUtil.generateAccessToken(userId, AuthRole.ADMIN);
        String refreshToken = jwtUtil.generateRefreshToken(userId, AuthRole.ADMIN);

        Token token = tokenRepository.save(
                new Token(
                        userId,
                        accessToken,
                        refreshToken
                )
        );

        return token.toTokenDto();
    }

    public String refreshAccessToken(String accessToken) {

        String uid = jwtUtil.getUid(accessToken);
        Token token = tokenRepository.findById(uid).orElseThrow(
                () -> new TokenNotFoundException("Could not find token!")
        );

        if (jwtUtil.verifyToken(token.getRefreshToken())) {
            String newAccessToken = jwtUtil.generateAccessToken(token.getId(), jwtUtil.getRole(token.getRefreshToken()));
            token.refresh(newAccessToken);
            return newAccessToken;

        } else {
            throw new InvalidTokenException("Refresh token is invalid!");
        }
    }
}