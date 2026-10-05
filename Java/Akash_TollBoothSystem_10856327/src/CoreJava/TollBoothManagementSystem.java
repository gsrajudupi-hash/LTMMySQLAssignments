package CoreJava;



public class TollBoothManagementSystem {

    public static void main(String[] args) {

        TollBooth booth = new TollBooth();

        try {

            Vehicle truck1 =
                    new Truck("TR101", "Ford", 5, 12500);

            Vehicle truck2 =
                    new Truck("TR102", "Volvo", 4, 10000);

            booth.processTruck(truck1);

            System.out.println();

            booth.processTruck(truck2);

            System.out.println();

            booth.collectReceipts();

            System.out.println();

            booth.displayTotals();

        } catch (IllegalArgumentException e) {
            System.out.println("Error : " + e.getMessage());
        }
    }
}
