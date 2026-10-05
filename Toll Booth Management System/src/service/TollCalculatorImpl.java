package service;

import model.Truck;

/**
 * Calculates the toll based on the truck's
 * number of axles and total weight.
 */
public class TollCalculatorImpl implements TollCalculator {

    private static final double CHARGE_PER_AXLE = 5.00;
    private static final double CHARGE_PER_WEIGHT_UNIT = 10.00;
    private static final int WEIGHT_UNIT_IN_KG = 500;

    @Override
    public double calculateToll(Truck truck) {

        double axleCharge =
                truck.getNumberOfAxles() * CHARGE_PER_AXLE;

        int weightUnits =
                truck.getTotalWeight() / WEIGHT_UNIT_IN_KG;

        double weightCharge =
                weightUnits * CHARGE_PER_WEIGHT_UNIT;

        return axleCharge + weightCharge;
    }
}
