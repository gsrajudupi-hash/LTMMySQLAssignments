package com.bank.service;

import com.bank.exception.*;

import com.bank.functional.BankingOperation;

import com.bank.model.*;

import com.bank.record.AccountSummary;

import com.bank.record.CustomerRecord;

import com.bank.record.TransactionRecord;

import java.util.*;

import java.util.function.*;

import java.util.stream.Collectors;

public class BankingService {

    private final List<Customer> customers = new ArrayList<>();

    private final List<BankAccount> accounts = new ArrayList<>();

    private final List<Transaction> transactions = new ArrayList<>();

    private int transactionId = 1;

    // =====================================================

    // CUSTOMER OPERATIONS

    // =====================================================

    public void addCustomer(Customer customer) {

        customers.add(customer);

        System.out.println("Customer added successfully.");

    }

    public List<Customer> getCustomers() {

        return customers;

    }

    public void displayCustomers() {

        customers.forEach(System.out::println);

    }

    public Customer searchCustomer(int customerId)

            throws CustomerNotFoundException {

        return customers.stream()

                        .filter(c -> c.getCustomerId() == customerId)

                        .findFirst()

                        .orElseThrow(() ->

                                             new CustomerNotFoundException(

                                                     "Customer not found: " + customerId));

    }

    // =====================================================

    // ACCOUNT OPERATIONS

    // =====================================================

    public void addAccount(BankAccount account) {

        accounts.add(account);

        System.out.println(

                "Account created successfully: "

                        + account.getAccountNumber());

    }

    public List<BankAccount> getAccounts() {

        return accounts;

    }

    public BankAccount findAccount(long accountNumber)

            throws InvalidAccountException {

        return accounts.stream()

                       .filter(a ->

                                       a.getAccountNumber() == accountNumber)

                       .findFirst()

                       .orElseThrow(() ->

                                            new InvalidAccountException(

                                                    "Invalid account number: "

                                                            + accountNumber));

    }

    public void displayAccounts() {

        accounts.forEach(System.out::println);

    }

    // =====================================================

    // DEPOSIT

    // =====================================================

    public void deposit(long accountNumber, double amount)

            throws InvalidAccountException,

            InvalidTransactionException {

        if (amount <= 0) {

            throw new InvalidTransactionException(

                    "Deposit amount must be greater than zero.");

        }

        BankAccount account = findAccount(accountNumber);

        BankingOperation depositOperation =

                (money, balance) -> balance + money;

        double newBalance =

                depositOperation.execute(

                        amount,

                        account.getBalance());

        account.credit(amount);

        addTransaction(

                accountNumber,

                "DEPOSIT",

                amount,

                "Money deposited");

        System.out.println(

                "Deposit successful. Balance = "

                        + newBalance);

    }

    // =====================================================

    // WITHDRAW

    // =====================================================

    public void withdraw(long accountNumber, double amount)

            throws InvalidAccountException,

            InsufficientBalanceException,

            InvalidTransactionException {

        if (amount <= 0) {

            throw new InvalidTransactionException(

                    "Withdrawal amount must be greater than zero.");

        }

        BankAccount account = findAccount(accountNumber);

        double remainingBalance =

                account.getBalance() - amount;

        if (remainingBalance <

                account.getMinimumBalance()) {

            throw new InsufficientBalanceException(

                    "Minimum balance requirement violated.");

        }

        BankingOperation withdrawalOperation =

                (money, balance) -> balance - money;

        double newBalance =

                withdrawalOperation.execute(

                        amount,

                        account.getBalance());

        account.debit(amount);

        addTransaction(

                accountNumber,

                "WITHDRAW",

                amount,

                "Money withdrawn");

        System.out.println(

                "Withdrawal successful. Balance = "

                        + newBalance);

    }

    // =====================================================

    // TRANSFER

    // =====================================================

    public void transfer(long sourceAccount,

                         long targetAccount,

                         double amount)

            throws InvalidAccountException,

            InvalidTransactionException,

