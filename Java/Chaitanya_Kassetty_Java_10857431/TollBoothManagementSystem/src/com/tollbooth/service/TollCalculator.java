package com.tollbooth.service;

import com.tollbooth.model.Truck;

/**
 * Defines the toll-calculation behavior.
 */
public interface TollCalculator {

    /**
     * Calculates the toll for the given truck.
     *
     * @param truck truck for which the toll must be calculated
     * @return calculated toll amount
     */
    double calculateToll(Truck truck);
}