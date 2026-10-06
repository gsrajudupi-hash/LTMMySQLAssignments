package com.bank.service;

import com.bank.exception.CustomerNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAccountException;
import com.bank.exception.InvalidCustomerException;
import com.bank.exception.InvalidTransactionException;
import com.bank.model.AccountType;
import com.bank.model.BankAccount;
import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.CustomerType;
import com.bank.model.LoanAccount;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.record.CustomerRecord;
import com.bank.record.TransactionRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BankingService {
    private final Map<Integer, Customer> customers = new LinkedHashMap<>();
    private final Map<Long, BankAccount> accounts = new LinkedHashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private long nextTransactionId = 1;

    public Customer addCustomer(int customerId, String name, String email, String city,
                                String phone, CustomerType type) throws InvalidCustomerException {
        if (customers.containsKey(customerId)) {
            throw new InvalidCustomerException("Customer ID " + customerId + " already exists.");
        }
        Customer customer = new Customer(customerId, name, email, city, phone, type);
        customers.put(customerId, customer);
        return customer;
    }

    public List<Customer> getCustomers() {
        return List.copyOf(customers.values());
    }

    public Customer findCustomer(int customerId) throws CustomerNotFoundException {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException(customerId);
        }
        return customer;
    }

    public BankAccount openAccount(AccountType type, long accountNumber, int customerId, double openingBalance)
            throws CustomerNotFoundException, InvalidAccountException {
        findCustomer(customerId);
        if (accounts.containsKey(accountNumber)) {
            throw new InvalidAccountException("Account number " + accountNumber + " already exists.");
        }
        if (type == null) {
            throw new InvalidAccountException("Account type is required.");
        }
        BankAccount account = switch (type) {
            case SAVINGS -> new SavingsAccount(accountNumber, customerId, openingBalance);
            case CURRENT -> new CurrentAccount(accountNumber, customerId, openingBalance);
            case LOAN -> new LoanAccount(accountNumber, customerId, openingBalance);
        };
        accounts.put(accountNumber, account);
        return account;
    }

    public List<BankAccount> getAccounts() {
        return List.copyOf(accounts.values());
    }

    public List<BankAccount> getAccountsForCustomer(int customerId) {
        return accounts.values().stream()
                .filter(account -> account.getCustomerId() == customerId)
                .toList();
    }

    public BankAccount findAccount(long accountNumber) throws InvalidAccountException {
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account " + accountNumber + " was not found.");
        }
        return account;
    }

    public TransactionRecord deposit(long accountNumber, double amount)
            throws InvalidAccountException, InvalidTransactionException {
        BankAccount account = findAccount(accountNumber);
        String description = account instanceof LoanAccount ? "Loan repayment" : "Cash deposit";
        double balanceAfter = account.deposit(amount);
        return record(account, TransactionType.DEPOSIT, amount, balanceAfter, description, 0,
                LocalDateTime.now()).toRecord();
    }

    public TransactionRecord withdraw(long accountNumber, double amount)
            throws InvalidAccountException, InvalidTransactionException, InsufficientBalanceException {
        BankAccount account = findAccount(accountNumber);
        double balanceAfter = account.withdraw(amount);
        return record(account, TransactionType.WITHDRAWAL, amount, balanceAfter, "Cash withdrawal", 0,
                LocalDateTime.now()).toRecord();
    }

    public List<TransactionRecord> transfer(long sourceNumber, long targetNumber, double amount)
            throws InvalidAccountException, InvalidTransactionException, InsufficientBalanceException {
        BankAccount source = findAccount(sourceNumber);
        BankAccount target = findAccount(targetNumber);
        if (sourceNumber == targetNumber) {
            throw new InvalidTransactionException("Source and target accounts must be different.");
        }
        // Validate both sides before changing either balance so a transfer is all-or-nothing.
        source.validateWithdrawal(amount);
        target.validateDeposit(amount);

        long transferId = nextTransactionId;
        LocalDateTime now = LocalDateTime.now();
        double sourceBalance = source.withdraw(amount);
        double targetBalance = target.deposit(amount);
        Transaction debit = record(source, TransactionType.TRANSFER_DEBIT, amount, sourceBalance,
                "Transfer to account " + targetNumber, transferId, now);
        Transaction credit = record(target, TransactionType.TRANSFER_CREDIT, amount, targetBalance,
                "Transfer from account " + sourceNumber, transferId, now);
        return List.of(debit.toRecord(), credit.toRecord());
    }

    public double checkBalance(long accountNumber) throws InvalidAccountException {
        return findAccount(accountNumber).getBalance();
    }

    public List<TransactionRecord> getTransactions() {
        return transactions.stream().map(Transaction::toRecord).toList();
    }

    public List<TransactionRecord> getTransactions(long accountNumber) throws InvalidAccountException {
        findAccount(accountNumber);
        return transactions.stream()
                .filter(transaction -> transaction.getAccountNumber() == accountNumber)
                .map(Transaction::toRecord)
                .toList();
    }

    public List<CustomerRecord> getCustomerSummaries() {
        return customers.values().stream()
                .map(customer -> {
                    List<BankAccount> owned = getAccountsForCustomer(customer.getCustomerId());
                    double deposits = owned.stream()
                            .filter(account -> !(account instanceof LoanAccount))
                            .mapToDouble(BankAccount::getBalance).sum();
                    double loans = owned.stream()
                            .filter(account -> account instanceof LoanAccount)
                            .mapToDouble(BankAccount::getBalance).sum();
                    return new CustomerRecord(customer.getCustomerId(), customer.getName(),
                            customer.getCity(), customer.getCustomerType(), owned.size(), deposits, loans);
                })
                .toList();
    }

    private Transaction record(BankAccount account, TransactionType type, double amount, double balanceAfter,
                               String description, long transferId, LocalDateTime timestamp) {
        Transaction transaction = new Transaction(nextTransactionId++, account.getAccountNumber(), type,
                amount, timestamp, balanceAfter, description, transferId);
        transactions.add(transaction);
        return transaction;
    }
}
