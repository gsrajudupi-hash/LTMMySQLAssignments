package com.bank.model;

public non-sealed class LoanAccount extends BankAccount {
    public LoanAccount(long n, int c, double b, String s) {
        super(n, c, b, s);
    }

    public String getAccountType() {
        return "LOAN";
    }

    public double minimumBalance() {
        return 0;
    }
}
