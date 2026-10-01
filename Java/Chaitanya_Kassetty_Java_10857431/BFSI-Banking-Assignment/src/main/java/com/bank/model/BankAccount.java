package com.bank.model;

public sealed abstract class BankAccount permits SavingsAccount, CurrentAccount, LoanAccount {
    private final long accountNumber;
    private final int customerId;
    private final String accountType;
    private double balance;
    private String status;

    protected BankAccount(long accountNumber, int customerId, String accountType, double balance, String status) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.status = status;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public abstract double minimumBalance();

    @Override
    public String toString() {
        return accountType + " Account{number=" + accountNumber + ", customerId=" + customerId + ", balance=" + String.format("%.2f", balance) + ", status=" + status + '}';
    }
}