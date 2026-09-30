package com.bank.model;

import java.util.Objects;

public class Customer {
    private final int customerId;
    private String name;
    private String email;
    private String city;
    private String phone;
    private String customerType;

    public Customer(int customerId, String name, String email, String city, String phone, String customerType) {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer that = (Customer) o;
        return customerId == that.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return "Customer{id=" + customerId + ", name='" + name + '\'' + ", email='" + email + '\'' +
                ", city='" + city + '\'' + ", phone='" + phone + '\'' + ", type='" + customerType + '\'' + '}';
    }
}