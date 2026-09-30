package com.bank.util;

import com.bank.model.*;
import com.bank.record.AccountSummary;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class BankingAnalytics {
    private BankingAnalytics() {
    }

    public static void demonstrate(List<Customer> cs, List<BankAccount> as, List<Transaction> ts) {
        Predicate<Customer> premium = c -> "PREMIUM".equals(c.getCustomerType()), bangalore = c -> "Bangalore".equalsIgnoreCase(c.getCity()), mangalore = c -> "Mangalore".equalsIgnoreCase(c.getCity()), idGt105 = c -> c.getCustomerId() > 105;
        Consumer<Customer> displayCustomer = System.out::println;
        Function<Customer, String> getCustomerName = Customer::getName;
        Supplier<Long> accountNumberGenerator = System::currentTimeMillis;
        System.out.println("Premium customers:");
        cs.stream().filter(premium).forEach(displayCustomer);
        System.out.println("Bangalore: " + cs.stream().filter(bangalore).map(getCustomerName).toList());
        System.out.println("Mangalore: " + cs.stream().filter(mangalore).map(Customer::getName).toList());
        System.out.println("ID > 105: " + cs.stream().filter(idGt105).map(Customer::getName).toList());
        System.out.println("Names A: " + cs.stream().map(Customer::getName).filter(n -> n.startsWith("A")).toList());
        System.out.println("Name asc: " + cs.stream().sorted(Comparator.comparing(Customer::getName)).map(Customer::getName).toList());
        System.out.println("Name desc: " + cs.stream().sorted(Comparator.comparing(Customer::getName).reversed()).map(Customer::getName).toList());
        System.out.println("By ID: " + cs.stream().sorted(Comparator.comparingInt(Customer::getCustomerId)).toList());
        System.out.println("Type then name: " + cs.stream().sorted(Comparator.comparing(Customer::getCustomerType).thenComparing(Customer::getName)).toList());
        Optional<BankAccount> max = as.stream().max(Comparator.comparingDouble(BankAccount::getBalance)), min = as.stream().min(Comparator.comparingDouble(BankAccount::getBalance));
        double total = as.stream().map(BankAccount::getBalance).reduce(0.0, Double::sum);
        double avg = as.stream().mapToDouble(BankAccount::getBalance).average().orElse(0);
        System.out.printf("Max=%s%nMin=%s%nTotal=%.2f Average=%.2f PremiumCount=%d%n", max.orElse(null), min.orElse(null), total, avg, cs.stream().filter(premium).count());
        System.out.println("Customers by city: " + cs.stream().collect(Collectors.groupingBy(Customer::getCity)));
        System.out.println("Account count by type: " + as.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting())));
        System.out.println("Balance by type: " + as.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.summingDouble(BankAccount::getBalance))));
        Map<Integer, Double> balanceByCustomer = as.stream().collect(Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
        System.out.println("High value customers: " + cs.stream().filter(c -> balanceByCustomer.getOrDefault(c.getCustomerId(), 0.0) > 100000).map(Customer::getName).toList());
        System.out.println("Top 3: " + cs.stream().sorted(Comparator.comparingDouble((Customer c) -> balanceByCustomer.getOrDefault(c.getCustomerId(), 0.0)).reversed()).limit(3).map(Customer::getName).toList());
        System.out.println("Partition premium: " + cs.stream().collect(Collectors.partitioningBy(premium)));
        transactionAnalysis(ts);
        System.out.println("Distinct cities: " + cs.stream().map(Customer::getCity).distinct().sorted().toList());
        System.out.println("skip/limit: " + cs.stream().skip(2).limit(3).map(Customer::getName).toList());
        System.out.println("Joining: " + cs.stream().map(Customer::getName).collect(Collectors.joining(", ")));
        System.out.println("flatMap account summaries: " + cs.stream().flatMap(c -> as.stream().filter(a -> a.getCustomerId() == c.getCustomerId())).map(a -> new AccountSummary(a.getAccountNumber(), a.getAccountType(), a.getBalance(), a.getStatus())).limit(5).toList());
        System.out.println("Generated account no example: " + accountNumberGenerator.get());
    }

    public static void transactionAnalysis(List<Transaction> ts) {
        Map<String, List<Transaction>> by = ts.stream().collect(Collectors.groupingBy(Transaction::getTransactionType));
        double deposits = ts.stream().filter(t -> "DEPOSIT".equals(t.getTransactionType())).mapToDouble(Transaction::getAmount).sum(), withdrawals = ts.stream().filter(t -> "WITHDRAW".equals(t.getTransactionType())).mapToDouble(Transaction::getAmount).sum();
        System.out.printf("Deposits=%.2f Withdrawals=%.2f Average=%.2f%n", deposits, withdrawals, ts.stream().collect(Collectors.averagingDouble(Transaction::getAmount)));
        System.out.println("Highest=" + ts.stream().max(Comparator.comparingDouble(Transaction::getAmount)).orElse(null));
        System.out.println("Lowest=" + ts.stream().min(Comparator.comparingDouble(Transaction::getAmount)).orElse(null));
        System.out.println("Count by type=" + ts.stream().collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.counting())));
        System.out.println("Above 50000=" + ts.stream().filter(t -> t.getAmount() > 50000).toList());
        System.out.println("Latest five=" + ts.stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).limit(5).toList());
        System.out.println("Summary=" + by.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Map.of("count", e.getValue().size(), "total", e.getValue().stream().mapToDouble(Transaction::getAmount).sum(), "average", e.getValue().stream().mapToDouble(Transaction::getAmount).average().orElse(0)))));
    }
}
