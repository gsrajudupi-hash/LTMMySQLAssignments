package com.bank;

import com.bank.model.*;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;

import java.util.*;

public class Main {
    private final BankingService service = new BankingService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().run();
    }

    // Starts the banking application and handles menu operations
    private void run() {
        SampleData.load(service);
        System.out.println("BFSI Banking Management System loaded with sample data.");
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                switch (choice) {
                    case 1 -> addCustomer();
                    case 2 -> service.getCustomers().forEach(System.out::println);
                    case 3 -> System.out.println(service.findCustomer(readInt("Customer ID: ")));
                    case 4 -> addAccount();
                    case 5 -> service.getAccounts().forEach(System.out::println);
                    case 6 -> service.deposit(readLong("Account number: "), readDouble("Amount: "));
                    case 7 -> service.withdraw(readLong("Account number: "), readDouble("Amount: "));
                    case 8 -> service.transfer(readLong("Source: "), readLong("Target: "), readDouble("Amount: "));
                    case 9 -> System.out.println("Balance: " + service.checkBalance(readLong("Account number: ")));
                    case 10 -> service.getTransactions().forEach(System.out::println);
                    case 11 ->
                            BankingReport.generateBankingDashboard(service.getCustomers(), service.getAccounts(), service.getTransactions());
                    case 12 -> customerReport();
                    case 13 ->
                            BankingReport.demonstrateJava8(service.getCustomers(), service.getAccounts(), service.getTransactions());
                    case 14 -> BankingReport.transactionAnalysis(service.getTransactions());
                    case 15 -> statement();
                    case 16 -> fullFeatureDemo();
                    case 17 -> running = false;
                    default -> System.out.println("Choose 1 to 17.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Application closed.");
    }

    // Displays the main menu options to the user
    private void printMenu() {
        System.out.println("""
                \n1. Add Customer
                2. Display All Customers
                3. Search Customer
                4. Add Bank Account
                5. Display All Accounts
                6. Deposit Money
                7. Withdraw Money
                8. Transfer Money
                9. Check Account Balance
                10. Display Transaction History
                11. Banking Analytics
                12. Customer Report
                13. Account Type / Stream Report
                14. Transaction Report
                15. Generate Account Statement
                16. Java 8 + Java 17 Full Feature Demo
                17. Exit
                Enter choice: """);
    }

    // Reads customer details and adds a new customer
    private void addCustomer() {
        int id = readInt("ID: ");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Type PREMIUM/REGULAR: ");
        String type = scanner.nextLine().toUpperCase();
        service.addCustomer(new Customer(id, name, email, city, phone, type));
    }

    // Creates a new bank account for an existing customer
    private void addAccount() {
        BankAccount a = service.addAccount(readInt("Customer ID: "), readText("Type SAVINGS/CURRENT/LOAN: "), readDouble("Opening balance: "));
        System.out.println("Created: " + a);
    }

    // Displays customer information and JSON representation
    private void customerReport() {
        service.getCustomers().forEach(System.out::println);
        System.out.println(BankingReport.customerJson(service.getCustomers().get(0)));
    }

    // Generates and displays an account statement
    private void statement() {
        BankAccount a = service.findAccount(readLong("Account number: "));
        Customer c = service.findCustomer(a.getCustomerId());
        System.out.println(BankingReport.accountStatement(a, c));
    }

    // Demonstrates all Java 8 and Java 17 features implemented in the project
    private void fullFeatureDemo() {
        BankingReport.demonstrateJava8(service.getCustomers(), service.getAccounts(), service.getTransactions());
        BankingReport.optionalDemo(service.getCustomers());
        BankingReport.dateTimeDemo();
        if (!service.getTransactions().isEmpty())
            BankingReport.recordDemo(service.getCustomers().get(0), service.getTransactions().get(0));
        service.getAccounts().stream().limit(3).forEach(BankingReport::showAccountSubtype);
        System.out.println("DEPOSIT classification: " + BankingReport.classify("DEPOSIT"));
        BankingReport.generateBankingDashboard(service.getCustomers(), service.getAccounts(), service.getTransactions());
    }

    // Reads and returns text input from the user
    private String readText(String m) {
        System.out.print(m);
        return scanner.nextLine().trim();
    }

    // Reads and converts user input to an integer
    private int readInt(String m) {
        return Integer.parseInt(readText(m));
    }

    // Reads and converts user input to a long value
    private long readLong(String m) {
        return Long.parseLong(readText(m));
    }

    // Reads and converts user input to a double value
    private double readDouble(String m) {
        return Double.parseDouble(readText(m));
    }
}