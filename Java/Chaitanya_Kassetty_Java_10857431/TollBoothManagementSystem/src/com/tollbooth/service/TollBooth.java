package com.tollbooth.service;

import com.tollbooth.exception.InvalidTruckDataException;
import com.tollbooth.model.Truck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Represents a toll booth.
 * <p>
 * It calculates truck tolls, processes trucks,
 * maintains totals and collects receipts.
 */
public class TollBooth implements TollCalculator {

    /*
     * Business constants.
     *
     * static means the value belongs to the class.
     * final means the value cannot be changed.
     */
    private static final double AXLE_RATE = 5.0;
    private static final double WEIGHT_UNIT_RATE = 10.0;
    private static final double WEIGHT_UNIT_SIZE = 500.0;

    // Totals maintained since the previous receipt collection.
    private int totalTrucks;
    private double totalReceipts;

    // Stores trucks processed during the current collection cycle.
    private final List<Truck> processedTrucks;

    /**
     * Constructor initializes counters and the collection.
     */
    public TollBooth() {
        this.totalTrucks = 0;
        this.totalReceipts = 0.0;
        this.processedTrucks = new ArrayList<>();
    }

    /**
     * Calculates toll using:
     * <p>
     * Toll = (Number of Axles * $5)
     * + ((Total Weight / 500) * $10)
     *
     * @param truck truck for which toll must be calculated
     * @return total toll amount
     */
    @Override
    public double calculateToll(Truck truck) {

        validateTruck(truck);

        double axleCharge =
                truck.getNumberOfAxles() * AXLE_RATE;

        double numberOfWeightUnits =
                truck.getTotalWeight() / WEIGHT_UNIT_SIZE;

        double weightCharge =
                numberOfWeightUnits * WEIGHT_UNIT_RATE;

        return axleCharge + weightCharge;
    }

    /**
     * Processes one truck.
     * <p>
     * Processing includes:
     * 1. Calculating its toll
     * 2. Increasing total truck count
     * 3. Increasing total receipts
     * 4. Storing the truck
     * 5. Displaying truck arrival details
     */
    public void processTruck(Truck truck) {

        validateTruck(truck);

        double toll = calculateToll(truck);

        totalTrucks++;
        totalReceipts += toll;
        processedTrucks.add(truck);

        displayTruckArrival(truck, toll);
    }

    /**
     * Displays the truck arrival information.
     */
    private void displayTruckArrival(Truck truck, double toll) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.printf("Truck ID         : %s%n",
                truck.getTruckId());
        System.out.printf("Truck Make       : %s%n",
                truck.getTruckMake());
        System.out.printf("Number of Axles  : %d%n",
                truck.getNumberOfAxles());
        System.out.printf("Total Weight     : %.2f kg%n",
                truck.getTotalWeight());
        System.out.printf("Toll Due         : $%.2f%n", toll);
        System.out.println("========================================");
    }

    /**
     * Displays totals accumulated since the previous
     * receipt collection.
     */
    public void displayTotals() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("     TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.printf("Total Trucks     : %d%n", totalTrucks);
        System.out.printf("Total Receipts   : $%.2f%n",
                totalReceipts);
        System.out.println("========================================");
    }

    /**
     * Displays current receipts and then resets all
     * values for the next collection cycle.
     */
    public void collectReceipts() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.printf("Total Trucks     : %d%n", totalTrucks);
        System.out.printf("Total Receipts   : $%.2f%n",
                totalReceipts);
        System.out.println("========================================");

        resetTotals();

        System.out.println();
        System.out.println("Totals have been reset.");
    }

    /**
     * Resets counters and clears the list after receipt collection.
     */
    private void resetTotals() {
        totalTrucks = 0;
        totalReceipts = 0.0;
        processedTrucks.clear();
    }

    /**
     * Prevents null trucks from being processed.
     */
    private void validateTruck(Truck truck) {

        if (truck == null) {
            throw new InvalidTruckDataException(
                    "Truck object cannot be null."
            );
        }
    }

    public int getTotalTrucks() {
        return totalTrucks;
    }

    public double getTotalReceipts() {
        return totalReceipts;
    }

    /**
     * Returns a read-only view of the processed truck collection.
     * <p>
     * Callers can view the trucks, but they cannot directly
     * add or remove trucks from the TollBooth collection.
     */
    public List<Truck> getProcessedTrucks() {
        return Collections.unmodifiableList(processedTrucks);
    }
}