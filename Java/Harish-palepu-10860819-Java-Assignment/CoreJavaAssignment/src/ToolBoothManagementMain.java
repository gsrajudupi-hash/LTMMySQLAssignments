public class ToolBoothManagementMain {
    public static void main(String[] args) {
        try {
            // Create the toll booth and sample trucks.
            TollBooth tollBooth = new TollBooth();
            Truck truck1 = new Truck("T001", "Ford", 5, 12500);
            Truck truck2 = new Truck("T002", "Volvo", 4, 10000);
            Truck truck3 = new Truck("T003", "Tata", 6, 15000);

            // Process each truck, then display and collect the receipts.
            tollBooth.processTruck(truck1);
            tollBooth.processTruck(truck2);
            tollBooth.processTruck(truck3);

            tollBooth.displayTotals();
            tollBooth.collectReceipts();
            tollBooth.displayTotals();
        } catch (InvalidTruckException exception) {
            System.err.println("Unable to create truck: " + exception.getMessage());
        }
    }
}