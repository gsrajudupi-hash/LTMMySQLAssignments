package bank.util;

import bank.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class SampleData {

    private SampleData() {
    }

    public static List<Customer> createCustomers() {

        return new ArrayList<>(List.of(
                new Customer(101, "Rahul", "rahul@gmail.com",
                        "Bangalore", "9876543210", "PREMIUM"),

                new Customer(102, "Priya", "priya@gmail.com",
                        "Mangalore", "9876543211", "REGULAR"),

                new Customer(103, "Arun", "arun@gmail.com",
                        "Mysore", "9876543212", "PREMIUM"),

                new Customer(104, "Sneha", "sneha@gmail.com",
                        "Udupi", "9876543213", "REGULAR"),

                new Customer(105, "Kiran", "kiran@gmail.com",
                        "Bangalore", "9876543214", "PREMIUM"),

                new Customer(106, "Anita", "anita@gmail.com",
                        "Hyderabad", "9876543215", "REGULAR"),

                new Customer(107, "Vikram", "vikram@gmail.com",
                        "Bangalore", "9876543216", "PREMIUM"),

                new Customer(108, "Meera", "meera@gmail.com",
                        "Chennai", "9876543217", "REGULAR"),

                new Customer(109, "Asha", "asha@gmail.com",
                        "Mangalore", "9876543218", "PREMIUM"),

                new Customer(110, "Rohan", "rohan@gmail.com",
                        "Pune", "9876543219", "REGULAR")
        ));
    }

    public static List<BankAccount> createAccounts() {

        return new ArrayList<>(List.of(
                new SavingsAccount(100001, 101, 85000, "ACTIVE"),
                new CurrentAccount(100002, 101, 150000, "ACTIVE"),
                new SavingsAccount(100003, 102, 42000, "ACTIVE"),
                new LoanAccount(100004, 103, 200000, "ACTIVE"),
                new SavingsAccount(100005, 103, 95000, "ACTIVE"),
                new CurrentAccount(100006, 104, 61000, "ACTIVE"),
                new SavingsAccount(100007, 105, 175000, "ACTIVE"),
                new CurrentAccount(100008, 105, 83000, "ACTIVE"),
                new SavingsAccount(100009, 106, 27000, "ACTIVE"),
                new LoanAccount(100010, 107, 300000, "ACTIVE"),
                new SavingsAccount(100011, 107, 125000, "ACTIVE"),
                new CurrentAccount(100012, 108, 58000, "ACTIVE"),
                new SavingsAccount(100013, 109, 110000, "ACTIVE"),
                new CurrentAccount(100014, 110, 71000, "ACTIVE"),
                new SavingsAccount(100015, 110, 36000, "ACTIVE")
        ));
    }

    public static List<Transaction> createTransactions() {

        List<Transaction> transactions = new ArrayList<>();

        String[] transactionTypes = {
                "DEPOSIT",
                "WITHDRAW",
                "TRANSFER",
                "INTEREST",
                "LOAN_PAYMENT"
        };

        for (int index = 1; index <= 30; index++) {

            long accountNumber = 100001 + ((index - 1) % 15);

            String transactionType =
                    transactionTypes[(index - 1) % transactionTypes.length];

            double amount = 5000 + ((index * 3750) % 90000);

            transactions.add(
                    new Transaction(
                            index,
                            accountNumber,
                            transactionType,
                            amount,
                            LocalDateTime.now()
                                    .minusDays(30L - index)
                                    .withNano(0),
                            "Sample " + transactionType.toLowerCase()
                    )
            );
        }

        return transactions;
    }
}