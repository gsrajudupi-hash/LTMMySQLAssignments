package util;

import model.Truck;

/**
 * Utility class responsible for formatted console output.
 */
public final class DisplayUtil {

    private static final String SEPARATOR =
            "========================================";

    /*
     * Private constructor prevents the creation
     * of DisplayUtil objects.
     */
    private DisplayUtil() {
    }

    public static void displayTruckArrival(
            Truck truck,
            double toll
    ) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("TRUCK ARRIVAL");
        System.out.println(SEPARATOR);
        System.out.printf(
                "Truck ID       : %s%n",
                truck.getVehicleId()
        );
        System.out.printf(
                "Truck Make     : %s%n",
                truck.getMake()
        );
        System.out.printf(
                "Number of Axles: %d%n",
                truck.getNumberOfAxles()
        );
        System.out.printf(
                "Total Weight   : %d kg%n",
                truck.getTotalWeight()
        );
        System.out.printf(
                "Toll Due       : $%.2f%n",
                toll
        );
        System.out.println(SEPARATOR);
    }

    public static void displayTotals(
            int totalTrucks,
            double totalReceipts
    ) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println(SEPARATOR);
        System.out.printf(
                "Total Trucks   : %d%n",
                totalTrucks
        );
        System.out.printf(
                "Total Receipts : $%.2f%n",
                totalReceipts
        );
        System.out.println(SEPARATOR);
    }

    public static void displayCollection(
            int totalTrucks,
            double totalReceipts
    ) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("COLLECTING RECEIPTS");
        System.out.println(SEPARATOR);
        System.out.printf(
                "Total Trucks   : %d%n",
                totalTrucks
        );
        System.out.printf(
                "Total Receipts : $%.2f%n",
                totalReceipts
        );
        System.out.println(SEPARATOR);
        System.out.println("Totals have been reset.");
    }

    public static void displayMenu() {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("TOLL BOOTH MANAGEMENT SYSTEM");
        System.out.println(SEPARATOR);
        System.out.println("1. Process truck");
        System.out.println("2. Display current totals");
        System.out.println("3. Collect receipts");
        System.out.println("4. Display processed trucks");
        System.out.println("5. Exit");
        System.out.println(SEPARATOR);
        System.out.print("Enter your choice: ");
    }
}