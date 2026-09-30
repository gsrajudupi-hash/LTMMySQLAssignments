package com.bank.data;

import com.bank.model.*;

import java.time.LocalDateTime;
import java.util.*;

public final class SampleData {
    private SampleData() {
    }

    public static List<Customer> customers() {
        return new ArrayList<>(List.of(
                new Customer(101, "Rahul", "rahul@gmail.com", "Bangalore", "9876543210", "PREMIUM"), new Customer(102, "Priya", "priya@gmail.com", "Mangalore", "9876543211", "REGULAR"), new Customer(103, "Arun", "arun@gmail.com", "Mysore", "9876543212", "PREMIUM"), new Customer(104, "Sneha", "sneha@gmail.com", "Udupi", "9876543213", "REGULAR"), new Customer(105, "Kiran", "kiran@gmail.com", "Bangalore", "9876543214", "PREMIUM"), new Customer(106, "Anita", "anita@gmail.com", "Bangalore", "9876543215", "REGULAR"), new Customer(107, "Ramesh", "ramesh@gmail.com", "Udupi", "9876543216", "PREMIUM"), new Customer(108, "Deepa", "deepa@gmail.com", "Mysore", "9876543217", "REGULAR"), new Customer(109, "Suresh", "suresh@gmail.com", "Mangalore", "9876543218", "PREMIUM"), new Customer(110, "Meena", "meena@gmail.com", "Bangalore", "9876543219", "REGULAR")));
    }

    public static List<BankAccount> accounts() {
        return new ArrayList<>(List.of(
                new SavingsAccount(100001, 101, 85000, "ACTIVE"), new CurrentAccount(100002, 101, 125000, "ACTIVE"), new SavingsAccount(100003, 102, 70000, "ACTIVE"), new LoanAccount(100004, 102, 200000, "ACTIVE"), new CurrentAccount(100005, 103, 160000, "ACTIVE"), new SavingsAccount(100006, 104, 45000, "ACTIVE"), new SavingsAccount(100007, 105, 210000, "ACTIVE"), new CurrentAccount(100008, 106, 95000, "ACTIVE"), new LoanAccount(100009, 107, 300000, "ACTIVE"), new SavingsAccount(100010, 107, 110000, "ACTIVE"), new CurrentAccount(100011, 108, 135000, "ACTIVE"), new SavingsAccount(100012, 109, 75000, "ACTIVE"), new CurrentAccount(100013, 109, 185000, "ACTIVE"), new SavingsAccount(100014, 110, 55000, "ACTIVE"), new LoanAccount(100015, 105, 250000, "ACTIVE")));
    }

    public static List<Transaction> transactions() {
        List<Transaction> list = new ArrayList<>();
        String[] types = {"DEPOSIT", "WITHDRAW", "TRANSFER", "INTEREST", "LOAN_PAYMENT"};
        double[] amounts = {15000, 5000, 25000, 1500, 30000, 60000, 12000, 75000, 8000, 45000};
        long[] acc = {100001, 100002, 100003, 100004, 100005, 100006, 100007, 100008, 100009, 100010, 100011, 100012, 100013, 100014, 100015};
        for (int i = 0; i < 30; i++)
            list.add(new Transaction(5001 + i, acc[i % acc.length], types[i % types.length], amounts[i % amounts.length], LocalDateTime.now().minusDays(30 - i), "Seed transaction " + (i + 1)));
        return list;
    }
}