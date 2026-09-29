package exception;

public class InvalidTruckDataException extends IllegalArgumentException {
    public InvalidTruckDataException(String message) {
        super(message);
    }
}