            InsufficientBalanceException {

        if (amount <= 0) {

            throw new InvalidTransactionException(

                    "Transfer amount must be greater than zero.");

        }

        BankAccount source =

                findAccount(sourceAccount);

        BankAccount target =

                findAccount(targetAccount);

        if (source.getBalance() - amount
                < source.getMinimumBalance()) {

            throw new InsufficientBalanceException(

                    "Insufficient balance for transfer.");

        }

        source.debit(amount);

        target.credit(amount);

        addTransaction(

                sourceAccount,

                "TRANSFER",

                amount,

                "Transferred to account "

                        + targetAccount);

        addTransaction(

                targetAccount,

                "TRANSFER",

                amount,

                "Received from account "

                        + sourceAccount);

        System.out.println("Transfer successful.");

    }

    // =====================================================

    // INTEREST

    // =====================================================

    public void calculateInterest(long accountNumber)

            throws InvalidAccountException {

        BankAccount account =

                findAccount(accountNumber);

        double interestRate;

        if (account instanceof SavingsAccount) {

            interestRate = 0.04;

        } else if (account instanceof CurrentAccount) {

            interestRate = 0.02;

        } else {

            interestRate = 0.0;

        }

        BankingOperation interestOperation =

                (amount, balance) ->

                        balance + (balance * amount);

        double interest =

                account.getBalance() * interestRate;

        double newBalance =

                interestOperation.execute(

                        interestRate,

                        account.getBalance());

        if (interest > 0) {

            account.credit(interest);

            addTransaction(

                    accountNumber,

                    "INTEREST",

                    interest,

                    "Interest credited");

        }

        System.out.println(

                "Interest = " + interest);

        System.out.println(

                "New Balance = " + newBalance);

    }

    // =====================================================

    // BALANCE

    // =====================================================

    public double checkBalance(long accountNumber)

            throws InvalidAccountException {

        return findAccount(accountNumber)

                .getBalance();

    }

    // =====================================================

    // TRANSACTION

    // =====================================================

    private void addTransaction(

            long accountNumber,

            String type,

            double amount,

            String description) {

        Transaction transaction =

                new Transaction(

                        transactionId++,

                        accountNumber,

                        type,

                        amount,

                        description);

        transactions.add(transaction);

    }

    public List<Transaction> getTransactions() {

        return transactions;

    }

    public void displayTransactions() {

        transactions.forEach(System.out::println);

    }

    // =====================================================

    // JAVA 8 PREDICATE

    // =====================================================

    public void predicateDemo() {

        Predicate<Customer> premiumCustomer =

                c -> c.getCustomerType()

                      .equalsIgnoreCase("PREMIUM");

        Predicate<Customer> bangaloreCustomer =

                c -> c.getCity()

                      .equalsIgnoreCase("Bangalore");

        Predicate<Customer> idGreaterThan105 =

                c -> c.getCustomerId() > 105;

        System.out.println("\nPremium Customers:");

        customers.stream()

                 .filter(premiumCustomer)

                 .forEach(System.out::println);

        System.out.println("\nBangalore Customers:");

        customers.stream()

                 .filter(bangaloreCustomer)

                 .forEach(System.out::println);

        System.out.println(

                "\nCustomer ID > 105:");

        customers.stream()

                 .filter(idGreaterThan105)

                 .forEach(System.out::println);

    }

    // =====================================================

    // JAVA 8 CONSUMER

    // =====================================================

    public void consumerDemo() {

        Consumer<Customer> displayCustomer =

                System.out::println;

        customers.forEach(displayCustomer);

    }

    // =====================================================

    // JAVA 8 FUNCTION

    // =====================================================

    public void functionDemo() {

        Function<Customer, String> getCustomerName =

                Customer::getName;

        customers.stream()

                 .map(getCustomerName)

                 .forEach(System.out::println);

    }

    // =====================================================

    // JAVA 8 SUPPLIER

    // =====================================================

    public void supplierDemo() {

        Supplier<Long> accountNumberGenerator =

                () -> System.currentTimeMillis();

        System.out.println(

                "Generated Account Number: "

                        + accountNumberGenerator.get());

    }

    // =====================================================

    // OPTIONAL

    // =====================================================

