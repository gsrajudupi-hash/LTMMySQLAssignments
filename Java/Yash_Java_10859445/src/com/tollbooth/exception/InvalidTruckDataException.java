package com.tollbooth.exception;

/**
 * Custom checked exception thrown when invalid
 * truck information is provided.
 */
public class InvalidTruckDataException extends Exception {

    public InvalidTruckDataException(String message) {
        super(message);
    }
}