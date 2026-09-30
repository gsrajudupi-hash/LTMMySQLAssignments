/**
 * Author  : 10856807
 * Date    : 28-09-2026
 * Time    : 22:57
 * Project : TollBoothManagementSystem
 */
public class HighwayTollBooth implements TollBooth {

    private int totalTrucks;
    private double totalReceipts;

    @Override
    public double calculateToll(Truck truck) {

        double axleCharge = truck.getNumberOfAxles() * 5;

        double weightCharge =
                (truck.getTotalWeight() / 500) * 10;

        return axleCharge + weightCharge;
    }

    @Override
    public void processTruckArrival(Truck truck) {

        double toll = calculateToll(truck);

        totalTrucks++;
        totalReceipts += toll;

        System.out.println("========================================");
        System.out.println("TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.println("Truck Make      : " + truck.getMake());
        System.out.println("Number of Axles : " + truck.getNumberOfAxles());
        System.out.println("Total Weight    : " + truck.getTotalWeight() + " kg");
        System.out.println("Toll Due        : $" + toll);
        System.out.println("========================================");
    }

    @Override
    public void displayTotals() {

        System.out.println("========================================");
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println("========================================");
        System.out.println("Total Trucks    : " + totalTrucks);
        System.out.println("Total Receipts  : $" + totalReceipts);
        System.out.println("========================================");
    }

    @Override
    public void collectReceipts() {

        System.out.println("========================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("========================================");
        System.out.println("Total Trucks    : " + totalTrucks);
        System.out.println("Total Receipts  : $" + totalReceipts);
        System.out.println("========================================");

        totalTrucks = 0;
        totalReceipts = 0.0;

        System.out.println("Totals have been reset.");

        System.out.println("\nThe toll booth is now ready to process a new collection cycle.");

        System.out.println("Total Trucks   = " + totalTrucks);
        System.out.println("Total Receipts = $" + String.format("%.2f", totalReceipts));
    }
}
