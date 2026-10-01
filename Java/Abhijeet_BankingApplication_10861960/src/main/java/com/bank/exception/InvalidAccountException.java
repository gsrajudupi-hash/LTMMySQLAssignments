package com.bank.exception;

public class InvalidAccountException extends RuntimeException {
    public InvalidAccountException(String m) {
        super(m);
    }
}