package com.bank.record;
public record TransactionRecord(
        int transactionId,
        long accountNumber,
        String transactionType,
        double amount) {
    public TransactionRecord {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be positive");
        }
        if (transactionType == null ||
                transactionType.isBlank()) {
            throw new IllegalArgumentException(
                    "Transaction type cannot be empty");
        }
    }
}