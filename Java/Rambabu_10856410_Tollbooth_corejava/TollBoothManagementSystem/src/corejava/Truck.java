package corejava;


public class Truck implements TollCalculator {

    private String truckId;
    private String truckMake;
    private int numberOfAxles;
    private double totalWeight;

    public Truck(String truckId, String truckMake,
                 int numberOfAxles, double totalWeight) {

        this.truckId = truckId;
        this.truckMake = truckMake;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    public String getTruckId() {
        return truckId;
    }

    public String getTruckMake() {
        return truckMake;
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    @Override
    public double calculateToll() {

        final int AXLE_RATE = 5;
        final int WEIGHT_UNIT = 500;
        final int WEIGHT_RATE = 10;

        double axleCharge = numberOfAxles * AXLE_RATE;

        double weightUnits = totalWeight / WEIGHT_UNIT;

        double weightCharge = weightUnits * WEIGHT_RATE;

        return axleCharge + weightCharge;
    }
}

