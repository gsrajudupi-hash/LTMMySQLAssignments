package com.bank.service;

import com.bank.exception.*;
import com.bank.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class BankingService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final AtomicInteger txId = new AtomicInteger(1000);

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
            throw new IllegalArgumentException("Customer ID already exists");
        customers.add(c);
    }

    public Customer findCustomer(int id) {
        return customers.stream().filter(c -> c.getCustomerId() == id).findFirst().orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    public void addAccount(BankAccount a) {
        findCustomer(a.getCustomerId());
        if (accounts.stream().anyMatch(x -> x.getAccountNumber() == a.getAccountNumber()))
            throw new IllegalArgumentException("Account already exists");
        accounts.add(a);
    }

    public BankAccount findAccount(long no) {
        return accounts.stream().filter(a -> a.getAccountNumber() == no).findFirst().orElseThrow(() -> new InvalidAccountException("Account not found: " + no));
    }

    private void valid(double a) {
        if (a <= 0) throw new InvalidTransactionException("Amount must be positive");
    }

    private Transaction record(long no, String type, double amount, String desc) {
        Transaction t = new Transaction(txId.incrementAndGet(), no, type, amount, LocalDateTime.now(), desc);
        transactions.add(t);
        return t;
    }

    public void deposit(long no, double amount) {
        valid(amount);
        BankAccount a = findAccount(no);
        a.credit(amount);
        record(no, "DEPOSIT", amount, "Cash deposit");
    }

    public void withdraw(long no, double amount) {
        valid(amount);
        BankAccount a = findAccount(no);
        if (a.getBalance() - amount < a.minimumBalance())
            throw new InsufficientBalanceException("Minimum balance required: " + a.minimumBalance());
        a.debit(amount);
        record(no, "WITHDRAW", amount, "Cash withdrawal");
    }

    public void transfer(long source, long target, double amount) {
        valid(amount);
        if (source == target) throw new InvalidTransactionException("Source and target cannot be same");
        BankAccount s = findAccount(source), t = findAccount(target);
        if (s.getBalance() - amount < s.minimumBalance())
            throw new InsufficientBalanceException("Insufficient balance in source account");
        s.debit(amount);
        t.credit(amount);
        record(source, "TRANSFER", amount, "Transfer to " + target);
        record(target, "TRANSFER", amount, "Transfer from " + source);
    }

    public double calculateInterest(long no, double rate) {
        valid(rate);
        BankAccount a = findAccount(no);
        double interest = a.getBalance() * rate / 100;
        a.credit(interest);
        record(no, "INTEREST", interest, "Interest at " + rate + "%");
        return interest;
    }

    public double checkBalance(long no) {
        return findAccount(no).getBalance();
    }
}
