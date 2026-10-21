package com.bank.model;

public sealed abstract class BankAccount
        permits SavingsAccount, CurrentAccount, LoanAccount {
    private long accountNumber;
    private int customerId;
    protected double balance;
    private String status;
    public BankAccount(long accountNumber,
                       int customerId,
                       double balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.status = "ACTIVE";
    }
    public long getAccountNumber() {
        return accountNumber;
    }
    public int getCustomerId() {
        return customerId;
    }
    public double getBalance() {
        return balance;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void credit(double amount) {
        balance += amount;
    }
    public void debit(double amount) {
        balance -= amount;
    }
    public abstract String getAccountType();
    public abstract double getMinimumBalance();
    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
                ", Customer ID: " + customerId +
                ", Type: " + getAccountType() +
                ", Balance: " + balance +
                ", Status: " + status;
    }
}