public class Truck {

    // Truck details are validated at creation and remain unchanged.
    private final String truckId;
    private final String truckMake;
    private final int numberOfAxle;
    private final double totalWeight;

    public Truck(String truckId, String truckMake, int numberOfAxle, double totalWeight) {
        // Reject invalid truck data before storing it.
        if (truckId == null || truckId.isBlank()){
            throw new InvalidTruckException("Truck ID cannot be empty");
        }
        if (truckMake == null || truckMake.isBlank()){
            throw new InvalidTruckException("Truck make cannot be empty");
        }
        if (numberOfAxle <= 0 ){
            throw new InvalidTruckException("Number of Axles must be greater than 0");
        }
        if (!Double.isFinite(totalWeight) || totalWeight <= 0) {
            throw new InvalidTruckException("Total weight must be a finite value greater than 0");
        }

        this.truckId = truckId;
        this.truckMake = truckMake;
        this.numberOfAxle = numberOfAxle;
        this.totalWeight = totalWeight;
    }

    public String getTruckId() {
        return truckId;
    }
    public String getTruckMake() {
        return truckMake;
    }
    public int getNumberOfAxle() {
        return numberOfAxle;
    }
    public double getTotalWeight() {
        return totalWeight;
    }

}
