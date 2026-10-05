package com.tollbooth.service;

import com.tollbooth.model.Truck;
import com.tollbooth.model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    private final List<Truck> processedTrucks;

    public TollBooth() {
        totalTrucks = 0;
        totalReceipts = 0.0;
        processedTrucks = new ArrayList<>();
    }

    public void processVehicle(Vehicle vehicle) {

        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle cannot be null."
            );
        }

        vehicle.displayDetails();

        totalTrucks++;
        totalReceipts += vehicle.calculateToll();

        if (vehicle instanceof Truck truck) {
            processedTrucks.add(truck);
        }

        System.out.println("Truck processed successfully.");

        displayTotals();
    }

    public void displayTotals() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.printf("Total Trucks   : %d%n", totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");
    }

    public void displayTruckHistory() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("CURRENT COLLECTION CYCLE TRUCK HISTORY");
        System.out.println("========================================");

        if (processedTrucks.isEmpty()) {
            System.out.println(
                    "No trucks have been processed in the current cycle."
            );
        } else {
            for (int index = 0;
                 index < processedTrucks.size();
                 index++) {

                Truck truck = processedTrucks.get(index);

                System.out.printf(
                        "%d. %s%n",
                        index + 1,
                        truck
                );
            }
        }

        System.out.println("========================================");
    }

    public void collectReceipts() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.printf("Total Trucks   : %d%n", totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");

        totalTrucks = 0;
        totalReceipts = 0.0;
        processedTrucks.clear();

        System.out.println("Totals have been reset.");
        System.out.println("Total Trucks   = 0");
        System.out.println("Total Receipts = $0.00");
    }

    public boolean containsTruckId(String truckId) {
        return processedTrucks
                .stream()
                .anyMatch(truck ->
                        truck.getTruckId()
                                .equalsIgnoreCase(truckId)
                );
    }

    public int getTotalTrucks() {
        return totalTrucks;
    }

    public double getTotalReceipts() {
        return totalReceipts;
    }

    public List<Truck> getProcessedTrucks() {
        return Collections.unmodifiableList(processedTrucks);
    }
}