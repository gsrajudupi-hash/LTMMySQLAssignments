package com.bank.data;

import com.bank.model.*;
import java.time.LocalDateTime;
import java.util.*;

public final class SampleData {
    private SampleData() {
    }

    public static List<Customer> customers() {
        return new ArrayList<>(List.of(
                new Customer(101, "Rahul", "rahul@gmail.com", "Bangalore", "9876543210", "PREMIUM"),
                new Customer(102, "Priya", "priya@gmail.com", "Mangalore", "9876543211", "REGULAR"),
                new Customer(103, "Arun", "arun@gmail.com", "Mysore", "9876543212", "PREMIUM"),
                new Customer(104, "Sneha", "sneha@gmail.com", "Udupi", "9876543213", "REGULAR"),
                new Customer(105, "Kiran", "kiran@gmail.com", "Bangalore", "9876543214", "PREMIUM"),
                new Customer(106, "Ananya", "ananya@gmail.com", "Chennai", "9876543215", "REGULAR"),
                new Customer(107, "Vikram", "vikram@gmail.com", "Bangalore", "9876543216", "PREMIUM"),
                new Customer(108, "Meera", "meera@gmail.com", "Mangalore", "9876543217", "REGULAR"),
                new Customer(109, "Dev", "dev@gmail.com", "Hyderabad", "9876543218", "PREMIUM"),
                new Customer(110, "Asha", "asha@gmail.com", "Mysore", "9876543219", "REGULAR")));
    }

    public static List<BankAccount> accounts() {
        return new ArrayList<>(List.of(new SavingsAccount(100001, 101, 85000, "ACTIVE"),
                new CurrentAccount(100002, 101, 70000, "ACTIVE"), new SavingsAccount(100003, 102, 42000, "ACTIVE"),
                new LoanAccount(100004, 102, 200000, "ACTIVE"), new SavingsAccount(100005, 103, 135000, "ACTIVE"),
                new CurrentAccount(100006, 104, 55000, "ACTIVE"), new SavingsAccount(100007, 105, 98000, "ACTIVE"),
                new CurrentAccount(100008, 105, 45000, "ACTIVE"), new SavingsAccount(100009, 106, 25000, "ACTIVE"),
                new LoanAccount(100010, 107, 300000, "ACTIVE"), new SavingsAccount(100011, 107, 175000, "ACTIVE"),
                new CurrentAccount(100012, 108, 66000, "ACTIVE"), new SavingsAccount(100013, 109, 120000, "ACTIVE"),
                new CurrentAccount(100014, 110, 35000, "ACTIVE"), new SavingsAccount(100015, 110, 78000, "ACTIVE")));
    }

    public static List<Transaction> transactions() {
        List<Transaction> list = new ArrayList<>();
        String[] types = { "DEPOSIT", "WITHDRAW", "TRANSFER", "INTEREST", "LOAN_PAYMENT" };
        double[] amounts = { 10000, 5000, 65000, 1200, 15000, 25000, 8000, 90000, 4500, 20000 };
        for (int i = 1; i <= 30; i++) {
            long account = 100000 + (i % 15) + 1;
            String type = types[(i - 1) % types.length];
            double amount = amounts[(i - 1) % amounts.length];
            list.add(new Transaction(i, account, type, amount, LocalDateTime.now().minusDays(30 - i).minusHours(i),
                    "Sample " + type.toLowerCase().replace('_', ' ')));
        }
        return list;
    }
}
