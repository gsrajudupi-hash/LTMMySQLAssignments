package com.bank.record;

import java.util.Objects;

public record TransactionRecord(
        int transactionId, long accountNumber, String transactionType, double amount) {

    public TransactionRecord {
        Objects.requireNonNull(transactionType, "Transaction type cannot be null");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}