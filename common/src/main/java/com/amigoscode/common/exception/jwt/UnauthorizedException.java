package com.amigoscode.common.exception.jwt;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException() {
        super("Unauthorized request");
    }
}
