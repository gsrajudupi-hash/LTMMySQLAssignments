package corejava;

public class TollBoothApplication {

    public static void main(String[] args) {

        TollBooth booth = new TollBooth();

        try {

            Truck truck1 =
                    new Truck("T101", "Ford", 5, 12500);

            Truck truck2 =
                    new Truck("T102", "Volvo", 4, 10000);

            TruckValidator.validate(truck1);
            TruckValidator.validate(truck2);

            booth.processTruck(truck1);
            booth.processTruck(truck2);

            booth.displayTotals();

            booth.displayTruckHistory();

            booth.collectReceipts();

            booth.displayTotals();

        } catch (InvalidTruckDataException e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}
