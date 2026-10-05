package com.bank.util;

import com.bank.model.BankAccount;
import com.bank.model.CurrentAccount;
import com.bank.model.LoanAccount;
import com.bank.model.SavingsAccount;
import com.bank.service.BankingService;

/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:36:57 pm
 * project  : BankingApplication
 */

public class BankingReport {
	 
    public static void generateBankingDashboard(
            BankingService service) {
 
        long totalCustomers =
                service.getCustomers().size();
 
        long premiumCustomers =
                service.countPremiumCustomers();
 
        long totalAccounts =
                service.getAccounts().size();
 
        double totalBalance =
                service.getTotalBalance();
 
        double averageBalance =
                service.getAverageBalance();
 
        String highestBalance =
                service.getHighestBalance()
                        .map(a ->
                                String.valueOf(a.getBalance()))
                        .orElse("N/A");
 
        long totalTransactions =
                service.getTransactions().size();
 
        String dashboard = """
                ========================================
                   BANKING ANALYTICS DASHBOARD
                ========================================
                Total Customers       : %d
                Premium Customers     : %d
                Total Accounts        : %d
                Total Balance         : %.2f
                Average Balance       : %.2f
                Highest Balance       : %s
                Total Transactions    : %d
                Total Deposits        : %.2f
                Total Withdrawals     : %.2f
                ========================================
                """.formatted(
                totalCustomers,
                premiumCustomers,
                totalAccounts,
                totalBalance,
                averageBalance,
                highestBalance,
                totalTransactions,
                service.totalDeposits(),
                service.totalWithdrawals());
 
        System.out.println(dashboard);
 
        System.out.println(
                "Accounts By Type: " +
                service.accountsByType());
 
        System.out.println(
                "Customers By City: " +
                service.customersByCity());
 
        System.out.println(
                "Balance By Account Type: " +
                service.balanceByAccountType());
    }
 
    // Pattern matching
 
    public static void displayAccountType(
            BankAccount account) {
 
        if (account instanceof SavingsAccount savings) {
 
            System.out.println(
                    "Savings Account: " +
                    savings.getAccountNumber());
 
        } else if (account instanceof CurrentAccount current) {
 
            System.out.println(
                    "Current Account: " +
                    current.getAccountNumber());
 
        } else if (account instanceof LoanAccount loan) {
 
            System.out.println(
                    "Loan Account: " +
                    loan.getAccountNumber());
        }
    }
 
    // Switch expression
 
    public static String classifyTransaction(
            String transactionType) {
 
        return switch (transactionType) {
 
            case "DEPOSIT", "INTEREST" ->
                    "CREDIT";
 
            case "WITHDRAW", "LOAN_PAYMENT" ->
                    "DEBIT";
 
            case "TRANSFER" ->
                    "TRANSFER";
 
            default ->
                    "UNKNOWN";
        };
    }
}
