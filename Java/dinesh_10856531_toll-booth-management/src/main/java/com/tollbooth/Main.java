package com.tollbooth;

public class Main {

    public static void main(String[] args){
        TollCalculator tollCalculator =
                new StandardTollCalculator();
        TollBooth tollBooth =
                new TollBooth(tollCalculator);
        try {
            Vehicle vehicle1 = new Vehicle(
                    "TRUCK-001",
                    "Ford",
                    5,
                    12500.0
            );
            Vehicle vehicle2 = new Vehicle(
                    "TRUCK-002",
                    "Volvo",
                    4,
                    10000.0
            );
            tollBooth.processVehicle(vehicle1);
            tollBooth.processVehicle(vehicle2);
            tollBooth.displayTotals();
            tollBooth.collectReceipts();
            tollBooth.displayTotals();
        } catch (InvalidVehicleException e) {
            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}
