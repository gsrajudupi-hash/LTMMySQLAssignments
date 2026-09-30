package service;

import exception.CustomerNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAccountException;
import model.BankAccount;
import model.CurrentAccount;
import model.Customer;
import model.SavingsAccount;
import model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BankingService {

    // Deposit
    public void deposit(BankAccount account,
                        double amount) {

        account.setBalance(
                account.getBalance() + amount
        );
    }

    // Withdraw
    public void withdraw(BankAccount account,
                         double amount)
            throws InsufficientBalanceException {

        double minimumBalance = 0;

        if(account instanceof SavingsAccount){
            minimumBalance = 1000;
        }

        if(account instanceof CurrentAccount){
            minimumBalance = 5000;
        }

        if(account.getBalance() - amount
                < minimumBalance){

            throw new InsufficientBalanceException(
                    "Insufficient Balance"
            );
        }

        account.setBalance(
                account.getBalance() - amount
        );
    }

    // Transfer
    public void transfer(
            BankAccount source,
            BankAccount target,
            double amount)
            throws InvalidAccountException,
            InsufficientBalanceException {

        if(source == null || target == null){

            throw new InvalidAccountException(
                    "Account Not Found"
            );
        }

        withdraw(source, amount);
        deposit(target, amount);
    }

    // Balance

    public double checkBalance(
            BankAccount account){

        return account.getBalance();
    }

    // Interest

    public double calculateInterest(
            BankAccount account,
            double rate){

        return account.getBalance()
                * rate / 100;
    }

    // Search Customer

    public Customer findCustomer(
            List<Customer> customers,
            int customerId)
            throws CustomerNotFoundException {

        return customers.stream()
                .filter(c ->
                        c.getCustomerId()
                                == customerId)
                .findFirst()
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer Not Found"
                        ));
    }

    // filter()

    public List<Customer> premiumCustomers(
            List<Customer> customers){

        return customers.stream()
                .filter(customer ->
                        customer.getCustomerType()
                                .equalsIgnoreCase(
                                        "PREMIUM"))
                .toList();
    }

    // map()

    public List<String> customerNames(
            List<Customer> customers){

        return customers.stream()
                .map(Customer::getName)
                .toList();
    }

    // sorted()

    public List<Customer> sortByName(
            List<Customer> customers){

        return customers.stream()
                .sorted(
                        Comparator.comparing(
                                Customer::getName))
                .toList();
    }

    // distinct()

    public List<String> distinctCities(
            List<Customer> customers){

        return customers.stream()
                .map(Customer::getCity)
                .distinct()
                .toList();
    }

    // count()

    public long countPremiumCustomers(
            List<Customer> customers){

        return customers.stream()
                .filter(customer ->
                        customer
                                .getCustomerType()
                                .equalsIgnoreCase(
                                        "PREMIUM"))
                .count();
    }

    // min()

    public Optional<BankAccount>
    minimumBalanceAccount(
            List<BankAccount> accounts){

        return accounts.stream()
                .min(
                        Comparator.comparingDouble(
                                BankAccount::getBalance
                        ));
    }

    // max()

    public Optional<BankAccount>
    maximumBalanceAccount(
            List<BankAccount> accounts){

        return accounts.stream()
                .max(
                        Comparator.comparingDouble(
                                BankAccount::getBalance
                        ));
    }

    // reduce()

    public double totalBalance(
            List<BankAccount> accounts){

        return accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(
                        0.0,
                        Double::sum
                );
    }

    // average

    public double averageBalance(
            List<BankAccount> accounts){

        return accounts.stream()
                .collect(
                        Collectors.averagingDouble(
                                BankAccount::getBalance));
    }

    // groupingBy()

    public Map<String,List<Customer>>
    groupCustomersByCity(
            List<Customer> customers){

        return customers.stream()
                .collect(
                        Collectors.groupingBy(
                                Customer::getCity
                        ));
    }

    // partitioningBy()

    public Map<Boolean,List<Customer>>
    partitionCustomers(
            List<Customer> customers){

        return customers.stream()
                .collect(
                        Collectors.partitioningBy(
                                customer ->
                                        customer
                                                .getCustomerType()
                                                .equalsIgnoreCase(
                                                        "PREMIUM"
                                                )
                        ));
    }

    // joining()

    public String customerNamesJoined(
            List<Customer> customers){

        return customers.stream()
                .map(Customer::getName)
                .collect(
                        Collectors.joining(
                                ", "
                        ));
    }

    // summingDouble()

    public Map<String,Double>
    totalBalanceByAccountType(
            List<BankAccount> accounts){

        return accounts.stream()
                .collect(
                        Collectors.groupingBy(
                                BankAccount::getAccountType,
                                Collectors.summingDouble(
                                        BankAccount::getBalance
                                )
                        ));
    }

    // Top 3 Accounts

    public List<BankAccount>
    topThreeAccounts(
            List<BankAccount> accounts){

        return accounts.stream()
                .sorted(
                        Comparator
                                .comparingDouble(
                                        BankAccount
                                                ::getBalance)
                                .reversed())
                .limit(3)
                .toList();
    }

    // Transaction Summary

    public Map<String,Double>
    transactionSummary(
            List<Transaction> transactions){

        return transactions.stream()
                .collect(
                        Collectors.groupingBy(
                                Transaction
                                        ::getTransactionType,

                                Collectors.summingDouble(
                                        Transaction
                                                ::getAmount
                                )
                        ));
    }

    // Dashboard

    public void generateBankingDashboard(

            List<Customer> customers,

            List<BankAccount> accounts){

        System.out.println(
                "Total Customers : "
                        + customers.size());

        System.out.println(
                "Premium Customers : "
                        + countPremiumCustomers(
                        customers));

        System.out.println(
                "Total Accounts : "
                        + accounts.size());

        System.out.println(
                "Total Balance : "
                        + totalBalance(accounts));

        System.out.println(
                "Average Balance : "
                        + averageBalance(accounts));
    }

    // Java 17 Switch Expression

    public String classifyTransaction(
            String transactionType) {

        return switch (transactionType) {

            case "DEPOSIT",
                 "INTEREST" -> "CREDIT";

            case "WITHDRAW",
                 "LOAN_PAYMENT" -> "DEBIT";

            case "TRANSFER" -> "TRANSFER";

            default -> "UNKNOWN";
        };
    }
}