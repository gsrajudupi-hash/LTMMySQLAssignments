package com.tollbooth.app;

import com.tollbooth.exception.InvalidTruckDataException;
import com.tollbooth.model.Truck;
import com.tollbooth.model.Vehicle;
import com.tollbooth.service.TollBooth;
import com.tollbooth.util.InputValidator;

import java.util.Scanner;

public class TollBoothApplication {

    private static final int PROCESS_TRUCK = 1;
    private static final int DISPLAY_TOTALS = 2;
    private static final int DISPLAY_HISTORY = 3;
    private static final int COLLECT_RECEIPTS = 4;
    private static final int EXIT = 5;

    public static void main(String[] args) {

        TollBooth tollBooth = new TollBooth();

        try (Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                displayMenu();

                int choice = InputValidator.readMenuChoice(
                        scanner,
                        "Enter your choice: ",
                        PROCESS_TRUCK,
                        EXIT
                );

                switch (choice) {

                    case PROCESS_TRUCK ->
                            processTruck(scanner, tollBooth);

                    case DISPLAY_TOTALS ->
                            tollBooth.displayTotals();

                    case DISPLAY_HISTORY ->
                            tollBooth.displayTruckHistory();

                    case COLLECT_RECEIPTS ->
                            tollBooth.collectReceipts();

                    case EXIT -> {
                        running = false;

                        System.out.println(
                                "Thank you for using the "
                                        + "Toll Booth Management System."
                        );
                    }

                    default ->
                            System.out.println("Invalid choice.");
                }
            }
        }
    }

    private static void processTruck(
            Scanner scanner,
            TollBooth tollBooth
    ) {
        System.out.println();
        System.out.println("========================================");
        System.out.println("ENTER TRUCK INFORMATION");
        System.out.println("========================================");

        String truckId = InputValidator.readRequiredString(
                scanner,
                "Enter truck ID: "
        );

        if (tollBooth.containsTruckId(truckId)) {
            System.out.println(
                    "Truck ID " + truckId
                            + " has already been processed "
                            + "in the current collection cycle."
            );

            return;
        }

        String truckMake = InputValidator.readRequiredString(
                scanner,
                "Enter truck make: "
        );

        int numberOfAxles =
                InputValidator.readPositiveInteger(
                        scanner,
                        "Enter number of axles: "
                );

        int totalWeight =
                InputValidator.readPositiveInteger(
                        scanner,
                        "Enter total weight in kg: "
                );

        try {
            Vehicle vehicle = new Truck(
                    truckId,
                    truckMake,
                    numberOfAxles,
                    totalWeight
            );

            tollBooth.processVehicle(vehicle);

        } catch (InvalidTruckDataException exception) {
            System.out.println(
                    "Unable to process truck: "
                            + exception.getMessage()
            );
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("TOLL BOOTH MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Process Truck");
        System.out.println("2. Display Current Totals");
        System.out.println("3. Display Truck History");
        System.out.println("4. Collect Receipts");
        System.out.println("5. Exit");
        System.out.println("========================================");
    }
}