package com.bank.model;

import com.bank.exception.InvalidAccountException;
import com.bank.exception.InvalidTransactionException;

/**
 * The balance of a loan account is the outstanding loan amount. Deposits are
 * repayments that reduce it, and money cannot be withdrawn or transferred out.
 */
public final class LoanAccount extends BankAccount {
    private static final double INTEREST_RATE = 8.0;

    public LoanAccount(long accountNumber, int customerId, double loanAmount)
            throws InvalidAccountException {
        super(accountNumber, customerId, loanAmount, 0.0);
        if (loanAmount <= 0) {
            throw new InvalidAccountException("Loan amount must be greater than zero.");
        }
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.LOAN;
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }

    @Override
    public void validateDeposit(double amount) throws InvalidTransactionException {
        validateAmount(amount, "Loan repayment");
        if (amount > balance) {
            throw new InvalidTransactionException(String.format(
                    "Repayment of %.2f exceeds the outstanding loan of %.2f for account %d.",
                    amount, balance, getAccountNumber()));
        }
    }

    @Override
    public void validateWithdrawal(double amount) throws InvalidTransactionException {
        throw new InvalidTransactionException(
                "Withdrawals and outgoing transfers are not allowed from loan account " + getAccountNumber() + ".");
    }

    @Override
    protected double balanceAfterDeposit(double amount) {
        return balance - amount;
    }
}
