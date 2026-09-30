package com.tollbooth.domain;

import com.tollbooth.exception.InvalidTruckDataException;
import java.util.Objects;

public final class Truck extends Vehicle implements Comparable<Truck> {
	private int numberOfAxles;
	private double totalWeightKg;

	public Truck(String id, String make, int numberOfAxles, double totalWeightKg) throws InvalidTruckDataException {
		super(validateText(id, "Truck ID"), validateText(make, "Truck make"));
		setNumberOfAxles(numberOfAxles);
		setTotalWeightKg(totalWeightKg);
	}

	// Overloaded constructor with a default make.
	public Truck(String id, int numberOfAxles, double totalWeightKg) throws InvalidTruckDataException {
		this(id, "Unknown", numberOfAxles, totalWeightKg);
	}

	private static String validateText(String value, String field) throws InvalidTruckDataException {
		if (value == null || value.isBlank()) {
			throw new InvalidTruckDataException(field + " cannot be blank");
		}
		return value.trim();
	}

	public int getNumberOfAxles() {
		return numberOfAxles;
	}

	public double getTotalWeightKg() {
		return totalWeightKg;
	}

	public void setNumberOfAxles(int numberOfAxles) throws InvalidTruckDataException {
		if (numberOfAxles <= 0) {
			throw new InvalidTruckDataException("Axles must be greater than zero");
		}
		this.numberOfAxles = numberOfAxles;
	}

	public void setTotalWeightKg(double totalWeightKg) throws InvalidTruckDataException {
		if (totalWeightKg <= 0) {
			throw new InvalidTruckDataException("Weight must be greater than zero");
		}
		this.totalWeightKg = totalWeightKg;
	}

	@Override
	public String getVehicleType() {
		return "Truck";
	}

	@Override
	public int compareTo(Truck other) {
		int result = getMake().compareToIgnoreCase(other.getMake());
		return result != 0 ? result : getId().compareToIgnoreCase(other.getId());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (!(object instanceof Truck truck))
			return false;
		return getId().equalsIgnoreCase(truck.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId().toLowerCase());
	}

	@Override
	public String toString() {
		return "Truck{id='%s', make='%s', axles=%d, weight=%.2f kg}".formatted(getId(), getMake(), numberOfAxles,
				totalWeightKg);
	}
}
