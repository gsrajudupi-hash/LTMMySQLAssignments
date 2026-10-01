import java.util.Scanner;

/*
Author : 
Date: 
Project : 
*/
public class TollBooth {

    double receipts = 0l;
    int totalTrucks = 0;

    public double collectToll(Truck t) {
        int axleCharge = 5 * t.getNoOfAxles();
        float weightCharge = 10 * t.getWeight() / 500;
        double tollCharge = axleCharge + weightCharge;
        receipts += tollCharge;
        totalTrucks++;
        return tollCharge;
    }

    public void collectReceipts() {
        System.out.println("Total Trucks : " + totalTrucks);
        System.out.println("Total Receipts : $" + receipts);
        totalTrucks = 0;
        receipts = 0;
    }

    public static void main(String[] args) {
        int res = 0;
        TollBooth booth = new TollBooth();
        do {

            System.out.println("Press 1 to add info for truck");
            System.out.println("Press 2 to collect receipts");

            Scanner sc = new Scanner(System.in);
            res = sc.nextInt();
            switch (res) {
                case 1: {

                    System.out.println("Enter truck make, no of axles, total weight");
                    Truck t = new Truck(sc.next(), sc.nextInt(), sc.nextInt());
                    System.out.println("Truck arrived with info --");
                    System.out.println(t.toString());
                    System.out.println("Total toll : $" + booth.collectToll(t));
                    break;
                }

                case 2:
                    booth.collectReceipts();
                    break;
                case 3:
                    break;
            }
        }
        while (res != 3);

    }
}
