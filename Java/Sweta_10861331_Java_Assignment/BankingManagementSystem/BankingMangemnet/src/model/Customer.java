package model;

import java.util.Objects;

public class Customer {


    private int customerId;
    private String name;
    private String email;
    private String city;
    private String phone;
    private String customerType;

    // Constructor
    public Customer(int customerId,
                    String name,
                    String email,
                    String city,
                    String phone,
                    String customerType) {

        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.customerType = customerType;
    }

    // Getters
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

    // Setters
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
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null ||
                getClass() != obj.getClass()) {
            return false;
        }

        Customer customer = (Customer) obj;

        return customerId ==
                customer.customerId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(customerId);
    }
}