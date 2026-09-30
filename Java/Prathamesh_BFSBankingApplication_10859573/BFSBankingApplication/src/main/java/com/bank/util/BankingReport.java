package com.bank.util;

import com.bank.model.*;

import java.util.*;
import java.util.stream.*;

public final class BankingReport {
    private BankingReport() {
    }

    public static String classify(String type) {
        return switch (type) {
            case "DEPOSIT", "INTEREST" -> "CREDIT";
            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";
            case "TRANSFER" -> "TRANSFER";
            default -> "UNKNOWN";
        };
    }

    public static String accountKind(BankAccount a) {
        if (a instanceof SavingsAccount s) return "Savings, minimum balance=" + s.minimumBalance();
        if (a instanceof CurrentAccount c) return "Current, minimum balance=" + c.minimumBalance();
        if (a instanceof LoanAccount l) return "Loan, minimum balance=" + l.minimumBalance();
        return "Unknown";
    }

    public static String statement(BankAccount a, Customer c) {
        return """
                ================================
                BANK ACCOUNT STATEMENT
                ================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : Rs. %.2f
                Status         : %s
                ================================
                """.formatted(a.getAccountNumber(), c.getName(), a.getAccountType(), a.getBalance(), a.getStatus());
    }

    public static String customerJson(Customer c) {
        return """
                {
                  "customerId": %d,
                  "name": "%s",
                  "city": "%s",
                  "customerType": "%s"
                }
                """.formatted(c.getCustomerId(), c.getName(), c.getCity(), c.getCustomerType());
    }

    public static String dashboard(List<Customer> cs, List<BankAccount> as, List<Transaction> ts) {
        double total = as.stream().mapToDouble(BankAccount::getBalance).sum();
        return """
                ========== BANKING DASHBOARD ==========
                Total Customers     : %d
                Premium Customers   : %d
                Total Accounts      : %d
                Total Balance       : Rs. %.2f
                Average Balance     : Rs. %.2f
                Highest Balance     : Rs. %.2f
                Total Transactions  : %d
                Deposits Total      : Rs. %.2f
                Withdrawals Total   : Rs. %.2f
                Accounts by Type    : %s
                Customers by City   : %s
                =======================================
                """.formatted(cs.size(), cs.stream().filter(c -> "PREMIUM".equals(c.getCustomerType())).count(), as.size(), total, as.stream().mapToDouble(BankAccount::getBalance).average().orElse(0), as.stream().mapToDouble(BankAccount::getBalance).max().orElse(0), ts.size(), ts.stream().filter(t -> "DEPOSIT".equals(t.getTransactionType())).mapToDouble(Transaction::getAmount).sum(), ts.stream().filter(t -> "WITHDRAW".equals(t.getTransactionType())).mapToDouble(Transaction::getAmount).sum(), as.stream().collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting())), cs.stream().collect(Collectors.groupingBy(Customer::getCity, Collectors.counting())));
    }
}
