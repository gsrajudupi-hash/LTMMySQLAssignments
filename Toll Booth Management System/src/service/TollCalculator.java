package service;

import model.Truck;

/**
 * Defines the toll calculation contract.
 */
public interface TollCalculator {

    double calculateToll(Truck truck);
}