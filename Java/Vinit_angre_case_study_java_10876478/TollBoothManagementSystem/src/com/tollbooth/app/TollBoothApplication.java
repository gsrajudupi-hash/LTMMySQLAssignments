package com.tollbooth.app;

import com.tollbooth.domain.Truck;
import com.tollbooth.exception.InvalidTruckDataException;
import com.tollbooth.service.StandardTollCalculator;
import com.tollbooth.service.TollBoothImpl;

public final class TollBoothApplication {
	private TollBoothApplication() {
	}

	public static void main(String[] args) {
		TollBoothImpl booth = new TollBoothImpl(new StandardTollCalculator());
		try {
			Truck ford = new Truck("TRK-101", "Ford", 5, 12_500);
			Truck tata = new Truck("TRK-102", "Tata", 4, 10_000);
			// Array demonstration and overloaded method call.
			booth.processTruck(new Truck[] { ford, tata });
			booth.displayTotals();
			booth.findTruckById("TRK-101").ifPresentOrElse(truck -> System.out.println("Found: " + truck),
					() -> System.out.println("Truck not found"));
			System.out.println("Sorted truck history:");
			booth.getProcessedTrucks().forEach(System.out::println);
			System.out.println("Trucks weighing at least 11,000 kg:");
			booth.findHeavyTrucks(11_000).forEach(System.out::println);
			booth.collectReceipts();
			booth.displayTotals();
			// Deliberate validation failure.
			new Truck("TRK-103", "", 0, -100);
		} catch (InvalidTruckDataException exception) {
			System.err.println("Invalid truck data: " + exception.getMessage());
		} catch (RuntimeException exception) {
			System.err.println("Unexpected error: " + exception.getMessage());
		}
	}
}