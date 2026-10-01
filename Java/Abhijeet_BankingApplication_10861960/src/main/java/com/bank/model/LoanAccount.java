package com.bank.model;

public non-sealed class LoanAccount extends BankAccount {
    public LoanAccount(long n, int c, double b, String s) {
        super(n, c, "LOAN", b, s);
    }

    public double minimumBalance() {
        return 0;
    }
}