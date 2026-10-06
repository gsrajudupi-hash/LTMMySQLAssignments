package com.bank.exception;

public class InvalidCustomerException extends BankingException {
    public InvalidCustomerException(String message) {
        super(message);
    }
}
