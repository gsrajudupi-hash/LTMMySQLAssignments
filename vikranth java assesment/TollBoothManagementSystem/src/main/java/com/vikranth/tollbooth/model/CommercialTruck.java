package com.vikranth.tollbooth.model;

import com.vikranth.tollbooth.exception.InvalidTruckDataException;

public final class CommercialTruck implements Truck {
    private final String truckId;
    private final String make;
    private final int numberOfAxles;
    private final long totalWeightKg;

    public CommercialTruck(String truckId, String make, int numberOfAxles,
                           long totalWeightKg) throws InvalidTruckDataException {
        if (truckId == null || truckId.isBlank()) {
            throw new InvalidTruckDataException("Truck ID cannot be empty.");
        }
        if (make == null || make.isBlank()) {
            throw new InvalidTruckDataException("Truck make cannot be empty.");
        }
        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException("Number of axles must be greater than zero.");
        }
        if (totalWeightKg <= 0) {
            throw new InvalidTruckDataException("Total weight must be greater than zero.");
        }
        this.truckId = truckId.trim();
        this.make = make.trim();
        this.numberOfAxles = numberOfAxles;
        this.totalWeightKg = totalWeightKg;
    }

    @Override public String getTruckId() { return truckId; }
    @Override public String getMake() { return make; }
    @Override public int getNumberOfAxles() { return numberOfAxles; }
    @Override public long getTotalWeightKg() { return totalWeightKg; }

    @Override
    public String toString() {
        return String.format("%s | %s | %d axles | %d kg",
                truckId, make, numberOfAxles, totalWeightKg);
    }
}
