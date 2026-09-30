package com.tollbooth.exception;

    /**
     * Custom unchecked exception used when invalid truck
     * information is provided.
     */
    public class InvalidTruckDataException extends RuntimeException {

        public InvalidTruckDataException(String message) {
            super(message);
        }
    }

