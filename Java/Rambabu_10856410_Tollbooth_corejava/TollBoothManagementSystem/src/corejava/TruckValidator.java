package corejava;

public class TruckValidator {

    public static void validate(Truck truck)
            throws InvalidTruckDataException {

        if (truck.getNumberOfAxles() <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than zero.");
        }

        if (truck.getTotalWeight() <= 0) {
            throw new InvalidTruckDataException(
                    "Weight must be greater than zero.");
        }
    }
}

