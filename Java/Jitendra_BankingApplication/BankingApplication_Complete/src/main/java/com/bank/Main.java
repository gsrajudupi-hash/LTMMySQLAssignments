package com.bank;

import com.bank.data.SampleData;
import com.bank.exception.*;
import com.bank.model.*;
import com.bank.record.*;
import com.bank.service.BankingService;
import com.bank.util.*;

import java.util.*;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final BankingService service;
    private final BankingAnalytics analytics;

    public Main() {
        var c = SampleData.customers();
        var a = SampleData.accounts();
        var t = SampleData.transactions();
        service = new BankingService(c, a, t);
        analytics = new BankingAnalytics(c, a, t);
    }

    public static void main(String[] args) {
        Main app = new Main();
        if (args.length > 0 && "demo".equalsIgnoreCase(args[0])) app.runDemo();
        else app.runMenu();
    }

    private void runDemo() {
        analytics.demonstrateJava8Features();
        analytics.demonstrateAllStreamOperations();
        Optional<Customer> customer = service.getCustomers().stream().filter(c -> c.getCustomerId() == 101).findFirst();
        customer.ifPresent(System.out::println);
        System.out.println(customer.orElse(new Customer(0, "Default", "", "", "", "REGULAR")));
        System.out.println(customer.orElseGet(() -> new Customer(-1, "Generated", "", "", "", "REGULAR")));
        System.out.println(customer.orElseThrow());
        Customer c = service.findCustomer(101);
        BankAccount a = service.findAccount(100001);
        CustomerRecord cr = new CustomerRecord(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
        TransactionRecord tr = new TransactionRecord(1, a.getAccountNumber(), "DEPOSIT", 1000);
        System.out.println(cr);
        System.out.println(tr);
        System.out.println(BankingReport.describeAccount(a));
        System.out.println(BankingReport.classify("DEPOSIT"));
        System.out.println(BankingReport.customerJson(c));
        System.out.println(BankingReport.accountJson(a));
        System.out.println(analytics.dashboard());
        System.out.println("High value customers: " + analytics.highValueCustomers(100000));
        System.out.println("Top 3: " + analytics.topThreeCustomers());
        System.out.println("Transaction summary: " + analytics.transactionSummary());
    }

    private void runMenu() {
        boolean running = true;
        while (running) {
            printMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addCustomer();
                    case 2 -> service.getCustomers().forEach(System.out::println);
                    case 3 -> searchCustomer();
                    case 4 -> addAccount();
                    case 5 -> service.getAccounts().forEach(System.out::println);
                    case 6 -> money("deposit");
                    case 7 -> money("withdraw");
                    case 8 -> transfer();
                    case 9 -> balance();
                    case 10 -> history();
                    case 11 -> System.out.println(analytics.dashboard());
                    case 12 -> customerReport();
                    case 13 -> System.out.println(analytics.accountCountByType() + "\n" + analytics.balanceByType());
                    case 14 -> System.out.println(analytics.transactionSummary());
                    case 15 -> statement();
                    case 16 -> running = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                
                1. Add Customer  2. Display All Customers  3. Search Customer
                4. Add Bank Account  5. Display All Accounts  6. Deposit Money
                7. Withdraw Money  8. Transfer Money  9. Check Account Balance
                10. Display Transaction History  11. Banking Analytics  12. Customer Report
                13. Account Type Report  14. Transaction Report  15. Generate Account Statement  16. Exit
                Enter choice:""");
    }

    private void addCustomer() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Name: ");
        String n = scanner.nextLine();
        System.out.print("Email: ");
        String e = scanner.nextLine();
        System.out.print("City: ");
        String city = scanner.nextLine();
        System.out.print("Phone: ");
        String p = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        service.addCustomer(new Customer(id, n, e, city, p, type.toUpperCase()));
    }

    private void searchCustomer() {
        System.out.print("Customer ID: ");
        System.out.println(service.findCustomer(Integer.parseInt(scanner.nextLine())));
    }

    private void addAccount() {
        System.out.print("Account no: ");
        long n = Long.parseLong(scanner.nextLine());
        System.out.print("Customer ID: ");
        int c = Integer.parseInt(scanner.nextLine());
        System.out.print("Type SAVINGS/CURRENT/LOAN: ");
        String type = scanner.nextLine().toUpperCase();
        System.out.print("Opening balance: ");
        double b = Double.parseDouble(scanner.nextLine());
        BankAccount a = switch (type) {
            case "SAVINGS" -> new SavingsAccount(n, c, b, "ACTIVE");
            case "CURRENT" -> new CurrentAccount(n, c, b, "ACTIVE");
            case "LOAN" -> new LoanAccount(n, c, b, "ACTIVE");
            default -> throw new InvalidAccountException("Unsupported account type");
        };
        service.addAccount(a);
    }

    private void money(String operation) {
        System.out.print("Account no: ");
        long n = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: ");
        double a = Double.parseDouble(scanner.nextLine());
        System.out.println("New balance: " + (operation.equals("deposit") ? service.deposit(n, a) : service.withdraw(n, a)));
    }

    private void transfer() {
        System.out.print("Source: ");
        long s = Long.parseLong(scanner.nextLine());
        System.out.print("Target: ");
        long t = Long.parseLong(scanner.nextLine());
        System.out.print("Amount: ");
        service.transfer(s, t, Double.parseDouble(scanner.nextLine()));
        System.out.println("Transfer successful");
    }

    private void balance() {
        System.out.print("Account no: ");
        System.out.println("Balance: " + service.checkBalance(Long.parseLong(scanner.nextLine())));
    }

    private void history() {
        service.getTransactions().stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).forEach(System.out::println);
        System.out.println("Deposits: " + service.getTransactions().stream().filter(t -> "DEPOSIT".equals(t.getTransactionType())).toList());
        System.out.println("Withdrawals: " + service.getTransactions().stream().filter(t -> "WITHDRAW".equals(t.getTransactionType())).toList());
        System.out.println("Transfers: " + service.getTransactions().stream().filter(t -> "TRANSFER".equals(t.getTransactionType())).toList());
        System.out.println("Above 50000: " + service.getTransactions().stream().filter(t -> t.getAmount() > 50000).toList());
        System.out.println("Latest five: " + service.getTransactions().stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).limit(5).toList());
    }

    private void customerReport() {
        System.out.println("By city: " + analytics.customersByCity());
        System.out.println("Premium count: " + analytics.premiumCount());
        System.out.println("High value: " + analytics.highValueCustomers(100000));
        System.out.println("Top three: " + analytics.topThreeCustomers());
    }

    private void statement() {
        System.out.print("Account no: ");
        BankAccount a = service.findAccount(Long.parseLong(scanner.nextLine()));
        System.out.println(BankingReport.accountStatement(service.findCustomer(a.getCustomerId()), a));
    }
}