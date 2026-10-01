package corejava;

import java.util.ArrayList;
import java.util.List;

public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    private List<Truck> truckHistory = new ArrayList<>();

    public void processTruck(Truck truck) {

        double toll = truck.calculateToll();

        totalTrucks++;
        totalReceipts += toll;

        truckHistory.add(truck);

        System.out.println("\n========================================");
        System.out.println("           TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.println("Truck ID       : " + truck.getTruckId());
        System.out.println("Truck Make     : " + truck.getTruckMake());
        System.out.println("Number of Axles: " + truck.getNumberOfAxles());
        System.out.println("Total Weight   : " + truck.getTotalWeight() + " kg");
        System.out.printf("Toll Due       : $%.2f%n", toll);
        System.out.println("========================================");
    }

    public void displayTotals() {

        System.out.println("\n========================================");
        System.out.println(" TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.println("Total Trucks   : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");
    }

    public void collectReceipts() {

        System.out.println("\n========================================");
        System.out.println("      COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.println("Total Trucks   : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");

        totalTrucks = 0;
        totalReceipts = 0.0;

        System.out.println("\nTotals have been reset.");
    }

    public void displayTruckHistory() {

        System.out.println("\n====== TRUCK HISTORY ======");

        for (Truck truck : truckHistory) {
            System.out.println(
                    truck.getTruckId() + " - "
                    + truck.getTruckMake());
        }
    }
}
