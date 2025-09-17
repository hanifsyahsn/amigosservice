package com.amigoscode.common.exception.jwt;

public class JwtClaimsException extends RuntimeException{
    public JwtClaimsException(String message) {
        super("JWT claims string is empty: " + message);
    }
}
