package com.portfolioapi.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(e.getMessage()))
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(e.getMessage()))
                .build();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ErrorResponse handleUnauthorizedException(UnauthorizedException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(Collections.singletonList(e.getMessage()))
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ArrayList<String> errors = new ArrayList<>();

        e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorResponse.builder()
                .message("Validation error happened")
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
