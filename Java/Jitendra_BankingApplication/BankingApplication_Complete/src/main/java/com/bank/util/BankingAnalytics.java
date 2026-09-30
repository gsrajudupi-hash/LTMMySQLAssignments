package com.bank.util;

import com.bank.model.*;
import com.bank.record.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class BankingAnalytics {
    private final List<Customer> customers;
    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;

    public BankingAnalytics(List<Customer> c, List<BankAccount> a, List<Transaction> t) {
        customers = c;
        accounts = a;
        transactions = t;
    }

    public void demonstrateJava8Features() {
        Predicate<Customer> premium = c -> "PREMIUM".equals(c.getCustomerType());
        Predicate<Customer> bangalore = c -> "Bangalore".equalsIgnoreCase(c.getCity());
        Predicate<Customer> mangalore = c -> "Mangalore".equalsIgnoreCase(c.getCity());
        Predicate<Customer> idGt105 = c -> c.getCustomerId() > 105;
        Consumer<Customer> displayCustomer = System.out::println;
        Function<Customer, String> getCustomerName = Customer::getName;
        Supplier<Long> accountNumberGenerator = System::currentTimeMillis;
        customers.stream().filter(premium).forEach(displayCustomer);
        customers.stream().filter(bangalore.or(mangalore)).map(getCustomerName).forEach(System.out::println);
        System.out.println("Generated account number: " + accountNumberGenerator.get());
        System.out.println("Starts with A: " + customers.stream().filter(c -> c.getName().startsWith("A")).toList());
        System.out.println("IDs > 105: " + customers.stream().filter(idGt105).toList());
        System.out.println("Names: " + customers.stream().map(Customer::getName).toList());
        System.out.println("Name asc: " + customers.stream().sorted(Comparator.comparing(Customer::getName)).toList());
        System.out.println("Name desc: " + customers.stream().sorted(Comparator.comparing(Customer::getName).reversed()).toList());
        System.out.println("ID sort: " + customers.stream().sorted(Comparator.comparingInt(Customer::getCustomerId)).toList());
        System.out.println("Type/name sort: " + customers.stream().sorted(Comparator.comparing(Customer::getCustomerType).thenComparing(Customer::getName)).toList());
    }

    public Optional<BankAccount> highestBalance() {
        return accounts.stream().max(Comparator.comparingDouble(BankAccount::getBalance));
    }

    public Optional<BankAccount> lowestBalance() {
        return accounts.stream().min(Comparator.comparingDouble(BankAccount::getBalance));
    }

    public double totalBalance() {
        return accounts.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum);
    }

    public double averageBalance() {
        return accounts.stream().collect(Collectors.averagingDouble(BankAccount::getBalance));
    }

    public long premiumCount() {
        return customers.stream().filter(c -> "PREMIUM".equals(c.getCustomerType())).count();
    }

    public Map<String, List<Customer>> customersByCity() {
        return customers.stream().collect(Collectors.groupingBy(Customer::getCity));
    }

    public Map<String, Long> accountCountByType() {
        return accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
    }

    public Map<String, Double> balanceByType() {
        return accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance)));
    }

    public List<Customer> highValueCustomers(double threshold) {
        Map<Integer, Double> totals = accounts.stream().collect(Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
        return customers.stream().filter(c -> totals.getOrDefault(c.getCustomerId(), 0.0) > threshold).toList();
    }

    public Map<String, TransactionSummary> transactionSummary() {
        return transactions.stream().collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.collectingAndThen(Collectors.summarizingDouble(Transaction::getAmount), s -> new TransactionSummary("", s.getCount(), s.getSum(), s.getAverage())))).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new TransactionSummary(e.getKey(), e.getValue().count(), e.getValue().total(), e.getValue().average())));
    }

    public List<Customer> topThreeCustomers() {
        Map<Integer, Double> totals = accounts.stream().collect(Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
        return customers.stream().sorted(Comparator.comparingDouble((Customer c) -> totals.getOrDefault(c.getCustomerId(), 0.0)).reversed()).limit(3).toList();
    }

    public void demonstrateAllStreamOperations() {
        System.out.println("filter/map/sorted: " + customers.stream().filter(c -> c.getCustomerId() > 102).map(Customer::getName).sorted().toList());
        System.out.println("flatMap/distinct: " + customers.stream().flatMap(c -> Stream.of(c.getCity(), c.getCustomerType())).distinct().toList());
        System.out.println("skip/limit: " + customers.stream().skip(2).limit(3).toList());
        System.out.println("count/min/max/reduce: " + accounts.stream().count() + ", " + lowestBalance() + ", " + highestBalance() + ", " + totalBalance());
        System.out.println("collect/grouping/counting/summing/averaging: " + accountCountByType() + ", " + balanceByType() + ", " + averageBalance());
        System.out.println("partitioning: " + customers.stream().collect(Collectors.partitioningBy(c -> "PREMIUM".equals(c.getCustomerType()))));
        System.out.println("joining: " + customers.stream().map(Customer::getName).collect(Collectors.joining(", ")));
    }

    public String dashboard() {
        long deposits = transactions.stream().filter(t -> "DEPOSIT".equals(t.getTransactionType())).count(), withdrawals = transactions.stream().filter(t -> "WITHDRAW".equals(t.getTransactionType())).count();
        var summary = new AccountSummary(highestBalance().map(BankAccount::getAccountNumber).orElse(0L), highestBalance().map(BankAccount::getAccountType).orElse("N/A"), highestBalance().map(BankAccount::getBalance).orElse(0.0), "HIGHEST");
        return """
                ================= BANKING ANALYTICS DASHBOARD =================
                Total Customers       : %d
                Premium Customers     : %d
                Total Accounts        : %d
                Total Balance         : Rs. %,.2f
                Average Balance       : Rs. %,.2f
                Highest Account       : %s
                Total Transactions    : %d
                Deposit Count         : %d
                Withdrawal Count      : %d
                Accounts By Type      : %s
                Customers By City     : %s
                ===============================================================
                """.formatted(customers.size(), premiumCount(), accounts.size(), totalBalance(), averageBalance(), summary, transactions.size(), deposits, withdrawals, accountCountByType(), customersByCity().entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size())));
    }
}