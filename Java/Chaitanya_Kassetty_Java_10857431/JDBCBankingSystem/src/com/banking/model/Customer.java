package com.banking.model;

public class Customer {

    private int customerId;
    private String customerName;
    private String email;
    private String mobile;
    private String city;

    public Customer() {
    }

    // Used when retrieving customer from database
    public Customer(int customerId, String customerName,
                    String email, String mobile, String city) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
    }

    // Used when adding a new customer
    public Customer(String customerName, String email,
                    String mobile, String city) {

        this.customerName = customerName;
        this.email = email;
        this.mobile = mobile;
        this.city = city;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %-4d | Name: %-15s | Email: %-25s | Mobile: %-12s | City: %s",
                customerId,
                customerName,
                email,
                mobile,
                city
        );
    }
}