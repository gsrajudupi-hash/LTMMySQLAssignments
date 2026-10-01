package com.bank.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String m) {
        super(m);
    }
}