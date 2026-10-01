package com.bank.functional;

@FunctionalInterface
public interface BankingOperation {
    double execute(double amount, double balance);
}
