package com.bank.model;

public final class CurrentAccount extends BankAccount {
    public CurrentAccount(long n, int c, double b, String s) {
        super(n, c, "CURRENT", b, s);
    }

    public double minimumBalance() {
        return 5000;
    }
}