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

    public Transaction(int id, long no, String type, double amount, LocalDateTime date, String desc) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.transactionId = id;
        this.accountNumber = no;
        this.transactionType = type;
        this.amount = amount;
        this.transactionDate = date;
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
        return "Transaction{id=%d, account=%d, type=%s, amount=%.2f, date=%s, description='%s'}".formatted(transactionId, accountNumber, transactionType, amount, transactionDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a")), description);
    }
}
