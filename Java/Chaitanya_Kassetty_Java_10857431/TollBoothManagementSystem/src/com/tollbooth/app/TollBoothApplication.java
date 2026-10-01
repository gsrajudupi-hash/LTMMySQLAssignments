package com.tollbooth.app;

import com.tollbooth.exception.InvalidTruckDataException;
import com.tollbooth.model.Truck;
import com.tollbooth.service.TollBooth;
import com.tollbooth.service.TollCalculator;

/**
 * Main class used to start the Toll Booth Management System.
 */
public class TollBoothApplication {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("     TOLL BOOTH MANAGEMENT SYSTEM");
        System.out.println("========================================");

        // Create one toll booth object.
        TollBooth tollBooth = new TollBooth();

        try {

            // First truck from the assignment example.
            Truck truck1 = new Truck(
                    "TRK001",
                    "Ford",
                    5,
                    12500
            );

            // Toll = (4 * 5) + ((11500 / 500) * 10)
            // Toll = 20 + 230
            // Toll = 250
            Truck truck2 = new Truck(
                    "TRK002",
                    "Volvo",
                    4,
                    11500
            );

            /*
             * Process the two trucks.
             *
             * Truck 1 toll = $275
             * Truck 2 toll = $250
             * Total        = $525
             */
            tollBooth.processTruck(truck1);
            tollBooth.processTruck(truck2);

            // Display totals before collecting receipts.
            tollBooth.displayTotals();

            // Demonstrating polymorphism.
            demonstratePolymorphism(tollBooth, truck1);

            // Display totals and reset them.
            tollBooth.collectReceipts();

            /*
             * Verify the reset.
             * This should display:
             * Total Trucks   : 0
             * Total Receipts : $0.00
             */
            tollBooth.displayTotals();

        } catch (InvalidTruckDataException exception) {

            System.out.println();
            System.out.println("Unable to process truck.");
            System.out.println("Reason: " + exception.getMessage());

        } catch (Exception exception) {

            System.out.println();
            System.out.println("An unexpected error occurred.");
            System.out.println("Reason: " + exception.getMessage());
        }

        System.out.println();
        System.out.println("========================================");
        System.out.println("        APPLICATION COMPLETED");
        System.out.println("========================================");
    }

    /**
     * Demonstrates polymorphism.
     *
     * The reference type is TollCalculator, which is an interface.
     * The actual object passed to it is TollBooth.
     */
    private static void demonstratePolymorphism(
            TollCalculator tollCalculator,
            Truck truck) {

        double toll = tollCalculator.calculateToll(truck);

        System.out.println();
        System.out.println("Polymorphism Demonstration");
        System.out.printf(
                "Calculated toll for %s through TollCalculator: $%.2f%n",
                truck.getTruckId(),
                toll
        );
    }
}