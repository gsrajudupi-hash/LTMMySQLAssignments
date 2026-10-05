package exception;

/**
 * Custom exception for invalid truck information.
 */
public class InvalidTruckData extends Exception {

    public InvalidTruckData(String message) {
        super(message);
    }
}