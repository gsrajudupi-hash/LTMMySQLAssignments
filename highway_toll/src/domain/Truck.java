package domain;

import exception.InvalidTruckDataException;

public final class Truck {

    private final String id;
    private final String make;
    private final int numberOfAxles;
    private final long totalWeightInKg;

    public Truck(
            String id,
            String make,
            int numberOfAxles,
            long totalWeightInKg
    ) {
        this.id = validateText(id, "Truck ID");
        this.make = validateText(make, "Truck make");
        this.numberOfAxles = validateAxles(numberOfAxles);
        this.totalWeightInKg = validateWeight(totalWeightInKg);
    }

    private static String validateText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new InvalidTruckDataException(
                    fieldName + " cannot be null or blank."
            );
        }

        return value.trim();
    }

    private static int validateAxles(int numberOfAxles) {
        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than zero."
            );
        }

        return numberOfAxles;
    }

    private static long validateWeight(long totalWeightInKg) {
        if (totalWeightInKg <= 0) {
            throw new InvalidTruckDataException(
                    "Total weight must be greater than zero."
            );
        }

        return totalWeightInKg;
    }

    public String getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public long getTotalWeightInKg() {
        return totalWeightInKg;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id='" + id + '\'' +
                ", make='" + make + '\'' +
                ", numberOfAxles=" + numberOfAxles +
                ", totalWeightInKg=" + totalWeightInKg +
                '}';
    }
}