package com.truper.test.dto.response;

public record AuthResponse(
        String token,
        long expirationIn
) {
}
