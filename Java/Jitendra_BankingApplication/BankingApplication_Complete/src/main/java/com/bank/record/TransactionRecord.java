package com.bank.record;

public record TransactionRecord(long transactionId, long accountNumber, String transactionType, double amount) {
    public TransactionRecord {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (transactionType == null || transactionType.isBlank())
            throw new IllegalArgumentException("Transaction type is required");
    }
}
