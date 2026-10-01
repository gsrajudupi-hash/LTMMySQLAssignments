package com.litmindtree.toolbooth;

import java.util.ArrayList;
import java.util.List;

public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    private List<Vechile> vehicles =
            new ArrayList<>();

    public void processTruck(Vechile vehicle) {

        double toll = vehicle.calculateToll();

        totalTrucks++;
        totalReceipts += toll;

        vehicles.add(vehicle);

        System.out.println("========================================");
        System.out.println("TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.println("Truck Make      : "
                + vehicle.getMake());
        System.out.println("Number of Axles : "
                + vehicle.getNumberOfAxles());
        System.out.println("Total Weight    : "
                + vehicle.getTotalWeight() + " kg");
        System.out.printf("Toll Due        : $%.2f%n", toll);
        System.out.println("========================================");
        System.out.println();
    }

    public void displayTotals() {

        System.out.println("========================================");
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.println("Total Trucks    : "
                + totalTrucks);
        System.out.printf("Total Receipts  : $%.2f%n",
                totalReceipts);
        System.out.println("========================================");
        System.out.println();
    }

    public void collectReceipts() {

        System.out.println("========================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.println("Total Trucks    : "
                + totalTrucks);
        System.out.printf("Total Receipts  : $%.2f%n",
                totalReceipts);
        System.out.println("========================================");

        totalTrucks = 0;
        totalReceipts = 0;
        vehicles.clear();

        System.out.println("Totals have been reset.");
    }
}