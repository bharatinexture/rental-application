package com.example.rentalapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ToolNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ToolNotFoundException(String message) {
        super(message);
    }

}
