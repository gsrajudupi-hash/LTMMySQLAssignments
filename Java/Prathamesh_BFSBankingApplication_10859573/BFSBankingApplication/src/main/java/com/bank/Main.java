package com.bank;

import com.bank.functional.BankingOperation;
import com.bank.model.*;
import com.bank.record.*;
import com.bank.service.BankingService;
import com.bank.util.*;

import java.util.*;

public class Main {
    private final Scanner in = new Scanner(System.in);
    private final BankingService service = new BankingService();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        SampleDataLoader.load(service);
        demoFeatures();
        while (true) {
            menu();
            try {
                switch (Integer.parseInt(in.nextLine())) {
                    case 1 -> addCustomer();
                    case 2 -> service.customers().forEach(System.out::println);
                    case 3 -> System.out.println(service.findCustomer(readInt("Customer ID: ")));
                    case 4 -> addAccount();
                    case 5 ->
                            service.accounts().forEach(a -> System.out.println(a + " | " + BankingReport.accountKind(a)));
                    case 6 -> service.deposit(readLong("Account: "), readDouble("Amount: "));
                    case 7 -> service.withdraw(readLong("Account: "), readDouble("Amount: "));
                    case 8 -> service.transfer(readLong("Source: "), readLong("Target: "), readDouble("Amount: "));
                    case 9 -> System.out.println("Balance: " + service.checkBalance(readLong("Account: ")));
                    case 10 -> history();
                    case 11 ->
                            System.out.println(BankingReport.dashboard(service.customers(), service.accounts(), service.transactions()));
                    case 12 -> service.customers().forEach(c -> System.out.println(BankingReport.customerJson(c)));
                    case 13 ->
                            service.accounts().stream().collect(java.util.stream.Collectors.groupingBy(BankAccount::getAccountType)).forEach((k, v) -> System.out.println(k + " -> " + v));
                    case 14 -> BankingAnalytics.transactionAnalysis(service.transactions());
                    case 15 -> statement();
                    case 16 -> {
                        System.out.println("Thank you. Application closed.");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void demoFeatures() {
        BankingOperation deposit = (amount, balance) -> balance + amount, withdraw = (amount, balance) -> balance - amount, interest = (rate, balance) -> balance + (balance * rate / 100);
        System.out.println("Lambda demo: " + deposit.execute(500, 1000) + ", " + withdraw.execute(200, 1000) + ", " + interest.execute(5, 1000));
        Customer c = service.customers().get(0);
        CustomerRecord cr = new CustomerRecord(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
        TransactionRecord tr = new TransactionRecord(1, 100001, "DEPOSIT", 100);
        System.out.println("Record demo: " + cr + " | " + tr);
        service.customers().stream().filter(x -> x.getCustomerId() == 101).findFirst().ifPresent(System.out::println);
        BankingAnalytics.demonstrate(service.customers(), service.accounts(), service.transactions());
    }

    private void menu() {
        System.out.println("""
                1.Add Customer  2.Display Customers  3.Search Customer  4.Add Account
                5.Display Accounts 6.Deposit 7.Withdraw 8.Transfer 9.Check Balance
                10.Transaction History 11.Banking Analytics 12.Customer Report
                13.Account Type Report 14.Transaction Report 15.Account Statement 16.Exit
                Enter choice:
                """);
    }

    private void addCustomer() {
        int id = readInt("ID: ");
        System.out.print("Name: ");
        String n = in.nextLine();
        System.out.print("Email: ");
        String e = in.nextLine();
        System.out.print("City: ");
        String c = in.nextLine();
        System.out.print("Phone: ");
        String p = in.nextLine();
        System.out.print("Type PREMIUM/REGULAR: ");
        String t = in.nextLine();
        service.addCustomer(new Customer(id, n, e, c, p, t));
        System.out.println("Customer added");
    }

    private void addAccount() {
        long n = readLong("Account number: ");
        int c = readInt("Customer ID: ");
        double b = readDouble("Opening balance: ");
        System.out.print("Type SAVINGS/CURRENT/LOAN: ");
        String t = in.nextLine().toUpperCase();
        BankAccount a = switch (t) {
            case "SAVINGS" -> new SavingsAccount(n, c, b, "ACTIVE");
            case "CURRENT" -> new CurrentAccount(n, c, b, "ACTIVE");
            case "LOAN" -> new LoanAccount(n, c, b, "ACTIVE");
            default -> throw new IllegalArgumentException("Invalid account type");
        };
        service.addAccount(a);
        System.out.println("Account added");
    }

    private void history() {
        System.out.println("1.All 2.Deposits 3.Withdrawals 4.Transfers 5.Above 50000 6.Sorted 7.Latest five");
        int c = readInt("Choice: ");
        var stream = service.transactions().stream();
        switch (c) {
            case 2 -> stream.filter(t -> "DEPOSIT".equals(t.getTransactionType())).forEach(System.out::println);
            case 3 -> stream.filter(t -> "WITHDRAW".equals(t.getTransactionType())).forEach(System.out::println);
            case 4 -> stream.filter(t -> "TRANSFER".equals(t.getTransactionType())).forEach(System.out::println);
            case 5 -> stream.filter(t -> t.getAmount() > 50000).forEach(System.out::println);
            case 6 -> stream.sorted(Comparator.comparingDouble(Transaction::getAmount)).forEach(System.out::println);
            case 7 ->
                    stream.sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).limit(5).forEach(System.out::println);
            default -> stream.forEach(System.out::println);
        }
    }

    private void statement() {
        BankAccount a = service.findAccount(readLong("Account: "));
        Customer c = service.findCustomer(a.getCustomerId());
        System.out.println(BankingReport.statement(a, c));
    }

    private int readInt(String p) {
        System.out.print(p);
        return Integer.parseInt(in.nextLine());
    }

    private long readLong(String p) {
        System.out.print(p);
        return Long.parseLong(in.nextLine());
    }

    private double readDouble(String p) {
        System.out.print(p);
        return Double.parseDouble(in.nextLine());
    }
}
