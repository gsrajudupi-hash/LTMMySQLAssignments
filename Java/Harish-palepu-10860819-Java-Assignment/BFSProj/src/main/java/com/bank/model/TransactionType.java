package com.bank.model;

public enum TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER_DEBIT,
    TRANSFER_CREDIT;

    public boolean isTransfer() {
        return this == TRANSFER_DEBIT || this == TRANSFER_CREDIT;
    }

    public boolean isMoneyIn() {
        return this == DEPOSIT || this == TRANSFER_CREDIT;
    }
}
