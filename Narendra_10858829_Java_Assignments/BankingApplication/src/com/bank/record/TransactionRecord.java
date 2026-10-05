package com.bank.record;

import java.util.Objects;

/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:34:17 pm
 * project  : BankingApplication
 */

public record TransactionRecord(int transactionId, long accountNumber,String transactionType,double amount) {
	public TransactionRecord{
		if(amount<=0) {
			throw new IllegalArgumentException("Transcation amount must be positive");
		}
		Objects.requireNonNull(transactionType,"Transaction type cannot be null");
	}

}
