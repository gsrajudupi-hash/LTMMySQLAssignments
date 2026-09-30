package com.prathamesh.ems.exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
