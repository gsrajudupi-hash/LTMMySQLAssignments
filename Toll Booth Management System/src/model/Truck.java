package model;

import exception.InvalidTruckData;

/**
 * Represents a truck passing through the toll booth.
 */
public class Truck extends Vehicle {

    private final int numberOfAxles;
    private final int totalWeight;

    public Truck(
            String vehicleId,
            String make,
            int numberOfAxles,
            int totalWeight
    ) throws InvalidTruckData {

        super(validateVehicleId(vehicleId), validateMake(make));

        if (numberOfAxles <= 0) {
            throw new InvalidTruckData(
                    "Number of axles must be greater than zero."
            );
        }

        if (totalWeight <= 0) {
            throw new InvalidTruckData(
                    "Total weight must be greater than zero."
            );
        }

        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    private static String validateVehicleId(String vehicleId)
            throws InvalidTruckData {

        if (vehicleId == null || vehicleId.isBlank()) {
            throw new InvalidTruckData(
                    "Truck ID cannot be empty."
            );
        }

        return vehicleId.trim();
    }

    private static String validateMake(String make)
            throws InvalidTruckData{

        if (make == null || make.isBlank()) {
            throw new InvalidTruckData(
                    "Truck make cannot be empty."
            );
        }

        return make.trim();
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    @Override
    public String getVehicleDetails() {
        return "Truck ID: " + getVehicleId()
                + ", Make: " + getMake()
                + ", Axles: " + numberOfAxles
                + ", Weight: " + totalWeight + " kg";
    }
}