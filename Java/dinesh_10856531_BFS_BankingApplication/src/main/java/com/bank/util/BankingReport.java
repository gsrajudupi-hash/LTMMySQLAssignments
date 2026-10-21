package com.bank.util;

import com.bank.model.*;

import com.bank.service.BankingService;

public class BankingReport {

    // =====================================================

    // PATTERN MATCHING

    // =====================================================

    public static void accountTypeDetails(

            BankAccount account) {

        if (account instanceof SavingsAccount savings) {

            System.out.println(

                    "Savings Account");

            System.out.println(

                    "Minimum Balance: "

                            + savings.getMinimumBalance());

        } else if (account instanceof CurrentAccount current) {

            System.out.println(

                    "Current Account");

            System.out.println(

                    "Minimum Balance: "

                            + current.getMinimumBalance());

        } else if (account instanceof LoanAccount loan) {

            System.out.println(

                    "Loan Account");

            System.out.println(

                    "Balance: "

                            + loan.getBalance());

        }

    }

    // =====================================================

    // SWITCH EXPRESSION

    // =====================================================

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

    // =====================================================

    // TEXT BLOCK ACCOUNT STATEMENT

    // =====================================================

    public static String generateAccountStatement(

            BankAccount account,

            Customer customer) {

        return """

                =========================================

                       BANK ACCOUNT STATEMENT

                =========================================

                Account Number : %d

                Customer       : %s

                Account Type   : %s

                Balance        : %.2f

                Status         : %s

                =========================================

                """.formatted(

                account.getAccountNumber(),

                customer.getName(),

                account.getAccountType(),

                account.getBalance(),

                account.getStatus());

    }

    // =====================================================

    // JSON TEXT BLOCK

    // =====================================================

    public static String customerJson(

            Customer customer) {

        return """

                {

                    "customerId": %d,

                    "name": "%s",

                    "city": "%s",

                    "customerType": "%s"

                }

                """.formatted(

                customer.getCustomerId(),

                customer.getName(),

                customer.getCity(),

                customer.getCustomerType());

    }

    // =====================================================

    // BANKING DASHBOARD

    // =====================================================

    public static void generateBankingDashboard(

            BankingService service) {

        long totalCustomers =

                service.getCustomers().size();

        long premiumCustomers =

                service.countPremiumCustomers();

        long totalAccounts =

                service.getAccounts().size();

        double totalBalance =

                service.getAccounts()

                       .stream()

                       .mapToDouble(

                               BankAccount::getBalance)

                       .sum();

        double averageBalance =

                service.getAccounts()

                       .stream()

                       .mapToDouble(

                               BankAccount::getBalance)

                       .average()

                       .orElse(0);

        double highestBalance =

                service.getAccounts()

                       .stream()

                       .mapToDouble(

                               BankAccount::getBalance)

                       .max()

                       .orElse(0);

        long totalTransactions =

                service.getTransactions().size();

        double deposits =

                service.getTransactions()

                       .stream()

                       .filter(t ->

                                       t.getTransactionType()

                                        .equals("DEPOSIT"))

                       .mapToDouble(

                               Transaction::getAmount)

                       .sum();

        double withdrawals =

                service.getTransactions()

                       .stream()

                       .filter(t ->

                                       t.getTransactionType()

                                        .equals("WITHDRAW"))

                       .mapToDouble(

                               Transaction::getAmount)

                       .sum();

        String dashboard = """

                =========================================

                     BANKING ANALYTICS DASHBOARD

                =========================================

                Total Customers       : %d

                Premium Customers     : %d

                Total Accounts        : %d

                Total Balance         : %.2f

                Average Balance       : %.2f

                Highest Balance       : %.2f

                Total Transactions    : %d

                Total Deposits        : %.2f

                Total Withdrawals     : %.2f

                =========================================

                """.formatted(

                totalCustomers,

                premiumCustomers,

                totalAccounts,

                totalBalance,

                averageBalance,

                highestBalance,

                totalTransactions,

                deposits,

                withdrawals);

        System.out.println(dashboard);

    }

}
