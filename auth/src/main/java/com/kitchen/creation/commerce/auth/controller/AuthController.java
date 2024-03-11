package com.kitchen.creation.commerce.auth.controller;

import com.kitchen.creation.commerce.auth.service.TokenService;
import com.kitchen.creation.commerce.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/v1/login")
    public SuccessResponse<?> logInAsAdmin(@RequestParam @Valid String userId) {
        return new SuccessResponse<>(
                HttpStatus.OK.value(),
                "Success",
                tokenService.logInAsAdmin(userId)
        );
    }

    @PostMapping("v1/token/refresh")
    public SuccessResponse<?> refresh(@RequestHeader("Authorization") final String accessToken) {
        return new SuccessResponse<>(
                HttpStatus.OK.value(),
                "Success",
                tokenService.refreshAccessToken(accessToken)
        );
    }
}
