package com.bank.model;

import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAccountException;
import com.bank.exception.InvalidTransactionException;

import java.util.Objects;

/**
 * Base account type. Each subclass defines its own minimum balance, interest rate,
 * and rules for money moving in and out of the account.
 */
public abstract sealed class BankAccount permits SavingsAccount, CurrentAccount, LoanAccount {
    private final long accountNumber;
    private final int customerId;
    private final double openingBalance;
    private final double minimumBalance;
    protected double balance;

    protected BankAccount(long accountNumber, int customerId, double openingBalance,
                          double minimumBalance) throws InvalidAccountException {
        if (accountNumber <= 0) {
            throw new InvalidAccountException("Account number must be a positive number.");
        }
        if (customerId <= 0) {
            throw new InvalidAccountException("Customer ID must be a positive number.");
        }
        if (!Double.isFinite(openingBalance) || openingBalance < minimumBalance) {
            throw new InvalidAccountException(String.format(
                    "Opening balance for a %s account must be at least %.2f.",
                    getClass().getSimpleName(), minimumBalance));
        }
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.openingBalance = openingBalance;
        this.minimumBalance = minimumBalance;
        this.balance = openingBalance;
    }

    public abstract AccountType getAccountType();

    public abstract double getInterestRate();

    public double calculateInterest() {
        return balance * getInterestRate() / 100;
    }

    public void validateDeposit(double amount) throws InvalidTransactionException {
        validateAmount(amount, "Deposit");
        if (!Double.isFinite(balance + amount)) {
            throw new InvalidTransactionException("Deposit would exceed the maximum account balance.");
        }
    }

    public void validateWithdrawal(double amount)
            throws InvalidTransactionException, InsufficientBalanceException {
        validateAmount(amount, "Withdrawal");
        if (balance - amount < minimumBalance) {
            throw new InsufficientBalanceException(accountNumber, balance, amount, minimumBalance);
        }
    }

    public final double deposit(double amount) throws InvalidTransactionException {
        validateDeposit(amount);
        balance = balanceAfterDeposit(amount);
        return balance;
    }

    public final double withdraw(double amount)
            throws InvalidTransactionException, InsufficientBalanceException {
        validateWithdrawal(amount);
        balance -= amount;
        return balance;
    }

    protected double balanceAfterDeposit(double amount) {
        return balance + amount;
    }

    protected static void validateAmount(double amount, String operation)
            throws InvalidTransactionException {
        if (!Double.isFinite(amount) || amount <= 0) {
            throw new InvalidTransactionException(
                    operation + " amount must be greater than zero. Received: " + amount);
        }
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BankAccount other && accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%-10d %-8d %-8s %15.2f %10.2f %6.2f%%",
                accountNumber, customerId, getAccountType(), balance, minimumBalance, getInterestRate());
    }
}
