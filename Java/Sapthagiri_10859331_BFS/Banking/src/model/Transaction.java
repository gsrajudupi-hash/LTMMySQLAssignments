package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private int transactionId;
    private long accountNumber;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionDate;
    private String description;

    public Transaction() {
    }

    public Transaction(int transactionId,
                       long accountNumber,
                       String transactionType,
                       double amount,
                       LocalDateTime transactionDate,
                       String description) {

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + accountNumber +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate=" + transactionDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (!(o instanceof Transaction))
            return false;

        Transaction that = (Transaction) o;

        return transactionId == that.transactionId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}
