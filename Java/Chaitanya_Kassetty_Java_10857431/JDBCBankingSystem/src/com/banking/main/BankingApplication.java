package com.banking.main;

import com.banking.model.Customer;
import com.banking.service.BankingService;
import com.banking.service.BankingServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingApplication {

    private static final Scanner SCANNER =
            new Scanner(System.in);

    private static final BankingService SERVICE =
            new BankingServiceImpl();

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            printMenu();

            int choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1 -> addCustomer();

                case 2 -> SERVICE.viewAllCustomers();

                case 3 -> findCustomer();

                case 4 -> updateCustomer();

                case 5 -> deleteCustomer();

                case 6 -> SERVICE.viewCustomerAccountDetails();

                case 7 -> SERVICE.viewCustomerAccountSummary();

                case 8 -> depositMoney();

                case 9 -> withdrawMoney();

                case 10 -> transferMoney();

                case 11 -> addCustomersBatch();

                case 12 -> {
                    running = false;
                    System.out.println(
                            "Exiting Banking Application."
                    );
                }

                default -> System.out.println(
                        "Invalid choice. Please select 1 to 12."
                );
            }
        }

        SCANNER.close();
    }

    // Displays all available banking operations
    private static void printMenu() {

        System.out.println();
        System.out.println(
                "================================================="
        );
        System.out.println(
                "            BANKING MANAGEMENT SYSTEM"
        );
        System.out.println(
                "================================================="
        );
        System.out.println("1. Add Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Find Customer By ID");
        System.out.println("4. Update Customer");
        System.out.println("5. Delete Customer");
        System.out.println(
                "6. Customer Account Details (JOIN)"
        );
        System.out.println(
                "7. Customer Account Summary (AGGREGATE)"
        );
        System.out.println(
                "8. Deposit Money (Stored Procedure)"
        );
        System.out.println(
                "9. Withdraw Money (Stored Procedure)"
        );
        System.out.println(
                "10. Transfer Money (JDBC Transaction)"
        );
        System.out.println(
                "11. Add Customers (Batch Processing)"
        );
        System.out.println("12. Exit");
        System.out.println(
                "================================================="
        );
    }

    // Accepts customer information and creates a new customer record
    private static void addCustomer() {

        System.out.println("\nAdd New Customer");

        String name =
                readRequiredText("Enter customer name: ");

        String email =
                readRequiredText("Enter email: ");

        String mobile =
                readRequiredText("Enter mobile number: ");

        String city =
                readRequiredText("Enter city: ");

        Customer customer = new Customer(
                name,
                email,
                mobile,
                city
        );

        SERVICE.addCustomer(customer);
    }

    // Finds and displays customer details using customer ID
    private static void findCustomer() {

        int customerId =
                readInteger("Enter customer ID: ");

        SERVICE.findCustomerById(customerId);
    }

    // Updates an existing customer record
    private static void updateCustomer() {

        System.out.println("\nUpdate Customer");

        int customerId =
                readInteger("Enter customer ID: ");

        String name =
                readRequiredText("Enter new name: ");

        String email =
                readRequiredText("Enter new email: ");

        String mobile =
                readRequiredText("Enter new mobile: ");

        String city =
                readRequiredText("Enter new city: ");

        Customer customer = new Customer(
                customerId,
                name,
                email,
                mobile,
                city
        );

        SERVICE.updateCustomer(customer);
    }

    // Deletes a customer using customer ID
    private static void deleteCustomer() {

        int customerId =
                readInteger("Enter customer ID to delete: ");

        SERVICE.deleteCustomer(customerId);
    }

    // Accepts deposit details and executes deposit operation
    private static void depositMoney() {

        int accountId =
                readInteger("Enter account ID: ");

        BigDecimal amount =
                readAmount("Enter deposit amount: ");

        SERVICE.depositMoney(accountId, amount);
    }

    // Accepts withdrawal details and executes withdrawal operation
    private static void withdrawMoney() {

        int accountId =
                readInteger("Enter account ID: ");

        BigDecimal amount =
                readAmount("Enter withdrawal amount: ");

        SERVICE.withdrawMoney(accountId, amount);
    }

    // Transfers money between two accounts
    private static void transferMoney() {

        int sourceAccountId =
                readInteger("Enter source account ID: ");

        int destinationAccountId =
                readInteger("Enter destination account ID: ");

        BigDecimal amount =
                readAmount("Enter transfer amount: ");

        SERVICE.transferMoney(
                sourceAccountId,
                destinationAccountId,
                amount
        );
    }

    // Accepts multiple customers and performs batch insert
    private static void addCustomersBatch() {

        int numberOfCustomers =
                readInteger(
                        "How many customers do you want to add? "
                );

        if (numberOfCustomers <= 0) {
            System.out.println(
                    "Number of customers must be greater than zero."
            );
            return;
        }

        List<Customer> customers = new ArrayList<>();

        for (int i = 1; i <= numberOfCustomers; i++) {

            System.out.println(
                    "\nEnter details for customer " + i
            );

            String name =
                    readRequiredText("Enter name: ");

            String email =
                    readRequiredText("Enter email: ");

            String mobile =
                    readRequiredText("Enter mobile: ");

            String city =
                    readRequiredText("Enter city: ");

            customers.add(
                    new Customer(
                            name,
                            email,
                            mobile,
                            city
                    )
            );
        }

        SERVICE.addCustomersBatch(customers);
    }

    // Reads and validates integer input from the user
    private static int readInteger(String message) {

        while (true) {

            try {
                System.out.print(message);

                String input =
                        SCANNER.nextLine().trim();

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Enter a whole number."
                );
            }
        }
    }

    // Reads and validates monetary amount input
    private static BigDecimal readAmount(
            String message) {

        while (true) {

            try {
                System.out.print(message);

                BigDecimal amount =
                        new BigDecimal(
                                SCANNER.nextLine().trim()
                        );

                if (amount.compareTo(
                        BigDecimal.ZERO) <= 0) {

                    System.out.println(
                            "Amount must be greater than zero."
                    );
                    continue;
                }

                return amount;

            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid amount. Enter a valid number."
                );
            }
        }
    }

    // Reads mandatory text input and prevents empty values
    private static String readRequiredText(
            String message) {

        while (true) {

            System.out.print(message);

            String value =
                    SCANNER.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "This value cannot be empty."
            );
        }
    }
}