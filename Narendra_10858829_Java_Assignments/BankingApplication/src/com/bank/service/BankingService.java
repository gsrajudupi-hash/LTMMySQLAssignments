package com.bank.service;


 
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.bank.exception.CustomerNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAccountException;
import com.bank.exception.InvalidTransactionException;
import com.bank.model.BankAccount;
import com.bank.model.Customer;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;
/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:34:52 pm
 * project  : BankingApplication
 */



 
public class BankingService {
 
    private final List<Customer> customers = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
 
    private int transactionId = 1;
 
    // ---------------- CUSTOMER ----------------
 
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
 
    public List<Customer> getCustomers() {
        return customers;
    }
 
    public Customer findCustomer(int customerId) {
 
        return customers.stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found: " + customerId));
    }
 
    // ---------------- ACCOUNT ----------------
 
    public void addAccount(BankAccount account) {
 
        findCustomer(account.getCustomerId());
 
        accounts.add(account);
    }
 
    public List<BankAccount> getAccounts() {
        return accounts;
    }
 
    public BankAccount findAccount(long accountNumber) {
 
        return accounts.stream()
                .filter(a -> a.getAccountNumber() == accountNumber)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidAccountException(
                                "Account not found: " + accountNumber));
    }
 
    // ---------------- DEPOSIT ----------------
 
    public void deposit(long accountNumber, double amount) {
 
        validateAmount(amount);
 
        BankAccount account = findAccount(accountNumber);
 
        account.deposit(amount);
 
        addTransaction(
                accountNumber,
                "DEPOSIT",
                amount,
                "Cash deposit");
    }
 
    // ---------------- WITHDRAW ----------------
 
    public void withdraw(long accountNumber, double amount) {
 
        validateAmount(amount);
 
        BankAccount account = findAccount(accountNumber);
 
        double minimumBalance =
                account instanceof SavingsAccount ? 1000 : 5000;
 
        if (account.getBalance() - amount < minimumBalance) {
 
            throw new InsufficientBalanceException(
                    "Minimum balance requirement violated");
        }
 
        account.withdraw(amount);
 
        addTransaction(
                accountNumber,
                "WITHDRAW",
                amount,
                "Cash withdrawal");
    }
 
    // ---------------- TRANSFER ----------------
 
    public void transfer(long sourceAccount,
                         long targetAccount,
                         double amount) {
 
        validateAmount(amount);
 
        BankAccount source = findAccount(sourceAccount);
        BankAccount target = findAccount(targetAccount);
 
        double minimumBalance =
                source instanceof SavingsAccount ? 1000 : 5000;
 
        if (source.getBalance() - amount < minimumBalance) {
 
            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer");
        }
 
        source.withdraw(amount);
        target.deposit(amount);
 
        addTransaction(
                sourceAccount,
                "TRANSFER",
                amount,
                "Transfer to " + targetAccount);
 
        addTransaction(
                targetAccount,
                "TRANSFER",
                amount,
                "Transfer from " + sourceAccount);
    }
 
    // ---------------- INTEREST ----------------
 
    public void calculateInterest(long accountNumber,
                                  double rate) {
 
        BankAccount account = findAccount(accountNumber);
 
        double interest = account.getBalance() * rate / 100;
 
        account.deposit(interest);
 
        addTransaction(
                accountNumber,
                "INTEREST",
                interest,
                "Interest credited");
    }
 
    // ---------------- BALANCE ----------------
 
    public double checkBalance(long accountNumber) {
 
        return findAccount(accountNumber).getBalance();
    }
 
    // ---------------- TRANSACTION ----------------
 
    private void addTransaction(long accountNumber,
                                String type,
                                double amount,
                                String description) {
 
        transactions.add(
                new Transaction(
                        transactionId++,
                        accountNumber,
                        type,
                        amount,
                        LocalDateTime.now(),
                        description));
    }
 
    public List<Transaction> getTransactions() {
        return transactions;
    }
 
    private void validateAmount(double amount) {
 
        if (amount <= 0) {
 
            throw new InvalidTransactionException(
                    "Amount must be greater than zero");
        }
    }
 
    // ---------------- ANALYTICS ----------------
 
    public double getTotalBalance() {
 
        return accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(0.0, Double::sum);
    }
 
    public double getAverageBalance() {
 
        return accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .average()
                .orElse(0);
    }
 
    public Optional<BankAccount> getHighestBalance() {
 
        return accounts.stream()
                .max(Comparator.comparingDouble(
                        BankAccount::getBalance));
    }
 
    public Optional<BankAccount> getLowestBalance() {
 
        return accounts.stream()
                .min(Comparator.comparingDouble(
                        BankAccount::getBalance));
    }
 
    public long countPremiumCustomers() {
 
        return customers.stream()
                .filter(c ->
                        c.getCustomerType()
                                .equalsIgnoreCase("PREMIUM"))
                .count();
    }
 
    public Map<String, Long> customersByCity() {
 
        return customers.stream()
                .collect(Collectors.groupingBy(
                        Customer::getCity,
                        Collectors.counting()));
    }
 
    public Map<String, Long> accountsByType() {
 
        return accounts.stream()
                .collect(Collectors.groupingBy(
                        BankAccount::getAccountType,
                        Collectors.counting()));
    }
 
    public Map<String, Double> balanceByAccountType() {
 
        return accounts.stream()
                .collect(Collectors.groupingBy(
                        BankAccount::getAccountType,
                        Collectors.summingDouble(
                                BankAccount::getBalance)));
    }
 
    public double totalDeposits() {
 
        return transactions.stream()
                .filter(t ->
                        t.getTransactionType()
                                .equals("DEPOSIT"))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }
 
    public double totalWithdrawals() {
 
        return transactions.stream()
                .filter(t ->
                        t.getTransactionType()
                                .equals("WITHDRAW"))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);
    }
 
    public long transactionsAbove50000() {
 
        return transactions.stream()
                .filter(t -> t.getAmount() > 50000)
                .count();
    }
}
