package com.vikranth.tollbooth.service;

import com.vikranth.tollbooth.model.Truck;
import java.math.BigDecimal;

public interface TollBooth {
    BigDecimal calculateToll(Truck truck);
    void processTruck(Truck truck);
    void displayTotals();
    void collectReceipts();
    void displayProcessedTrucks();
}
