package org.example.tollbooth;

import java.util.List;

public class Truck {


    private String truckMake;
    private Integer truckAxel;
    private Integer truckWeight;

    //all arguments constructor
    public Truck(String truckMake, Integer truckAxel, Integer truckWeight) {

        this.truckMake = truckMake;
        this.truckAxel = truckAxel;
        this.truckWeight = truckWeight;
    }

    public Truck() {

    }

    //generated all getters

    public String getTruckMake() {
        return truckMake;
    }

    public Integer getTruckAxel() {
        return truckAxel;
    }

    public Integer getTruckWeight() {
        return truckWeight;
    }

    public void setTruckMake(String truckMake) {
        this.truckMake = truckMake;
    }

    public void setTruckAxel(Integer truckAxel) {
        this.truckAxel = truckAxel;
    }

    public void setTruckWeight(Integer truckWeight) {
        this.truckWeight = truckWeight;
    }
}
