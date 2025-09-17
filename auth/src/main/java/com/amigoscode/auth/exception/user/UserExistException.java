package com.amigoscode.auth.exception.user;

public class UserExistException extends RuntimeException {
    public UserExistException(String email) {
        super("User with email: " + email + " is already exist.");
    }
}
