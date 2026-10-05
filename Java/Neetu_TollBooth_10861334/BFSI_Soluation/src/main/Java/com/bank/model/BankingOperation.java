package com.bank.model;


@FunctionalInterface
public interface BankingOperation {
    double execute(double amount, double balance);
}
