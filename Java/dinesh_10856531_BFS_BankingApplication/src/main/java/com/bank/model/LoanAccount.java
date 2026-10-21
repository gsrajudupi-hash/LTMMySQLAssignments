package com.bank.model;

public non-sealed class LoanAccount extends BankAccount {
    public LoanAccount(long accountNumber,
                       int customerId,
                       double balance) {
        super(accountNumber, customerId, balance);
    }
    @Override
    public String getAccountType() {
        return "LOAN";
    }
    @Override
    public double getMinimumBalance() {
        return 0;
    }
}