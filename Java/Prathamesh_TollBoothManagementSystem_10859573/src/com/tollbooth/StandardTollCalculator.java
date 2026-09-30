package com.tollbooth;

/**
 * StandardTollCalculator
 *
 * @author Prathamesh
 */
public class StandardTollCalculator implements TollCalculator{

    private  static final double AXLE_RATE = 5.0;

    private  static final double WEIGHT_UNIT_RATE = 10.0;

    private  static final double WEIGHT_UNIT =500;


    @Override
    public double calculateToll(Vehicle vehicle) {

        double axleCharge = vehicle.getNumberOfAxles() * AXLE_RATE;

        int weightUnits = (int) (vehicle.getTotalWeight() / WEIGHT_UNIT);

        double weightCharge = weightUnits * WEIGHT_UNIT_RATE;

        return axleCharge + weightCharge;
    }
}