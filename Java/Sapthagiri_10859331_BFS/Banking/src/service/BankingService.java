package service;

import exception.InsufficientBalanceException;
import exception.InvalidAccountException;
import exception.InvalidTransactionException;
import model.BankAccount;
import model.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankingService {

    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;

    private int transactionIdCounter;

    public BankingService(
            List<BankAccount> accounts,
            List<Transaction> transactions) {

        this.accounts = new ArrayList<>(accounts);
        this.transactions = new ArrayList<>(transactions);

        this.transactionIdCounter =
                transactions.stream()
                        .mapToInt(Transaction::getTransactionId)
                        .max()
                        .orElse(0);
    }

    // =========================================================
    // TASK 31 - DEPOSIT
    // =========================================================

    public void deposit(
            long accountNumber,
            double amount) {

        validateAmount(amount);

        BankAccount account =
                findAccount(accountNumber);

        validateActiveAccount(account);

        account.setBalance(
                account.getBalance() + amount
        );

        createTransaction(
                accountNumber,
                "DEPOSIT",
                amount,
                "Cash deposit"
        );
    }

    // =========================================================
    // TASK 31 - WITHDRAW
    // =========================================================

    public void withdraw(
            long accountNumber,
            double amount) {

        validateAmount(amount);

        BankAccount account =
                findAccount(accountNumber);

        validateActiveAccount(account);

        double minimumBalance =
                getMinimumBalance(account);

        double remainingBalance =
                account.getBalance() - amount;

        if (remainingBalance < minimumBalance) {

            throw new InsufficientBalanceException(
                    "Insufficient balance. "
                            + "Minimum required balance for "
                            + account.getAccountType()
                            + " account is "
                            + minimumBalance
            );
        }

        account.setBalance(remainingBalance);

        createTransaction(
                accountNumber,
                "WITHDRAW",
                amount,
                "Cash withdrawal"
        );
    }

    // =========================================================
    // TASK 32 - TRANSFER
    // =========================================================

    public void transfer(
            long sourceAccountNumber,
            long targetAccountNumber,
            double amount) {

        validateAmount(amount);

        if (sourceAccountNumber == targetAccountNumber) {

            throw new InvalidTransactionException(
                    "Source and target accounts cannot be same"
            );
        }

        BankAccount sourceAccount =
                findAccount(sourceAccountNumber);

        BankAccount targetAccount =
                findAccount(targetAccountNumber);

        validateActiveAccount(sourceAccount);
        validateActiveAccount(targetAccount);

        double minimumBalance =
                getMinimumBalance(sourceAccount);

        double remainingBalance =
                sourceAccount.getBalance() - amount;

        if (remainingBalance < minimumBalance) {

            throw new InsufficientBalanceException(
                    "Insufficient balance for transfer. "
                            + "Minimum balance required: "
                            + minimumBalance
            );
        }

        // Debit source
        sourceAccount.setBalance(
                sourceAccount.getBalance() - amount
        );

        // Credit target
        targetAccount.setBalance(
                targetAccount.getBalance() + amount
        );

        // Create transaction for source
        createTransaction(
                sourceAccountNumber,
                "TRANSFER",
                amount,
                "Transfer to account "
                        + targetAccountNumber
        );

        // Create transaction for target
        createTransaction(
                targetAccountNumber,
                "TRANSFER",
                amount,
                "Transfer from account "
                        + sourceAccountNumber
        );
    }

    // =========================================================
    // TASK 31 - CALCULATE INTEREST
    // =========================================================

    public double calculateInterest(
            long accountNumber,
            double interestRate) {

        if (interestRate <= 0) {

            throw new InvalidTransactionException(
                    "Interest rate must be positive"
            );
        }

        BankAccount account =
                findAccount(accountNumber);

        validateActiveAccount(account);

        double interest =
                account.getBalance()
                        * interestRate
                        / 100;

        return interest;
    }

    // =========================================================
    // TASK 31 - CHECK BALANCE
    // =========================================================

    public double checkBalance(
            long accountNumber) {

        BankAccount account =
                findAccount(accountNumber);

        return account.getBalance();
    }

    // =========================================================
    // FIND ACCOUNT
    // =========================================================

    public BankAccount findAccount(
            long accountNumber) {

        return accounts.stream()
                .filter(account ->
                        account.getAccountNumber()
                                == accountNumber)
                .findFirst()
                .orElseThrow(
                        () ->
                                new InvalidAccountException(
                                        "Account not found: "
                                                + accountNumber
                                )
                );
    }

    // =========================================================
    // MINIMUM BALANCE
    // =========================================================

    private double getMinimumBalance(
            BankAccount account) {

        return switch (account.getAccountType()) {

            case "SAVINGS" -> 1000;

            case "CURRENT" -> 5000;

            default ->
                    throw new InvalidAccountException(
                            "Unsupported account type: "
                                    + account.getAccountType()
                    );
        };
    }

    // =========================================================
    // VALIDATE AMOUNT
    // =========================================================

    private void validateAmount(double amount) {

        if (amount <= 0) {

            throw new InvalidTransactionException(
                    "Transaction amount must be greater than zero"
            );
        }
    }

    // =========================================================
    // VALIDATE ACCOUNT STATUS
    // =========================================================

    private void validateActiveAccount(
            BankAccount account) {

        if (!"ACTIVE".equals(account.getStatus())) {

            throw new InvalidAccountException(
                    "Account is not active: "
                            + account.getAccountNumber()
            );
        }
    }

    // =========================================================
    // CREATE TRANSACTION
    // =========================================================

    private void createTransaction(
            long accountNumber,
            String transactionType,
            double amount,
            String description) {

        Transaction transaction =
                new Transaction(
                        ++transactionIdCounter,
                        accountNumber,
                        transactionType,
                        amount,
                        LocalDateTime.now(),
                        description
                );

        transactions.add(transaction);
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // =========================================================
    // TASK 33 - ALL TRANSACTIONS
    // =========================================================

    public List<Transaction> getAllTransactions() {

        return new ArrayList<>(transactions);
    }

    // =========================================================
    // DEPOSITS
    // =========================================================

    public List<Transaction> getDeposits() {

        return transactions.stream()
                .filter(transaction ->
                        "DEPOSIT".equals(
                                transaction.getTransactionType()
                        ))
                .collect(Collectors.toList());
    }

    // =========================================================
    // WITHDRAWALS
    // =========================================================

    public List<Transaction> getWithdrawals() {

        return transactions.stream()
                .filter(transaction ->
                        "WITHDRAW".equals(
                                transaction.getTransactionType()
                        ))
                .collect(Collectors.toList());
    }

    // =========================================================
    // TRANSFERS
    // =========================================================

    public List<Transaction> getTransfers() {

        return transactions.stream()
                .filter(transaction ->
                        "TRANSFER".equals(
                                transaction.getTransactionType()
                        ))
                .collect(Collectors.toList());
    }

    // =========================================================
    // ABOVE 50,000
    // =========================================================

    public List<Transaction> getTransactionsAbove50000() {

        return transactions.stream()
                .filter(transaction ->
                        transaction.getAmount() > 50000)
                .collect(Collectors.toList());
    }

    // =========================================================
    // SORTED TRANSACTIONS
    // =========================================================

    public List<Transaction> getSortedTransactions() {

        return transactions.stream()
                .sorted(
                        Comparator.comparing(
                                Transaction::getTransactionDate
                        ).reversed()
                )
                .collect(Collectors.toList());
    }

    // =========================================================
    // LATEST FIVE
    // =========================================================

    public List<Transaction> getLatestFiveTransactions() {

        return transactions.stream()
                .sorted(
                        Comparator.comparing(
                                Transaction::getTransactionDate
                        ).reversed()
                )
                .limit(5)
                .collect(Collectors.toList());
    }
}
