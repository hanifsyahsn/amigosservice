package com.amigoscode.common.exception.jwt;

public class JwtExpiredException extends RuntimeException {
    public JwtExpiredException(String message) {
        super("JWT token is expired: " + message);
    }
}
