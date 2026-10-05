package com.toll;
/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 11:43:00 pm
 * project  : TollBoothManagementSystem
 */

class Truck implements Vehicle {
	 
    // Encapsulation - private variables
    private final String truckId;
    private final String truckMake;
    private final int numberOfAxles;
    private final int totalWeight;
 
    // Constructor
    public Truck(String truckId, String truckMake,
                 int numberOfAxles, int totalWeight)
            throws InvalidTruckException {
 
        if (truckId == null || truckId.isBlank()) {
            throw new InvalidTruckException("Truck ID cannot be empty.");
        }
 
        if (truckMake == null || truckMake.isBlank()) {
            throw new InvalidTruckException("Truck make cannot be empty.");
        }
 
        if (numberOfAxles <= 0) {
            throw new InvalidTruckException(
                    "Number of axles must be greater than zero.");
        }
 
        if (totalWeight <= 0) {
            throw new InvalidTruckException(
                    "Truck weight must be greater than zero.");
        }
 
        this.truckId = truckId;
        this.truckMake = truckMake;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }
 
    // Getters
    @Override
    public String getTruckId() {
        return truckId;
    }
 
    @Override
    public String getTruckMake() {
        return truckMake;
    }
 
    @Override
    public int getNumberOfAxles() {
        return numberOfAxles;
    }
 
    @Override
    public int getTotalWeight() {
        return totalWeight;
    }
 
    // Method overriding
    @Override
    public String toString() {
        return "Truck ID: " + truckId +
                ", Make: " + truckMake +
                ", Axles: " + numberOfAxles +
                ", Weight: " + totalWeight + " kg";
    }
}
