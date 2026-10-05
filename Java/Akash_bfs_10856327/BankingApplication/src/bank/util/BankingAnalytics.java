package bank.util;

import bank.model.BankAccount;
import bank.model.Customer;
import bank.model.Transaction;
import bank.record.AccountSummary;
import bank.record.CustomerRecord;
import bank.record.TransactionRecord;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class BankingAnalytics {

    private BankingAnalytics() {
    }

    public static void demonstrateFunctionalInterfaces(
            List<Customer> customers) {

        Predicate<Customer> premiumCustomer =
                customer -> "PREMIUM".equalsIgnoreCase(
                        customer.getCustomerType()
                );

        Predicate<Customer> bangaloreCustomer =
                customer -> "Bangalore".equalsIgnoreCase(
                        customer.getCity()
                );

        Predicate<Customer> mangaloreCustomer =
                customer -> "Mangalore".equalsIgnoreCase(
                        customer.getCity()
                );

        Predicate<Customer> customerIdGreaterThan105 =
                customer -> customer.getCustomerId() > 105;

        Consumer<Customer> displayCustomer =
                System.out::println;

        Function<Customer, String> getCustomerName =
                Customer::getName;

        Supplier<Long> accountNumberGenerator =
                System::currentTimeMillis;

        System.out.println("\nPremium customers:");

        customers.stream()
                .filter(premiumCustomer)
                .forEach(displayCustomer);

        System.out.println("\nBangalore customers:");

        customers.stream()
                .filter(bangaloreCustomer)
                .forEach(displayCustomer);

        System.out.println("\nMangalore customers:");

        customers.stream()
                .filter(mangaloreCustomer)
                .forEach(displayCustomer);

        System.out.println("\nCustomers with ID greater than 105:");

        customers.stream()
                .filter(customerIdGreaterThan105)
                .forEach(displayCustomer);

        System.out.println("\nCustomer names:");

        customers.stream()
                .map(getCustomerName)
                .forEach(System.out::println);

        System.out.println(
                "Generated account number: " +
                        accountNumberGenerator.get()
        );
    }

    public static void displayCustomerFilters(
            List<Customer> customers) {

        System.out.println("\nNames beginning with A:");

        customers.stream()
                .filter(customer ->
                        customer.getName().startsWith("A"))
                .forEach(System.out::println);

        System.out.println("\nCustomer names using map():");

        customers.stream()
                .map(Customer::getName)
                .forEach(System.out::println);
    }

    public static void displaySortedCustomers(
            List<Customer> customers) {

        System.out.println("\nCustomers sorted by name:");

        customers.stream()
                .sorted(Comparator.comparing(Customer::getName))
                .forEach(System.out::println);

        System.out.println("\nCustomers sorted by name descending:");

        customers.stream()
                .sorted(Comparator.comparing(Customer::getName)
                        .reversed())
                .forEach(System.out::println);

        System.out.println("\nCustomers sorted by ID:");

        customers.stream()
                .sorted(Comparator.comparingInt(
                        Customer::getCustomerId))
                .forEach(System.out::println);

        System.out.println("\nSorted by customer type and name:");

        customers.stream()
                .sorted(
                        Comparator.comparing(Customer::getCustomerType)
                                .thenComparing(Customer::getName)
                )
                .forEach(System.out::println);
    }

    public static void displayAccountAnalytics(
            List<BankAccount> accounts) {

        Optional<BankAccount> highestBalanceAccount =
                accounts.stream()
                        .max(Comparator.comparingDouble(
                                BankAccount::getBalance));

        Optional<BankAccount> lowestBalanceAccount =
                accounts.stream()
                        .min(Comparator.comparingDouble(
                                BankAccount::getBalance));

        highestBalanceAccount.ifPresent(account ->
                System.out.println(
                        "Highest-balance account: " + account
                ));

        lowestBalanceAccount.ifPresent(account ->
                System.out.println(
                        "Lowest-balance account: " + account
                ));

        double totalBalance = accounts.stream()
                .map(BankAccount::getBalance)
                .reduce(0.0, Double::sum);

        double averageBalance = accounts.stream()
                .collect(Collectors.averagingDouble(
                        BankAccount::getBalance));

        Map<String, Long> accountsByType =
                accounts.stream()
                        .collect(Collectors.groupingBy(
                                BankAccount::getAccountType,
                                Collectors.counting()
                        ));

        Map<String, Double> balanceByAccountType =
                accounts.stream()
                        .collect(Collectors.groupingBy(
                                BankAccount::getAccountType,
                                Collectors.summingDouble(
                                        BankAccount::getBalance)
                        ));

        System.out.println("Total balance: " + totalBalance);
        System.out.println("Average balance: " + averageBalance);
        System.out.println("Accounts by type: " + accountsByType);
        System.out.println(
                "Balance by account type: " +
                        balanceByAccountType
        );
    }

    public static void displayCustomerAnalytics(
            List<Customer> customers,
            List<BankAccount> accounts) {

        long premiumCustomerCount = customers.stream()
                .filter(customer ->
                        "PREMIUM".equalsIgnoreCase(
                                customer.getCustomerType()))
                .count();

        Map<String, List<Customer>> customersByCity =
                customers.stream()
                        .collect(Collectors.groupingBy(
                                Customer::getCity));

        Map<Integer, Double> totalBalanceByCustomer =
                accounts.stream()
                        .collect(Collectors.groupingBy(
                                BankAccount::getCustomerId,
                                Collectors.summingDouble(
                                        BankAccount::getBalance)
                        ));

        Set<Integer> highValueCustomerIds =
                totalBalanceByCustomer.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() > 100000)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());

        List<Customer> highValueCustomers =
                customers.stream()
                        .filter(customer ->
                                highValueCustomerIds.contains(
                                        customer.getCustomerId()))
                        .toList();

        System.out.println(
                "Premium customer count: " +
                        premiumCustomerCount
        );

        System.out.println(
                "Customers grouped by city: " +
                        customersByCity
        );

        System.out.println(
                "High-value customers: " +
                        highValueCustomers
        );
    }

    public static void displayTransactionAnalytics(
            List<Transaction> transactions) {

        double totalDeposits = transactions.stream()
                .filter(transaction ->
                        "DEPOSIT".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);

        double totalWithdrawals = transactions.stream()
                .filter(transaction ->
                        "WITHDRAW".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .map(Transaction::getAmount)
                .reduce(0.0, Double::sum);

        Optional<Transaction> highestTransaction =
                transactions.stream()
                        .max(Comparator.comparingDouble(
                                Transaction::getAmount));

        Optional<Transaction> lowestTransaction =
                transactions.stream()
                        .min(Comparator.comparingDouble(
                                Transaction::getAmount));

        Map<String, Long> transactionCountByType =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getTransactionType,
                                Collectors.counting()
                        ));

        Map<String, Double> totalAmountByType =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getTransactionType,
                                Collectors.summingDouble(
                                        Transaction::getAmount)
                        ));

        Map<String, Double> averageAmountByType =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getTransactionType,
                                Collectors.averagingDouble(
                                        Transaction::getAmount)
                        ));

        System.out.println("Total deposits: " + totalDeposits);
        System.out.println(
                "Total withdrawals: " + totalWithdrawals
        );

        highestTransaction.ifPresent(transaction ->
                System.out.println(
                        "Highest transaction: " + transaction
                ));

        lowestTransaction.ifPresent(transaction ->
                System.out.println(
                        "Lowest transaction: " + transaction
                ));

        System.out.println(
                "Transaction count by type: " +
                        transactionCountByType
        );

        System.out.println(
                "Transaction total by type: " +
                        totalAmountByType
        );

        System.out.println(
                "Transaction average by type: " +
                        averageAmountByType
        );

        System.out.println("\nTransactions above Rs.50,000:");

        transactions.stream()
                .filter(transaction ->
                        transaction.getAmount() > 50000)
                .forEach(System.out::println);
    }

    public static void demonstrateOptional(
            List<Customer> customers) {

        Optional<Customer> optionalCustomer =
                customers.stream()
                        .filter(customer ->
                                customer.getCustomerId() == 101)
                        .findFirst();

        optionalCustomer.ifPresent(customer ->
                System.out.println(
                        "ifPresent result: " + customer
                ));

        Customer defaultCustomer = optionalCustomer.orElse(
                new Customer(
                        0,
                        "Default",
                        "default@gmail.com",
                        "Unknown",
                        "0000000000",
                        "REGULAR"
                )
        );

        System.out.println(
                "orElse result: " + defaultCustomer
        );

        Customer generatedCustomer =
                optionalCustomer.orElseGet(() ->
                        new Customer(
                                -1,
                                "Generated",
                                "generated@gmail.com",
                                "Unknown",
                                "0000000000",
                                "REGULAR"
                        ));

        System.out.println(
                "orElseGet result: " + generatedCustomer
        );

        Customer requiredCustomer =
                optionalCustomer.orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer is unavailable"
                        ));

        System.out.println(
                "orElseThrow result: " + requiredCustomer
        );
    }

    public static void demonstrateMandatoryStreams(
            List<Customer> customers,
            List<BankAccount> accounts) {

        List<String> distinctCities = customers.stream()
                .map(Customer::getCity)
                .distinct()
                .sorted()
                .toList();

        List<String> limitedCustomerNames = customers.stream()
                .map(Customer::getName)
                .skip(2)
                .limit(3)
                .toList();

        long flattenedAccountCount = customers.stream()
                .flatMap(customer ->
                        accounts.stream()
                                .filter(account ->
                                        account.getCustomerId() ==
                                                customer.getCustomerId()))
                .count();

        Map<Boolean, List<Customer>> premiumPartition =
                customers.stream()
                        .collect(Collectors.partitioningBy(
                                customer ->
                                        "PREMIUM".equalsIgnoreCase(
                                                customer.getCustomerType())
                        ));

        String joinedCustomerNames = customers.stream()
                .map(Customer::getName)
                .collect(Collectors.joining(", "));

        System.out.println("Distinct cities: " + distinctCities);
        System.out.println(
                "Names after skip and limit: " +
                        limitedCustomerNames
        );
        System.out.println(
                "FlatMap account count: " +
                        flattenedAccountCount
        );
        System.out.println(
                "Premium partition: " +
                        premiumPartition
        );
        System.out.println(
                "Joined customer names: " +
                        joinedCustomerNames
        );
    }

    public static void displayTopThreeCustomers(
            List<Customer> customers,
            List<BankAccount> accounts) {

        Map<Integer, Double> totalBalanceByCustomer =
                accounts.stream()
                        .collect(Collectors.groupingBy(
                                BankAccount::getCustomerId,
                                Collectors.summingDouble(
                                        BankAccount::getBalance)
                        ));

        System.out.println("\nTop three customers:");

        totalBalanceByCustomer.entrySet()
                .stream()
                .sorted(
                        Map.Entry
                                .<Integer, Double>comparingByValue()
                                .reversed()
                )
                .limit(3)
                .forEach(entry -> {

                    String customerName = customers.stream()
                            .filter(customer ->
                                    customer.getCustomerId() ==
                                            entry.getKey())
                            .map(Customer::getName)
                            .findFirst()
                            .orElse("Unknown");

                    System.out.println(
                            customerName + " - Rs." +
                                    entry.getValue()
                    );
                });
    }

    public static void demonstrateRecords(
            List<Customer> customers,
            List<BankAccount> accounts,
            List<Transaction> transactions) {

        Customer customer = customers.get(0);

        CustomerRecord customerRecord =
                new CustomerRecord(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getCity(),
                        customer.getCustomerType()
                );

        Transaction transaction = transactions.get(0);

        TransactionRecord transactionRecord =
                new TransactionRecord(
                        transaction.getTransactionId(),
                        transaction.getAccountNumber(),
                        transaction.getTransactionType(),
                        transaction.getAmount()
                );

        List<AccountSummary> accountSummaries =
                accounts.stream()
                        .collect(Collectors.groupingBy(
                                BankAccount::getAccountType))
                        .entrySet()
                        .stream()
                        .map(entry -> {

                            List<BankAccount> groupedAccounts =
                                    entry.getValue();

                            double total = groupedAccounts.stream()
                                    .mapToDouble(
                                            BankAccount::getBalance)
                                    .sum();

                            double average = groupedAccounts.stream()
                                    .mapToDouble(
                                            BankAccount::getBalance)
                                    .average()
                                    .orElse(0.0);

                            return new AccountSummary(
                                    entry.getKey(),
                                    groupedAccounts.size(),
                                    total,
                                    average
                            );
                        })
                        .toList();

        System.out.println("Customer record: " + customerRecord);
        System.out.println(
                "Customer record accessor: " +
                        customerRecord.name()
        );
        System.out.println(
                "Transaction record: " +
                        transactionRecord
        );
        System.out.println(
                "Account summaries: " +
                        accountSummaries
        );
    }

    public static String generateBankingDashboard(
            List<Customer> customers,
            List<BankAccount> accounts,
            List<Transaction> transactions) {

        long premiumCustomers = customers.stream()
                .filter(customer ->
                        "PREMIUM".equalsIgnoreCase(
                                customer.getCustomerType()))
                .count();

        double totalBalance = accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .sum();

        double averageBalance = accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .average()
                .orElse(0.0);

        double highestBalance = accounts.stream()
                .mapToDouble(BankAccount::getBalance)
                .max()
                .orElse(0.0);

        long deposits = transactions.stream()
                .filter(transaction ->
                        "DEPOSIT".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .count();

        long withdrawals = transactions.stream()
                .filter(transaction ->
                        "WITHDRAW".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .count();

        Map<String, Long> accountsByType =
                accounts.stream()
                        .collect(Collectors.groupingBy(
                                BankAccount::getAccountType,
                                Collectors.counting()
                        ));

        Map<String, Long> customersByCity =
                customers.stream()
                        .collect(Collectors.groupingBy(
                                Customer::getCity,
                                Collectors.counting()
                        ));

        return """
                ==========================================
                    BANKING ANALYTICS DASHBOARD
                ==========================================
                Total customers       : %d
                Premium customers     : %d
                Total accounts        : %d
                Total balance         : Rs. %,.2f
                Average balance       : Rs. %,.2f
                Highest balance       : Rs. %,.2f
                Total transactions    : %d
                Deposit transactions  : %d
                Withdrawal transactions: %d
                Accounts by type      : %s
                Customers by city     : %s
                ==========================================
                """.formatted(
                customers.size(),
                premiumCustomers,
                accounts.size(),
                totalBalance,
                averageBalance,
                highestBalance,
                transactions.size(),
                deposits,
                withdrawals,
                accountsByType,
                customersByCity
        );
    }
}