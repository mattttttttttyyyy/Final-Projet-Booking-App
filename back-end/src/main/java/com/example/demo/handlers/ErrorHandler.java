package com.example.demo.handlers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class})
    String handleConstraint(final ConstraintViolationException e) {
        final StringJoiner joiner = new StringJoiner("\n", "", "");
        joiner.add("Error: ");
        e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).forEach(joiner::add);
        return joiner.toString();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    String IllegalArgumentHandler(final IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    String handleNotReadable(final HttpMessageNotReadableException e) {
        return "There was a problem with your input. Please try again.";
    }
}
