package com.bank.util;

import com.bank.model.AccountType;
import com.bank.model.BankAccount;
import com.bank.model.Customer;
import com.bank.model.TransactionType;
import com.bank.record.CustomerRecord;
import com.bank.record.TransactionRecord;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class BankingReport {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final double HIGH_VALUE_THRESHOLD = 50000;
    private static final Comparator<TransactionRecord> NEWEST_FIRST =
            Comparator.comparing(TransactionRecord::timestamp)
                    .thenComparingLong(TransactionRecord::transactionId)
                    .reversed();

    private BankingReport() {
    }

    public static void printCustomers(List<Customer> customers) {
        title("CUSTOMERS");
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.printf("%-6s %-20s %-25s %-12s %-15s %-8s%n", "ID", "NAME", "EMAIL", "CITY", "PHONE", "TYPE");
        customers.forEach(System.out::println);
    }

    public static void printAccounts(List<BankAccount> accounts) {
        title("BANK ACCOUNTS");
        if (accounts.isEmpty()) {
            System.out.println("No bank accounts found.");
            return;
        }
        System.out.printf("%-10s %-8s %-8s %15s %10s %7s%n",
                "ACCOUNT", "CUSTOMER", "TYPE", "BALANCE", "MINIMUM", "RATE");
        accounts.forEach(System.out::println);
    }

    public static void printTransactionHistory(List<TransactionRecord> transactions) {
        printTransactions("TRANSACTION HISTORY", transactions);
    }

    public static void printAnalytics(List<Customer> customers, List<BankAccount> accounts,
                                      List<TransactionRecord> transactions) {
        Predicate<BankAccount> isLoan = account -> account.getAccountType() == AccountType.LOAN;
        DoubleSummaryStatistics depositStats = accounts.stream()
                .filter(isLoan.negate())
                .mapToDouble(BankAccount::getBalance)
                .summaryStatistics();
        double loanOutstanding = accounts.stream().filter(isLoan).mapToDouble(BankAccount::getBalance).sum();
        Map<TransactionType, Double> amountByType = transactions.stream()
                .collect(Collectors.groupingBy(TransactionRecord::type, () -> new EnumMap<>(TransactionType.class),
                        Collectors.summingDouble(TransactionRecord::amount)));
        Map<String, Long> customersByCity = customers.stream()
                .collect(Collectors.groupingBy(Customer::getCity, TreeMap::new, Collectors.counting()));
        Optional<TransactionRecord> largest = transactions.stream()
                .max(Comparator.comparingDouble(TransactionRecord::amount));

        System.out.println("""

                ===== BANKING ANALYTICS =====
                Total customers          : %d
                Premium customers        : %d
                Total accounts           : %d
                Total deposit holdings   : %.2f
                Average deposit balance  : %.2f
                Highest deposit balance  : %.2f
                Total loan outstanding   : %.2f
                Total transactions       : %d
                Total deposited          : %.2f
                Total withdrawn          : %.2f
                Total transferred        : %.2f
                Largest transaction      : %s
                Customers by city        : %s""".formatted(
                customers.size(),
                customers.stream().filter(Customer::isPremium).count(),
                accounts.size(),
                depositStats.getSum(),
                depositStats.getCount() == 0 ? 0.0 : depositStats.getAverage(),
                depositStats.getCount() == 0 ? 0.0 : depositStats.getMax(),
                loanOutstanding,
                transactions.size(),
                amountByType.getOrDefault(TransactionType.DEPOSIT, 0.0),
                amountByType.getOrDefault(TransactionType.WITHDRAWAL, 0.0),
                amountByType.getOrDefault(TransactionType.TRANSFER_DEBIT, 0.0),
                largest.map(t -> "%.2f (ID %d, %s)".formatted(t.amount(), t.transactionId(), t.type()))
                        .orElse("None"),
                customersByCity.isEmpty() ? "None" : customersByCity));
    }

    public static void printCustomerReport(List<CustomerRecord> summaries) {
        title("CUSTOMER REPORT");
        if (summaries.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        System.out.printf("%-6s %-20s %-12s %-8s %8s %15s %15s%n",
                "ID", "NAME", "CITY", "TYPE", "ACCOUNTS", "DEPOSITS", "LOANS");
        summaries.stream()
                .sorted(Comparator.comparingDouble(CustomerRecord::depositBalance).reversed()
                        .thenComparingInt(CustomerRecord::customerId))
                .forEach(c -> System.out.printf("%-6d %-20s %-12s %-8s %8d %15.2f %15.2f%n",
                        c.customerId(), c.name(), c.city(), c.customerType(),
                        c.accountCount(), c.depositBalance(), c.loanOutstanding()));
    }

    public static void printAccountTypeReport(List<BankAccount> accounts) {
        title("ACCOUNT TYPE REPORT");
        if (accounts.isEmpty()) {
            System.out.println("No bank accounts found.");
            return;
        }
        Map<AccountType, DoubleSummaryStatistics> byType = accounts.stream()
                .collect(Collectors.groupingBy(BankAccount::getAccountType,
                        () -> new EnumMap<>(AccountType.class),
                        Collectors.summarizingDouble(BankAccount::getBalance)));
        System.out.printf("%-8s %8s %15s %15s %15s %15s%n", "TYPE", "COUNT", "TOTAL", "AVERAGE", "LOWEST", "HIGHEST");
        byType.forEach((type, stats) -> System.out.printf("%-8s %8d %15.2f %15.2f %15.2f %15.2f%n",
                type, stats.getCount(), stats.getSum(), stats.getAverage(), stats.getMin(), stats.getMax()));
    }

    public static void printTransactionReport(List<TransactionRecord> transactions) {
        List<TransactionRecord> sorted = transactions.stream().sorted(NEWEST_FIRST).toList();
        printTransactions("ALL TRANSACTIONS", transactions);
        printTransactions("DEPOSITS", filter(transactions, t -> t.type() == TransactionType.DEPOSIT));
        printTransactions("WITHDRAWALS", filter(transactions, t -> t.type() == TransactionType.WITHDRAWAL));
        printTransactions("TRANSFERS", filter(transactions, TransactionRecord::isTransfer));
        printTransactions("TRANSACTIONS ABOVE 50,000", filter(transactions, t -> t.amount() > HIGH_VALUE_THRESHOLD));
        printTransactions("SORTED TRANSACTIONS (NEWEST FIRST)", sorted);
        printTransactions("LATEST 5 TRANSACTIONS", sorted.stream().limit(5).toList());
    }

    public static void printAccountStatement(Customer customer, BankAccount account,
                                             List<TransactionRecord> transactions) {
        title("ACCOUNT STATEMENT");
        System.out.printf("Customer        : %s (ID %d)%n", customer.getName(), customer.getCustomerId());
        System.out.printf("Account         : %d (%s)%n", account.getAccountNumber(), account.getAccountType());
        System.out.printf("Opening balance : %.2f%n", account.getOpeningBalance());
        printTransactions("STATEMENT TRANSACTIONS", transactions);
        double moneyIn = transactions.stream().filter(t -> t.type().isMoneyIn())
                .mapToDouble(TransactionRecord::amount).sum();
        double moneyOut = transactions.stream().filter(t -> !t.type().isMoneyIn())
                .mapToDouble(TransactionRecord::amount).sum();
        System.out.printf("Total money in  : %.2f%n", moneyIn);
        System.out.printf("Total money out : %.2f%n", moneyOut);
        System.out.printf("Closing balance : %.2f%n", account.getBalance());
        System.out.printf("Interest (%.2f%%) : %.2f%n", account.getInterestRate(), account.calculateInterest());
    }

    private static List<TransactionRecord> filter(List<TransactionRecord> transactions,
                                                  Predicate<TransactionRecord> predicate) {
        return transactions.stream().filter(predicate).toList();
    }

    private static void printTransactions(String heading, List<TransactionRecord> transactions) {
        title(heading);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.printf("%-6s %-10s %-16s %12s %15s %-19s %s%n",
                "ID", "ACCOUNT", "TYPE", "AMOUNT", "BALANCE AFTER", "DATE", "DESCRIPTION");
        transactions.forEach(t -> System.out.printf("%-6d %-10d %-16s %12.2f %15.2f %-19s %s%n",
                t.transactionId(), t.accountNumber(), t.type(), t.amount(), t.balanceAfter(),
                t.timestamp().format(TIME_FORMAT), t.description()));
    }

    private static void title(String heading) {
        System.out.println("\n===== " + heading + " =====");
    }
}
