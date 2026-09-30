package com.bank.util;

import com.bank.model.*;
import com.bank.service.BankingService;

import java.time.LocalDateTime;

public final class SampleDataLoader {
    private SampleDataLoader() {
    }

    public static void load(BankingService s) {
        String[][] c = {{"101", "Rahul", "rahul@gmail.com", "Bangalore", "9876543210", "PREMIUM"}, {"102", "Priya", "priya@gmail.com", "Mangalore", "9876543211", "REGULAR"}, {"103", "Arun", "arun@gmail.com", "Mysore", "9876543212", "PREMIUM"}, {"104", "Sneha", "sneha@gmail.com", "Udupi", "9876543213", "REGULAR"}, {"105", "Kiran", "kiran@gmail.com", "Bangalore", "9876543214", "PREMIUM"}, {"106", "Anita", "anita@gmail.com", "Bangalore", "9876543215", "PREMIUM"}, {"107", "Ramesh", "ramesh@gmail.com", "Udupi", "9876543216", "REGULAR"}, {"108", "Deepa", "deepa@gmail.com", "Mysore", "9876543217", "PREMIUM"}, {"109", "Suresh", "suresh@gmail.com", "Mangalore", "9876543218", "REGULAR"}, {"110", "Meena", "meena@gmail.com", "Bangalore", "9876543219", "PREMIUM"}};
        for (String[] x : c) s.addCustomer(new Customer(Integer.parseInt(x[0]), x[1], x[2], x[3], x[4], x[5]));
        BankAccount[] a = {new SavingsAccount(100001, 101, 85000, "ACTIVE"), new CurrentAccount(100002, 102, 150000, "ACTIVE"), new SavingsAccount(100003, 103, 95000, "ACTIVE"), new SavingsAccount(100004, 104, 65000, "ACTIVE"), new CurrentAccount(100005, 105, 125000, "ACTIVE"), new SavingsAccount(100006, 106, 180000, "ACTIVE"), new CurrentAccount(100007, 107, 85000, "ACTIVE"), new SavingsAccount(100008, 108, 220000, "ACTIVE"), new CurrentAccount(100009, 109, 95000, "ACTIVE"), new SavingsAccount(100010, 110, 125000, "ACTIVE"), new LoanAccount(100011, 101, 50000, "ACTIVE"), new SavingsAccount(100012, 102, 40000, "ACTIVE"), new CurrentAccount(100013, 103, 110000, "ACTIVE"), new LoanAccount(100014, 104, 30000, "ACTIVE"), new SavingsAccount(100015, 105, 70000, "ACTIVE")};
        for (BankAccount x : a) s.addAccount(x);
        String[] types = {"DEPOSIT", "WITHDRAW", "DEPOSIT", "TRANSFER", "INTEREST"};
        for (int i = 0; i < 30; i++) {
            long no = 100001 + (i % 15);
            double amt = 5000 + (i * 2750);
            String type = types[i % types.length];
            s.transactions().add(new Transaction(2001 + i, no, type, amt, LocalDateTime.now().minusDays(30 - i), "Sample " + type.toLowerCase()));
        }
    }
}
