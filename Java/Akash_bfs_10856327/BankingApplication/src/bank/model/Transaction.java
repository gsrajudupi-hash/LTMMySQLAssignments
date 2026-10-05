package bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private final int transactionId;
    private final long accountNumber;
    private final String transactionType;
    private final double amount;
    private final LocalDateTime transactionDate;
    private final String description;

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

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + accountNumber +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + String.format("%.2f", amount) +
                ", transactionDate=" + transactionDate.format(formatter) +
                ", description='" + description + '\'' +
                '}';
    }
}