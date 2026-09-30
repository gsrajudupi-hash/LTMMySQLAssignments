package com.bank.model;

public final class SavingsAccount extends BankAccount {
    public SavingsAccount(long n, int c, double b, String s) {
        super(n, c, b, s);
    }

    public String getAccountType() {
        return "SAVINGS";
    }

    public double minimumBalance() {
        return 1000;
    }
}
