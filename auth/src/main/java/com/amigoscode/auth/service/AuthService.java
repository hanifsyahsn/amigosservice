package com.amigoscode.auth.service;

import com.amigoscode.auth.request.LoginRequest;
import com.amigoscode.auth.request.RegistrationRequest;
import com.amigoscode.auth.response.LoginResponse;

public interface AuthService {
    void register(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest loginRequest);
}
