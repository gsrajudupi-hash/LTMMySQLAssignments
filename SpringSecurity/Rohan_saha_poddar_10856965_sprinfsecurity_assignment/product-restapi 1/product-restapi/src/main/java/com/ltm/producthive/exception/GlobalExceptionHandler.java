package com.ltm.producthive.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Author  : 10856807
 * Date    : 08-10-2026
 * Time    : 12:06
 * Project : product-restapi
 */

/*
 * Global exception Handler class to manage Custom Exceptions.
 * This approach allows you to remove the exception handling
 * logic from your controller method.
 *
 * The @ControllerAdvice annotation makes this class a global exception handler.
 * The @ExceptionHandler annotation specifies that the handleResourceNotFoundException method
 * should be invoked when a ResourceNotFoundException is thrown.
 * The method returns a ResponseEntity with an appropriate status code (404 Not Found).
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error("Resource not found: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


}
