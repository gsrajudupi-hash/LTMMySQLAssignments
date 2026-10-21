package com.bank.model;

public final class CurrentAccount extends BankAccount {
    public CurrentAccount(long accountNumber,
                          int customerId,
                          double balance) {
        super(accountNumber, customerId, balance);
    }
    @Override
    public String getAccountType() {
        return "CURRENT";
    }
    @Override
    public double getMinimumBalance() {
        return 5000;
    }
}