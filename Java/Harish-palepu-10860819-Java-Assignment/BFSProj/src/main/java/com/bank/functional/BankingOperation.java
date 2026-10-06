package com.bank.functional;

import com.bank.exception.BankingException;

/**
 * A banking action that can be executed from the console menu.
 */
@FunctionalInterface
public interface BankingOperation {
    void execute() throws BankingException;
}
