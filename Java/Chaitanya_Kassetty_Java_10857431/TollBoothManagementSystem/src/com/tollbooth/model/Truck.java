package com.tollbooth.model;

import com.tollbooth.exception.InvalidTruckDataException;

/**
 * Represents a truck passing through the toll booth.
 *
 * This class demonstrates:
 * 1. Encapsulation
 * 2. Constructor
 * 3. Access modifiers
 * 4. final variables
 * 5. Exception handling through validation
 */
public class Truck {

    // final means these values cannot be changed after creating the truck.
    private final String truckId;
    private final String truckMake;
    private final int numberOfAxles;
    private final double totalWeight;

    /**
     * Constructor used to create a Truck object.
     *
     * @param truckId       unique identification of the truck
     * @param truckMake     manufacturer or make of the truck
     * @param numberOfAxles number of axles in the truck
     * @param totalWeight   total weight of the truck in kilograms
     */
    public Truck(
            String truckId,
            String truckMake,
            int numberOfAxles,
            double totalWeight) {

        validateTruckId(truckId);
        validateTruckMake(truckMake);
        validateNumberOfAxles(numberOfAxles);
        validateTotalWeight(totalWeight);

        this.truckId = truckId.trim();
        this.truckMake = truckMake.trim();
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    private void validateTruckId(String truckId) {

        if (truckId == null || truckId.isBlank()) {
            throw new InvalidTruckDataException(
                    "Truck ID cannot be null, empty or blank."
            );
        }
    }

    private void validateTruckMake(String truckMake) {

        if (truckMake == null || truckMake.isBlank()) {
            throw new InvalidTruckDataException(
                    "Truck make cannot be null, empty or blank."
            );
        }
    }

    private void validateNumberOfAxles(int numberOfAxles) {

        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than zero."
            );
        }
    }

    private void validateTotalWeight(double totalWeight) {

        if (totalWeight <= 0) {
            throw new InvalidTruckDataException(
                    "Total weight must be greater than zero."
            );
        }
    }

    public String getTruckId() {
        return truckId;
    }

    public String getTruckMake() {
        return truckMake;
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "truckId='" + truckId + '\'' +
                ", truckMake='" + truckMake + '\'' +
                ", numberOfAxles=" + numberOfAxles +
                ", totalWeight=" + totalWeight +
                '}';
    }
}