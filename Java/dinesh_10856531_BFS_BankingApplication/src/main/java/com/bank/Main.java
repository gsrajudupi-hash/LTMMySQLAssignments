package com.bank;

import com.bank.model.*;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BankingService service =
                new BankingService();
        Scanner scanner =
                new Scanner(System.in);
        loadSampleData(service);
        int choice;
        do {
            printMenu();
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print(
                                "Customer ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print(
                                "Name: ");
                        String name =
                                scanner.nextLine();
                        System.out.print(
                                "Email: ");
                        String email =
                                scanner.nextLine();
                        System.out.print(
                                "City: ");
                        String city =
                                scanner.nextLine();
                        System.out.print(
                                "Phone: ");
                        String phone =
                                scanner.nextLine();
                        System.out.print(
                                "Customer Type: ");
                        String type =
                                scanner.nextLine();
                        service.addCustomer(
                                new Customer(
                                        id,
                                        name,
                                        email,
                                        city,
                                        phone,
                                        type));
                    }
                    case 2 ->
                            service.displayCustomers();
                    case 3 -> {
                        System.out.print(
                                "Customer ID: ");
                        int id =
                                scanner.nextInt();
                        System.out.println(
                                service.searchCustomer(id));
                    }
                    case 4 -> {
                        System.out.print(
                                "Customer ID: ");
                        int customerId =
                                scanner.nextInt();
                        System.out.print(
                                "Account Number: ");
                        long accountNumber =
                                scanner.nextLong();
                        System.out.print(
                                "Opening Balance: ");
                        double balance =
                                scanner.nextDouble();
                        System.out.println(
                                "1. Savings");
                        System.out.println(
                                "2. Current");
                        System.out.println(
                                "3. Loan");
                        System.out.print(
                                "Account Type: ");
                        int type =
                                scanner.nextInt();
                        BankAccount account;
                        if (type == 1) {
                            account =
                                    new SavingsAccount(
                                            accountNumber,
                                            customerId,
                                            balance);
                        } else if (type == 2) {
                            account =
                                    new CurrentAccount(
                                            accountNumber,
                                            customerId,
                                            balance);
                        } else {
                            account =
                                    new LoanAccount(
                                            accountNumber,
                                            customerId,
                                            balance);
                        }
                        service.addAccount(account);
                    }
                    case 5 ->
                            service.displayAccounts();
                    case 6 -> {
                        System.out.print(
                                "Account Number: ");
                        long account =
                                scanner.nextLong();
                        System.out.print(
                                "Amount: ");
                        double amount =
                                scanner.nextDouble();
                        service.deposit(
                                account,
                                amount);
                    }
                    case 7 -> {
                        System.out.print(
                                "Account Number: ");
                        long account =
                                scanner.nextLong();
                        System.out.print(
                                "Amount: ");
                        double amount =
                                scanner.nextDouble();
                        service.withdraw(
                                account,
                                amount);
                    }
                    case 8 -> {
                        System.out.print(
                                "Source Account: ");
                        long source =
                                scanner.nextLong();
                        System.out.print(
                                "Target Account: ");
                        long target =
                                scanner.nextLong();
                        System.out.print(
                                "Amount: ");
                        double amount =
                                scanner.nextDouble();
                        service.transfer(
                                source,
                                target,
                                amount);
                    }
                    case 9 -> {
                        System.out.print(
                                "Account Number: ");
                        long account =
                                scanner.nextLong();
                        double balance =
                                service.checkBalance(
                                        account);
                        System.out.println(
                                "Balance = " + balance);
                    }
                    case 10 ->
                            service.displayTransactions();
                    case 11 ->
                            BankingReport
                                    .generateBankingDashboard(
                                            service);
                    case 12 -> {
                        System.out.println(
                                "\nCustomer Report");
                        service.streamCustomerOperations();
                        service.groupCustomersByCity();
                    }
                    case 13 -> {
                        System.out.println(
                                "\nAccount Type Report");
                        service.groupAccountsByType();
                        System.out.println(
                                "\nBalance By Account Type");
                        service.balanceByAccountType();
                    }
                    case 14 -> {
                        System.out.println(
                                "\nTransaction Report");
                        service.transactionAnalytics();
                        System.out.println(
                                "\nTransaction Count By Type");
                        service.transactionTypeReport();
                    }
                    case 15 -> {
                        System.out.print(
                                "Account Number: ");
                        long accountNumber =
                                scanner.nextLong();
                        BankAccount account =
                                service.findAccount(
                                        accountNumber);
                        Customer customer =
                                service.searchCustomer(
                                        account.getCustomerId());
                        System.out.println(
                                BankingReport
                                        .generateAccountStatement(
                                                account,
                                                customer));
                    }
                    case 16 -> {
                        System.out.println(
                                "Thank you for using Banking System.");
                    }
                    case 21 -> {
                        System.out.println(
                                BankingReport
                                        .classifyTransaction(
                                                "DEPOSIT"));
                        System.out.println(
                                BankingReport
                                        .classifyTransaction(
                                                "WITHDRAW"));
                        System.out.println(
                                BankingReport
                                        .classifyTransaction(
                                                "TRANSFER"));
                    }
                    default ->
                            System.out.println(
                                    "Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println(
                        "Error: " + e.getMessage());
            }
        } while (choice != 16);
        scanner.close();
    }
    // =====================================================
    // MENU
    // =====================================================
    private static void printMenu() {
        System.out.println("""
               =========================================
                   BANKING MANAGEMENT SYSTEM
               =========================================
               1.  Add Customer
               2.  Display All Customers
               3.  Search Customer
               4.  Add Bank Account
               5.  Display All Accounts
               6.  Deposit Money
               7.  Withdraw Money
               8.  Transfer Money
               9.  Check Account Balance
               10. Display Transaction History
               11. Banking Analytics
               12. Customer Report
               13. Account Type Report
               14. Transaction Report
               15. Generate Account Statement
               16. Exit
               =========================================
               """);
    }
    // =====================================================
    // SAMPLE DATA
    // =====================================================
    private static void loadSampleData(
            BankingService service) {
        service.addCustomer(
                new Customer(
                        101,
                        "Rahul",
                        "rahul@gmail.com",
                        "Bangalore",
                        "9876543210",
                        "PREMIUM"));
        service.addCustomer(
                new Customer(
                        102,
                        "Priya",
                        "priya@gmail.com",
                        "Mangalore",
                        "9876543211",
                        "REGULAR"));
        service.addCustomer(
                new Customer(
                        103,
                        "Arun",
                        "arun@gmail.com",
                        "Mysore",
                        "9876543212",
                        "PREMIUM"));
        service.addCustomer(
                new Customer(
                        104,
                        "Sneha",
                        "sneha@gmail.com",
                        "Udupi",
                        "9876543213",
                        "REGULAR"));
        service.addCustomer(
                new Customer(
                        105,
                        "Kiran",
                        "kiran@gmail.com",
                        "Bangalore",
                        "9876543214",
                        "PREMIUM"));
        service.addCustomer(
                new Customer(
                        106,
                        "Anand",
                        "anand@gmail.com",
                        "Chennai",
                        "9876543215",
                        "REGULAR"));
        service.addCustomer(
                new Customer(
                        107,
                        "Deepak",
                        "deepak@gmail.com",
                        "Madurai",
                        "9876543216",
                        "PREMIUM"));
        service.addCustomer(
                new Customer(
                        108,
                        "Meena",
                        "meena@gmail.com",
                        "Coimbatore",
                        "9876543217",
                        "REGULAR"));
        // Accounts
        service.addAccount(
                new SavingsAccount(
                        100001,
                        101,
                        85000));
        service.addAccount(
                new CurrentAccount(
                        100002,
                        102,
                        60000));
        service.addAccount(
                new SavingsAccount(
                        100003,
                        103,
                        125000));
        service.addAccount(
                new CurrentAccount(
                        100004,
                        104,
                        45000));
        service.addAccount(
                new SavingsAccount(
                        100005,
                        105,
                        95000));
        service.addAccount(
                new SavingsAccount(
                        100006,
                        106,
                        70000));
        service.addAccount(
                new CurrentAccount(
                        100007,
                        107,
                        150000));
        service.addAccount(
                new LoanAccount(
                        100008,
                        108,
                        200000));
    }
}