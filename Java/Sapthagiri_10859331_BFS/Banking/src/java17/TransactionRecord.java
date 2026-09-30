package java17;

import java.util.Objects;

public record TransactionRecord(
        int transactionId,
        long accountNumber,
        String transactionType,
        double amount
) {

    public TransactionRecord {

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be positive"
            );
        }

        Objects.requireNonNull(
                transactionType,
                "Transaction type cannot be null"
        );
    }
}
