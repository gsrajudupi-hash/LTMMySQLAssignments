package com.bank.model;


/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:30:23 pm
 * project  : BankingApplication
 */

public sealed abstract class BankAccount permits SavingsAccount,CurrentAccount,LoanAccount{
	
	protected long accountNumber;
	
	protected int customerId;
	
	protected double balance;
	
	protected String status;

	public BankAccount(long accountNumber, int customerId, double balance, String status) {
		super();
		this.accountNumber = accountNumber;
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

	
	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", customerId=" + customerId + getAccountType()+ ", balance=" + balance
				+ ", status=" + status + "]";
	}
	
	//Deposit method
	public void deposit(double amount) {
		balance += amount;
	}
	
	//Withdraw method
	public void withdraw(double amount) {
		balance -= amount;
	}
	
	//to display account type
	public abstract String getAccountType();
	
	

}

