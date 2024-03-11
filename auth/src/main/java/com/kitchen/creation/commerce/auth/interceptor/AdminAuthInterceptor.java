package com.kitchen.creation.commerce.auth.interceptor;

import com.kitchen.creation.commerce.auth.util.JwtUtil;
import com.kitchen.creation.commerce.global.exception.auth.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER = "Authorization";
    private final JwtUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(AUTH_HEADER);
        validateToken(token);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void validateToken(@NonNull String accessToken) {
        if (!jwtUtil.verifyToken(accessToken)) {
            throw new InvalidTokenException("Access token is invalid!");
        }

        if (!jwtUtil.hasAdminRole(accessToken)) {
            throw new InvalidTokenException("Access token does not have admin access!");
        }
    }
}
