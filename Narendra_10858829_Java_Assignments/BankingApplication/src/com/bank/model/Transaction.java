package com.bank.model;

import java.time.LocalDateTime;

/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:33:20 pm
 * project  : BankingApplication
 */

public class Transaction {
	
	private int transactionId;
	
	private long accountNumber;
	
	private String transactionType;
	
	private double amount;
	
	private LocalDateTime transactionDate;
	
	private String description;

	public Transaction(int transactionId, long accountNumber, String transactionType, double amount,
			LocalDateTime transactionDate, String description) {
		super();
		this.transactionId = transactionId;
		this.accountNumber = accountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.description = description;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Transcation [transactionId=" + transactionId + ", accountNumber=" + accountNumber + ", transactionType="
				+ transactionType + ", amount=" + amount + ", transactionDate=" + transactionDate + ", description="
				+ description + "]";
	}
	
	
	
	

}

