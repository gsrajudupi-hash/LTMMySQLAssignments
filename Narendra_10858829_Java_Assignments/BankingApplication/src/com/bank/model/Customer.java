package com.bank.model;

import java.util.Objects;


/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:31:02 pm
 * project  : BankingApplication
 */

public class Customer {
	 
    private int customerId;
    private String name;
    private String email;
    private String city;
    private String phone;
    private String customerType;
 
    public Customer(int customerId, String name, String email,
                    String city, String phone, String customerType) {
 
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.customerType = customerType;
    }
 
    public int getCustomerId() {
        return customerId;
    }
 
    public String getName() {
        return name;
    }
 
    public String getEmail() {
        return email;
    }
 
    public String getCity() {
        return city;
    }
 
    public String getPhone() {
        return phone;
    }
 
    public String getCustomerType() {
        return customerType;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public void setPhone(String phone) {
        this.phone = phone;
    }
 
    public void setCity(String city) {
        this.city = city;
    }
 
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", phone='" + phone + '\'' +
                ", customerType='" + customerType + '\'' +
                '}';
    }
 
    @Override
    public boolean equals(Object o) {
 
        if (this == o)
            return true;
 
        if (!(o instanceof Customer))
            return false;
 
        Customer customer = (Customer) o;
 
        return customerId == customer.customerId;
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}