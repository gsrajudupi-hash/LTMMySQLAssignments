package com.toll;

import java.util.ArrayList;
import java.util.List;

/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 11:45:19 pm
 * project  : TollBoothManagementSystem
 */

class TollBooth {
	 
    private final TollCalculator tollCalculator;
 
    // Collection of trucks
    private final List<Truck> trucks;
 
    // Internal totals
    private int totalTrucks;
    private double totalReceipts;
 
    // Constructor
    public TollBooth() {
        this.tollCalculator = new TollCalculator();
        this.trucks = new ArrayList<>();
 
        this.totalTrucks = 0;
        this.totalReceipts = 0.0;
    }
 
    // Process truck arrival
    public void processTruck(Truck truck) {
 
        double toll = tollCalculator.calculateToll(truck);
 
        trucks.add(truck);
 
        totalTrucks++;
        totalReceipts += toll;
 
        System.out.println();
        System.out.println("=================================");
        System.out.println("          TRUCK ARRIVAL");
        System.out.println("=================================");
 
        System.out.println("Truck ID       : " + truck.getTruckId());
        System.out.println("Truck Make     : " + truck.getTruckMake());
        System.out.println("Number of Axles: " + truck.getNumberOfAxles());
        System.out.println("Total Weight   : " + truck.getTotalWeight() + " kg");
        System.out.printf("Toll Due       : $%.2f%n", toll);
 
        System.out.println("=================================");
    }
 
    // Display totals
    public void displayTotals() {
 
        System.out.println();
        System.out.println("=================================");
        System.out.println("    TOTALS SINCE LAST COLLECTION");
        System.out.println("=================================");
 
        System.out.println("Total Trucks   : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
 
        System.out.println("=================================");
    }
 
    // Collect receipts
    public void collectReceipts() {
 
        System.out.println();
        System.out.println("=================================");
        System.out.println("       COLLECTING RECEIPTS");
        System.out.println("=================================");
 
        System.out.println("Total Trucks   : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
 
        // Reset totals
        totalTrucks = 0;
        totalReceipts = 0.0;
 
        // Clear trucks for new collection cycle
        trucks.clear();
 
        System.out.println();
        System.out.println("Totals have been reset.");
        System.out.println("=================================");
    }
 
    // Get current truck count
    public int getTotalTrucks() {
        return totalTrucks;
    }
 
    // Get current receipts
    public double getTotalReceipts() {
        return totalReceipts;
    }
}
