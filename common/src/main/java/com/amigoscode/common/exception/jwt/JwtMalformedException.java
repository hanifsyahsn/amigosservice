package com.amigoscode.common.exception.jwt;

public class JwtMalformedException extends RuntimeException {
    public JwtMalformedException(String message) {
        super("Invalid JWT token: " + message);
    }
}
