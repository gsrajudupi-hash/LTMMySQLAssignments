package com.tollbooth.domain;

public abstract class Vehicle {
	private final String id;
	private String make;

	protected Vehicle(String id, String make) {
		this.id = id;
		this.make = make;
	}

	public final String getId() {
		return id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public abstract String getVehicleType();
}