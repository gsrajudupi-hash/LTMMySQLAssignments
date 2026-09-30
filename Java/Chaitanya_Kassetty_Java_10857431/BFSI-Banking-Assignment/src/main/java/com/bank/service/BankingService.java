package com.bank.service;

import com.bank.exception.*;
import com.bank.functional.BankingOperation;
import com.bank.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

public class BankingService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private int transactionSequence = 1;
    private final Supplier<Long> accountNumberGenerator = () -> System.currentTimeMillis() + accounts.size();

    private final BankingOperation depositOperation = (amount, balance) -> balance + amount;
    private final BankingOperation withdrawalOperation = (amount, balance) -> balance - amount;
    private final BankingOperation interestOperation = (rate, balance) -> balance + (balance * rate / 100.0);

    // Returns all customers stored in the system
    public List<Customer> getCustomers() {
        return customers;
    }

    // Returns all bank accounts stored in the system
    public List<BankAccount> getAccounts() {
        return accounts;
    }

    // Returns all transactions performed in the system
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Adds a new customer after validation checks
    public void addCustomer(Customer customer) {
        if (customer == null) throw new IllegalArgumentException("Customer cannot be null");
        if (customers.stream().anyMatch(c -> c.getCustomerId() == customer.getCustomerId()))
            throw new IllegalArgumentException("Duplicate customer ID");
        customers.add(customer);
    }

    // Searches and returns a customer by customer ID
    public Customer findCustomer(int id) {
        return customers.stream().filter(c -> c.getCustomerId() == id).findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
    }

    // Creates and adds a new bank account for a customer
    public BankAccount addAccount(int customerId, String type, double openingBalance) {
        findCustomer(customerId);
        if (openingBalance < 0) throw new InvalidTransactionException("Opening balance cannot be negative");
        long number = accountNumberGenerator.get();
        BankAccount account = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(number, customerId, openingBalance, "ACTIVE");
            case "CURRENT" -> new CurrentAccount(number, customerId, openingBalance, "ACTIVE");
            case "LOAN" -> new LoanAccount(number, customerId, openingBalance, "ACTIVE");
            default -> throw new InvalidAccountException("Unsupported account type: " + type);
        };
        if (openingBalance < account.minimumBalance())
            throw new InsufficientBalanceException("Minimum opening balance is " + account.minimumBalance());
        accounts.add(account);
        return account;
    }

    // Searches and returns an account using account number
    public BankAccount findAccount(long number) {
        return accounts.stream().filter(a -> a.getAccountNumber() == number).findFirst()
                .orElseThrow(() -> new InvalidAccountException("Account not found: " + number));
    }

    // Validates that the transaction amount is greater than zero
    private void validateAmount(double amount) {
        if (amount <= 0) throw new InvalidTransactionException("Amount must be greater than zero");
    }

    // Creates and stores a new transaction record
    private void addTransaction(long account, String type, double amount, String description) {
        transactions.add(new Transaction(transactionSequence++, account, type, amount, LocalDateTime.now(), description));
    }

    // Deposits money into a specified account
    public void deposit(long number, double amount) {
        validateAmount(amount);
        BankAccount a = findAccount(number);
        a.setBalance(depositOperation.execute(amount, a.getBalance()));
        addTransaction(number, "DEPOSIT", amount, "Cash deposit");
    }

    // Withdraws money while maintaining minimum balance rules
    public void withdraw(long number, double amount) {
        validateAmount(amount);
        BankAccount a = findAccount(number);
        double remaining = withdrawalOperation.execute(amount, a.getBalance());
        if (remaining < a.minimumBalance())
            throw new InsufficientBalanceException("Minimum balance violation. Required: " + a.minimumBalance());
        a.setBalance(remaining);
        addTransaction(number, "WITHDRAW", amount, "Cash withdrawal");
    }

    // Transfers money between two valid accounts
    public void transfer(long source, long target, double amount) {
        validateAmount(amount);
        if (source == target) throw new InvalidTransactionException("Source and target must differ");
        BankAccount s = findAccount(source);
        BankAccount t = findAccount(target);
        double remaining = s.getBalance() - amount;
        if (remaining < s.minimumBalance()) throw new InsufficientBalanceException("Insufficient transferable balance");
        s.setBalance(remaining);
        t.setBalance(t.getBalance() + amount);
        addTransaction(source, "TRANSFER", amount, "Transfer to " + target);
        addTransaction(target, "TRANSFER", amount, "Transfer from " + source);
    }

    // Calculates and credits interest to an account
    public double calculateInterest(long number, double annualRate) {
        validateAmount(annualRate);
        BankAccount a = findAccount(number);
        double before = a.getBalance();
        a.setBalance(interestOperation.execute(annualRate, before));
        double interest = a.getBalance() - before;
        addTransaction(number, "INTEREST", interest, "Interest credited");
        return interest;
    }

    // Returns the current balance of an account.
    public double checkBalance(long number) {
        return findAccount(number).getBalance();
    }
}