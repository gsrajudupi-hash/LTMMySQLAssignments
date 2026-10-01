package bfs;
public record TransactionRecord(int transactionId, long accountNumber, String transactionType, double amount) {
    public TransactionRecord {
        if (transactionType == null || transactionType.isBlank()) throw new IllegalArgumentException("Transaction type is required");
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}
