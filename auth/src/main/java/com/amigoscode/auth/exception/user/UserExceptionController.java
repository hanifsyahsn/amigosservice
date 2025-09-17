package com.amigoscode.auth.exception.user;

import com.amigoscode.auth.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class UserExceptionController {
    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity exception(UserExistException e) {
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).zonedDateTime(ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity exception(UserNotFoundException e) {
        return new ResponseEntity<>(ErrorResponse.builder().message(e.getMessage()).httpStatus(HttpStatus.BAD_REQUEST).zonedDateTime(ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
    }
}
