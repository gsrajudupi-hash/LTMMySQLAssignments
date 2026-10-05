package com.bank.model;

import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final long accountNumber;
    private final String transactionType;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String description;

    public Transaction(int transactionId, long accountNumber, String transactionType,
                       double amount, LocalDateTime transactionDate, String description) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }
    public int getTransactionId() { return transactionId; }
    public long getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public String getDescription() { return description; }
    @Override public String toString() {
        return "Transaction{" + "transactionId=" + transactionId + ", accountNumber=" + accountNumber +
                ", transactionType='" + transactionType + '\'' + ", amount=" + amount +
                ", transactionDate=" + transactionDate + ", description='" + description + '\'' + '}';
    }
}
