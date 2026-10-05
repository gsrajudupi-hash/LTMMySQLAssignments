package com.toll;

import java.util.Scanner;

/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 11:46:48 pm
 * project  : TollBoothManagementSystem
 */
 
   
public class TollBoothManagementSystem {
 
    public static void main(String[] args) {
 
        Scanner scanner = new Scanner(System.in);
 
        TollBooth tollBooth = new TollBooth();
 
        try {
 
            // Truck 1
            Truck truck1 = new Truck(
                    "TRK001",
                    "Ford",
                    5,
                    12500
            );
 
            // Truck 2
            Truck truck2 = new Truck(
                    "TRK002",
                    "Volvo",
                    4,
                    10000
            );
 
            // Polymorphism
            Vehicle vehicle = truck1;
 
            // Process trucks
            tollBooth.processTruck(truck1);
            tollBooth.processTruck(truck2);
 
            // Display totals
            tollBooth.displayTotals();
 
            // Collect receipts
            tollBooth.collectReceipts();
 
            // Display totals after collection
            tollBooth.displayTotals();
 
        } catch (InvalidTruckException e) {
 
            System.out.println("Error: " + e.getMessage());
 
        } finally {
 
            scanner.close();
        }
    }
}
 
