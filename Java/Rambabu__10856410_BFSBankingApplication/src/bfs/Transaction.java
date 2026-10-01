package bfs;

import java.time.LocalDateTime;

public class Transaction {
    private final int transactionId;
    private final long accountNumber;
    private final String transactionType;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String description;

    public Transaction(int id, long accountNumber, String type, double amount, LocalDateTime date, String description) {
        this.transactionId = id; this.accountNumber = accountNumber; this.transactionType = type;
        this.amount = amount; this.transactionDate = date; this.description = description;
    }
    public int getTransactionId() { return transactionId; }
    public long getAccountNumber() { return accountNumber; }
    public String getTransactionType() { return transactionType; }
    public double getAmount() { return amount; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public String getDescription() { return description; }
    @Override public String toString() { return transactionId + " | " + accountNumber + " | " + transactionType + " | " + String.format("%.2f", amount) + " | " + transactionDate + " | " + description; }
}
