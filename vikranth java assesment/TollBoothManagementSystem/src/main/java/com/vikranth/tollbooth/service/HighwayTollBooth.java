package com.vikranth.tollbooth.service;

import com.vikranth.tollbooth.model.Truck;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HighwayTollBooth implements TollBooth {
    private static final BigDecimal AXLE_CHARGE = new BigDecimal("5.00");
    private static final BigDecimal WEIGHT_UNIT_CHARGE = new BigDecimal("10.00");
    private static final long WEIGHT_UNIT_KG = 500;

    private int totalTrucks;
    private BigDecimal totalReceipts = BigDecimal.ZERO.setScale(2);
    private final List<Truck> processedTrucks = new ArrayList<>();

    @Override
    public BigDecimal calculateToll(Truck truck) {
        Objects.requireNonNull(truck, "Truck cannot be null.");
        BigDecimal axleAmount = AXLE_CHARGE.multiply(
                BigDecimal.valueOf(truck.getNumberOfAxles()));
        long completeWeightUnits = truck.getTotalWeightKg() / WEIGHT_UNIT_KG;
        BigDecimal weightAmount = WEIGHT_UNIT_CHARGE.multiply(
                BigDecimal.valueOf(completeWeightUnits));
        return axleAmount.add(weightAmount).setScale(2);
    }

    @Override
    public void processTruck(Truck truck) {
        BigDecimal toll = calculateToll(truck);
        totalTrucks++;
        totalReceipts = totalReceipts.add(toll);
        processedTrucks.add(truck);

        System.out.println("\n========================================");
        System.out.println("             TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.printf("Truck ID       : %s%n", truck.getTruckId());
        System.out.printf("Truck Make     : %s%n", truck.getMake());
        System.out.printf("Number of Axles: %d%n", truck.getNumberOfAxles());
        System.out.printf("Total Weight   : %d kg%n", truck.getTotalWeightKg());
        System.out.printf("Toll Due       : $%.2f%n", toll);
        System.out.println("========================================");
    }

    @Override
    public void displayTotals() {
        System.out.println("\n========================================");
        System.out.println("      TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.printf("Total Trucks   : %d%n", totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");
    }

    @Override
    public void collectReceipts() {
        System.out.println("\n========================================");
        System.out.println("          COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.printf("Total Trucks   : %d%n", totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");
        totalTrucks = 0;
        totalReceipts = BigDecimal.ZERO.setScale(2);
        processedTrucks.clear();
        System.out.println("Totals have been reset.");
    }

    @Override
    public void displayProcessedTrucks() {
        System.out.println("\n========================================");
        System.out.println("          PROCESSED TRUCKS");
        System.out.println("========================================");
        if (processedTrucks.isEmpty()) {
            System.out.println("No trucks have been processed.");
        } else {
            for (int i = 0; i < processedTrucks.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, processedTrucks.get(i));
            }
        }
        System.out.println("========================================");
    }

    public int getTotalTrucks() { return totalTrucks; }
    public BigDecimal getTotalReceipts() { return totalReceipts; }
    public List<Truck> getProcessedTrucks() {
        return Collections.unmodifiableList(processedTrucks);
    }
}
