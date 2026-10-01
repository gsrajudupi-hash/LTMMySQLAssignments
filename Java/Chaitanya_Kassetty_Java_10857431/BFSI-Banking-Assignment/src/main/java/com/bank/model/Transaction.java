package com.bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final int transactionId;
    private final long accountNumber;
    private final String transactionType;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String description;

    public Transaction(int id, long account, String type, double amount, LocalDateTime date, String description) {
        this.transactionId = id;
        this.accountNumber = account;
        this.transactionType = type;
        this.amount = amount;
        this.transactionDate = date;
        this.description = description;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{id=" + transactionId + ", account=" + accountNumber + ", type=" + transactionType +
                ", amount=" + String.format("%.2f", amount) + ", date=" + transactionDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) +
                ", description='" + description + '\'' + '}';
    }
}