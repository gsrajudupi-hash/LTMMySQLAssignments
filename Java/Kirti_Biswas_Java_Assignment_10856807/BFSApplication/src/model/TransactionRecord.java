package model;

public record TransactionRecord(
        int transactionId,
        long accountNumber,
        String transactionType,
        double amount
) {

    // Compact Constructor
    public TransactionRecord {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount must be greater than 0"
            );
        }

        if (transactionType == null ||
                transactionType.isBlank()) {

            throw new IllegalArgumentException(
                    "Transaction Type cannot be null"
            );
        }
    }
}