package com.example.rentalapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ToolTypeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ToolTypeNotFoundException(String message) {
        super(message);
    }
}