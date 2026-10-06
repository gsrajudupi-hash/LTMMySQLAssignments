public class TollCalculator implements Tollable {

    private final Truck truck;

    public TollCalculator(Truck truck) {
        if (truck == null) {
            throw new IllegalArgumentException("Truck cannot be null");
        }
        this.truck = truck;
    }

    @Override
    public double calculateToll() {
        // Charge $5 per axle and $10 per 500 kg of weight.
        double axleCharge = truck.getNumberOfAxle() * 5;

        // Weight is charged proportionally, including partial 500 kg units.
        double weightUnits = truck.getTotalWeight() / 500;

        double weightCharge = weightUnits * 10;

        return axleCharge + weightCharge;
    }

}
