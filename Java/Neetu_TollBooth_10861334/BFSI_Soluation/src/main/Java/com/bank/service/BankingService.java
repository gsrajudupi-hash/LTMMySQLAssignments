package com.bank.service;



import com.bank.exception.*;
import com.bank.model.*;

        import java.time.LocalDateTime;
import java.util.*;
        import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BankingService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<BankAccount> accounts = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final AtomicInteger transactionSequence = new AtomicInteger(1);

    public List<Customer> getCustomers() { return customers; }
    public List<BankAccount> getAccounts() { return accounts; }
    public List<Transaction> getTransactions() { return transactions; }

    public void addCustomer(Customer customer) {
        boolean duplicate = customers.stream().anyMatch(c -> c.getCustomerId() == customer.getCustomerId());
        if (duplicate) throw new IllegalArgumentException("Customer ID already exists");
        customers.add(customer);
    }

    public Customer findCustomer(int customerId) {
        return customers.stream().filter(c -> c.getCustomerId() == customerId).findFirst()
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
    }

    public void addAccount(BankAccount account) {
        findCustomer(account.getCustomerId());
        if (accounts.stream().anyMatch(a -> a.getAccountNumber() == account.getAccountNumber()))
            throw new InvalidAccountException("Account number already exists");
        accounts.add(account);
    }

    public BankAccount findAccount(long accountNumber) {
        return accounts.stream().filter(a -> a.getAccountNumber() == accountNumber).findFirst()
                .orElseThrow(() -> new InvalidAccountException("Account not found: " + accountNumber));
    }

    private void validateAmount(double amount) {
        if (amount <= 0) throw new InvalidTransactionException("Amount must be greater than zero");
    }

    private void addTransaction(BankAccount account, String type, double amount, String description) {
        transactions.add(new Transaction(transactionSequence.getAndIncrement(), account.getAccountNumber(),
                type, amount, LocalDateTime.now(), description));
    }

    public double deposit(long accountNumber, double amount) {
        validateAmount(amount);
        BankAccount account = findAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        addTransaction(account, "DEPOSIT", amount, "Cash deposited");
        return account.getBalance();
    }

    public double withdraw(long accountNumber, double amount) {
        validateAmount(amount);
        BankAccount account = findAccount(accountNumber);
        double remaining = account.getBalance() - amount;
        if (remaining < account.minimumBalance())
            throw new InsufficientBalanceException("Minimum balance required: " + account.minimumBalance());
        account.setBalance(remaining);
        addTransaction(account, "WITHDRAW", amount, "Cash withdrawn");
        return remaining;
    }

    public void transfer(long sourceNumber, long targetNumber, double amount) {
        validateAmount(amount);
        if (sourceNumber == targetNumber) throw new InvalidTransactionException("Source and target cannot be same");
        BankAccount source = findAccount(sourceNumber);
        BankAccount target = findAccount(targetNumber);
        double remaining = source.getBalance() - amount;
        if (remaining < source.minimumBalance())
            throw new InsufficientBalanceException("Insufficient balance in source account");
        source.setBalance(remaining);
        target.setBalance(target.getBalance() + amount);
        addTransaction(source, "TRANSFER", amount, "Transferred to " + targetNumber);
        addTransaction(target, "TRANSFER", amount, "Received from " + sourceNumber);
    }

    public double calculateInterest(long accountNumber, double rate) {
        if (rate < 0) throw new InvalidTransactionException("Interest rate cannot be negative");
        BankAccount account = findAccount(accountNumber);
        double interest = account.getBalance() * rate / 100;
        account.setBalance(account.getBalance() + interest);
        addTransaction(account, "INTEREST", interest, "Interest at " + rate + "%");
        return interest;
    }

    public double checkBalance(long accountNumber) { return findAccount(accountNumber).getBalance(); }

    public List<Transaction> history(long accountNumber) {
        findAccount(accountNumber);
        return transactions.stream().filter(t -> t.getAccountNumber() == accountNumber)
                .sorted(Comparator.comparing(Transaction::getTransactionDate).reversed()).toList();
    }

    public List<Transaction> transactionsByType(String type) {
        return transactions.stream().filter(t -> t.getTransactionType().equalsIgnoreCase(type)).toList();
    }

    public List<Transaction> transactionsAbove(double amount) {
        return transactions.stream().filter(t -> t.getAmount() > amount).toList();
    }

    public List<Transaction> latestFiveTransactions() {
        return transactions.stream().sorted(Comparator.comparing(Transaction::getTransactionDate).reversed())
                .limit(5).toList();
    }

    public Map<String, Long> accountCountByType() {
        return accounts.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
    }
}
