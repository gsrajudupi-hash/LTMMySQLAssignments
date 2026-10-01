/**
 * Author  : 10856807
 * Date    : 28-09-2026
 * Time    : 22:48
 * Project : Default (Template) Project
 *///TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        try {

            TollBooth tollBooth = new HighwayTollBooth();

            Truck truck1 =
                    new CommercialTruck(
                            "TRK001",
                            "Ford",
                            5,
                            12500);

            Truck truck2 =
                    new CommercialTruck(
                            "TRK002",
                            "Tata",
                            4,
                            11500);

            tollBooth.processTruckArrival(truck1);
            tollBooth.processTruckArrival(truck2);

            tollBooth.displayTotals();

            tollBooth.collectReceipts();

        } catch (InvalidTruckDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}