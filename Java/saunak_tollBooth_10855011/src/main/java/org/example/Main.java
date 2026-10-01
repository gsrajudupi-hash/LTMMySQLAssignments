package org.example;

import org.example.tollbooth.Truck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // toll calculation logic
    public static int calculateToll(Truck t){
        int partA, partB;
        int noOfAxels=t.getTruckAxel();
        int totalTruckWeight=t.getTruckWeight();
        //calculation of axel charges
        partA=noOfAxels*5;

        //calculation of 500kg weight units
        partB=totalTruckWeight/500;
        return partA+partB;
    }

    public static void displayCollection(List<Truck> trucks) {
        int totalTrucks = 0;
        int totalCollection = 0;
        int totalWeight = 0;
        for (var truck : trucks) {
            totalTrucks++;
            totalCollection += calculateToll(truck);
            totalWeight += truck.getTruckWeight();
        }
        System.out.println("========================================");
        System.out.println("TOTALS SINCE LAST COLLECTION ");
        System.out.println("========================================");

        System.out.println("Total Trucks :" + totalTrucks);
        System.out.println("Total Receipts :$" + totalCollection);

        System.out.println("========================================");
    }
        public static void welcomeScreen(){
        System.out.println("========================================");
        System.out.println("Select your options:");
        System.out.println("1. Scan a truck");
        System.out.println("2. Collect Receipts");
        System.out.println("3. Reset data");
        System.out.println("EXIT or exit to stop");
        System.out.println("========================================");
    }
    static void main() {
    String optionSelected;
    String status="running";
    Scanner sc=new Scanner(System.in);
    List<Truck> trucks=new ArrayList<>();
        System.out.println("========================================");
        System.out.println("********* Welcome To Tool Booth Management System **********");
        System.out.println("========================================");
    do{
        welcomeScreen();
        optionSelected=sc.nextLine();


        if(optionSelected.equals("1")){
            Truck t1=new Truck();
            System.out.println("======================================== TRUCK ARRIVAL ========================================");

            System.out.print("Truck Make : ");
            t1.setTruckMake(sc.nextLine());

            System.out.print("Number of Axles : ");
            t1.setTruckAxel(sc.nextInt());
            System.out.print("Total Weight in KG : ");
            t1.setTruckWeight(sc.nextInt());
            sc.nextLine();
            int result=calculateToll(t1);
            System.out.println("Toll Due : $"+result);

            System.out.println("==============================================================================================");
            trucks.add(t1);
        }
        else if(optionSelected.equals("2")){
           displayCollection(trucks);
        }
        else if(optionSelected.equals("3")){
            trucks.clear();
            displayCollection(trucks);
        }
        else if(optionSelected.equalsIgnoreCase("EXIT")) {
            break;
        }
        else {
            System.out.println("Please select correct options.");
        }
    }while(!optionSelected.equalsIgnoreCase("EXIT"));
        System.out.println("You have exited the program");
        ;

    }
}
