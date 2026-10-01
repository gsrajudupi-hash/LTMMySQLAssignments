package com.bank;

import com.bank.model.*;
import com.bank.service.BankingService;

// Loads sample customers, accounts, and transactions for testing and analytics
public final class SampleData {
    private SampleData() {
    }

    // Populates the application with predefined banking data.
    public static void load(BankingService s) {
        s.addCustomer(new Customer(101, "Rahul", "rahul@gmail.com", "Bangalore", "9876543210", "PREMIUM"));
        s.addCustomer(new Customer(102, "Priya", "priya@gmail.com", "Mangalore", "9876543211", "REGULAR"));
        s.addCustomer(new Customer(103, "Arun", "arun@gmail.com", "Mysore", "9876543212", "PREMIUM"));
        s.addCustomer(new Customer(104, "Sneha", "sneha@gmail.com", "Udupi", "9876543213", "REGULAR"));
        s.addCustomer(new Customer(105, "Kiran", "kiran@gmail.com", "Bangalore", "9876543214", "PREMIUM"));
        s.addCustomer(new Customer(106, "Anita", "anita@gmail.com", "Hyderabad", "9876543215", "REGULAR"));
        s.addCustomer(new Customer(107, "Vikram", "vikram@gmail.com", "Chennai", "9876543216", "PREMIUM"));
        s.addCustomer(new Customer(108, "Meera", "meera@gmail.com", "Pune", "9876543217", "REGULAR"));
        s.addCustomer(new Customer(109, "Ajay", "ajay@gmail.com", "Mumbai", "9876543218", "PREMIUM"));
        s.addCustomer(new Customer(110, "Neha", "neha@gmail.com", "Bangalore", "9876543219", "REGULAR"));
        for (int i = 0; i < 15; i++) {
            int cid = 101 + (i % 10);
            String type = i % 3 == 0 ? "CURRENT" : i % 3 == 1 ? "SAVINGS" : "LOAN";
            double bal = "CURRENT".equals(type) ? 60000 + i * 5000 : 25000 + i * 6000;
            s.addAccount(cid, type, bal);
        }
        for (int i = 0; i < 30; i++) {
            BankAccount a = s.getAccounts().get(i % 15);
            if (i % 3 == 0) s.deposit(a.getAccountNumber(), 10000 + i * 2000);
            else if (i % 3 == 1) {
                try {
                    s.withdraw(a.getAccountNumber(), 1000 + i * 100);
                } catch (RuntimeException ignored) {
                }
            } else s.calculateInterest(a.getAccountNumber(), 1.0);
        }
    }
}