package com.bank.model;

import java.util.Objects;

public abstract sealed class BankAccount permits SavingsAccount, CurrentAccount, LoanAccount {
	private final long accountNumber;
	private final int customerId;
	private double balance;
	private String status;

	protected BankAccount(long no, int customerId, double balance, String status) {
		this.accountNumber = no;
		this.customerId = customerId;
		this.balance = balance;
		this.status = status;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public int getCustomerId() {
		return customerId;
	}

	public double getBalance() {
		return balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void credit(double amount) {
		balance += amount;
	}

	public void debit(double amount) {
		balance -= amount;
	}

	public abstract String getAccountType();

	public abstract double getMinimumBalance();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{accountNumber=" + accountNumber + ", customerId=" + customerId + ", type="
				+ getAccountType() + ", balance=" + String.format("%.2f", balance) + ", status=" + status + "}";
	}

	@Override
	public boolean equals(Object o) {
		return this == o || (o instanceof BankAccount a && accountNumber == a.accountNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accountNumber);
	}
}