    public void optionalDemo(int customerId) {

        Optional<Customer> customer =

                customers.stream()

                         .filter(c ->

                                         c.getCustomerId()

                                                 == customerId)

                         .findFirst();

        customer.ifPresent(

                c -> System.out.println(

                        "Customer found: " + c));

        String name =

                customer

                        .map(Customer::getName)

                        .orElse("Unknown Customer");

        System.out.println("Name: " + name);

        String name2 =

                customer

                        .map(Customer::getName)

                        .orElseGet(() ->

                                           "Generated Default Name");

        System.out.println("Name using orElseGet: "

                                   + name2);

    }

    // =====================================================

    // STREAM - CUSTOMER FILTER

    // =====================================================

    public void streamCustomerOperations() {

        System.out.println(

                "\n--- Names Starting With A ---");

        customers.stream()

                 .filter(c ->

                                 c.getName()

                                  .startsWith("A"))

                 .forEach(System.out::println);

        System.out.println(

                "\n--- Customer Names ---");

        customers.stream()

                 .map(Customer::getName)

                 .forEach(System.out::println);

        System.out.println(

                "\n--- Sorted Customers ---");

        customers.stream()

                 .sorted(

                         Comparator.comparing(

                                 Customer::getName))

                 .forEach(System.out::println);

        System.out.println(

                "\n--- Reverse Sorted ---");

        customers.stream()

                 .sorted(

                         Comparator.comparing(

                                           Customer::getName)

                                   .reversed())

                 .forEach(System.out::println);

    }

    // =====================================================

    // ACCOUNT STREAM ANALYTICS

    // =====================================================

    public void accountAnalytics() {

        Optional<BankAccount> highest =

                accounts.stream()

                        .max(Comparator.comparingDouble(

                                BankAccount::getBalance));

        Optional<BankAccount> lowest =

                accounts.stream()

                        .min(Comparator.comparingDouble(

                                BankAccount::getBalance));

        double totalBalance =

                accounts.stream()

                        .map(BankAccount::getBalance)

                        .reduce(0.0, Double::sum);

        double averageBalance =

                accounts.stream()

                        .mapToDouble(

                                BankAccount::getBalance)

                        .average()

                        .orElse(0.0);

        long accountCount =

                accounts.stream().count();

        System.out.println(

                "Total Accounts: " + accountCount);

        System.out.println(

                "Total Balance: " + totalBalance);

        System.out.println(

                "Average Balance: " + averageBalance);

        highest.ifPresent(a ->

                                  System.out.println(

                                          "Highest Balance: " + a));

        lowest.ifPresent(a ->

                                 System.out.println(

                                         "Lowest Balance: " + a));

    }

    // =====================================================

    // GROUPING BY ACCOUNT TYPE

    // =====================================================

    public void groupAccountsByType() {

        Map<String, Long> result =

                accounts.stream()

                        .collect(

                                Collectors.groupingBy(

                                        BankAccount::getAccountType,

                                        Collectors.counting()));

        result.forEach(

                (type, count) ->

                        System.out.println(

                                type + " = " + count));

    }

    // =====================================================

    // BALANCE BY ACCOUNT TYPE

    // =====================================================

    public void balanceByAccountType() {

        Map<String, Double> result =

                accounts.stream()

                        .collect(

                                Collectors.groupingBy(

                                        BankAccount::getAccountType,

                                        Collectors.summingDouble(

                                                BankAccount::getBalance)));

        result.forEach(

                (type, balance) ->

                        System.out.println(

                                type + " = " + balance));

    }

    // =====================================================

    // GROUP CUSTOMERS BY CITY

    // =====================================================

    public void groupCustomersByCity() {

        Map<String, List<Customer>> result =

                customers.stream()

                         .collect(

                                 Collectors.groupingBy(

                                         Customer::getCity));

        result.forEach(

                (city, list) -> {

                    System.out.println(

                            "\n" + city);

                    list.forEach(

                            System.out::println);

                });

    }

    // =====================================================

    // PREMIUM CUSTOMER COUNT

    // =====================================================

    public long countPremiumCustomers() {

        return customers.stream()

                        .filter(c ->

                                        c.getCustomerType()

                                         .equalsIgnoreCase("PREMIUM"))

                        .count();

    }

    // =====================================================

    // PARTITIONING

    // =====================================================

