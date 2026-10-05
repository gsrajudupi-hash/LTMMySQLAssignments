package CoreJava;

public class Truck implements Vehicle {

    private final String truckId;
    private final String truckMake;
    private final int numberOfAxles;
    private final double totalWeight;

    public Truck(String truckId, String truckMake,
                 int numberOfAxles, double totalWeight) {

        if (numberOfAxles <= 0) {
            throw new IllegalArgumentException(
                    "Number of axles must be greater than zero.");
        }

        if (totalWeight <= 0) {
            throw new IllegalArgumentException(
                    "Weight must be greater than zero.");
        }

        this.truckId = truckId;
        this.truckMake = truckMake;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    @Override
    public double calculateToll() {

        double axleCharge = numberOfAxles * 5;

        double weightUnits = totalWeight / 500;

        double weightCharge = weightUnits * 10;

        return axleCharge + weightCharge;
    }

    @Override
    public void displayVehicleInfo() {

        System.out.println("========================================");
        System.out.println("TRUCK ARRIVAL");
        System.out.println("========================================");
        System.out.println("Truck ID       : " + truckId);
        System.out.println("Truck Make     : " + truckMake);
        System.out.println("Number of Axles: " + numberOfAxles);
        System.out.println("Total Weight   : " + totalWeight + " kg");
        System.out.printf("Toll Due       : $%.2f%n", calculateToll());
        System.out.println("========================================");
    }
}
