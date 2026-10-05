package com.bank.model;


/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:31:48 pm
 * project  : BankingApplication
 */

public non-sealed class LoanAccount extends BankAccount {

	public LoanAccount(long accountNumber, int customerId, double balance, String status) {
		super(accountNumber, customerId, balance, status);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getAccountType() {
		// TODO Auto-generated method stub
		return "LOAN";
	}

}
