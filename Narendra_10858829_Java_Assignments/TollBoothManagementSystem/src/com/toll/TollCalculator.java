package com.toll;
/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 11:43:59 pm
 * project  : TollBoothManagementSystem
 */

class TollCalculator {
	 
    // final constants
    private static final int AXLE_RATE = 5;
    private static final int WEIGHT_UNIT = 500;
    private static final int WEIGHT_RATE = 10;
 
    // Calculate toll
    public double calculateToll(Vehicle vehicle) {
 
        int axleCharge =
                vehicle.getNumberOfAxles() * AXLE_RATE;
 
        int weightUnits =
                vehicle.getTotalWeight() / WEIGHT_UNIT;
 
        int weightCharge =
                weightUnits * WEIGHT_RATE;
 
        return axleCharge + weightCharge;
    }
}
 
