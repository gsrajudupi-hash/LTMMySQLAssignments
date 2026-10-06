package com.bank.exception;

/**
 * Base type for all checked banking errors.
 */
public abstract class BankingException extends Exception {
    protected BankingException(String message) {
        super(message);
    }
}
