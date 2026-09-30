public class TollBoothApplication {

    public static void main(String[] args) {

        TollBooth tollBooth = new TollBooth();

        try {
            TollTruck truck1 = new TollTruck(
                    "TRK-101",
                    "Ford",
                    5,
                    12_500
            );

            TollTruck truck2 = new TollTruck(
                    "TRK-102",
                    "Tata",
                    4,
                    10_000
            );

            // Process first collection cycle
            tollBooth.processTruck(truck1);
            tollBooth.processTruck(truck2);

            tollBooth.displayTotals();

            tollBooth.displayProcessedTrucks();

            tollBooth.collectReceipts();

            // Totals should now be zero
            tollBooth.displayTotals();

            TollTruck truck3 = new TollTruck(
                    "TRK-103",
                    "Volvo",
                    6,
                    15_000
            );

            // Process a new collection cycle
            tollBooth.processTruck(truck3);

            tollBooth.displayTotals();

        } catch (InvalidTruckDataException exception) {

            System.out.println(
                    "Invalid truck data: " +
                            exception.getMessage()
            );

        } catch (IllegalArgumentException exception) {

            System.out.println(
                    "Processing error: " +
                            exception.getMessage()
            );

        } catch (Exception exception) {

            System.out.println(
                    "Unexpected error: " +
                            exception.getMessage()
            );
        }
    }
}