    public void partitionCustomers() {

        Map<Boolean, List<Customer>> result =

                customers.stream()

                         .collect(

                                 Collectors.partitioningBy(

                                         c -> c.getCustomerType()

                                               .equalsIgnoreCase(

                                                       "PREMIUM")));

        System.out.println(

                "Premium Customers:");

        result.get(true)

              .forEach(System.out::println);

        System.out.println(

                "Non Premium Customers:");

        result.get(false)

              .forEach(System.out::println);

    }

    // =====================================================

    // TRANSACTION ANALYTICS

    // =====================================================

    public void transactionAnalytics() {

        double totalDeposits =

                transactions.stream()

                            .filter(t ->

                                            t.getTransactionType()

                                             .equals("DEPOSIT"))

                            .mapToDouble(

                                    Transaction::getAmount)

                            .sum();

        double totalWithdrawals =

                transactions.stream()

                            .filter(t ->

                                            t.getTransactionType()

                                             .equals("WITHDRAW"))

                            .mapToDouble(

                                    Transaction::getAmount)

                            .sum();

        Optional<Transaction> highest =

                transactions.stream()

                            .max(Comparator.comparingDouble(

                                    Transaction::getAmount));

        Optional<Transaction> lowest =

                transactions.stream()

                            .min(Comparator.comparingDouble(

                                    Transaction::getAmount));

        double average =

                transactions.stream()

                            .mapToDouble(

                                    Transaction::getAmount)

                            .average()

                            .orElse(0);

        long above50000 =

                transactions.stream()

                            .filter(t ->

                                            t.getAmount() > 50000)

                            .count();

        System.out.println(

                "Total Deposits: " + totalDeposits);

        System.out.println(

                "Total Withdrawals: " + totalWithdrawals);

        System.out.println(

                "Average Transaction: " + average);

        System.out.println(

                "Transactions > 50000: "

                        + above50000);

        highest.ifPresent(t ->

                                  System.out.println(

                                          "Highest Transaction: " + t));

        lowest.ifPresent(t ->

                                 System.out.println(

                                         "Lowest Transaction: " + t));

    }

    // =====================================================

    // TRANSACTION GROUPING

    // =====================================================

    public void transactionTypeReport() {

        Map<String, Long> result =

                transactions.stream()

                            .collect(

                                    Collectors.groupingBy(

                                            Transaction::getTransactionType,

                                            Collectors.counting()));

        result.forEach(

                (type, count) ->

                        System.out.println(

                                type + " = " + count));

    }

    // =====================================================

    // TOP 3 ACCOUNTS

    // =====================================================

    public void topThreeAccounts() {

        accounts.stream()

                .sorted(

                        Comparator.comparingDouble(

                                          BankAccount::getBalance)

                                  .reversed())

                .limit(3)

                .forEach(System.out::println);

    }

    // =====================================================

    // DISTINCT CITIES

    // =====================================================

    public void distinctCities() {

        customers.stream()

                 .map(Customer::getCity)

                 .distinct()

                 .forEach(System.out::println);

    }

    // =====================================================

    // SKIP / LIMIT

    // =====================================================

    public void paginationDemo() {

        customers.stream()

                 .skip(2)

                 .limit(3)

                 .forEach(System.out::println);

    }

    // =====================================================

    // FLAT MAP

    // =====================================================

    public void flatMapDemo() {

        List<List<String>> customerNames =

                customers.stream()

                         .map(c ->

                                      List.of(

                                              c.getName(),

                                              c.getCity()))

                         .collect(Collectors.toList());

        customerNames.stream()

                     .flatMap(List::stream)

                     .forEach(System.out::println);

    }

    // =====================================================

    // RECORD DEMO

    // =====================================================

    public void recordDemo() {

        Customer customer = customers.get(0);

        CustomerRecord record =

                new CustomerRecord(

                        customer.getCustomerId(),

                        customer.getName(),

                        customer.getCity(),

                        customer.getCustomerType());

        System.out.println(record);

        TransactionRecord transactionRecord =

                new TransactionRecord(

                        1,

                        100001,

                        "DEPOSIT",

                        5000);

        System.out.println(transactionRecord);

        AccountSummary summary =

                new AccountSummary(

                        100001,

                        "SAVINGS",

                        85000);

        System.out.println(summary);

    }

}
