/**
 * Author  : 10856807
 * Date    : 28-09-2026
 * Time    : 22:53
 * Project : TollBoothManagementSystem
 */
public class CommercialTruck implements Truck {

    private String truckId;
    private String make;
    private int numberOfAxles;
    private double totalWeight;

    public CommercialTruck(String truckId,
                           String make,
                           int numberOfAxles,
                           double totalWeight)
            throws InvalidTruckDataException {

        if (numberOfAxles <= 0) {
            throw new InvalidTruckDataException(
                    "Number of axles must be greater than 0");
        }

        if (totalWeight <= 0) {
            throw new InvalidTruckDataException(
                    "Weight must be greater than 0");
        }

        this.truckId = truckId;
        this.make = make;
        this.numberOfAxles = numberOfAxles;
        this.totalWeight = totalWeight;
    }

    @Override
    public String getTruckId() {
        return truckId;
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public int getNumberOfAxles() {
        return numberOfAxles;
    }

    @Override
    public double getTotalWeight() {
        return totalWeight;
    }
}
