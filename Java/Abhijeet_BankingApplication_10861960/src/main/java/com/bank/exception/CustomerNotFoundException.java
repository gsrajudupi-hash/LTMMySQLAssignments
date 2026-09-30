package com.bank.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String m) {
        super(m);
    }
}