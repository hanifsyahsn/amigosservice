package com.amigoscode.auth.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User is not found with username: " + username + ".");
    }
}
