package com.bank.service;

import com.bank.exception.*;
import com.bank.functional.BankingOperation;
import com.bank.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BankingService {
    private final List<Customer> customers;
    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;
    private final AtomicLong txSequence = new AtomicLong(9000);

    public BankingService(List<Customer> c, List<BankAccount> a, List<Transaction> t) {
        customers = c;
        accounts = a;
        transactions = t;
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void addCustomer(Customer c) {
        if (customers.contains(c)) throw new IllegalArgumentException("Customer already exists");
        customers.add(c);
    }

    public void addAccount(BankAccount a) {
        findCustomer(a.getCustomerId());
        if (accounts.contains(a)) throw new InvalidAccountException("Account already exists");
        accounts.add(a);
    }

    public Customer findCustomer(int id) {
        return customers.stream().filter(c -> c.getCustomerId() == id).findFirst().orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    public BankAccount findAccount(long n) {
        return accounts.stream().filter(a -> a.getAccountNumber() == n).findFirst().orElseThrow(() -> new InvalidAccountException("Account not found: " + n));
    }

    private void validateAmount(double a) {
        if (a <= 0) throw new InvalidTransactionException("Amount must be positive");
    }

    public double deposit(long n, double amount) {
        validateAmount(amount);
        BankAccount a = findAccount(n);
        BankingOperation op = (x, b) -> b + x;
        a.credit(amount);
        record(n, "DEPOSIT", amount, "Cash deposit");
        return op.execute(amount, a.getBalance() - amount);
    }

    public double withdraw(long n, double amount) {
        validateAmount(amount);
        BankAccount a = findAccount(n);
        if (a.getBalance() - amount < a.minimumBalance())
            throw new InsufficientBalanceException("Minimum balance required: " + a.minimumBalance());
        BankingOperation op = (x, b) -> b - x;
        double old = a.getBalance();
        a.debit(amount);
        record(n, "WITHDRAW", amount, "Cash withdrawal");
        return op.execute(amount, old);
    }

    public void transfer(long source, long target, double amount) {
        validateAmount(amount);
        if (source == target) throw new InvalidTransactionException("Source and target must differ");
        BankAccount s = findAccount(source), t = findAccount(target);
        if (s.getBalance() - amount < s.minimumBalance())
            throw new InsufficientBalanceException("Insufficient source balance");
        s.debit(amount);
        t.credit(amount);
        record(source, "TRANSFER", amount, "Transfer to " + target);
        record(target, "DEPOSIT", amount, "Transfer from " + source);
    }

    public double calculateInterest(long n, double rate) {
        validateAmount(rate);
        BankAccount a = findAccount(n);
        BankingOperation interest = (r, b) -> b * r / 100;
        double value = interest.execute(rate, a.getBalance());
        a.credit(value);
        record(n, "INTEREST", value, "Interest at " + rate + "%");
        return value;
    }

    public double checkBalance(long n) {
        return findAccount(n).getBalance();
    }

    private void record(long n, String type, double amount, String desc) {
        transactions.add(new Transaction(txSequence.incrementAndGet(), n, type, amount, LocalDateTime.now(), desc));
    }

    public List<BankAccount> searchAccounts(String type, Double minBalance, String status) {
        return accounts.stream().filter(a -> type == null || a.getAccountType().equalsIgnoreCase(type)).filter(a -> minBalance == null || a.getBalance() >= minBalance).filter(a -> status == null || a.getStatus().equalsIgnoreCase(status)).toList();
    }
}