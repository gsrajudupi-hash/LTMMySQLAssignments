package domain;

import java.math.BigDecimal;

public record TollCalculation(
        BigDecimal axleCharge,
        long weightUnits,
        BigDecimal weightCharge,
        BigDecimal totalToll
) {
    public TollCalculation {
        if (axleCharge == null
                || weightCharge == null
                || totalToll == null) {
            throw new IllegalArgumentException(
                    "Toll calculation values cannot be null."
            );
        }

        if (weightUnits < 0) {
            throw new IllegalArgumentException(
                    "Weight units cannot be negative."
            );
        }
    }
}