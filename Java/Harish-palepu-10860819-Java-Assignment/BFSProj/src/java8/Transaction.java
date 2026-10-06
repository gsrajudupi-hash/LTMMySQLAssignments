package java8;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 * Class Name : Transaction
 * Created By : 10860819
 * Created Date : 9/29/2026
 * Created Time : 10:03 AM
 */
public class Transaction {
    private long transactionId;
    private long accountNumber;
    private String transactionType;
    private double amount;
    private LocalDateTime transactionDate;
    private String description;
    private final long transferId;

    //Parameterized constructor
    public Transaction(long transactionId, long accountNumber, String transactionType,
                       double amount, LocalDateTime transactionDate, String description) {
        this(transactionId, accountNumber, transactionType, amount, transactionDate, description, 0);
    }

    public Transaction(long transactionId, long accountNumber, String transactionType,
                       double amount, LocalDateTime transactionDate, String description,
                       long transferId) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transferId = transferId;
    }

    //Getters
    public long getTransactionId() {
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

    public long getTransferId() {
        return transferId;
    }

    public boolean isTransfer() {
        return transferId != 0;
    }

    //Setters
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + accountNumber +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", transactionDate='" + transactionDate + '\'' +
                ", description='" + description + '\'' +
                ", transferId=" + transferId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transactionId == that.transactionId &&
                transferId == that.transferId &&
                accountNumber == that.accountNumber &&
                Double.compare(amount, that.amount) == 0 &&
                Objects.equals(transactionType, that.transactionType) &&
                Objects.equals(transactionDate, that.transactionDate) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, accountNumber, transactionType, amount, transactionDate, description, transferId);
    }

    // Display transaction details
    public void displayTransaction() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.println("Transaction ID : " + transactionId);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Transaction Type : " + transactionType);
        System.out.println("Amount : " + amount);
        System.out.println("Transaction Date : " + transactionDate.format(formatter));
        System.out.println("Description : " + description);
        if (isTransfer()) {
            System.out.println("Transfer ID : " + transferId);
        }
    }
}
