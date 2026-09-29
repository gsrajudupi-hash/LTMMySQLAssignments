package service;

import domain.CollectionReceipt;
import domain.TollCalculation;
import domain.Truck;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

public final class TollBooth {

    private final TollCalculator tollCalculator;
    private final Clock clock;

    private int totalTrucks;
    private BigDecimal totalReceipts;

    public TollBooth(
            TollCalculator tollCalculator,
            Clock clock
    ) {
        this.tollCalculator = Objects.requireNonNull(
                tollCalculator,
                "Toll calculator cannot be null."
        );

        this.clock = Objects.requireNonNull(
                clock,
                "Clock cannot be null."
        );

        this.totalReceipts = BigDecimal.ZERO;
    }

    public synchronized TollCalculation processTruck(Truck truck) {
        Objects.requireNonNull(truck, "Truck cannot be null.");

        TollCalculation calculation =
                tollCalculator.calculate(truck);

        totalTrucks++;
        totalReceipts = totalReceipts.add(
                calculation.totalToll()
        );

        return calculation;
    }

    public synchronized CollectionReceipt getCurrentTotals() {
        return new CollectionReceipt(
                totalTrucks,
                totalReceipts,
                LocalDateTime.now(clock)
        );
    }

    public synchronized CollectionReceipt collectReceipts() {
        CollectionReceipt receipt = new CollectionReceipt(
                totalTrucks,
                totalReceipts,
                LocalDateTime.now(clock)
        );

        reset();

        return receipt;
    }

    private void reset() {
        totalTrucks = 0;
        totalReceipts = BigDecimal.ZERO;
    }
}
