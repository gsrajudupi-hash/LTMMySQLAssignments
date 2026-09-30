package com.bank.model;

public sealed abstract class BankAccount permits SavingsAccount, CurrentAccount, LoanAccount {
    private final long accountNumber;
    private final int customerId;
    private double balance;
    private String status;

    protected BankAccount(long no, int cid, double bal, String status) {
        if (no <= 0 || cid <= 0 || bal < 0) throw new IllegalArgumentException("Invalid account data");
        this.accountNumber = no;
        this.customerId = cid;
        this.balance = bal;
        this.status = status;
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

    public void setStatus(String s) {
        status = s;
    }

    public void credit(double a) {
        balance += a;
    }

    public void debit(double a) {
        balance -= a;
    }

    public abstract String getAccountType();

    public abstract double minimumBalance();

    public String toString() {
        return "%s{account=%d, customer=%d, balance=%.2f, status=%s}".formatted(getAccountType(), accountNumber, customerId, balance, status);
    }
}
