package com.vikranth.tollbooth.service;

import com.vikranth.tollbooth.exception.InvalidTruckDataException;
import com.vikranth.tollbooth.model.CommercialTruck;
import com.vikranth.tollbooth.model.Truck;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class HighwayTollBoothTest {
    @Test
    void shouldCalculateSampleTollAs275() throws InvalidTruckDataException {
        TollBooth booth = new HighwayTollBooth();
        Truck truck = new CommercialTruck("TRK101", "Ford", 5, 12_500);
        assertEquals(new BigDecimal("275.00"), booth.calculateToll(truck));
    }

    @Test
    void shouldUpdateAndResetTotals() throws InvalidTruckDataException {
        HighwayTollBooth booth = new HighwayTollBooth();
        booth.processTruck(new CommercialTruck("TRK101", "Ford", 5, 12_500));
        booth.processTruck(new CommercialTruck("TRK102", "Volvo", 4, 11_500));
        assertEquals(2, booth.getTotalTrucks());
        assertEquals(new BigDecimal("525.00"), booth.getTotalReceipts());
        booth.collectReceipts();
        assertEquals(0, booth.getTotalTrucks());
        assertEquals(new BigDecimal("0.00"), booth.getTotalReceipts());
        assertTrue(booth.getProcessedTrucks().isEmpty());
    }

    @Test
    void shouldRejectInvalidTruckData() {
        assertThrows(InvalidTruckDataException.class,
                () -> new CommercialTruck("", "Ford", 0, -1));
    }
}
