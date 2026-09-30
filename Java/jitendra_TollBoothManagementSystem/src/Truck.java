public class Truck {

    private final String truckId;
    private final String truckMake;
    private final int numberOfAxles;
    private final int totalWeight;

    public Truck(
            String truckId,
            String truckMake,
            int numberOfAxles,
            int totalWeight) {

        validateTruckData(
                truckId,
                truckMake,
                numberOfAxles,
                totalWeight
        );

        this.truckId = truckId;
        this.truckMake = truckMake;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    private void validateTruckData(
            String truckId,
            String truckMake,
            int numberOfAxles,
            int totalWeight) {

        if (truckId == null || truckId.isBlank()) {
            throw new InvalidTruckDataException(
                    "Truck ID cannot be null or empty."
            );
        }

        if (truckMake == null || truckMake.isBlank()) {
            throw new InvalidTruckDataException(
                    "Truck make cannot be null or empty."
            );
        }

        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than zero."
            );
        }

        if (totalWeight <= 0) {
            throw new InvalidTruckDataException(
                    "Total weight must be greater than zero."
            );
        }
    }

    public String getTruckId() {
        return truckId;
    }

    public String getTruckMake() {
        return truckMake;
    }

    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "truckId='" + truckId + '\'' +
                ", truckMake='" + truckMake + '\'' +
                ", numberOfAxles=" + numberOfAxles +
                ", totalWeight=" + totalWeight +
                '}';
    }
}