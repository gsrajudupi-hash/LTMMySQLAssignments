package com.litmindtree.toolbooth;


public class TollBoothApplication {

    public static void main(String[] args) {

        try {

            TollBooth tollBooth = new TollBooth();

            Truck truck1 =
                    new Truck("T101",
                            "Ford",
                            5,
                            12500);

            Truck truck2 =
                    new Truck("T102",
                            "Tata",
                            4,
                            11500);

            tollBooth.processTruck(truck1);
            tollBooth.processTruck(truck2);

            tollBooth.displayTotals();

            tollBooth.collectReceipts();

        } catch (InvalidTruckDataException e) {
            System.out.println(e.getMessage());
        }
    }
}



