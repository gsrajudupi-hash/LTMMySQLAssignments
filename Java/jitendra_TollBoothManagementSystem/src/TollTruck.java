public class TollTruck extends Truck implements TollCalculable {

    private static final double AXLE_RATE = 5.00;
    private static final double WEIGHT_UNIT_RATE = 10.00;
    private static final int WEIGHT_UNIT_IN_KG = 500;

    public TollTruck(
            String truckId,
            String truckMake,
            int numberOfAxles,
            int totalWeight) {

        super(
                truckId,
                truckMake,
                numberOfAxles,
                totalWeight
        );
    }

    @Override
    public double calculateToll() {

        double axleCharge =
                getNumberOfAxles() * AXLE_RATE;

        int numberOfWeightUnits =
                getTotalWeight() / WEIGHT_UNIT_IN_KG;

        double weightCharge =
                numberOfWeightUnits * WEIGHT_UNIT_RATE;

        return axleCharge + weightCharge;
    }

    public double calculateAxleCharge() {
        return getNumberOfAxles() * AXLE_RATE;
    }

    public int calculateWeightUnits() {
        return getTotalWeight() / WEIGHT_UNIT_IN_KG;
    }

    public double calculateWeightCharge() {
        return calculateWeightUnits() * WEIGHT_UNIT_RATE;
    }
}