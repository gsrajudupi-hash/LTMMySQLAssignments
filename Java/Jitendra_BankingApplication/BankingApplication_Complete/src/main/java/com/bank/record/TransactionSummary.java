package com.bank.record;

public record TransactionSummary(String type, long count, double total, double average) {
}
