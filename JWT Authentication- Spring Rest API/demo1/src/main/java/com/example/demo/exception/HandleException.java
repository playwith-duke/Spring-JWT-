package com.example.demo.exception;

import com.example.demo.exception.exceptions.InvalidUserCredentials;
import com.example.demo.exception.exceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class HandleException {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException exception, WebRequest request){
        ExceptionMessage exceptionMessage=new ExceptionMessage(new Date(),exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidUserCredentials.class)
    public ResponseEntity<?> handleInvalidUserCredentials(InvalidUserCredentials exception, WebRequest request){
        ExceptionMessage exceptionMessage=new ExceptionMessage(new Date(),exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception, WebRequest request){
        ExceptionMessage exceptionMessage=new ExceptionMessage(new Date(),exception.getMessage(),request.getDescription(false));
        return new ResponseEntity(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
