package com.bank;

import com.bank.functional.BankingOperation;
import com.bank.model.BankAccount;
import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.LoanAccount;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;
import com.bank.record.CustomerRecord;
import com.bank.record.TransactionRecord;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;
 
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
/**
 * Author   : 10858829
 * Date     : 29 Sept 2026
 * Time     : 10:29:43 pm
 * project  : BankingApplication
 */


 
public class Main {
 
    public static void main(String[] args) {
 
        BankingService service =
                new BankingService();
 
        loadCustomers(service);
        loadAccounts(service);
 
        demonstrateJava8(service);
        demonstrateJava17(service);
 
        BankingReport.generateBankingDashboard(service);
 
        demonstrateBankingOperations(service);
    }
 
    private static void loadCustomers(
            BankingService service) {
 
        service.addCustomer(
                new Customer(
                        101,
                        "Rahul",
                        "rahul@gmail.com",
                        "Bangalore",
                        "9876543210",
                        "PREMIUM"));
 
        service.addCustomer(
                new Customer(
                        102,
                        "Priya",
                        "priya@gmail.com",
                        "Mangalore",
                        "9876543211",
                        "REGULAR"));
 
        service.addCustomer(
                new Customer(
                        103,
                        "Arun",
                        "arun@gmail.com",
                        "Mysore",
                        "9876543212",
                        "PREMIUM"));
 
        service.addCustomer(
                new Customer(
                        104,
                        "Sneha",
                        "sneha@gmail.com",
                        "Udupi",
                        "9876543213",
                        "REGULAR"));
 
        service.addCustomer(
                new Customer(
                        105,
                        "Kiran",
                        "kiran@gmail.com",
                        "Bangalore",
                        "9876543214",
                        "PREMIUM"));
 
        service.addCustomer(
                new Customer(
                        106,
                        "Anil",
                        "anil@gmail.com",
                        "Hyderabad",
                        "9876543215",
                        "REGULAR"));
 
        service.addCustomer(
                new Customer(
                        107,
                        "Meena",
                        "meena@gmail.com",
                        "Chennai",
                        "9876543216",
                        "PREMIUM"));
 
        service.addCustomer(
                new Customer(
                        108,
                        "Ravi",
                        "ravi@gmail.com",
                        "Bangalore",
                        "9876543217",
                        "REGULAR"));
 
        service.addCustomer(
                new Customer(
                        109,
                        "Anusha",
                        "anusha@gmail.com",
                        "Hyderabad",
                        "9876543218",
                        "PREMIUM"));
 
        service.addCustomer(
                new Customer(
                        110,
                        "Vijay",
                        "vijay@gmail.com",
                        "Mysore",
                        "9876543219",
                        "REGULAR"));
    }
 
    private static void loadAccounts(
            BankingService service) {
 
        service.addAccount(
                new SavingsAccount(
                        100001,
                        101,
                        85000,
                        "ACTIVE"));
 
        service.addAccount(
                new CurrentAccount(
                        100002,
                        102,
                        120000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100003,
                        103,
                        150000,
                        "ACTIVE"));
 
        service.addAccount(
                new CurrentAccount(
                        100004,
                        104,
                        70000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100005,
                        105,
                        200000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100006,
                        106,
                        45000,
                        "ACTIVE"));
 
        service.addAccount(
                new CurrentAccount(
                        100007,
                        107,
                        175000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100008,
                        108,
                        95000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100009,
                        109,
                        225000,
                        "ACTIVE"));
 
        service.addAccount(
                new CurrentAccount(
                        100010,
                        110,
                        60000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100011,
                        101,
                        50000,
                        "ACTIVE"));
 
        service.addAccount(
                new CurrentAccount(
                        100012,
                        102,
                        80000,
                        "ACTIVE"));
 
        service.addAccount(
                new SavingsAccount(
                        100013,
                        103,
                        110000,
                        "ACTIVE"));
 
        service.addAccount(
                new CurrentAccount(
                        100014,
                        105,
                        140000,
                        "ACTIVE"));
 
        service.addAccount(
                new LoanAccount(
                        100015,
                        109,
                        300000,
                        "ACTIVE"));
    }
    
