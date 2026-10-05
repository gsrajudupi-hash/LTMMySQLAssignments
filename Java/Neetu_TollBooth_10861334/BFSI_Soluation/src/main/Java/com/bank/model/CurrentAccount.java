package com.bank.model;

public final class CurrentAccount extends BankAccount {
    public CurrentAccount(long accountNumber, int customerId, double balance, String status) {
        super(accountNumber, customerId, "CURRENT", balance, status);
    }
    @Override public double minimumBalance() { return 5000.0; }
}
