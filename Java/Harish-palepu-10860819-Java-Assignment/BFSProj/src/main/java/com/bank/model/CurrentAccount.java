package com.bank.model;

import com.bank.exception.InvalidAccountException;

public final class CurrentAccount extends BankAccount {
    public static final double MINIMUM_BALANCE = 5000.0;

    public CurrentAccount(long accountNumber, int customerId, double openingBalance)
            throws InvalidAccountException {
        super(accountNumber, customerId, openingBalance, MINIMUM_BALANCE);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.CURRENT;
    }

    @Override
    public double getInterestRate() {
        return 0.0;
    }
}
