package service;

import domain.TollCalculation;
import domain.Truck;

public interface TollCalculator {

    TollCalculation calculate(Truck truck);
}