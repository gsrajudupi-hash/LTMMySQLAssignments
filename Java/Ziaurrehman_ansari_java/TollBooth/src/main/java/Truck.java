/*
Author : 
Date: 
Project : 
*/
public class Truck {

    private final String make;
    private final int noOfAxles;
    private final int weight;

    public Truck(String make, int noOfAxles, int weight) {
        this.make = make;
        this.noOfAxles = noOfAxles;
        this.weight = weight;
    }

    public String getMake() {
        return make;
    }

    public int getNoOfAxles() {
        return noOfAxles;
    }

    public int getWeight() {
        return weight;
    }

    public String toString(){
        return "Truck make : " + getMake() + "\n No.of Axles : " + getNoOfAxles() + "\nTotal Weight : " + getWeight();
    }
}
