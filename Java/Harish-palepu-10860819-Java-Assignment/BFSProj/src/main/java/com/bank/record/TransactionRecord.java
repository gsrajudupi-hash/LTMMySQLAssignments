package com.bank.record;

import com.bank.model.TransactionType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Immutable, read-only view of a transaction used for reporting.
 */
public record TransactionRecord(long transactionId,
                                long accountNumber,
                                TransactionType type,
                                double amount,
                                LocalDateTime timestamp,
                                double balanceAfter,
                                String description,
                                long transferId) {

    public TransactionRecord {
        Objects.requireNonNull(type, "Transaction type is required.");
        Objects.requireNonNull(timestamp, "Transaction timestamp is required.");
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than zero.");
        }
    }

    public boolean isTransfer() {
        return type.isTransfer();
    }
}
