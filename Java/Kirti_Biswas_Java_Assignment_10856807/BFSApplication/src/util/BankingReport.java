package util;

import model.BankAccount;
import model.Customer;
import model.Transaction;

import java.util.List;

public class BankingReport {

    // Customer Report
    public static void generateCustomerReport(
            List<Customer> customers) {

        System.out.println("\n========== CUSTOMER REPORT ==========");

        customers.forEach(System.out::println);

        System.out.println("Total Customers : "
                + customers.size());

        System.out.println("=====================================");
    }

    // Account Report
    public static void generateAccountReport(
            List<BankAccount> accounts) {

        System.out.println("\n========== ACCOUNT REPORT ==========");

        accounts.forEach(System.out::println);

        System.out.println("Total Accounts : "
                + accounts.size());

        System.out.println("====================================");
    }

    // Transaction Report
    public static void generateTransactionReport(
            List<Transaction> transactions) {

        System.out.println("\n======== TRANSACTION REPORT ========");

        transactions.forEach(System.out::println);

        System.out.println("Total Transactions : "
                + transactions.size());

        System.out.println("====================================");
    }

    // Java 17 Text Block
    public static String generateAccountStatement(
            BankAccount account,
            String customerName) {

        return """
                ==================================
                BANK ACCOUNT STATEMENT
                ==================================

                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : %.2f
                Status         : %s

                ==================================
                """
                .formatted(
                        account.getAccountNumber(),
                        customerName,
                        account.getAccountType(),
                        account.getBalance(),
                        account.getStatus()
                );
    }

    // Banking Dashboard
    public static void generateDashboard(

            int totalCustomers,

            int totalAccounts,

            double totalBalance,

            double averageBalance,

            long premiumCustomers) {

        String dashboard = """
                ==================================
                BANKING ANALYTICS DASHBOARD
                ==================================

                Total Customers   : %d
                Premium Customers : %d
                Total Accounts    : %d

                Total Balance     : %.2f
                Average Balance   : %.2f

                ==================================
                """
                .formatted(
                        totalCustomers,
                        premiumCustomers,
                        totalAccounts,
                        totalBalance,
                        averageBalance
                );

        System.out.println(dashboard);
    }
}