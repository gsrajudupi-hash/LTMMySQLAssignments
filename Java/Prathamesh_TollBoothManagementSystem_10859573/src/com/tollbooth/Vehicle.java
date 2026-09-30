package com.tollbooth;

/**
 * Vehicle
 *
 * @author Prathamesh
 */
public class Vehicle {
    private final String truckId;
    private final String truckMake;
    private final int numberOfAxles;
    private final double totalWeight;

    public Vehicle(String truckId, String truckMake, int numberOfAxles, double totalWeight) {

        if(truckId == null || truckId.isBlank()){
                throw new IllegalArgumentException("Truck ID cannot be empty");
        }

        if(truckMake == null || truckMake.isBlank()){
            throw new IllegalArgumentException("Truck Make cannot be empty");
        }

        if(numberOfAxles <= 0){
            throw new IllegalArgumentException("Number of axles must be greater than 0");
        }

        if(totalWeight <= 0){
            throw new IllegalArgumentException("Weight must be greater then 0");
        }

        this.truckId = truckId;
        this.truckMake = truckMake;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
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
}
