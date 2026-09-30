package com.bank.record;

public record AccountSummary(long accountNumber, String accountType, double balance, String status) {
}
