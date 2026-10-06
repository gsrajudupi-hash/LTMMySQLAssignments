package com.bank.model;

import com.bank.exception.InvalidAccountException;

public final class SavingsAccount extends BankAccount {
    public static final double MINIMUM_BALANCE = 1000.0;
    private static final double INTEREST_RATE = 4.0;

    public SavingsAccount(long accountNumber, int customerId, double openingBalance)
            throws InvalidAccountException {
        super(accountNumber, customerId, openingBalance, MINIMUM_BALANCE);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.SAVINGS;
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }
}
