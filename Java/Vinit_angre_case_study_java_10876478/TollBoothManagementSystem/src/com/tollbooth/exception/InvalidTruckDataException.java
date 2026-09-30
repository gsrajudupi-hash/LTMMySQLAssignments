package com.tollbooth.exception;

public class InvalidTruckDataException extends Exception {
	public InvalidTruckDataException(String message) {
		super(message);
	}

	public InvalidTruckDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
