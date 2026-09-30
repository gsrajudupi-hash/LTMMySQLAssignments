package com.tollbooth.service;

import com.tollbooth.domain.Truck;

public final class StandardTollCalculator implements TollCalculator {
	public static final double AXLE_RATE = 5.0;
	public static final double WEIGHT_UNIT_RATE = 10.0;
	public static final int WEIGHT_UNIT_KG = 500;

	@Override
	public double calculateToll(Truck truck) {
		long completeWeightUnits = (long) truck.getTotalWeightKg() / WEIGHT_UNIT_KG;
		return truck.getNumberOfAxles() * AXLE_RATE + completeWeightUnits * WEIGHT_UNIT_RATE;
	}
}
