package com.amigoscode.common.exception.jwt;

public class JwtUnsupportedException extends RuntimeException {
    public JwtUnsupportedException(String message) {
        super("JWT token is unsupported: " + message);
    }
}
