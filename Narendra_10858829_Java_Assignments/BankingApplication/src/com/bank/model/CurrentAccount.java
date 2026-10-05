package com.bank.model;


/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:30:41 pm
 * project  : BankingApplication
 */

public final class CurrentAccount extends BankAccount {

	public CurrentAccount(long accountNumber, int customerId, double balance, String status) {
		super(accountNumber, customerId, balance, status);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "CURRENT";
	}

}