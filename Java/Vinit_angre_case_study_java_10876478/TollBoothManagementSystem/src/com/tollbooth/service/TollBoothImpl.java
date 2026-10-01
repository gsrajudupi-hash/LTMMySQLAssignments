package com.tollbooth.service;

import com.tollbooth.domain.Truck;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class TollBoothImpl implements TollBooth {
	private final TollCalculator calculator;
	private final Map<String, Truck> truckHistory = new LinkedHashMap<>();
	private int currentTruckCount;
	private double currentReceipts;

	public TollBoothImpl(TollCalculator calculator) {
		this.calculator = calculator;
	}

	@Override
	public double processTruck(Truck truck) {
		if (truck == null) {
			throw new IllegalArgumentException("Truck cannot be null");
		}
		double toll = calculator.calculateToll(truck);
		currentTruckCount++;
		currentReceipts += toll;
		truckHistory.put(truck.getId().toLowerCase(), truck);
		printArrival(truck, toll);
		return toll;
	}

	// Method overloading: process an array of trucks.
	public double processTruck(Truck[] trucks) {
		if (trucks == null) {
			throw new IllegalArgumentException("Truck array cannot be null");
		}
		double total = 0.0;
		for (Truck truck : trucks) {
			total += processTruck(truck);
		}
		return total;
	}

	private void printArrival(Truck truck, double toll) {
		System.out.println("========================================");
		System.out.println(" TRUCK ARRIVAL");
		System.out.println("========================================");
		System.out.printf("Truck ID : %s%n", truck.getId());
		System.out.printf("Truck Make : %s%n", truck.getMake());
		System.out.printf("Number of Axles: %d%n", truck.getNumberOfAxles());
		System.out.printf("Total Weight : %.0f kg%n", truck.getTotalWeightKg());
		System.out.printf("Toll Due : $%.2f%n", toll);
		System.out.println("========================================");
	}

	@Override
	public Optional<Truck> findTruckById(String id) {
		if (id == null || id.isBlank())
			return Optional.empty();
		return Optional.ofNullable(truckHistory.get(id.trim().toLowerCase()));
	}

	@Override
	public List<Truck> getProcessedTrucks() {
		List<Truck> trucks = new ArrayList<>(truckHistory.values());
		Collections.sort(trucks);
		return List.copyOf(trucks); // immutable result
	}

	public List<Truck> findHeavyTrucks(double minimumWeightKg) {
		return truckHistory.values().stream().filter(t -> t.getTotalWeightKg() >= minimumWeightKg).sorted().toList();
	}

	@Override
	public void displayTotals() {
		System.out.println("========================================");
		System.out.println(" TOTALS SINCE LAST COLLECTION");
		System.out.println("========================================");
		System.out.printf("Total Trucks : %d%n", currentTruckCount);
		System.out.printf("Total Receipts : $%.2f%n", currentReceipts);
		System.out.println("========================================");
	}

	@Override
	public void collectReceipts() {
		System.out.println("========================================");
		System.out.println(" COLLECTING RECEIPTS");
		System.out.println("========================================");
		System.out.printf("Total Trucks : %d%n", currentTruckCount);
		System.out.printf("Total Receipts : $%.2f%n", currentReceipts);
		System.out.println("========================================");
		currentTruckCount = 0;
		currentReceipts = 0.0;
		System.out.println("Totals have been reset.");
	}
}
