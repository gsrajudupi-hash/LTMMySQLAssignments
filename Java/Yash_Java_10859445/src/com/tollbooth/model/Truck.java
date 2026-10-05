package com.tollbooth.model;

import com.tollbooth.exception.InvalidTruckDataException;

public class Truck implements Vehicle {

    private static final double AXLE_RATE = 5.0;
    private static final double WEIGHT_UNIT_RATE = 10.0;
    private static final int WEIGHT_UNIT_SIZE = 500;

    private final String truckId;
    private final String truckMake;
    private final int numberOfAxles;
    private final int totalWeight;

    public Truck(
            String truckId,
            String truckMake,
            int numberOfAxles,
            int totalWeight
    ) throws InvalidTruckDataException {

        if (truckId == null || truckId.isBlank()) {
            throw new InvalidTruckDataException(
                    "Truck ID cannot be empty."
            );
        }

        if (truckMake == null || truckMake.isBlank()) {
            throw new InvalidTruckDataException(
                    "Truck make cannot be empty."
            );
        }

        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than zero."
            );
        }

        if (totalWeight <= 0) {
            throw new InvalidTruckDataException(
                    "Total weight must be greater than zero."
            );
        }

        this.truckId = truckId.trim();
        this.truckMake = truckMake.trim();
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    @Override
    public double calculateToll() {
        return calculateAxleCharge() + calculateWeightCharge();
    }

    public double calculateAxleCharge() {
        return numberOfAxles * AXLE_RATE;
    }

    public int calculateWeightUnits() {
        return totalWeight / WEIGHT_UNIT_SIZE;
    }

    public double calculateWeightCharge() {
        return calculateWeightUnits() * WEIGHT_UNIT_RATE;
    }

    @Override
    public void displayDetails() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.printf("Truck ID       : %s%n", truckId);
        System.out.printf("Truck Make     : %s%n", truckMake);
        System.out.printf("Number of Axles: %d%n", numberOfAxles);
        System.out.printf("Total Weight   : %d kg%n", totalWeight);
        System.out.printf("Toll Due       : $%.2f%n", calculateToll());
        System.out.println("========================================");
    }

    @Override
    public String getVehicleId() {
        return truckId;
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

    public int getTotalWeight() {
        return totalWeight;
    }

    @Override
    public String toString() {
        return String.format(
                "Truck ID: %-10s | Make: %-12s | Axles: %-3d | "
                        + "Weight: %-8d kg | Toll: $%.2f",
                truckId,
                truckMake,
                numberOfAxles,
                totalWeight,
                calculateToll()
        );
    }
}