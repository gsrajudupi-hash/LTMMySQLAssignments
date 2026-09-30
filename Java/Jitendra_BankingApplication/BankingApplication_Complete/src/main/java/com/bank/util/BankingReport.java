package com.bank.util;

import com.bank.model.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

public final class BankingReport {
    private BankingReport() {
    }

    public static String accountStatement(Customer c, BankAccount a) {
        LocalDate d = LocalDate.now();
        LocalTime t = LocalTime.now();
        LocalDateTime dt = LocalDateTime.of(d, t);
        return """
                ================================
                BANK ACCOUNT STATEMENT
                ================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : Rs. %,.2f
                Status         : %s
                Generated At   : %s
                ================================
                """.formatted(a.getAccountNumber(), c.getName(), a.getAccountType(), a.getBalance(), a.getStatus(), dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }

    public static String customerJson(Customer c) {
        return """
                {"customerId":%d,"name":"%s","city":"%s","customerType":"%s"}
                """.formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
    }

    public static String accountJson(BankAccount a) {
        return """
                {"accountNumber":%d,"customerId":%d,"accountType":"%s","balance":%.2f,"status":"%s"}
                """.formatted(a.getAccountNumber(), a.getCustomerId(), a.getAccountType(), a.getBalance(), a.getStatus());
    }

    public static String transactionJson(Transaction t) {
        return """
                {"transactionId":%d,"accountNumber":%d,"transactionType":"%s","amount":%.2f,"transactionDate":"%s"}
                """.formatted(t.getTransactionId(), t.getAccountNumber(), t.getTransactionType(), t.getAmount(), t.getTransactionDate());
    }

    public static String classify(String type) {
        return switch (type.toUpperCase()) {
            case "DEPOSIT", "INTEREST" -> "CREDIT";
            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
            case "TRANSFER" -> "TRANSFER";
            default -> "UNKNOWN";
        };
    }

    public static String describeAccount(BankAccount a) {
        if (a instanceof SavingsAccount s) return "Savings account, minimum balance Rs. " + s.minimumBalance();
        if (a instanceof CurrentAccount c) return "Current account, minimum balance Rs. " + c.minimumBalance();
        if (a instanceof LoanAccount l) return "Loan account, outstanding Rs. " + l.getBalance();
        return "Unknown account";
    }
}