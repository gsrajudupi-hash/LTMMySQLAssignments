package com.bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final long transactionId;
    private final long accountNumber;
    private final String transactionType;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String description;

    public Transaction(long id, long acc, String type, double amount, LocalDateTime date, String desc) {
        this.transactionId = id;
        this.accountNumber = acc;
        this.transactionType = type;
        this.amount = amount;
        this.transactionDate = date;
        this.description = desc;
    }

    public long getTransactionId() {
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
        return "Transaction{id=%d, account=%d, type='%s', amount=%.2f, date=%s, description='%s'}".formatted(transactionId, accountNumber, transactionType, amount, transactionDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), description);
    }
}