package java8;

import java.util.Objects;

/**
 * Class Name : Customer
 * Created By : 10860819
 * Created Date : 9/27/2026
 * Created Time : 5:01 PM
 */
public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String city;
    private String phone;
    private String customerType;

//Parameterized constructor
    public Customer(int customerId, String name, String email, String city, String phone, String customerType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.city = city;
        this.phone = phone;
        this.customerType = customerType;
    }

    //getters
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

    //Setters
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


    //toString()
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


    //equls() & hashCode()
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Customer customer = (Customer) o;
        return customerId == customer.customerId &&
                Objects.equals(name, customer.name) && Objects.equals(email, customer.email) &&
                Objects.equals(city, customer.city) && Objects.equals(phone, customer.phone) &&
                Objects.equals(customerType, customer.customerType);
    }
    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, email, city, phone, customerType);
    }


    // Method to display customer details
    public void displayCustomer() {
        System.out.println("Customer ID : " + customerId);
        System.out.println("Name : " + name);
        System.out.println("Email : " + email);
        System.out.println("City : " + city);
        System.out.println("Phone : " + phone);
        System.out.println("Customer Type : " + customerType);
    }
}
