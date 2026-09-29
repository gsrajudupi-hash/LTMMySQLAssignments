package service;

import domain.TollCalculation;
import domain.Truck;

import java.math.BigDecimal;
import java.util.Objects;

public final class StandardTollCalculator implements TollCalculator {

    private static final BigDecimal AXLE_RATE =
            BigDecimal.valueOf(5);

    private static final BigDecimal WEIGHT_UNIT_RATE =
            BigDecimal.valueOf(10);

    private static final long WEIGHT_UNIT_IN_KG = 500;

    @Override
    public TollCalculation calculate(Truck truck) {
        Objects.requireNonNull(truck, "Truck cannot be null.");

        BigDecimal axleCharge = AXLE_RATE.multiply(
                BigDecimal.valueOf(truck.getNumberOfAxles())
        );

        long weightUnits =
                truck.getTotalWeightInKg() / WEIGHT_UNIT_IN_KG;

        BigDecimal weightCharge = WEIGHT_UNIT_RATE.multiply(
                BigDecimal.valueOf(weightUnits)
        );

        BigDecimal totalToll = axleCharge.add(weightCharge);

        return new TollCalculation(
                axleCharge,
                weightUnits,
                weightCharge,
                totalToll
        );
    }
}
