package service;

import model.Truck;
import util.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Maintains toll booth operations and collection totals.
 */
public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    private final TollCalculator tollCalculator;
    private final List<Truck> processedTrucks;

    public TollBooth(TollCalculator tollCalculator) {

        if (tollCalculator == null) {
            throw new IllegalArgumentException(
                    "Toll calculator cannot be null."
            );
        }

        this.tollCalculator = tollCalculator;
        this.processedTrucks = new ArrayList<>();
        this.totalTrucks = 0;
        this.totalReceipts = 0.0;
    }

    /**
     * Calculates the toll and updates the booth totals.
     */
    public double processTruck(Truck truck) {

        if (truck == null) {
            throw new IllegalArgumentException(
                    "Truck cannot be null."
            );
        }

        double toll = tollCalculator.calculateToll(truck);

        totalTrucks++;
        totalReceipts += toll;
        processedTrucks.add(truck);

        DisplayUtil.displayTruckArrival(truck, toll);

        return toll;
    }

    /**
     * Displays the totals accumulated during the current cycle.
     */
    public void displayTotals() {
        DisplayUtil.displayTotals(
                totalTrucks,
                totalReceipts
        );
    }

    /**
     * Displays the collection totals and starts a new cycle.
     */
    public void collectReceipts() {

        DisplayUtil.displayCollection(
                totalTrucks,
                totalReceipts
        );

        resetCollection();
    }

    /**
     * Displays trucks processed during the current cycle.
     */
    public void displayProcessedTrucks() {

        System.out.println();
        System.out.println(
                "========================================"
        );
        System.out.println("PROCESSED TRUCKS");
        System.out.println(
                "========================================"
        );

        if (processedTrucks.isEmpty()) {
            System.out.println(
                    "No trucks have been processed."
            );
        } else {
            for (int index = 0;
                 index < processedTrucks.size();
                 index++) {

                Truck truck = processedTrucks.get(index);

                System.out.println(
                        (index + 1) + ". "
                                + truck.getVehicleDetails()
                );
            }
        }

        System.out.println(
                "========================================"
        );
    }

    /**
     * Resets all information for the current collection cycle.
     */
    private void resetCollection() {
        totalTrucks = 0;
        totalReceipts = 0.0;
        processedTrucks.clear();
    }

    public int getTotalTrucks() {
        return totalTrucks;
    }

    public double getTotalReceipts() {
        return totalReceipts;
    }

    /**
     * Returns an unmodifiable list so outside classes
     * cannot directly change the internal list.
     */
    public List<Truck> getProcessedTrucks() {
        return Collections.unmodifiableList(
                processedTrucks
        );
    }
}