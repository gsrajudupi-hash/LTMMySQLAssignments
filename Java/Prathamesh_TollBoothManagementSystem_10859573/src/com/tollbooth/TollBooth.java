package com.tollbooth;

import java.util.ArrayList;
import java.util.List;

/**
 * TollBooth
 *
 * @author Prathamesh
 */
public class TollBooth {

    private final TollCalculator tollCalculator;

    private int totalTrucks;

    private double totalReceipts;

    private  final List<Vehicle> trucks;


    public TollBooth(TollCalculator tollCalculator) {
        this.tollCalculator = tollCalculator;
        this.totalTrucks = 0;
        this.totalReceipts = 0.0;
        this.trucks = new ArrayList<>();
    }

    public void processTruck(Vehicle vehicle){
        double toll = tollCalculator.calculateToll(vehicle);
        totalTrucks++;
        totalReceipts += toll;

        trucks.add(vehicle);

        System.out.println("===========================================");

        System.out.println("TRUCK ARRIVAL");

        System.out.println("===========================================");

        System.out.println("Truck Id    : " + vehicle.getTruckId());

        System.out.println("Truck Make    : " + vehicle.getTruckMake());

        System.out.println("Number of Axles: " + vehicle.getNumberOfAxles());

        System.out.println("Total Weight   : " + vehicle.getTotalWeight());

        System.out.printf("Toll Due       : $%.2f%n" , toll);

        System.out.println("============================================");

    }
    public void displayTotals(){

        System.out.println();

        System.out.println("============================================");

        System.out.println("TOTALS SINCE LAST COLLECTION");

        System.out.println("============================================");

        System.out.println("Total Trucks  : " + totalTrucks);

        System.out.printf("Receipts   : $%.2f%n" , totalReceipts);

        System.out.println("============================================");
    }

    public void collectReceipts(){

        System.out.println();

        System.out.println("============================================");

        System.out.println("COLLECTING RECEIPTS");

        System.out.println("============================================");

        System.out.println("Total Trucks : " + totalTrucks);

        System.out.printf("Receipts   : $%.2f%n" , totalReceipts);

        totalTrucks = 0;
        totalReceipts = 0.0;
        trucks.clear();

        System.out.println("============================================");

        System.out.println("Totals have been reset. ");

        System.out.println("============================================");
    }
}