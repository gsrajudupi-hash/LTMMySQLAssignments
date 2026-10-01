package basic;

import java.util.Objects;

class Truck {
    private final String truckId;
    private final String make;
    private final int numberOfAxles;
    private final double totalWeightkg;

    public Truck(String truckId, String make, int numberOfAxles , double totalWeightkg) {
        if(truckId == null || truckId.isBlank()) {
            throw new IllegalArgumentException("Truck ID cannot be empty");
        }
        if(make == null || make.isBlank()) {
            throw new IllegalArgumentException("Truck make cannot be empty");
        }
        if(numberOfAxles <=0) {
            throw new IllegalArgumentException("number of axles must be positive");
        }
        if(!Double.isFinite(totalWeightkg) || totalWeightkg <=0) {
            throw new IllegalArgumentException("weight must be a positive finite number");
        }
        this.truckId=truckId;
        this.make=make;
        this.numberOfAxles=numberOfAxles;
        this.totalWeightkg=totalWeightkg;




    }
    public String getTruckId() {
        return truckId;
    }
    public String getMake() {
        return make;
    }
    public int getNumberOfAxles() {
        return numberOfAxles;
    }
    public double getTotalWeightkg() {
        return totalWeightkg;
    }
}
//2 . total calculation interface
interface TollCalculator{
    double calculateToll(Truck truck);
}
//3. implementation of toll calculation
class StandardTollCalculator implements TollCalculator {
    private static final double AXLE_RATE =5.0;
    private static final double WEIGHT_UNIT_RATE =10.0;
    private static final double WEIGHT_UNIT_KG =500.0;
    //private static final double AXLE_MALE =5.0;
    //private static final double AXLE_MALE =5.0;
    public double calculateToll( Truck truck) {
        Objects.requireNonNull(truck , "Truck cannot be null");
        double axleCharge = truck.getNumberOfAxles()* AXLE_RATE;
        double weightUnits =truck.getTotalWeightkg() / WEIGHT_UNIT_KG;
        double weightCharge =weightUnits*WEIGHT_UNIT_RATE;
        return axleCharge + weightCharge;
    }

}
//4. TOLL BOOTH
class TollBooth {
    private final TollCalculator tollCalculator;
    private int totalTrucks;
    private double totalReceipts;
    public TollBooth(TollCalculator tollCalculator) {
        this.tollCalculator = Objects.requireNonNull(tollCalculator ,"Calculator cannot be null");
        this.totalTrucks=0;
        this.totalReceipts=0.0;
    }
    public void processTruck(Truck truck) {
        Objects.requireNonNull(truck,"Truck cannot be null");
        double toll = tollCalculator.calculateToll(truck);
        totalTrucks++;
        totalReceipts+=toll;
        System.out.println("===================================");
        System.out.println("Truck ID :" + truck.getTruckId());
        System.out.println("Truck Make :" + truck.getMake());
        System.out.println("Number of Axles:" + truck.getNumberOfAxles());
        System.out.printf("Toll Due : $%.2f%n", toll);
        System.out.println("=================");
        displayTotals();
        //System.out.println();
    }
    public void displayTotals() {
        System.out.println("\n===================================");
        System.out.println("TOTALS SINCE LAST COLLECTION");
        System.out.println("==================================");
        System.out.println("Total Trucks : " + totalTrucks);
        System.out.printf("Total Receipts : $%.2f%n" , totalReceipts);
        System.out.println("===================================\n");
    }
    public void collectReceipts() {
        System.out.println("==================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("==================================");
        System.out.println("COLLECTING RECEIPTS");
        System.out.println("==================================");
        System.out.printf("Total Receipts : $%.2f%n" , totalReceipts);
        System.out.println("==================================");
         totalTrucks =0;
         totalReceipts =0.0;
         System.out.println("Totals have been reset");
         displayTotals();








    }

}

public class Assignments1 {
    public static void main(String[] args) {
        TollCalculator calculator = new StandardTollCalculator();
        TollBooth tollBooth = new TollBooth(calculator);
        Truck truck1 = new Truck("TRK101","Ford" ,5, 12500);
        Truck truck2 = new Truck("TRK102","Volvo" ,5, 11500);
        tollBooth.processTruck(truck1);
        tollBooth.processTruck(truck2);
        tollBooth.collectReceipts();
        Truck truck3 = new Truck("TRK103","volvo" ,4, 10000);
        tollBooth.processTruck(truck3);
    }


}
