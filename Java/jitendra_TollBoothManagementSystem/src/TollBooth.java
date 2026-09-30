import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    private final List<Truck> processedTrucks;

    public TollBooth() {
        this.totalTrucks = 0;
        this.totalReceipts = 0.00;
        this.processedTrucks = new ArrayList<>();
    }

    public void processTruck(TollTruck truck) {

        if (truck == null) {
            throw new IllegalArgumentException(
                    "Truck cannot be null."
            );
        }

        /*
         * Polymorphism:
         * TollCalculable reference refers to a TollTruck object.
         */
        TollCalculable tollCalculable = truck;

        double tollDue =
                tollCalculable.calculateToll();

        displayTruckArrival(truck, tollDue);

        totalTrucks++;
        totalReceipts += tollDue;
        processedTrucks.add(truck);
    }

    private void displayTruckArrival(
            TollTruck truck,
            double tollDue) {

        System.out.println("========================================");
        System.out.println("TRUCK ARRIVAL");
        System.out.println("========================================");

        System.out.printf(
                "%-17s: %s%n",
                "Truck ID",
                truck.getTruckId()
        );

        System.out.printf(
                "%-17s: %s%n",
                "Truck Make",
                truck.getTruckMake()
        );

        System.out.printf(
                "%-17s: %d%n",
                "Number of Axles",
                truck.getNumberOfAxles()
        );

        System.out.printf(
                "%-17s: %d kg%n",
                "Total Weight",
                truck.getTotalWeight()
        );

        System.out.printf(
                "%-17s: $%.2f%n",
                "Toll Due",
                tollDue
        );

        System.out.println("========================================");
        System.out.println();
    }

    public void displayTotals() {

        System.out.println("========================================");
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");

        System.out.printf(
                "%-17s: %d%n",
                "Total Trucks",
                totalTrucks
        );

        System.out.printf(
                "%-17s: $%.2f%n",
                "Total Receipts",
                totalReceipts
        );

        System.out.println("========================================");
        System.out.println();
    }

    public void displayProcessedTrucks() {

        System.out.println("========================================");
        System.out.println("PROCESSED TRUCKS");
        System.out.println("========================================");

        if (processedTrucks.isEmpty()) {
            System.out.println("No trucks have been processed.");
        } else {
            for (Truck truck : processedTrucks) {
                System.out.println(truck);
            }
        }

        System.out.println("========================================");
        System.out.println();
    }

    public void collectReceipts() {

        System.out.println("========================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("========================================");

        System.out.printf(
                "%-17s: %d%n",
                "Total Trucks",
                totalTrucks
        );

        System.out.printf(
                "%-17s: $%.2f%n",
                "Total Receipts",
                totalReceipts
        );

        System.out.println("========================================");

        resetTotals();

        System.out.println("Totals have been reset.");
        System.out.println();
    }

    private void resetTotals() {
        totalTrucks = 0;
        totalReceipts = 0.00;
        processedTrucks.clear();
    }

    public int getTotalTrucks() {
        return totalTrucks;
    }

    public double getTotalReceipts() {
        return totalReceipts;
    }

    public List<Truck> getProcessedTrucks() {

        /*
         * Returns a read-only view so outside classes
         * cannot directly modify the original list.
         */
        return Collections.unmodifiableList(processedTrucks);
    }
}