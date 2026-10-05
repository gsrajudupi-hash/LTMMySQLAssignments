package bank.service;

import bank.exception.CustomerNotFoundException;
import bank.exception.InsufficientBalanceException;
import bank.exception.InvalidAccountException;
import bank.exception.InvalidTransactionException;
import bank.model.BankAccount;
import bank.model.Customer;
import bank.model.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BankingService {

    private final List<Customer> customers;
    private final List<BankAccount> accounts;
    private final List<Transaction> transactions;

    private final AtomicInteger transactionIdGenerator;

    public BankingService(List<Customer> customers,
                          List<BankAccount> accounts,
                          List<Transaction> transactions) {

        this.customers = customers;
        this.accounts = accounts;
        this.transactions = transactions;

        int maximumTransactionId = transactions.stream()
                .mapToInt(Transaction::getTransactionId)
                .max()
                .orElse(0);

        this.transactionIdGenerator =
                new AtomicInteger(maximumTransactionId + 1);
    }

    public Customer findCustomer(int customerId) {

        return customers.stream()
                .filter(customer ->
                        customer.getCustomerId() == customerId)
                .findFirst()
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found: " + customerId
                        ));
    }

    public BankAccount findAccount(long accountNumber) {

        return accounts.stream()
                .filter(account ->
                        account.getAccountNumber() == accountNumber)
                .findFirst()
                .orElseThrow(() ->
                        new InvalidAccountException(
                                "Account not found: " + accountNumber
                        ));
    }

    public void deposit(long accountNumber, double amount) {

        validateAmount(amount);

        BankAccount account = findAccount(accountNumber);

        account.setBalance(account.getBalance() + amount);

        createTransaction(
                accountNumber,
                "DEPOSIT",
                amount,
                "Money deposited"
        );

        System.out.println("Deposit successful.");
        System.out.println("Available balance: " + account.getBalance());
    }

    public void withdraw(long accountNumber, double amount) {

        validateAmount(amount);

        BankAccount account = findAccount(accountNumber);

        double balanceAfterWithdrawal =
                account.getBalance() - amount;

        if (balanceAfterWithdrawal < account.getMinimumBalance()) {
            throw new InsufficientBalanceException(
                    "Withdrawal failed. Minimum balance of Rs." +
                            account.getMinimumBalance() +
                            " must be maintained."
            );
        }

        account.setBalance(balanceAfterWithdrawal);

        createTransaction(
                accountNumber,
                "WITHDRAW",
                amount,
                "Money withdrawn"
        );

        System.out.println("Withdrawal successful.");
        System.out.println("Available balance: " + account.getBalance());
    }

    public void transfer(long sourceAccountNumber,
                         long targetAccountNumber,
                         double amount) {

        validateAmount(amount);

        if (sourceAccountNumber == targetAccountNumber) {
            throw new InvalidTransactionException(
                    "Source and target accounts cannot be the same."
            );
        }

        BankAccount sourceAccount =
                findAccount(sourceAccountNumber);

        BankAccount targetAccount =
                findAccount(targetAccountNumber);

        double sourceBalanceAfterTransfer =
                sourceAccount.getBalance() - amount;

        if (sourceBalanceAfterTransfer <
                sourceAccount.getMinimumBalance()) {

            throw new InsufficientBalanceException(
                    "Transfer failed due to insufficient balance."
            );
        }

        sourceAccount.setBalance(sourceBalanceAfterTransfer);

        targetAccount.setBalance(
                targetAccount.getBalance() + amount
        );

        createTransaction(
                sourceAccountNumber,
                "TRANSFER",
                amount,
                "Transferred to account " + targetAccountNumber
        );

        createTransaction(
                targetAccountNumber,
                "DEPOSIT",
                amount,
                "Received from account " + sourceAccountNumber
        );

        System.out.println("Transfer successful.");
    }

    public double calculateInterest(long accountNumber,
                                    double interestRate) {

        if (interestRate < 0) {
            throw new InvalidTransactionException(
                    "Interest rate cannot be negative."
            );
        }

        BankAccount account = findAccount(accountNumber);

        double interest =
                account.getBalance() * interestRate / 100;

        account.setBalance(
                account.getBalance() + interest
        );

        createTransaction(
                accountNumber,
                "INTEREST",
                interest,
                "Interest credited at " + interestRate + "%"
        );

        return interest;
    }

    public double checkBalance(long accountNumber) {
        return findAccount(accountNumber).getBalance();
    }

    private void validateAmount(double amount) {

        if (amount <= 0) {
            throw new InvalidTransactionException(
                    "Amount must be greater than zero."
            );
        }
    }

    private void createTransaction(long accountNumber,
                                   String transactionType,
                                   double amount,
                                   String description) {

        Transaction transaction = new Transaction(
                transactionIdGenerator.getAndIncrement(),
                accountNumber,
                transactionType,
                amount,
                LocalDateTime.now(),
                description
        );

        transactions.add(transaction);
    }
}