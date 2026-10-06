package java8;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class Name : AdvancedStreamDemo
 * Created By : 10860819
 * Created Date : 9/30/2026
 * Created Time : 9:54 AM
 */
public class AdvancedStreamDemo {

    public static void analyze(
            List<Customer> customers,
            List<BankAccount> accounts,
            List<Transaction> transactions) {

        // GROUP CUSTOMERS BY CITY
        Map<String, List<Customer>> customersByCity = customers.stream()
                .collect(Collectors.groupingBy(Customer::getCity));

        System.out.println("\n=====CUSTOMERS GROUPED BY CITY =====");
        customersByCity.forEach((city, customerList) -> {
            System.out.println("\nCity: " + city);
            customerList.forEach(System.out::println);
        });

        //GROUP ACCOUNTS BY TYPE AND COUNT
        Map<String, Long> accountCountByType = accounts.stream()
                .collect(Collectors.groupingBy(BankAccount::getAccountType, Collectors.counting()));
        System.out.println(
                "\n=====ACCOUNT COUNT BY TYPE ====="
        );
        accountCountByType.forEach((accountType, count) ->
                System.out.println(
                        accountType + " Accounts: " + count
                )
        );

        //TOTAL BALANCE BY ACCOUNT TYPE
        Map<String, Double> balanceByAccountType = accounts.stream()
                .collect(Collectors.groupingBy(BankAccount::getAccountType,
                        Collectors.summingDouble(BankAccount::getBalance)));
        System.out.println("\n===== BALANCE BY ACCOUNT TYPE =====");
        balanceByAccountType.forEach((accountType, totalBalance) ->
                System.out.printf(
                        "%s Total Balance: %.2f%n",
                        accountType,
                        totalBalance
                )
        );

        // HIGH-VALUE CUSTOMERS
        // Total account balance > 1,00,000

        Map<Integer, Double> totalBalanceByCustomerId =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getCustomerId,
                                        Collectors.summingDouble(
                                                BankAccount::getBalance
                                        )
                                )
                        );

        System.out.println(
                "\n===== TASK 18: HIGH-VALUE CUSTOMERS ====="
        );

        customers.stream()
                .filter(customer ->
                        totalBalanceByCustomerId.getOrDefault(
                                customer.getCustomerId(),
                                0.0
                        ) > 100000
                )
                .forEach(customer -> {
                    double totalBalance =
                            totalBalanceByCustomerId.getOrDefault(
                                    customer.getCustomerId(),
                                    0.0
                            );
                    System.out.println(
                            "Customer ID : "
                                    + customer.getCustomerId()
                    );
                    System.out.println(
                            "Customer Name : "
                                    + customer.getName()
                    );
                    System.out.printf(
                            "Total Balance : %.2f%n",
                            totalBalance
                    );
                    System.out.println(
                            "Customer Type : "
                                    + customer.getCustomerType()
                    );
                    System.out.println(
                            "--------------------------------"
                    );
                });
        /*
         * ==========================================
         *  TRANSACTION ANALYSIS
         * ==========================================
         */

        System.out.println("\n=====  TRANSACTION ANALYSIS =====");

        /*
         *  Total Deposit Amount
         */
        double totalDeposits = transactions.stream()
                .filter(transaction -> transaction.getTransactionType().
                        equalsIgnoreCase("DEPOSIT")).map(Transaction::getAmount).
                        reduce(0.0,Double::sum);
        System.out.printf("Total Deposits : %.2f%n", totalDeposits);

        /*
         * Total Withdrawal Amount
         */
        double totalWithdrawals = transactions.stream()
                .filter(transaction -> transaction.getTransactionType().equalsIgnoreCase("WITHDRAWAL")).
                map(Transaction::getAmount).reduce(0.0, Double::sum);

        System.out.printf("Total Withdrawals : %.2f%n", totalWithdrawals);

        /*
         *  Highest Transaction
         */
        Optional<Transaction> highestTransaction = transactions.stream()
                .max(Comparator.comparingDouble(Transaction::getAmount));

        System.out.println("\n----- HIGHEST TRANSACTION -----");
        highestTransaction.ifPresent(Transaction::displayTransaction);

        /*
         *
         *  Lowest Transaction
         */
        Optional<Transaction> lowestTransaction = transactions.stream().
                min(Comparator.comparingDouble(Transaction::getAmount));
        System.out.println("\n----- LOWEST TRANSACTION -----");
        lowestTransaction.ifPresent(Transaction::displayTransaction);

        /*
         *  Transaction Count By Type
         */

        Map<String, Long> transactionCountByType =
                transactions.stream()
                        .collect(Collectors.groupingBy(transaction -> transaction
                                .getTransactionType()
                                .toUpperCase(),Collectors.counting())
                        );

        System.out.println("\n----- TRANSACTION COUNT BY TYPE -----");
        transactionCountByType.forEach((type, count) ->
                System.out.println(type + ": " + count)
        );

        /*
         *  Average Transaction Amount
         */

        double averageTransactionAmount =
                transactions.stream()
                        .mapToDouble(Transaction::getAmount)
                        .average()
                        .orElse(0.0);
        System.out.printf("%nAverage Transaction Amount: %.2f%n", averageTransactionAmount);


        /*
         *  Transactions Above 50,000
         */
        System.out.println("\n----- TRANSACTIONS ABOVE 50,000 -----");

        List<Transaction> transactionsAbove50000 =
                transactions.stream()
                        .filter(transaction -> transaction.getAmount() > 50000)
                        .toList();
        if (transactionsAbove50000.isEmpty()) {
            System.out.println("No transactions found above 50,000.");
        } else {
            transactionsAbove50000.forEach(Transaction::displayTransaction);
        }
    }
}
