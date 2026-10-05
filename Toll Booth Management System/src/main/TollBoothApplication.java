package main;

import exception.InvalidTruckData;
import model.Truck;
import service.TollBooth;
import service.TollCalculator;
import service.TollCalculatorImpl;
import util.DisplayUtil;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Entry point for the Toll Booth Management System.
 */
public class TollBoothApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        /*
         * Polymorphism:
         * Interface reference points to its implementation.
         */
        TollCalculator tollCalculator =
                new TollCalculatorImpl();

        TollBooth tollBooth =
                new TollBooth(tollCalculator);

        boolean applicationRunning = true;

        System.out.println(
                "Welcome to the Toll Booth Management System"
        );

        while (applicationRunning) {

            DisplayUtil.displayMenu();

            int choice = readInteger(scanner);

            switch (choice) {
                case 1:
                    processTruck(scanner, tollBooth);
                    break;

                case 2:
                    tollBooth.displayTotals();
                    break;

                case 3:
                    tollBooth.collectReceipts();
                    break;

                case 4:
                    tollBooth.displayProcessedTrucks();
                    break;

                case 5:
                    applicationRunning = false;
                    System.out.println(
                            "Toll Booth Management System closed."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Enter a number from 1 to 5."
                    );
            }
        }

        scanner.close();
    }

    /**
     * Reads truck information and sends the truck
     * to the toll booth for processing.
     */
    private static void processTruck(
            Scanner scanner,
            TollBooth tollBooth
    ) {
        try {
            System.out.println();
            System.out.println("Enter truck information");

            System.out.print("Truck ID: ");
            String truckId = scanner.nextLine();

            System.out.print("Truck make: ");
            String make = scanner.nextLine();

            System.out.print("Number of axles: ");
            int numberOfAxles = readInteger(scanner);

            System.out.print("Total weight in kg: ");
            int totalWeight = readInteger(scanner);

            Truck truck = new Truck(
                    truckId,
                    make,
                    numberOfAxles,
                    totalWeight
            );

            tollBooth.processTruck(truck);

        } catch (InvalidTruckData exception) {
            System.out.println(
                    "Truck could not be processed: "
                            + exception.getMessage()
            );
        }
    }

    /**
     * Safely reads an integer and handles invalid input.
     */
    private static int readInteger(Scanner scanner) {

        while (true) {
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;

            } catch (InputMismatchException exception) {
                System.out.print(
                        "Invalid input. Enter a whole number: "
                );

                /*
                 * Removes the incorrect input from Scanner.
                 */
                scanner.nextLine();
            }
        }
    }
}