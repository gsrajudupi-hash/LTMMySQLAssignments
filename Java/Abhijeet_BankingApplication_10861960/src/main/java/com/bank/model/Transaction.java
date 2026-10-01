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

    public Transaction(int id, long a, String t, double amount, LocalDateTime d, String desc) {
        this.transactionId = id;
        this.accountNumber = a;
        this.transactionType = t;
        this.amount = amount;
        this.transactionDate = d;
        this.description = desc;
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

    public String toString() {
        return transactionId + " | " + accountNumber + " | " + transactionType + " | "
                + String.format("Rs %.2f", amount) + " | "
                + transactionDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + " | " + description;
    }
}