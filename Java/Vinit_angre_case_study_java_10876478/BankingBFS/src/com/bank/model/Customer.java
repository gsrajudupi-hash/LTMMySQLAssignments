package com.bank.model;

import java.util.Objects;

public class Customer {
	private final int customerId;
	private String name, email, city, phone, customerType;

	public Customer(int id, String name, String email, String city, String phone, String type) {
		this.customerId = id;
		this.name = name;
		this.email = email;
		this.city = city;
		this.phone = phone;
		this.customerType = type;
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

	public void setName(String v) {
		name = v;
	}

	public void setEmail(String v) {
		email = v;
	}

	public void setCity(String v) {
		city = v;
	}

	public void setPhone(String v) {
		phone = v;
	}

	public void setCustomerType(String v) {
		customerType = v;
	}

	@Override
	public String toString() {
		return "Customer{id=" + customerId + ", name='" + name + "', email='" + email + "', city='" + city
				+ "', phone='" + phone + "', type='" + customerType + "'}";
	}

	@Override
	public boolean equals(Object o) {
		return this == o || (o instanceof Customer c && customerId == c.customerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId);
	}
}
