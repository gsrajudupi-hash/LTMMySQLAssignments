package com.bank.service;

import com.bank.exception.*;
import com.bank.model.*;
import com.bank.record.TransactionRecord;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.*;

public class BankingService {
    private final List<Customer> customers;
    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;
    private final AtomicInteger txnSeq;

    public BankingService(List<Customer> c, List<BankAccount> a, List<Transaction> t) {
        customers = c;
        accounts = a;
        transactions = t;
        txnSeq = new AtomicInteger(t.stream().mapToInt(Transaction::getTransactionId).max().orElse(0) + 1);
    }

    public List<Customer> customers() {
        return customers;
    }

    public List<BankAccount> accounts() {
        return accounts;
    }

    public List<Transaction> transactions() {
        return transactions;
    }

    public void addCustomer(Customer c) {
        if (customers.stream().anyMatch(x -> x.getCustomerId() == c.getCustomerId()))
            throw new InvalidTransactionException("Customer ID already exists");
        customers.add(c);
    }

    public Customer findCustomer(int id) {
        return customers.stream().filter(c -> c.getCustomerId() == id).findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    public void addAccount(BankAccount a) {
        findCustomer(a.getCustomerId());
        if (accounts.stream().anyMatch(x -> x.getAccountNumber() == a.getAccountNumber()))
            throw new InvalidAccountException("Account already exists");
        accounts.add(a);
    }

    public BankAccount getAccount(long n) {
        return accounts.stream().filter(a -> a.getAccountNumber() == n).findFirst()
                .orElseThrow(() -> new InvalidAccountException("Invalid account: " + n));
    }

    private void validAmount(double a) {
        if (a <= 0)
            throw new InvalidTransactionException("Amount must be greater than zero");
    }

    private Transaction create(long n, String type, double amount, String description) {
        Transaction t = new Transaction(txnSeq.getAndIncrement(), n, type, amount, LocalDateTime.now(), description);
        transactions.add(t);
        new TransactionRecord(t.getTransactionId(), n, type, amount);
        return t;
    }

    public void deposit(long n, double amount) {
        validAmount(amount);
        BankAccount a = getAccount(n);
        a.setBalance(a.getBalance() + amount);
        create(n, "DEPOSIT", amount, "Cash deposit");
    }

    public void withdraw(long n, double amount) {
        validAmount(amount);
        BankAccount a = getAccount(n);
        if (a.getBalance() - amount < a.minimumBalance())
            throw new InsufficientBalanceException("Minimum balance of Rs " + a.minimumBalance() + " must remain");
        a.setBalance(a.getBalance() - amount);
        create(n, "WITHDRAW", amount, "Cash withdrawal");
    }

    public void transfer(long s, long d, double amount) {
        if (s == d)
            throw new InvalidTransactionException("Source and target must differ");
        BankAccount source = getAccount(s), target = getAccount(d);
        validAmount(amount);
        if (source.getBalance() - amount < source.minimumBalance())
            throw new InsufficientBalanceException("Insufficient transferable balance");
        source.setBalance(source.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);
        create(s, "TRANSFER", amount, "Transfer to " + d);
        create(d, "TRANSFER", amount, "Transfer from " + s);
    }

    public double calculateInterest(long n, double rate) {
        BankAccount a = getAccount(n);
        double interest = a.getBalance() * rate / 100;
        a.setBalance(a.getBalance() + interest);
        create(n, "INTEREST", interest, "Interest at " + rate + "%");
        return interest;
    }

    public double checkBalance(long n) {
        return getAccount(n).getBalance();
    }

    public List<BankAccount> searchAccounts(String type, Double min, Double max, String status) {
        return accounts.stream().filter(a -> type == null || a.getAccountType().equalsIgnoreCase(type))
                .filter(a -> min == null || a.getBalance() >= min).filter(a -> max == null || a.getBalance() <= max)
                .filter(a -> status == null || a.getStatus().equalsIgnoreCase(status)).toList();
    }

    public Map<Integer, Double> totalsByCustomer() {
        return accounts.stream().collect(
                Collectors.groupingBy(BankAccount::getCustomerId, Collectors.summingDouble(BankAccount::getBalance)));
    }
}