public class TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    public void processTruck(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("Truck cannot be null");
        }

        // Calculate the toll through the shared Tollable interface.
        Tollable tollCalculator = new TollCalculator(truck);
        double toll = tollCalculator.calculateToll();

        // Add this truck and its toll to the current collection totals.
        totalTrucks++;
        totalReceipts += toll;

        System.out.println();

        System.out.println("=======================================");
        System.out.println("TRUCK ARRIVAL");
        System.out.println("========================================");

        System.out.println("Truck ID : " + truck.getTruckId());
        System.out.println("Truck Make : " + truck.getTruckMake());
        System.out.println("Number Of Axles : " + truck.getNumberOfAxle());
        System.out.println("Total Weight : " + truck.getTotalWeight());
        System.out.printf("Toll Due : $%.2f%n", toll);
        System.out.println("==========================================");
    }

    // Show the truck and receipt totals accumulated since the last collection.
    public void displayTotals() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("TOTAL SINCE LAST COLLECTION");
        System.out.println("==========================================");
        System.out.println("Total Trucks : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("==========================================");
    }

    // Report the current collection, then reset its totals for the next one.
    public void collectReceipts() {
        System.out.println();
        System.out.println("==================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("===================================");
        System.out.println("Total Trucks : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n", totalReceipts);
        System.out.println("====================================");

        totalTrucks = 0;
        totalReceipts = 0;
        System.out.println("====================================");
        System.out.println("Totals Have been reset.");
        System.out.println("=====================================");
    }
}
