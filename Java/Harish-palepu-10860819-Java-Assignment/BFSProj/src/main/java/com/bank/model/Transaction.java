package com.bank.model;

import com.bank.record.TransactionRecord;

import java.time.LocalDateTime;

public final class Transaction {
    private final long transactionId;
    private final long accountNumber;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;
    private final double balanceAfter;
    private final String description;
    private final long transferId;

    public Transaction(long transactionId, long accountNumber, TransactionType type, double amount,
                       LocalDateTime timestamp, double balanceAfter, String description, long transferId) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.transferId = transferId;
    }

    public TransactionRecord toRecord() {
        return new TransactionRecord(transactionId, accountNumber, type, amount,
                timestamp, balanceAfter, description, transferId);
    }

    public long getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public long getTransferId() {
        return transferId;
    }
}
