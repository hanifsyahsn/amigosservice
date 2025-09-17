package com.amigoscode.common.exception.jwt;

public class JwtSignatureException extends RuntimeException {
    public JwtSignatureException(String message) {
        super("Invalid JWT signature: " + message);
    }
}
