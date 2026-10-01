package com.bank.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String m) {
        super(m);
    }
}