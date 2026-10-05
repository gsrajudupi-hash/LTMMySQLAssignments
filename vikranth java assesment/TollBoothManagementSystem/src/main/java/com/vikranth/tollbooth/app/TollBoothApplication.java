package com.vikranth.tollbooth.app;

import com.vikranth.tollbooth.exception.InvalidTruckDataException;
import com.vikranth.tollbooth.model.CommercialTruck;
import com.vikranth.tollbooth.model.Truck;
import com.vikranth.tollbooth.service.HighwayTollBooth;
import com.vikranth.tollbooth.service.TollBooth;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TollBoothApplication {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TollBooth tollBooth = new HighwayTollBooth();
            boolean running = true;

            System.out.println("========================================");
            System.out.println("     TOLL BOOTH MANAGEMENT SYSTEM");
            System.out.println("========================================");

            while (running) {
                displayMenu();
                try {
                    int choice = readInt(scanner, "Enter your choice: ");
                    switch (choice) {
                        case 1 -> addTruck(scanner, tollBooth);
                        case 2 -> tollBooth.displayTotals();
                        case 3 -> tollBooth.collectReceipts();
                        case 4 -> tollBooth.displayProcessedTrucks();
                        case 0 -> {
                            running = false;
                            System.out.println("Application closed successfully.");
                        }
                        default -> System.out.println("Invalid choice. Enter a number from 0 to 4.");
                    }
                } catch (InputMismatchException exception) {
                    System.out.println("Invalid input. Please enter numeric values where required.");
                    scanner.nextLine();
                }
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n--------------- MENU -------------------");
        System.out.println("1. Process Truck");
        System.out.println("2. Display Totals");
        System.out.println("3. Collect Receipts");
        System.out.println("4. Display Processed Trucks");
        System.out.println("0. Exit");
        System.out.println("----------------------------------------");
    }

    private static void addTruck(Scanner scanner, TollBooth tollBooth) {
        try {
            System.out.print("Enter truck ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter truck make: ");
            String make = scanner.nextLine();
            int axles = readInt(scanner, "Enter number of axles: ");
            long weight = readLong(scanner, "Enter total weight in kg: ");
            Truck truck = new CommercialTruck(id, make, axles, weight);
            tollBooth.processTruck(truck);
        } catch (InvalidTruckDataException exception) {
            System.out.println("Truck validation error: " + exception.getMessage());
        }
    }

    private static int readInt(Scanner scanner, String message) {
        System.out.print(message);
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static long readLong(Scanner scanner, String message) {
        System.out.print(message);
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }
}
