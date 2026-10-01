package com.litmindtree.toolbooth;

public final class TollCalculator {

    private static final double AXLE_CHARGE = 5;
    private static final double WEIGHT_CHARGE = 10;

    private TollCalculator() {
    }

    public static double calculateToll(Vechile vehicle) {

        double axleAmount =
                vehicle.getNumberOfAxles() * AXLE_CHARGE;

        double weightUnits =
                vehicle.getTotalWeight() / 500;

        double weightAmount =
                weightUnits * WEIGHT_CHARGE;

        return axleAmount + weightAmount;
    }
}