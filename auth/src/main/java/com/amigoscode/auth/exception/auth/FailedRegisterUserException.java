package com.amigoscode.auth.exception.auth;

public class FailedRegisterUserException extends RuntimeException {
    public FailedRegisterUserException(String message) {
        super("Failed to register user. " + message);
    }
}
