package com.amigoscode.auth.exception.auth;

import com.amigoscode.auth.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class AuthExceptionController {
    @ExceptionHandler(value = FailedRegisterUserException.class)
    public ResponseEntity exception(FailedRegisterUserException e) {
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).zonedDateTime(ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity exception(LoginException e) {
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).zonedDateTime(ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
