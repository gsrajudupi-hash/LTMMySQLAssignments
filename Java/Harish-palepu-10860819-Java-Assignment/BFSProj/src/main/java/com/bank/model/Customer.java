package com.bank.model;

import com.bank.exception.InvalidCustomerException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final Pattern PHONE = Pattern.compile("^\\+?\\d{10,15}$");

    private final int customerId;
    private final String name;
    private final String email;
    private final String city;
    private final String phone;
    private final CustomerType customerType;

    public Customer(int customerId, String name, String email, String city,
                    String phone, CustomerType customerType) throws InvalidCustomerException {
        if (customerId <= 0) {
            throw new InvalidCustomerException("Customer ID must be a positive number.");
        }
        this.customerId = customerId;
        this.name = requireText(name, "Customer name");
        this.city = requireText(city, "City");
        this.email = requireText(email, "Email");
        this.phone = requireText(phone, "Phone");
        if (!EMAIL.matcher(this.email).matches()) {
            throw new InvalidCustomerException("Email address is not valid: " + email);
        }
        if (!PHONE.matcher(this.phone).matches()) {
            throw new InvalidCustomerException("Phone number must contain 10 to 15 digits: " + phone);
        }
        if (customerType == null) {
            throw new InvalidCustomerException("Customer type is required.");
        }
        this.customerType = customerType;
    }

    private static String requireText(String value, String field) throws InvalidCustomerException {
        if (value == null || value.isBlank()) {
            throw new InvalidCustomerException(field + " is required.");
        }
        return value.trim();
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

    public CustomerType getCustomerType() {
        return customerType;
    }

    public boolean isPremium() {
        return customerType == CustomerType.PREMIUM;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Customer other && customerId == other.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public String toString() {
        return String.format("%-6d %-20s %-25s %-12s %-15s %-8s",
                customerId, name, email, city, phone, customerType);
    }
}
