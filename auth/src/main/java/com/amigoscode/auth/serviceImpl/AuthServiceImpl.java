package com.amigoscode.auth.serviceImpl;

import com.amigoscode.auth.exception.auth.FailedRegisterUserException;
import com.amigoscode.auth.exception.auth.LoginException;
import com.amigoscode.auth.exception.user.UserExistException;
import com.amigoscode.auth.exception.user.UserNotFoundException;
import com.amigoscode.auth.model.User;
import com.amigoscode.auth.repository.UserRepository;
import com.amigoscode.auth.request.LoginRequest;
import com.amigoscode.auth.request.RegistrationRequest;
import com.amigoscode.auth.response.LoginResponse;
import com.amigoscode.auth.service.AuthService;
import com.amigoscode.common.request.GenerateTokenRequest;
import com.amigoscode.common.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @Override
    public void register(RegistrationRequest registrationRequest) {
        try {
            Optional<User> existedUser = userRepository.findUserByEmail(registrationRequest.getEmail());
            if (existedUser.isPresent()) throw new UserExistException(registrationRequest.getEmail());
            User user = User.builder().name(registrationRequest.getName()).username(registrationRequest.getUsername()).email(registrationRequest.getEmail()).password(registrationRequest.getPassword()).build();
            userRepository.save(user);
        } catch (Exception e) {
            throw new FailedRegisterUserException(e.getMessage());
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            User existedUser = userRepository.findUserByUsername(loginRequest.getUsername()).orElseThrow(() -> new UserNotFoundException(loginRequest.getUsername()));
            String token = jwtUtil.generateToken(GenerateTokenRequest.builder().email(existedUser.getEmail()).username(existedUser.getUsername()).id(existedUser.getId()).build());
            LoginResponse loginResponse = LoginResponse.builder().name(existedUser.getName()).email(existedUser.getEmail()).username(existedUser.getUsername()).token(token).build();
            return loginResponse;
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        }
    }
}
