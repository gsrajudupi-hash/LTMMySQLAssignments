package com.foodexpress.exception;
//# Custom exception class for resource not found scenarios
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String m) {
        super(m);
    }
}
