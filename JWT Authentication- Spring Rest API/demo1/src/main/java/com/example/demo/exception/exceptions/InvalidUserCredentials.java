package com.example.demo.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidUserCredentials extends Exception{
    private static final long serialVersionUID = 1L;

    public InvalidUserCredentials(String message) {
        super(message);
    }
}
