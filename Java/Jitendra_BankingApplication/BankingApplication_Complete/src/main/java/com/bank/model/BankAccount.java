package com.bank.model;

import java.util.Objects;

public sealed abstract class BankAccount permits SavingsAccount, CurrentAccount, LoanAccount {
    private final long accountNumber;
    private final int customerId;
    private final String accountType;
    protected double balance;
    private String status;

    protected BankAccount(long n, int c, String t, double b, String s) {
        if (b < 0) throw new IllegalArgumentException("Opening balance cannot be negative");
        accountNumber = n;
        customerId = c;
        accountType = t;
        balance = b;
        status = s;
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

    public void setStatus(String s) {
        status = s;
    }

    public void credit(double a) {
        balance += a;
    }

    public void debit(double a) {
        balance -= a;
    }

    public abstract double minimumBalance();

    @Override
    public boolean equals(Object o) {
        return o instanceof BankAccount a && accountNumber == a.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "%s{accountNumber=%d, customerId=%d, balance=%.2f, status='%s'}".formatted(accountType, accountNumber, customerId, balance, status);
    }
}