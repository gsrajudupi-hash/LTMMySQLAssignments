package com.tollbooth;

import java.util.ArrayList;

import java.util.List;

public class TollBooth {

    private int totalVehicles;

    private double totalReceipts;

    private final TollCalculator tollCalculator;

    private final List<Vehicle> vehicles;

    public TollBooth(TollCalculator tollCalculator) {

        this.tollCalculator = tollCalculator;

        this.vehicles = new ArrayList<>();

        this.totalVehicles = 0;

        this.totalReceipts = 0.0;

    }

    public void processVehicle(Vehicle vehicle)

            throws InvalidVehicleException {

        validateVehicle(vehicle);

        double toll = tollCalculator.calculateToll(vehicle);

        totalVehicles++;

        totalReceipts += toll;

        vehicles.add(vehicle);

        displayVehicleArrival(vehicle, toll);

    }

    private void validateVehicle(Vehicle vehicle)

            throws InvalidVehicleException {

        if (vehicle == null) {

            throw new InvalidVehicleException(

                    "Vehicle cannot be null."

            );

        }

        if (vehicle.getVehicleId() == null ||

                vehicle.getVehicleId().isBlank()) {

            throw new InvalidVehicleException(

                    "Vehicle ID cannot be empty."

            );

        }

        if (vehicle.getVehicleMake() == null ||

                vehicle.getVehicleMake().isBlank()) {

            throw new InvalidVehicleException(

                    "Vehicle make cannot be empty."

            );

        }

        if (vehicle.getNumberOfAxles() <= 0) {

            throw new InvalidVehicleException(

                    "Number of axles must be greater than zero."

            );

        }

        if (vehicle.getTotalWeight() <= 0) {

            throw new InvalidVehicleException(

                    "Total weight must be greater than zero."

            );

        }

    }

    private void displayVehicleArrival(

            Vehicle vehicle,

            double toll) {

        System.out.println();

        System.out.println("========================================");

        System.out.println("             TRUCK ARRIVAL");

        System.out.println("========================================");

        System.out.println(

                "Vehicle ID       : "

                        + vehicle.getVehicleId());

        System.out.println(

                "Truck Make       : "

                        + vehicle.getVehicleMake());

        System.out.println(

                "Number of Axles  : "

                        + vehicle.getNumberOfAxles());

        System.out.println(

                "Total Weight     : "

                        + vehicle.getTotalWeight()

                        + " kg");

        System.out.printf(

                "Toll Due         : $%.2f%n",

                toll);

        System.out.println("========================================");

    }

    public void displayTotals() {

        System.out.println();

        System.out.println("========================================");

        System.out.println("     TOTALS SINCE LAST COLLECTION");

        System.out.println("========================================");

        System.out.println(

                "Total Trucks     : "

                        + totalVehicles);

        System.out.printf(

                "Total Receipts   : $%.2f%n",

                totalReceipts);

        System.out.println("========================================");

    }

    public void collectReceipts() {

        System.out.println();

        System.out.println("========================================");

        System.out.println("          COLLECTING RECEIPTS");

        System.out.println("========================================");

        System.out.println(

                "Total Trucks     : "

                        + totalVehicles);

        System.out.printf(

                "Total Receipts   : $%.2f%n",

                totalReceipts);

        totalVehicles = 0;

        totalReceipts = 0.0;

        vehicles.clear();

        System.out.println();

        System.out.println("Totals have been reset.");

        System.out.println("========================================");

    }

    public int getTotalVehicles() {

        return totalVehicles;

    }

    public double getTotalReceipts() {

        return totalReceipts;

    }

    public List<Vehicle> getVehicles() {

        return List.copyOf(vehicles);

    }

}

