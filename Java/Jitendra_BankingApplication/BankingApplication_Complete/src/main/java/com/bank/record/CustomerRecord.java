package com.bank.record;

public record CustomerRecord(int customerId, String name, String city, String customerType) {
    public CustomerRecord {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
    }
}
