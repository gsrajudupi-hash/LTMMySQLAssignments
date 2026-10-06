package com.bank.exception;

public class CustomerNotFoundException extends BankingException {
    public CustomerNotFoundException(int customerId) {
        super("Customer with ID " + customerId + " was not found.");
    }
}
