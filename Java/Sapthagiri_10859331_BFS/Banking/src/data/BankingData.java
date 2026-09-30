package data;
import model.Customer;
import model.BankAccount;
import model.Transaction;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BankingData {

    // =========================================================
    // CUSTOMERS
    // =========================================================

    public static List<Customer> createCustomers() {

        return Arrays.asList(

                new Customer(
                        101,
                        "Rahul",
                        "rahul@gmail.com",
                        "Bangalore",
                        "9876543210",
                        "PREMIUM"
                ),

                new Customer(
                        102,
                        "Priya",
                        "priya@gmail.com",
                        "Mangalore",
                        "9876543211",
                        "REGULAR"
                ),

                new Customer(
                        103,
                        "Arun",
                        "arun@gmail.com",
                        "Mysore",
                        "9876543212",
                        "PREMIUM"
                ),

                new Customer(
                        104,
                        "Sneha",
                        "sneha@gmail.com",
                        "Udupi",
                        "9876543213",
                        "REGULAR"
                ),

                new Customer(
                        105,
                        "Kiran",
                        "kiran@gmail.com",
                        "Bangalore",
                        "9876543214",
                        "PREMIUM"
                ),

                // Additional customers

                new Customer(
                        106,
                        "Amit",
                        "amit@gmail.com",
                        "Bangalore",
                        "9876543215",
                        "REGULAR"
                ),

                new Customer(
                        107,
                        "Deepa",
                        "deepa@gmail.com",
                        "Mysore",
                        "9876543216",
                        "PREMIUM"
                ),

                new Customer(
                        108,
                        "Vikram",
                        "vikram@gmail.com",
                        "Mangalore",
                        "9876543217",
                        "REGULAR"
                ),

                new Customer(
                        109,
                        "Anjali",
                        "anjali@gmail.com",
                        "Udupi",
                        "9876543218",
                        "PREMIUM"
                ),

                new Customer(
                        110,
                        "Rohit",
                        "rohit@gmail.com",
                        "Bangalore",
                        "9876543219",
                        "REGULAR"
                )
        );
    }


    // =========================================================
    // BANK ACCOUNTS
    // =========================================================

    public static List<BankAccount> createAccounts() {

        return Arrays.asList(

                new BankAccount(
                        100001L,
                        101,
                        "SAVINGS",
                        85000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100002L,
                        101,
                        "CURRENT",
                        150000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100003L,
                        102,
                        "SAVINGS",
                        45000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100004L,
                        102,
                        "CURRENT",
                        75000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100005L,
                        103,
                        "SAVINGS",
                        125000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100006L,
                        103,
                        "CURRENT",
                        90000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100007L,
                        104,
                        "SAVINGS",
                        35000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100008L,
                        104,
                        "CURRENT",
                        60000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100009L,
                        105,
                        "SAVINGS",
                        200000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100010L,
                        105,
                        "CURRENT",
                        175000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100011L,
                        106,
                        "SAVINGS",
                        55000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100012L,
                        107,
                        "SAVINGS",
                        110000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100013L,
                        108,
                        "CURRENT",
                        80000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100014L,
                        109,
                        "SAVINGS",
                        95000,
                        "ACTIVE"
                ),

                new BankAccount(
                        100015L,
                        110,
                        "CURRENT",
                        130000,
                        "ACTIVE"
                )
        );
    }


    // =========================================================
    // TRANSACTIONS
    // =========================================================

    public static List<Transaction> createTransactions() {

        return Arrays.asList(

                new Transaction(
                        1,
                        100001L,
                        "DEPOSIT",
                        50000,
                        LocalDateTime.now().minusDays(1),
                        "Salary Credit"
                ),

                new Transaction(
                        2,
                        100001L,
                        "WITHDRAW",
                        10000,
                        LocalDateTime.now().minusDays(2),
                        "ATM Withdrawal"
                ),

                new Transaction(
                        3,
                        100002L,
                        "DEPOSIT",
                        100000,
                        LocalDateTime.now().minusDays(3),
                        "Business Credit"
                ),

                new Transaction(
                        4,
                        100002L,
                        "WITHDRAW",
                        25000,
                        LocalDateTime.now().minusDays(4),
                        "Business Expense"
                ),

                new Transaction(
                        5,
                        100003L,
                        "DEPOSIT",
                        30000,
                        LocalDateTime.now().minusDays(5),
                        "Salary Credit"
                ),

                new Transaction(
                        6,
                        100003L,
                        "WITHDRAW",
                        5000,
                        LocalDateTime.now().minusDays(6),
                        "ATM Withdrawal"
                ),

                new Transaction(
                        7,
                        100004L,
                        "DEPOSIT",
                        75000,
                        LocalDateTime.now().minusDays(7),
                        "Fund Transfer"
                ),

                new Transaction(
                        8,
                        100004L,
                        "WITHDRAW",
                        15000,
                        LocalDateTime.now().minusDays(8),
                        "Utility Payment"
                ),

                new Transaction(
                        9,
                        100005L,
                        "DEPOSIT",
                        120000,
                        LocalDateTime.now().minusDays(9),
                        "Salary Credit"
                ),

                new Transaction(
                        10,
                        100005L,
                        "WITHDRAW",
                        20000,
                        LocalDateTime.now().minusDays(10),
                        "ATM Withdrawal"
                ),

                new Transaction(
                        11,
                        100006L,
                        "DEPOSIT",
                        80000,
                        LocalDateTime.now().minusDays(11),
                        "Business Credit"
                ),

                new Transaction(
                        12,
                        100006L,
                        "WITHDRAW",
                        10000,
                        LocalDateTime.now().minusDays(12),
                        "Online Payment"
                ),

                new Transaction(
                        13,
                        100007L,
                        "DEPOSIT",
                        40000,
                        LocalDateTime.now().minusDays(13),
                        "Salary Credit"
                ),

                new Transaction(
                        14,
                        100007L,
                        "WITHDRAW",
                        5000,
                        LocalDateTime.now().minusDays(14),
                        "ATM Withdrawal"
                ),

                new Transaction(
                        15,
                        100008L,
                        "DEPOSIT",
                        60000,
                        LocalDateTime.now().minusDays(15),
                        "Fund Transfer"
                ),

                new Transaction(
                        16,
                        100008L,
                        "WITHDRAW",
                        12000,
                        LocalDateTime.now().minusDays(16),
                        "Utility Payment"
                ),

                new Transaction(
                        17,
                        100009L,
                        "DEPOSIT",
                        200000,
                        LocalDateTime.now().minusDays(17),
                        "Investment Credit"
                ),

                new Transaction(
                        18,
                        100009L,
                        "WITHDRAW",
                        50000,
                        LocalDateTime.now().minusDays(18),
                        "Investment Withdrawal"
                ),

                new Transaction(
                        19,
                        100010L,
                        "DEPOSIT",
                        150000,
                        LocalDateTime.now().minusDays(19),
                        "Business Credit"
                ),

                new Transaction(
                        20,
                        100010L,
                        "WITHDRAW",
                        30000,
                        LocalDateTime.now().minusDays(20),
                        "Business Expense"
                ),

                new Transaction(
                        21,
                        100011L,
                        "DEPOSIT",
                        55000,
                        LocalDateTime.now().minusDays(21),
                        "Salary Credit"
                ),

                new Transaction(
                        22,
                        100011L,
                        "WITHDRAW",
                        5000,
                        LocalDateTime.now().minusDays(22),
                        "ATM Withdrawal"
                ),

                new Transaction(
                        23,
                        100012L,
                        "DEPOSIT",
                        110000,
                        LocalDateTime.now().minusDays(23),
                        "Salary Credit"
                ),

                new Transaction(
                        24,
                        100012L,
                        "WITHDRAW",
                        15000,
                        LocalDateTime.now().minusDays(24),
                        "Online Payment"
                ),

                new Transaction(
                        25,
                        100013L,
                        "DEPOSIT",
                        80000,
                        LocalDateTime.now().minusDays(25),
                        "Business Credit"
                ),

                new Transaction(
                        26,
                        100013L,
                        "WITHDRAW",
                        10000,
                        LocalDateTime.now().minusDays(26),
                        "Utility Payment"
                ),

                new Transaction(
                        27,
                        100014L,
                        "DEPOSIT",
                        95000,
                        LocalDateTime.now().minusDays(27),
                        "Salary Credit"
                ),

                new Transaction(
                        28,
                        100014L,
                        "WITHDRAW",
                        20000,
                        LocalDateTime.now().minusDays(28),
                        "ATM Withdrawal"
                ),

                new Transaction(
                        29,
                        100015L,
                        "DEPOSIT",
                        130000,
                        LocalDateTime.now().minusDays(29),
                        "Business Credit"
                ),

                new Transaction(
                        30,
                        100015L,
                        "WITHDRAW",
                        25000,
                        LocalDateTime.now().minusDays(30),
                        "Business Expense"
                )
        );
    }
}
