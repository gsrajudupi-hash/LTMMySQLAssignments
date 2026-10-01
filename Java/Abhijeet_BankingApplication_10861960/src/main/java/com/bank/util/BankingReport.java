package com.bank.util;

import com.bank.model.*;
import com.bank.record.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class BankingReport {
    private BankingReport() {
    }

    public static String classify(String t) {
        return switch (t.toUpperCase()) {
            case "DEPOSIT", "INTEREST" -> "CREDIT";
            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
            case "TRANSFER" -> "TRANSFER";
            default -> "UNKNOWN";
        };
    }

    public static void describeAccount(BankAccount a) {
        if (a instanceof SavingsAccount s)
            System.out.println("Savings account, minimum Rs " + s.minimumBalance());
        else if (a instanceof CurrentAccount c)
            System.out.println("Current account, minimum Rs " + c.minimumBalance());
        else if (a instanceof LoanAccount l)
            System.out.println("Loan account, outstanding Rs " + l.getBalance());
    }

    public static String statement(BankAccount a, Customer c) {
        return """
                ================================
                BANK ACCOUNT STATEMENT
                ================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : Rs %,.2f
                Status         : %s
                ================================
                """.formatted(a.getAccountNumber(), c.getName(), a.getAccountType(), a.getBalance(), a.getStatus());
    }

    public static String customerJson(Customer c) {
        return """
                {"customerId":%d,"name":"%s","city":"%s","customerType":"%s"}
                """.formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
    }

    public static String accountJson(BankAccount a) {
        return """
                {"accountNumber":%d,"customerId":%d,"accountType":"%s","balance":%.2f,"status":"%s"}
                """.formatted(a.getAccountNumber(), a.getCustomerId(), a.getAccountType(), a.getBalance(),
                a.getStatus());
    }

    public static String transactionJson(Transaction t) {
        return """
                {"transactionId":%d,"accountNumber":%d,"transactionType":"%s","amount":%.2f,"transactionDate":"%s"}
                """.formatted(t.getTransactionId(), t.getAccountNumber(), t.getTransactionType(), t.getAmount(),
                t.getTransactionDate());
    }

    public static void customerReport(List<Customer> cs, List<BankAccount> as) {
        Predicate<Customer> premium = c -> "PREMIUM".equalsIgnoreCase(c.getCustomerType()),
                bangalore = c -> "Bangalore".equalsIgnoreCase(c.getCity()),
                mangalore = c -> "Mangalore".equalsIgnoreCase(c.getCity()), idAbove105 = c -> c.getCustomerId() > 105;
        Consumer<Customer> displayCustomer = System.out::println;
        Function<Customer, String> getCustomerName = Customer::getName;
        Supplier<Long> generator = System::currentTimeMillis;
        System.out.println("Premium:");
        cs.stream().filter(premium).forEach(displayCustomer);
        System.out.println("Bangalore:");
        cs.stream().filter(bangalore).forEach(displayCustomer);
        System.out.println("Mangalore:");
        cs.stream().filter(mangalore).forEach(displayCustomer);
        System.out.println(
                "Names beginning A: " + cs.stream().map(getCustomerName).filter(n -> n.startsWith("A")).toList());
        System.out.println("ID > 105: " + cs.stream().filter(idAbove105).toList());
        System.out.println("Names: " + cs.stream().map(Customer::getName).toList());
        System.out.println("Ascending: " + cs.stream().sorted(Comparator.comparing(Customer::getName)).toList());
        System.out.println(
                "Descending: " + cs.stream().sorted(Comparator.comparing(Customer::getName).reversed()).toList());
        System.out.println("By ID: " + cs.stream().sorted(Comparator.comparingInt(Customer::getCustomerId)).toList());
        System.out.println("Type then name: " + cs.stream()
                .sorted(Comparator.comparing(Customer::getCustomerType).thenComparing(Customer::getName)).toList());
        System.out.println("Cities: " + cs.stream().collect(Collectors.groupingBy(Customer::getCity)));
        System.out.println("Partition premium: " + cs.stream().collect(Collectors.partitioningBy(premium)));
        System.out.println("Distinct cities: "
                + cs.stream().map(Customer::getCity).distinct().sorted().collect(Collectors.joining(", ")));
        System.out.println("Skip 2, limit 3: " + cs.stream().skip(2).limit(3).toList());
        System.out.println("Generated account no: " + generator.get());
        Optional<Customer> found = cs.stream().filter(c -> c.getCustomerId() == 101).findFirst();
        found.ifPresent(System.out::println);
        System.out.println(found.orElse(cs.get(0)));
        System.out.println(found.orElseGet(() -> new Customer(0, "Guest", "", "", "", "REGULAR")));
        found.orElseThrow();
        Map<Integer, Double> totals = as.stream().collect(
                Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
        System.out.println("High value: "
                + cs.stream().filter(c -> totals.getOrDefault(c.getCustomerId(), 0.0) > 100000).toList());
        System.out.println("Top 3: " + cs.stream()
                .map(c -> new AccountSummary(c.getCustomerId(), c.getName(),
                        totals.getOrDefault(c.getCustomerId(), 0.0)))
                .sorted(Comparator.comparingDouble(AccountSummary::totalBalance).reversed()).limit(3).toList());
        System.out.println("flatMap transactions/account demonstration: " + as.stream()
                .flatMap(a -> java.util.stream.Stream.of(a.getAccountNumber(), a.getCustomerId())).limit(10).toList());
    }

    public static void accountReport(List<BankAccount> as) {
        System.out
                .println("Max: " + as.stream().max(Comparator.comparingDouble(BankAccount::getBalance)).orElseThrow());
        System.out
                .println("Min: " + as.stream().min(Comparator.comparingDouble(BankAccount::getBalance)).orElseThrow());
        double total = as.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum);
        System.out.println("Total: " + total + ", average: "
                + as.stream().collect(Collectors.averagingDouble(BankAccount::getBalance)));
        System.out.println("Count/type: "
                + as.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting())));
        System.out.println("Balance/type: " + as.stream().collect(
                Collectors.groupingBy(BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance))));
    }

    public static void transactionReport(List<Transaction> ts) {
        System.out.println("Deposits total: " + sum(ts, "DEPOSIT") + ", withdrawals total: " + sum(ts, "WITHDRAW"));
        System.out.println(
                "Highest: " + ts.stream().max(Comparator.comparingDouble(Transaction::getAmount)).orElseThrow());
        System.out.println(
                "Lowest: " + ts.stream().min(Comparator.comparingDouble(Transaction::getAmount)).orElseThrow());
        System.out.println("Counts: "
                + ts.stream().collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.counting())));
        System.out.println("Average: " + ts.stream().collect(Collectors.averagingDouble(Transaction::getAmount)));
        System.out.println("Above Rs 50,000: " + ts.stream().filter(t -> t.getAmount() > 50000).toList());
        System.out.println("Summary: " + ts.stream()
                .collect(Collectors.groupingBy(Transaction::getTransactionType,
                        Collectors.teeing(Collectors.counting(), Collectors.summarizingDouble(Transaction::getAmount),
                                (count, stats) -> "count=" + count + ", total=" + stats.getSum() + ", avg="
                                        + stats.getAverage()))));
    }

    private static double sum(List<Transaction> ts, String type) {
        return ts.stream().filter(t -> type.equals(t.getTransactionType())).mapToDouble(Transaction::getAmount).sum();
    }

    public static String dashboard(List<Customer> cs, List<BankAccount> as, List<Transaction> ts) {
        long premium = cs.stream().filter(c -> "PREMIUM".equals(c.getCustomerType())).count();
        double total = as.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum),
                avg = as.stream().collect(Collectors.averagingDouble(BankAccount::getBalance)),
                high = as.stream().mapToDouble(BankAccount::getBalance).max().orElse(0), dep = sum(ts, "DEPOSIT"),
                with = sum(ts, "WITHDRAW");
        var byType = as.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
        var byCity = cs.stream().collect(Collectors.groupingBy(Customer::getCity, Collectors.counting()));
        CustomerRecord sample = new CustomerRecord(cs.get(0).getCustomerId(), cs.get(0).getName(), cs.get(0).getCity(),
                cs.get(0).getCustomerType());
        return """
                ========== BANKING ANALYTICS DASHBOARD ==========
                Customers: %d | Premium: %d | Accounts: %d
                Total balance: Rs %,.2f | Average: Rs %,.2f | Highest: Rs %,.2f
                Transactions: %d | Deposits: Rs %,.2f | Withdrawals: Rs %,.2f
                Accounts by type: %s
                Customers by city: %s
                Record sample: %s
                Transaction category sample: %s
                =================================================
                """.formatted(cs.size(), premium, as.size(), total, avg, high, ts.size(), dep, with, byType, byCity,
                sample, classify(ts.get(0).getTransactionType()));
    }
}