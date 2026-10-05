package com.bank.model;


/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:33:03 pm
 * project  : BankingApplication
 */

public final class SavingsAccount extends BankAccount {

	public SavingsAccount(long accountNumber, int customerId, double balance, String status) {
		super(accountNumber, customerId, balance, status);
	}

	@Override
	public String getAccountType() {
		
		return "SAVINGS";
	}

}
