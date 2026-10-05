package com.bank.util;

import com.bank.model.*;
import com.bank.service.BankingService;


import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public final class BankingReport {
    private BankingReport() { }

    public static void demonstrateJava8(BankingService service) {
        List<Customer> customers = service.getCustomers();
        List<BankAccount> accounts = service.getAccounts();

        Predicate<Customer> premium = c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType());
        Predicate<Customer> bangalore = c -> "Bangalore".equalsIgnoreCase(c.getCity());
        Predicate<Customer> mangalore = c -> "Mangalore".equalsIgnoreCase(c.getCity());
        Predicate<Customer> idAbove105 = c -> c.getCustomerId() > 105;
        Consumer<Customer> displayCustomer = System.out::println;
        Function<Customer, String> getCustomerName = Customer::getName;
        Supplier<Long> accountNumberGenerator = System::currentTimeMillis;

        System.out.println("Premium customers:");
        customers.stream().filter(premium).forEach(displayCustomer);
        System.out.println("Bangalore customers:");
        customers.stream().filter(bangalore).forEach(displayCustomer);
        System.out.println("Mangalore customers:");
        customers.stream().filter(mangalore).forEach(displayCustomer);
        System.out.println("Names beginning with A:");
        customers.stream().filter(c -> c.getName().startsWith("A")).forEach(displayCustomer);
        System.out.println("IDs greater than 105:");
        customers.stream().filter(idAbove105).forEach(displayCustomer);
        System.out.println("Names: " + customers.stream().map(getCustomerName).toList());
        System.out.println("Generated account number: " + accountNumberGenerator.get());

        System.out.println("Ascending: " + customers.stream().sorted(Comparator.comparing(Customer::getName)).toList());
        System.out.println("Descending: " + customers.stream().sorted(Comparator.comparing(Customer::getName).reversed()).toList());
        System.out.println("By ID: " + customers.stream().sorted(Comparator.comparingInt(Customer::getCustomerId)).toList());
        System.out.println("By type/name: " + customers.stream().sorted(Comparator.comparing(Customer::getCustomerType)
                .thenComparing(Customer::getName)).toList());

        BankAccount max = accounts.stream().max(Comparator.comparingDouble(BankAccount::getBalance)).orElseThrow();
        BankAccount min = accounts.stream().min(Comparator.comparingDouble(BankAccount::getBalance)).orElseThrow();
        double total = accounts.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum);
        double average = accounts.stream().mapToDouble(BankAccount::getBalance).average().orElse(0);
        long premiumCount = customers.stream().filter(premium).count();
        System.out.printf("Max=%s%nMin=%s%nTotal=%.2f%nAverage=%.2f%nPremium count=%d%n", max, min, total, average, premiumCount);

        System.out.println("Customers by city: " + customers.stream().collect(Collectors.groupingBy(Customer::getCity)));
        System.out.println("Accounts by type: " + accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting())));
        System.out.println("Balance by type: " + accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType,
                Collectors.summingDouble(BankAccount::getBalance))));

        Map<Integer, Double> balanceByCustomer = accounts.stream().collect(Collectors.groupingBy(BankAccount::getCustomerId,
                Collectors.summingDouble(BankAccount::getBalance)));
        System.out.println("High-value customers: " + customers.stream()
                .filter(c -> balanceByCustomer.getOrDefault(c.getCustomerId(), 0.0) > 100000).toList());

        Optional<Customer> found = customers.stream().filter(c -> c.getCustomerId() == 101).findFirst();
        found.ifPresent(c -> System.out.println("Optional ifPresent: " + c));
        System.out.println("Optional orElse: " + found.orElse(customers.get(0)));
        System.out.println("Optional orElseGet: " + found.orElseGet(() -> customers.get(0)));
        found.orElseThrow(() -> new IllegalStateException("Customer 101 not found"));

        System.out.println("Distinct cities: " + customers.stream().map(Customer::getCity).distinct().toList());
        System.out.println("Skip 2, limit 3: " + customers.stream().skip(2).limit(3).toList());
        System.out.println("Partition premium: " + customers.stream().collect(Collectors.partitioningBy(premium)));
        System.out.println("Joined names: " + customers.stream().map(Customer::getName).collect(Collectors.joining(", ")));
        System.out.println("FlatMap accounts: " + customers.stream()
                .flatMap(c -> accounts.stream().filter(a -> a.getCustomerId() == c.getCustomerId())).toList());
    }

    public static void transactionAnalysis(BankingService service) {
        List<Transaction> tx = service.getTransactions();
        double deposits = tx.stream().filter(t -> t.getTransactionType().equals("DEPOSIT"))
                .mapToDouble(Transaction::getAmount).sum();
        double withdrawals = tx.stream().filter(t -> t.getTransactionType().equals("WITHDRAW"))
                .mapToDouble(Transaction::getAmount).sum();
        System.out.println("Total deposits: " + deposits);
        System.out.println("Total withdrawals: " + withdrawals);
        System.out.println("Highest: " + tx.stream().max(Comparator.comparingDouble(Transaction::getAmount)).orElse(null));
        System.out.println("Lowest: " + tx.stream().min(Comparator.comparingDouble(Transaction::getAmount)).orElse(null));
        System.out.println("Count by type: " + tx.stream().collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.counting())));
        System.out.println("Average amount: " + tx.stream().collect(Collectors.averagingDouble(Transaction::getAmount)));
        System.out.println("Above 50000: " + tx.stream().filter(t -> t.getAmount() > 50000).toList());
        System.out.println("Summary: " + tx.stream().collect(Collectors.groupingBy(Transaction::getTransactionType,
                Collectors.summarizingDouble(Transaction::getAmount))));
    }

    public static void displayAccountType(BankAccount account) {
        if (account instanceof SavingsAccount savings) System.out.println("Savings minimum: " + savings.minimumBalance());
        else if (account instanceof CurrentAccount current) System.out.println("Current minimum: " + current.minimumBalance());
        else if (account instanceof LoanAccount loan) System.out.println("Loan minimum: " + loan.minimumBalance());
    }

    public static String classify(String transactionType) {
        return switch (transactionType.toUpperCase()) {
            case "DEPOSIT", "INTEREST" -> "CREDIT";
            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
            case "TRANSFER" -> "TRANSFER";
            default -> "UNKNOWN";
        };
    }

    public static String accountStatement(Customer customer, BankAccount account) {
        String generated = java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        return """
                =================================
                BANK ACCOUNT STATEMENT
                =================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : %.2f
                Status         : %s
                Generated On   : %s
                =================================
                """.formatted(account.getAccountNumber(), customer.getName(), account.getAccountType(),
                account.getBalance(), account.getStatus(), generated);
    }

    public static String customerJson(Customer c) {
        return """
                {"customerId": %d, "name": "%s", "city": "%s", "customerType": "%s"}
                """.formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
    }

    public static void dashboard(BankingService service) {
        var customers = service.getCustomers();
        var accounts = service.getAccounts();
        var transactions = service.getTransactions();
        double total = accounts.stream().mapToDouble(BankAccount::getBalance).sum();
        double avg = accounts.stream().mapToDouble(BankAccount::getBalance).average().orElse(0);
        double highest = accounts.stream().mapToDouble(BankAccount::getBalance).max().orElse(0);
        long premium = customers.stream().filter(c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType())).count();
        long deposits = transactions.stream().filter(t -> t.getTransactionType().equals("DEPOSIT")).count();
        long withdrawals = transactions.stream().filter(t -> t.getTransactionType().equals("WITHDRAW")).count();
        String dashboard = """
                ========== BANKING ANALYTICS DASHBOARD ==========
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
                ================================================
                """.formatted(customers.size(), premium, accounts.size(), total, avg, highest,
                transactions.size(), deposits, withdrawals,
                accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting())),
                customers.stream().collect(Collectors.groupingBy(Customer::getCity, Collectors.counting())));
        System.out.println(dashboard);
    }
}
