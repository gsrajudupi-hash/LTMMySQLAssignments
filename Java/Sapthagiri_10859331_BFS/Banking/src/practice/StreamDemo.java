package practice;

import data.BankingData;
import model.BankAccount;
import model.Customer;
import model.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamDemo {

    public static void main(String[] args) {

        List<Customer> customers =
                BankingData.createCustomers();

        List<BankAccount> accounts =
                BankingData.createAccounts();

        List<Transaction> transactions =
                BankingData.createTransactions();

        System.out.println("========================================");
        System.out.println("          PART C - STREAM API");
        System.out.println("========================================");

        task7FilterCustomers(customers);
        task8MapCustomers(customers);
        task9SortCustomers(customers);
        task10MaximumBalance(accounts);
        task11MinimumBalance(accounts);
        task12TotalBalance(accounts);
        task13AverageBalance(accounts);
        task14CountPremiumCustomers(customers);
        task15GroupCustomersByCity(customers);
        task16GroupAccountsByType(accounts);
        task17BalanceByAccountType(accounts);
        task18HighValueCustomers(accounts);
        task19TransactionAnalysis(transactions);
    }

    // Task 7
    private static void task7FilterCustomers(
            List<Customer> customers) {

        System.out.println("\n--- Task 7: Filter Customers ---");

        System.out.println("\nPremium Customers:");

        customers.stream()
                .filter(c ->
                        c.getCustomerType()
                                .equals("PREMIUM"))
                .forEach(System.out::println);

        System.out.println("\nBangalore Customers:");

        customers.stream()
                .filter(c ->
                        c.getCity()
                                .equals("Bangalore"))
                .forEach(System.out::println);

        System.out.println("\nNames Starting With A:");

        customers.stream()
                .filter(c ->
                        c.getName()
                                .startsWith("A"))
                .forEach(System.out::println);

        System.out.println("\nCustomer ID > 105:");

        customers.stream()
                .filter(c ->
                        c.getCustomerId() > 105)
                .forEach(System.out::println);
    }

    // Task 8
    private static void task8MapCustomers(
            List<Customer> customers) {

        System.out.println("\n--- Task 8: Map ---");

        customers.stream()
                .map(Customer::getName)
                .forEach(System.out::println);
    }

    // Task 9
    private static void task9SortCustomers(
            List<Customer> customers) {

        System.out.println("\n--- Task 9: Sorting ---");

        System.out.println("\nName Ascending:");

        customers.stream()
                .sorted(
                        Comparator.comparing(
                                Customer::getName
                        )
                )
                .forEach(System.out::println);

        System.out.println("\nName Descending:");

        customers.stream()
                .sorted(
                        Comparator.comparing(
                                Customer::getName
                        ).reversed()
                )
                .forEach(System.out::println);

        System.out.println("\nCustomer ID:");

        customers.stream()
                .sorted(
                        Comparator.comparing(
                                Customer::getCustomerId
                        )
                )
                .forEach(System.out::println);

        System.out.println("\nCustomer Type Then Name:");

        customers.stream()
                .sorted(
                        Comparator
                                .comparing(
                                        Customer::getCustomerType
                                )
                                .thenComparing(
                                        Customer::getName
                                )
                )
                .forEach(System.out::println);
    }

    // Task 10
    private static void task10MaximumBalance(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 10: Maximum Balance ---"
        );

        Optional<BankAccount> highestBalance =
                accounts.stream()
                        .max(
                                Comparator.comparing(
                                        BankAccount::getBalance
                                )
                        );

        highestBalance.ifPresent(
                account ->
                        System.out.println(
                                "Highest Balance Account: "
                                        + account
                        )
        );
    }

    // Task 11
    private static void task11MinimumBalance(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 11: Minimum Balance ---"
        );

        Optional<BankAccount> lowestBalance =
                accounts.stream()
                        .min(
                                Comparator.comparing(
                                        BankAccount::getBalance
                                )
                        );

        lowestBalance.ifPresent(
                account ->
                        System.out.println(
                                "Lowest Balance Account: "
                                        + account
                        )
        );
    }

    // Task 12
    private static void task12TotalBalance(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 12: Total Balance ---"
        );

        double totalBalance =
                accounts.stream()
                        .map(BankAccount::getBalance)
                        .reduce(
                                0.0,
                                Double::sum
                        );

        System.out.println(
                "Total Balance: " + totalBalance
        );
    }

    // Task 13
    private static void task13AverageBalance(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 13: Average Balance ---"
        );

        double averageBalance =
                accounts.stream()
                        .mapToDouble(
                                BankAccount::getBalance
                        )
                        .average()
                        .orElse(0.0);

        System.out.println(
                "Average Balance: "
                        + averageBalance
        );
    }

    // Task 14
    private static void task14CountPremiumCustomers(
            List<Customer> customers) {

        System.out.println(
                "\n--- Task 14: Premium Customer Count ---"
        );

        long count =
                customers.stream()
                        .filter(c ->
                                c.getCustomerType()
                                        .equals("PREMIUM"))
                        .count();

        System.out.println(
                "Premium Customers: " + count
        );
    }

    // Task 15
    private static void task15GroupCustomersByCity(
            List<Customer> customers) {

        System.out.println(
                "\n--- Task 15: Group Customers By City ---"
        );

        Map<String, List<Customer>> customersByCity =
                customers.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Customer::getCity
                                )
                        );

        customersByCity.forEach(
                (city, customerList) -> {

                    System.out.println(
                            "\nCity: " + city
                    );

                    customerList.forEach(
                            customer ->
                                    System.out.println(
                                            "  "
                                                    + customer.getName()
                                    )
                    );
                }
        );
    }

    // Task 16
    private static void task16GroupAccountsByType(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 16: Group Accounts By Type ---"
        );

        Map<String, Long> accountCountByType =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getAccountType,
                                        Collectors.counting()
                                )
                        );

        accountCountByType.forEach(
                (type, count) ->
                        System.out.println(
                                type + " = " + count
                        )
        );
    }

    // Task 17
    private static void task17BalanceByAccountType(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 17: Balance By Account Type ---"
        );

        Map<String, Double> balanceByType =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getAccountType,
                                        Collectors.summingDouble(
                                                BankAccount::getBalance
                                        )
                                )
                        );

        balanceByType.forEach(
                (type, balance) ->
                        System.out.println(
                                type + " = " + balance
                        )
        );
    }

    // Task 18
    private static void task18HighValueCustomers(
            List<BankAccount> accounts) {

        System.out.println(
                "\n--- Task 18: High Value Customers ---"
        );

        Map<Integer, Double> customerBalances =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getCustomerId,
                                        Collectors.summingDouble(
                                                BankAccount::getBalance
                                        )
                                )
                        );

        customerBalances.entrySet()
                .stream()
                .filter(entry ->
                        entry.getValue() > 100000)
                .forEach(entry ->
                        System.out.println(
                                "Customer ID: "
                                        + entry.getKey()
                                        + " | Total Balance: "
                                        + entry.getValue()
                        )
                );
    }

    // Task 19
    private static void task19TransactionAnalysis(
            List<Transaction> transactions) {

        System.out.println(
                "\n--- Task 19: Transaction Analysis ---"
        );

        double totalDeposits =
                transactions.stream()
                        .filter(t ->
                                t.getTransactionType()
                                        .equals("DEPOSIT"))
                        .mapToDouble(
                                Transaction::getAmount
                        )
                        .sum();

        System.out.println(
                "Total Deposits: "
                        + totalDeposits
        );

        double totalWithdrawals =
                transactions.stream()
                        .filter(t ->
                                t.getTransactionType()
                                        .equals("WITHDRAW"))
                        .mapToDouble(
                                Transaction::getAmount
                        )
                        .sum();

        System.out.println(
                "Total Withdrawals: "
                        + totalWithdrawals
        );

        Optional<Transaction> highestTransaction =
                transactions.stream()
                        .max(
                                Comparator.comparing(
                                        Transaction::getAmount
                                )
                        );

        highestTransaction.ifPresent(
                t ->
                        System.out.println(
                                "Highest Transaction: " + t
                        )
        );

        Optional<Transaction> lowestTransaction =
                transactions.stream()
                        .min(
                                Comparator.comparing(
                                        Transaction::getAmount
                                )
                        );

        lowestTransaction.ifPresent(
                t ->
                        System.out.println(
                                "Lowest Transaction: " + t
                        )
        );

        Map<String, Long> transactionCount =
                transactions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Transaction::getTransactionType,
                                        Collectors.counting()
                                )
                        );

        System.out.println(
                "\nTransaction Count By Type:"
        );

        transactionCount.forEach(
                (type, count) ->
                        System.out.println(
                                type + " = " + count
                        )
        );

        double averageTransaction =
                transactions.stream()
                        .mapToDouble(
                                Transaction::getAmount
                        )
                        .average()
                        .orElse(0.0);

        System.out.println(
                "\nAverage Transaction Amount: "
                        + averageTransaction
        );

        System.out.println(
                "\nTransactions Above 50,000:"
        );

        transactions.stream()
                .filter(t ->
                        t.getAmount() > 50000)
                .forEach(System.out::println);
    }
}
