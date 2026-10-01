package com.litmindtree.toolbooth;
public class Truck implements Vechile {

    private String vehicleId;
    private String make;
    private int numberOfAxles;
    private double totalWeight;

    public Truck(String vehicleId, String make,
                 int numberOfAxles, double totalWeight)
            throws InvalidTruckDataException {

        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than zero.");
        }

        if (totalWeight <= 0) {
            throw new InvalidTruckDataException(
                    "Weight must be greater than zero.");
        }

        this.vehicleId = vehicleId;
        this.make = make;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    @Override
    public String getVehicleId() {
        return vehicleId;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    @Override
    public double getTotalWeight() {
        return totalWeight;
    }

    @Override
    public double calculateToll() {
        return TollCalculator.calculateToll(this);
    }
}