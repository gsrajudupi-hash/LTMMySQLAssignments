package com.example.Fooddelivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex){ return build(HttpStatus.NOT_FOUND,ex.getMessage(),null); }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> business(BusinessException ex){ return build(HttpStatus.BAD_REQUEST,ex.getMessage(),null); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex){
        Map<String,String> errors=new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e->errors.put(e.getField(),e.getDefaultMessage()));
        return build(HttpStatus.BAD_REQUEST,"Validation failed",errors);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> general(Exception ex){ return build(HttpStatus.INTERNAL_SERVER_ERROR,"Unexpected server error",null); }
    private ResponseEntity<ErrorResponse> build(HttpStatus s,String m,Map<String,String> v){ return ResponseEntity.status(s).body(new ErrorResponse(LocalDateTime.now(),s.value(),s.getReasonPhrase(),m,v)); }
}

