


package com.bank.model;
public non-sealed class LoanAccount extends BankAccount {
    public LoanAccount(long accountNumber, int customerId, double balance, String status) {
        super(accountNumber, customerId, "LOAN", balance, status);
    }
    @Override public double minimumBalance() { return 0.0; }
}
