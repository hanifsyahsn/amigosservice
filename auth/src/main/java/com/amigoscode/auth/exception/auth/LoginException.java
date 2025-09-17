package com.amigoscode.auth.exception.auth;

import com.amigoscode.auth.request.LoginRequest;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super("Failed to login. " + message);
    }
}
