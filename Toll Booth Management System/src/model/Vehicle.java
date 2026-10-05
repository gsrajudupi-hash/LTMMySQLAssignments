package model;

/**
 * Abstract parent class for different vehicle types.
 */
public abstract class Vehicle {

    private final String vehicleId;
    private final String make;

    public Vehicle(String vehicleId, String make) {
        this.vehicleId = vehicleId;
        this.make = make;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getMake() {
        return make;
    }

    /**
     * Every child vehicle class must provide its details.
     */
    public abstract String getVehicleDetails();
}