    private static void demonstrateJava8(
            BankingService service) {
 
        System.out.println("\n===== PREMIUM CUSTOMERS =====");
 
        // Predicate
        Predicate<Customer> premium =
                c -> c.getCustomerType()
                        .equalsIgnoreCase("PREMIUM");
 
        service.getCustomers()
                .stream()
                .filter(premium)
                .forEach(System.out::println);
 
 
        System.out.println("\n===== BANGALORE CUSTOMERS =====");
 
        Predicate<Customer> bangalore =
                c -> c.getCity()
                        .equalsIgnoreCase("Bangalore");
 
        service.getCustomers()
                .stream()
                .filter(bangalore)
                .forEach(System.out::println);
 
 
        System.out.println("\n===== NAMES STARTING WITH A =====");
 
        service.getCustomers()
                .stream()
                .filter(c -> c.getName().startsWith("A"))
                .forEach(System.out::println);
 
 
        System.out.println("\n===== ID > 105 =====");
 
        service.getCustomers()
                .stream()
                .filter(c -> c.getCustomerId() > 105)
                .forEach(System.out::println);
 
 
        // Consumer
        Consumer<Customer> displayCustomer =
                customer -> System.out.println(customer);
 
        System.out.println("\n===== CONSUMER =====");
 
        service.getCustomers()
                .stream()
                .limit(2)
                .forEach(displayCustomer);
 
 
        // Function
        Function<Customer, String> getCustomerName =
                Customer::getName;
 
        System.out.println("\n===== CUSTOMER NAMES =====");
 
        service.getCustomers()
                .stream()
                .map(getCustomerName)
                .forEach(System.out::println);
 
 
        // Supplier
        Supplier<Long> accountNumberGenerator =
                System::currentTimeMillis;
 
        System.out.println(
                "\nGenerated Account Number: " +
                accountNumberGenerator.get());
 
 
        // Sorting
        System.out.println("\n===== SORT BY NAME =====");
 
        service.getCustomers()
                .stream()
                .sorted(Comparator.comparing(
                        Customer::getName))
                .forEach(System.out::println);
 
 
        System.out.println("\n===== SORT BY ID =====");
 
        service.getCustomers()
                .stream()
                .sorted(Comparator.comparingInt(
                        Customer::getCustomerId))
                .forEach(System.out::println);
 
 
        // Highest
        System.out.println("\n===== HIGHEST BALANCE =====");
 
        service.getHighestBalance()
                .ifPresent(System.out::println);
 
 
        // Lowest
        System.out.println("\n===== LOWEST BALANCE =====");
 
        service.getLowestBalance()
                .ifPresent(System.out::println);
 
 
        // Optional
        Optional<Customer> customer =
                service.getCustomers()
                        .stream()
                        .filter(c ->
                                c.getCustomerId() == 101)
                        .findFirst();
 
        customer.ifPresent(c ->
                System.out.println(
                        "Found: " + c));
 
        System.out.println(
                customer.orElse(
                        new Customer(
                                0,
                                "Unknown",
                                "",
                                "",
                                "",
                                "REGULAR")));
 
        System.out.println(
                customer.orElseGet(() ->
                        new Customer(
                                0,
                                "Generated",
                                "",
                                "",
                                "",
                                "REGULAR")));
 
 
        // flatMap
        System.out.println(
                "\n===== FLAT MAP ACCOUNT NUMBERS =====");
 
        service.getCustomers()
                .stream()
                .flatMap(c ->
                        service.getAccounts()
                                .stream()
                                .filter(a ->
                                        a.getCustomerId()
                                                == c.getCustomerId()))
                .map(BankAccount::getAccountNumber)
                .forEach(System.out::println);
 
 
        // distinct
        System.out.println(
                "\n===== DISTINCT CITIES =====");
 
        service.getCustomers()
                .stream()
                .map(Customer::getCity)
                .distinct()
                .forEach(System.out::println);
 
 
        // joining
        String names =
                service.getCustomers()
                        .stream()
                        .map(Customer::getName)
                        .collect(Collectors.joining(", "));
 
        System.out.println(
                "\nCustomer Names: " + names);
 
 
        // partitioningBy
        Map<Boolean, List<Customer>> partition =
                service.getCustomers()
                        .stream()
                        .collect(Collectors.partitioningBy(
                                premium));
 
        System.out.println(
                "\nPremium/Non-Premium: " +
                partition);
 
 
        // limit
        System.out.println(
                "\n===== TOP 3 BALANCES =====");
 
        service.getAccounts()
                .stream()
                .sorted(Comparator.comparingDouble(
                        BankAccount::getBalance)
                        .reversed())
                .limit(3)
                .forEach(System.out::println);
 
 
        // skip
        System.out.println(
                "\n===== SKIP FIRST 5 =====");
 
        service.getCustomers()
                .stream()
                .skip(5)
                .forEach(System.out::println);
    }
    
