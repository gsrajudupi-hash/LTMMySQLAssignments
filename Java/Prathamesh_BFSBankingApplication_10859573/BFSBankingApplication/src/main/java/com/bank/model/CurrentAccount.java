package com.bank.model;

public final class CurrentAccount extends BankAccount {
    public CurrentAccount(long n, int c, double b, String s) {
        super(n, c, b, s);
    }

    public String getAccountType() {
        return "CURRENT";
    }

    public double minimumBalance() {
        return 5000;
    }
}
