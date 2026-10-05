package com.toll;
/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 11:42:14 pm
 * project  : TollBoothManagementSystem
 */

class InvalidTruckException extends Exception {
	 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidTruckException(String message) {
        super(message);
    }
}
