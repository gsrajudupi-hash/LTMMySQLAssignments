package com.bank.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private int transactionId;

    private long accountNumber;

    private String transactionType;

    private double amount;

    private LocalDateTime transactionDate;

    private String description;

    public Transaction(int transactionId,

                       long accountNumber,

                       String transactionType,

                       double amount,

                       String description) {

        this.transactionId = transactionId;

        this.accountNumber = accountNumber;

        this.transactionType = transactionType;

        this.amount = amount;

        this.transactionDate = LocalDateTime.now();

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

    public String getFormattedDate() {

        DateTimeFormatter formatter =

                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return transactionDate.format(formatter);

    }

    @Override

    public String toString() {

        return "Transaction{" +

                "transactionId=" + transactionId +

                ", accountNumber=" + accountNumber +

                ", transactionType='" + transactionType + '\'' +

                ", amount=" + amount +

                ", date=" + getFormattedDate() +

                ", description='" + description + '\'' +

                '}';

    }

}
