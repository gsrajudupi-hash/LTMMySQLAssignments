package com.bank.model;

public final class SavingsAccount extends BankAccount {
    public SavingsAccount(long accountNumber,
                          int customerId,
                          double balance) {
        super(accountNumber, customerId, balance);
    }
    @Override
    public String getAccountType() {
        return "SAVINGS";
    }
    @Override
    public double getMinimumBalance() {
        return 1000;
    }
}