    private static void demonstrateJava17(
            BankingService service) {
 
        System.out.println(
                "\n===== JAVA 17 RECORD =====");
 
        CustomerRecord record =
                new CustomerRecord(
                        101,
                        "Rahul",
                        "Bangalore",
                        "PREMIUM");
 
        System.out.println(record);
        System.out.println(record.name());
        System.out.println(record.city());
 
 
        TransactionRecord transactionRecord =
                new TransactionRecord(
                        1,
                        100001,
                        "DEPOSIT",
                        50000);
 
        System.out.println(transactionRecord);
 
 
        System.out.println(
                "\n===== PATTERN MATCHING =====");
 
        service.getAccounts()
                .forEach(
                        com.bank.util.BankingReport::
                                displayAccountType);
 
 
        System.out.println(
                "\n===== SWITCH EXPRESSION =====");
 
        System.out.println(
                BankingReport.classifyTransaction(
                        "DEPOSIT"));
 
        System.out.println(
                BankingReport.classifyTransaction(
                        "WITHDRAW"));
 
        System.out.println(
                BankingReport.classifyTransaction(
                        "TRANSFER"));
 
 
        System.out.println(
                "\n===== TEXT BLOCK =====");
 
        String json = """
                {
                  "customerId": 101,
                  "name": "Rahul",
                  "city": "Bangalore",
                  "customerType": "PREMIUM"
                }
                """;
 
        System.out.println(json);
    }
    
    private static void demonstrateBankingOperations(
            BankingService service) {
 
        System.out.println(
                "\n===== BANKING OPERATIONS =====");
 
        System.out.println(
                "Initial Balance: " +
                service.checkBalance(100001));
 
        service.deposit(
                100001,
                10000);
 
        System.out.println(
                "After Deposit: " +
                service.checkBalance(100001));
 
        service.withdraw(
                100001,
                5000);
 
        System.out.println(
                "After Withdrawal: " +
                service.checkBalance(100001));
 
        service.transfer(
                100001,
                100002,
                10000);
 
        System.out.println(
                "Source Balance: " +
                service.checkBalance(100001));
 
        System.out.println(
                "Target Balance: " +
                service.checkBalance(100002));
 
        service.calculateInterest(
                100001,
                5);
 
        System.out.println(
                "After Interest: " +
                service.checkBalance(100001));
    }
    
    public static void generateStatement(
            BankAccount account,
            Customer customer) {
     
        String statement = """
                ==================================
                     BANK ACCOUNT STATEMENT
                ==================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : %.2f
                Status         : %s
                ==================================
                """.formatted(
                account.getAccountNumber(),
                customer.getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus());
     
        System.out.println(statement);
    }
    
    public void transactionAnalysis(BankingService service) {
    	
    	List<Transaction> transactions = service.getTransactions();
    	 
        System.out.println("\n===== TRANSACTION ANALYSIS =====");
     
        double deposits =
                transactions.stream()
                        .filter(t ->
                                t.getTransactionType()
                                        .equals("DEPOSIT"))
                        .mapToDouble(
                                Transaction::getAmount)
                        .sum();
     
        double withdrawals =
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
     
        Map<String, Long> countByType =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getTransactionType,
                                Collectors.counting()));
     
        System.out.println(
                "Total Deposits: " + deposits);
     
        System.out.println(
                "Total Withdrawals: " + withdrawals);
     
        System.out.println(
                "Highest Transaction: " +
                highest.orElse(null));
     
        System.out.println(
                "Lowest Transaction: " +
                lowest.orElse(null));
     
        System.out.println(
                "Average Transaction: " +
                average);
     
        System.out.println(
                "Count By Type: " +
                countByType);
     
        System.out.println(
                "Above 50000: " +
                transactions.stream()
                        .filter(t -> t.getAmount() > 50000)
                        .count());
    }
}
