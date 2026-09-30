package com.bank.model;

public final class SavingsAccount extends BankAccount {
    public SavingsAccount(long n, int c, double b, String s) {
        super(n, c, "SAVINGS", b, s);
    }

    public double minimumBalance() {
        return 1000;
    }
}