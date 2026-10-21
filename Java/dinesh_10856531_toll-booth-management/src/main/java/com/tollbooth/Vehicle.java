package com.tollbooth;

public class Vehicle {
    public String getVehicleId() {
        return vehicleId;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public void setNumberOfAxles(int numberOfAxles) {
        this.numberOfAxles = numberOfAxles;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    private final String vehicleId;
    private String vehicleMake;
    private int numberOfAxles;
    private double totalWeight;

    public Vehicle(String vehicleId,String vehicleMake,
                   int numberOfAxles,double totalWeight) {
        this.vehicleId = vehicleId;
        this.vehicleMake = vehicleMake;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", vehicleMake='" + vehicleMake + '\'' +
                ", numberOfAxles=" + numberOfAxles +
                ", totalWeight=" + totalWeight +
                '}';
    }
}
