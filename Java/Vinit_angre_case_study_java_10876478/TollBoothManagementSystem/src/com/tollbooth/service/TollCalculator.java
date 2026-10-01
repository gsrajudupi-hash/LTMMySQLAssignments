package com.tollbooth.service;

import com.tollbooth.domain.Truck;

@FunctionalInterface
public interface TollCalculator {
	double calculateToll(Truck truck);
}
