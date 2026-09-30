package com.tollbooth.service;

import com.tollbooth.domain.Truck;
import java.util.List;
import java.util.Optional;

public interface TollBooth {
	double processTruck(Truck truck);

	Optional<Truck> findTruckById(String id);

	List<Truck> getProcessedTrucks();

	void displayTotals();

	void collectReceipts();
}
