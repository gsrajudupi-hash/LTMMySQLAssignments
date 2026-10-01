package service;

import java17.CustomerRecord;
import model.BankAccount;
import model.Customer;
import model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AnalyticsService {

    // =========================================================
    // TASK 30 - BANKING DASHBOARD
    // =========================================================

    public String generateBankingDashboard(
            List<Customer> customers,
            List<BankAccount> accounts,
            List<Transaction> transactions) {

        // -----------------------------------------------------
        // Total customers
        // -----------------------------------------------------

        long totalCustomers =
                customers.stream()
                        .count();

        // -----------------------------------------------------
        // Premium customers
        // -----------------------------------------------------

        Predicate<Customer> premiumPredicate =
                customer ->
                        "PREMIUM".equals(
                                customer.getCustomerType()
                        );

        long premiumCustomers =
                customers.stream()
                        .filter(premiumPredicate)
                        .count();

        // -----------------------------------------------------
        // Total accounts
        // -----------------------------------------------------

        long totalAccounts =
                accounts.stream()
                        .count();

        // -----------------------------------------------------
        // Total balance
        // -----------------------------------------------------

        double totalBalance =
                accounts.stream()
                        .mapToDouble(
                                BankAccount::getBalance
                        )
                        .sum();

        // -----------------------------------------------------
        // Average balance
        // -----------------------------------------------------

        double averageBalance =
                accounts.stream()
                        .mapToDouble(
                                BankAccount::getBalance
                        )
                        .average()
                        .orElse(0);

        // -----------------------------------------------------
        // Highest balance - Optional
        // -----------------------------------------------------

        Optional<BankAccount> highestBalance =
                accounts.stream()
                        .max(
                                Comparator.comparing(
                                        BankAccount::getBalance
                                )
                        );

        String highestBalanceInfo =
                highestBalance
                        .map(account ->
                                account.getAccountNumber()
                                        + " : "
                                        + account.getBalance()
                        )
                        .orElse("No accounts");

        // -----------------------------------------------------
        // Transaction count
        // -----------------------------------------------------

        long totalTransactions =
                transactions.stream()
                        .count();

        // -----------------------------------------------------
        // Deposits
        // -----------------------------------------------------

        double totalDeposits =
                transactions.stream()
                        .filter(transaction ->
                                "DEPOSIT".equals(
                                        transaction
                                                .getTransactionType()
                                ))
                        .mapToDouble(
                                Transaction::getAmount
                        )
                        .sum();

        // -----------------------------------------------------
        // Withdrawals
        // -----------------------------------------------------

        double totalWithdrawals =
                transactions.stream()
                        .filter(transaction ->
                                "WITHDRAW".equals(
                                        transaction
                                                .getTransactionType()
                                ))
                        .mapToDouble(
                                Transaction::getAmount
                        )
                        .sum();

        // -----------------------------------------------------
        // Accounts by type
        // -----------------------------------------------------

        Map<String, Long> accountsByType =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getAccountType,
                                        Collectors.counting()
                                )
                        );

        // -----------------------------------------------------
        // Customers by city
        // -----------------------------------------------------

        Map<String, Long> customersByCity =
                customers.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Customer::getCity,
                                        Collectors.counting()
                                )
                        );

        // -----------------------------------------------------
        // Records
        // -----------------------------------------------------

        List<CustomerRecord> premiumCustomerRecords =
                customers.stream()
                        .filter(premiumPredicate)
                        .map(customer ->
                                new CustomerRecord(
                                        customer.getCustomerId(),
                                        customer.getName(),
                                        customer.getCity(),
                                        customer.getCustomerType()
                                )
                        )
                        .collect(Collectors.toList());

        // -----------------------------------------------------
        // Text Block
        // -----------------------------------------------------

        return """
                ========================================
                    BFS BANKING ANALYTICS DASHBOARD
                ========================================

                CUSTOMER SUMMARY
                ----------------------------------------
                Total Customers       : %d
                Premium Customers     : %d

                ACCOUNT SUMMARY
                ----------------------------------------
                Total Accounts        : %d
                Total Balance         : %.2f
                Average Balance       : %.2f
                Highest Balance       : %s

                TRANSACTION SUMMARY
                ----------------------------------------
                Total Transactions    : %d
                Total Deposits        : %.2f
                Total Withdrawals     : %.2f

                ACCOUNTS BY TYPE
                ----------------------------------------
                %s

                CUSTOMERS BY CITY
                ----------------------------------------
                %s

                PREMIUM CUSTOMER RECORDS
                ----------------------------------------
                %s

                ========================================
                """.formatted(
                totalCustomers,
                premiumCustomers,
                totalAccounts,
                totalBalance,
                averageBalance,
                highestBalanceInfo,
                totalTransactions,
                totalDeposits,
                totalWithdrawals,
                accountsByType,
                customersByCity,
                premiumCustomerRecords
        );
    }


    // =========================================================
    // CUSTOMER REPORT
    // =========================================================

    public void customerReport(
            List<Customer> customers) {

        System.out.println(
                "\n========== CUSTOMER REPORT =========="
        );

        customers.forEach(customer ->
                System.out.println(
                        "ID: "
                                + customer.getCustomerId()
                                + " | Name: "
                                + customer.getName()
                                + " | City: "
                                + customer.getCity()
                                + " | Type: "
                                + customer.getCustomerType()
                )
        );
    }


    // =========================================================
    // ACCOUNT TYPE REPORT
    // =========================================================

    public void accountTypeReport(
            List<BankAccount> accounts) {

        System.out.println(
                "\n========== ACCOUNT TYPE REPORT =========="
        );

        Map<String, Long> result =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getAccountType,
                                        Collectors.counting()
                                )
                        );

        result.forEach(
                (type, count) ->
                        System.out.println(
                                type + " = " + count
                        )
        );
    }


    // =========================================================
    // TRANSACTION REPORT
    // =========================================================

    public void transactionReport(
            List<Transaction> transactions) {

        System.out.println(
                "\n========== TRANSACTION REPORT =========="
        );

        Map<String, Double> result =
                transactions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Transaction::getTransactionType,
                                        Collectors.summingDouble(
                                                Transaction::getAmount
                                        )
                                )
                        );

        result.forEach(
                (type, amount) ->
                        System.out.println(
                                type + " = " + amount
                        )
        );
    }
}
