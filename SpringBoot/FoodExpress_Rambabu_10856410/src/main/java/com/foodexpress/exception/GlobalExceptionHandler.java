package com.foodexpress.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
//# Global exception handler for the application
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<?> notFound(Exception e) {
        return body(HttpStatus.NOT_FOUND, e.getMessage());
    }
// Handler for bad request exceptions (400)
    @ExceptionHandler({ IllegalArgumentException.class, MethodArgumentNotValidException.class })
    ResponseEntity<?> bad(Exception e) {
        return body(HttpStatus.BAD_REQUEST, e.getMessage());
    }
// Handler for all other exceptions (500)
    @ExceptionHandler(Exception.class)
    ResponseEntity<?> other(Exception e) {
        return body(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
// Helper method to create the response body for exceptions
    private ResponseEntity<?> body(HttpStatus s, String m) {
        return ResponseEntity.status(s).body(Map.of("timestamp", Instant.now(), "status", s.value(), "message", m));
    }
}
