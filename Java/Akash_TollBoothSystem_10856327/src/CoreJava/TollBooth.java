package CoreJava;

import java.util.ArrayList;
import java.util.List;

public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    private final List<Truck> truckHistory;

    public TollBooth() {
        truckHistory = new ArrayList<>();
    }

    public void processTruck(Vehicle vehicle) {

        vehicle.displayVehicleInfo();

        double toll = vehicle.calculateToll();

        totalTrucks++;
        totalReceipts += toll;

        if (vehicle instanceof Truck truck) {
            truckHistory.add(truck);
        }

        System.out.println();
        displayTotals();
    }

    public void displayTotals() {

        System.out.println("========================================");
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.println("Total Trucks   : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");
    }

    public void collectReceipts() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.println("Total Trucks   : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("========================================");

        totalTrucks = 0;
        totalReceipts = 0;

        System.out.println("Totals have been reset.");
    }
}
