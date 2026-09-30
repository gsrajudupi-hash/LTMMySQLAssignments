package com.tollbooth;

/**
 * Main
 *
 * @author Prathamesh
 */
public class Main {
    static void main() {
        try {

            TollCalculator calculator =
                    new StandardTollCalculator();

            TollBooth booth =
                    new TollBooth(calculator);

            Vehicle truck1 =
                    new Vehicle("TRK001", "Ford", 5, 12500);

            Vehicle truck2 =
                    new Vehicle("TRK002", "Volvo", 4, 10000);

            booth.processTruck(truck1);

            booth.processTruck(truck2);

            booth.displayTotals();

            booth.collectReceipts();

            booth.displayTotals();

        } catch (IllegalArgumentException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }
}