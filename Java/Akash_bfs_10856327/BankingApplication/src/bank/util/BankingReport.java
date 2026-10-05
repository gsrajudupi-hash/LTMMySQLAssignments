package bank.util;

import bank.model.BankAccount;
import bank.model.Customer;
import bank.model.Transaction;

public final class BankingReport {

    private BankingReport() {
    }

    public static String generateAccountStatement(
            BankAccount account,
            Customer customer) {

        return """
                =================================
                     BANK ACCOUNT STATEMENT
                =================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : Rs. %,.2f
                Status         : %s
                =================================
                """.formatted(
                account.getAccountNumber(),
                customer.getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    public static String generateCustomerJson(Customer customer) {

        return """
                {
                    "customerId": %d,
                    "name": "%s",
                    "email": "%s",
                    "city": "%s",
                    "phone": "%s",
                    "customerType": "%s"
                }
                """.formatted(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCity(),
                customer.getPhone(),
                customer.getCustomerType()
        );
    }

    public static String generateAccountJson(BankAccount account) {

        return """
                {
                    "accountNumber": %d,
                    "customerId": %d,
                    "accountType": "%s",
                    "balance": %.2f,
                    "status": "%s"
                }
                """.formatted(
                account.getAccountNumber(),
                account.getCustomerId(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    public static String generateTransactionJson(
            Transaction transaction) {

        return """
                {
                    "transactionId": %d,
                    "accountNumber": %d,
                    "transactionType": "%s",
                    "amount": %.2f,
                    "transactionDate": "%s",
                    "description": "%s"
                }
                """.formatted(
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription()
        );
    }
}