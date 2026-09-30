package com.bank.util;

import com.bank.model.*;
import com.bank.record.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class BankingReport {
    private BankingReport() {
    }

    // Demonstrates Java 8 functional interfaces, streams, and analytics operations
    public static void demonstrateJava8(List<Customer> customers, List<BankAccount> accounts, List<Transaction> txns) {
        Predicate<Customer> premium = c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType());
        Predicate<Customer> bangalore = c -> "Bangalore".equalsIgnoreCase(c.getCity());
        Predicate<Customer> mangalore = c -> "Mangalore".equalsIgnoreCase(c.getCity());
        Predicate<Customer> idAbove105 = c -> c.getCustomerId() > 105;
        Consumer<Customer> displayCustomer = System.out::println;
        Function<Customer, String> getCustomerName = Customer::getName;

        System.out.println("\nPremium customers:");
        customers.stream()
                .filter(premium)
                .forEach(displayCustomer);

        System.out.println("\nBangalore customers:");
        customers.stream()
                .filter(bangalore)
                .forEach(displayCustomer);

        System.out.println("\nMangalore customers:");
        customers.stream()
                .filter(mangalore)
                .forEach(displayCustomer);

        System.out.println("\nNames beginning with A:");
        customers.stream().filter(c -> c.getName().startsWith("A")).forEach(displayCustomer);

        System.out.println("\nID greater than 105:");
        customers.stream().filter(idAbove105).forEach(displayCustomer);

        System.out.println("Names: " + customers.stream().map(getCustomerName).collect(Collectors.joining(", ")));

        System.out.println("Ascending: " + customers.stream().sorted(Comparator.comparing(Customer::getName)).map(Customer::getName).toList());

        System.out.println("Descending: " + customers.stream().sorted(Comparator.comparing(Customer::getName).reversed()).map(Customer::getName).toList());

        System.out.println("By ID: " + customers.stream().sorted(Comparator.comparingInt(Customer::getCustomerId)).toList());

        System.out.println("By type/name: " + customers.stream().sorted(Comparator.comparing(Customer::getCustomerType).thenComparing(Customer::getName)).toList());

        accounts.stream()
                .max(Comparator.comparingDouble(BankAccount::getBalance)).ifPresent(a -> System.out.println("Highest balance: " + a));

        accounts.stream().min(Comparator.comparingDouble(BankAccount::getBalance)).ifPresent(a -> System.out.println("Lowest balance: " + a));

        double total = accounts.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum);

        System.out.println("Total balance: " + total);
        System.out.println("Average balance: " + accounts.stream().mapToDouble(BankAccount::getBalance).average().orElse(0));
        System.out.println("Premium count: " + customers.stream().filter(premium).count());
        System.out.println("Customers by city: " + customers.stream().collect(Collectors.groupingBy(Customer::getCity)));
        System.out.println("Account count by type: " + accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting())));
        System.out.println("Balance by type: " + accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance))));

        Map<Integer, Double> customerTotals = accounts.stream().collect(Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
        System.out.println("High-value customers: " + customers.stream().filter(c -> customerTotals.getOrDefault(c.getCustomerId(), 0.0) > 100000).toList());
        System.out.println("Top three customers: " + customers.stream().sorted(Comparator.comparingDouble((Customer c) -> customerTotals.getOrDefault(c.getCustomerId(), 0.0)).reversed()).limit(3).toList());
        System.out.println("Premium partition: " + customers.stream().collect(Collectors.partitioningBy(premium)));

        System.out.println("All account numbers using flatMap: " + customers.stream()
                .flatMap(c -> accounts.stream().filter(a -> a.getCustomerId() == c.getCustomerId())).map(BankAccount::getAccountNumber).toList());
        System.out.println("Distinct cities: " + customers.stream().map(Customer::getCity).distinct().sorted().toList());
        System.out.println("Limit 3 names: " + customers.stream().map(Customer::getName).limit(3).toList());
        System.out.println("Skip 2 names: " + customers.stream().map(Customer::getName).skip(2).toList());

        transactionAnalysis(txns);
    }

    // Generates transaction statistics and reports using Stream API
    public static void transactionAnalysis(List<Transaction> txns) {
        Map<String, DoubleSummaryStatistics> summary = txns.stream().collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.summarizingDouble(Transaction::getAmount)));
        System.out.println("Transaction summary: " + summary);
        txns.stream().max(Comparator.comparingDouble(Transaction::getAmount)).ifPresent(t -> System.out.println("Highest transaction: " + t));
        txns.stream().min(Comparator.comparingDouble(Transaction::getAmount)).ifPresent(t -> System.out.println("Lowest transaction: " + t));
        System.out.println("Above 50000: " + txns.stream().filter(t -> t.getAmount() > 50000).toList());
        System.out.println("Latest five: " + txns.stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).limit(5).toList());
    }

    // Optional methods such as orElse, orElseGet, and orElseThrow
    public static void optionalDemo(List<Customer> customers) {
        Optional<Customer> found = customers.stream().filter(c -> c.getCustomerId() == 101).findFirst();
        System.out.println("orElse: " + found.orElse(new Customer(0, "Unknown", "", "", "", "REGULAR")));
        System.out.println("orElseGet: " + found.orElseGet(() -> new Customer(-1, "Generated", "", "", "", "REGULAR")));
        found.ifPresent(System.out::println);
        Customer required = found.orElseThrow(() -> new NoSuchElementException("Customer 101 missing"));
        System.out.println("orElseThrow result: " + required.getName());
    }

    // Java 17 records
    public static void recordDemo(Customer c, Transaction t) {
        CustomerRecord r1 = new CustomerRecord(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
        CustomerRecord r2 = new CustomerRecord(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
        TransactionRecord tr = new TransactionRecord(t.getTransactionId(), t.getAccountNumber(), t.getTransactionType(), t.getAmount());
        AccountSummary as = new AccountSummary(t.getAccountNumber(), "SUMMARY", t.getAmount());
        System.out.println(r1);
        System.out.println("Record accessor: " + r1.name());
        System.out.println("Record equals/hashCode: " + r1.equals(r2) + " / " + r1.hashCode());
        System.out.println(tr);
        System.out.println(as);
    }

    // Displays account details based on account subtype using pattern matching
    public static void showAccountSubtype(BankAccount account) {
        if (account instanceof SavingsAccount savings)
            System.out.println("Savings minimum: " + savings.minimumBalance());
        else if (account instanceof CurrentAccount current)
            System.out.println("Current minimum: " + current.minimumBalance());
        else if (account instanceof LoanAccount loan) System.out.println("Loan balance: " + loan.getBalance());
    }

    // Classifies transaction types using Java 17 switch expressions -- Categorizes Transactions
    public static String classify(String type) {
        return switch (type.toUpperCase()) {
            case "DEPOSIT", "INTEREST" -> "CREDIT";
            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
            case "TRANSFER" -> "TRANSFER";
            default -> "UNKNOWN";
        };
    }

    // Displays current date and time using Java Date and Time API
    public static void dateTimeDemo() {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("LocalDate: " + LocalDate.now());
        System.out.println("LocalTime: " + LocalTime.now());
        System.out.println("LocalDateTime: " + LocalDateTime.now().format(f));
    }

    // Creates a formatted bank account statement using text blocks
    public static String accountStatement(BankAccount a, Customer c) {
        return """
                ================================
                BANK ACCOUNT STATEMENT
                ================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : %.2f
                Status         : %s
                ================================
                """.formatted(a.getAccountNumber(), c.getName(), a.getAccountType(), a.getBalance(), a.getStatus());
    }

    // Generates customer information in JSON format using text blocks
    //Converts customer information into JSON format
    public static String customerJson(Customer c) {
        return """
                {
                  "customerId": %d,
                  "name": "%s",
                  "city": "%s",
                  "customerType": "%s"
                }
                """.formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
    }

    // Generates a banking analytics dashboard with customer, account, and transaction statistics
    //Generates a consolidated banking analytics dashboard
    public static void generateBankingDashboard(List<Customer> customers, List<BankAccount> accounts, List<Transaction> txns) {
        long premium = customers.stream().filter(c -> "PREMIUM".equals(c.getCustomerType())).count();
        DoubleSummaryStatistics balances = accounts.stream().collect(Collectors.summarizingDouble(BankAccount::getBalance));
        Map<String, Long> byType = accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
        Map<String, Long> byCity = customers.stream().collect(Collectors.groupingBy(Customer::getCity, Collectors.counting()));
        long deposits = txns.stream().filter(t -> "DEPOSIT".equals(t.getTransactionType())).count();
        long withdrawals = txns.stream().filter(t -> "WITHDRAW".equals(t.getTransactionType())).count();
        String dashboard = """
                ========== BANKING ANALYTICS ==========
                Total Customers : %d
                Premium Customers: %d
                Total Accounts  : %d
                Total Balance   : %.2f
                Average Balance : %.2f
                Highest Balance : %.2f
                Transactions    : %d
                Deposits        : %d
                Withdrawals     : %d
                Accounts by Type: %s
                Customers by City: %s
                =======================================
                """.formatted(customers.size(), premium, accounts.size(), balances.getSum(), balances.getAverage(), balances.getMax(), txns.size(), deposits, withdrawals, byType, byCity);
        System.out.println(dashboard);
    }
}