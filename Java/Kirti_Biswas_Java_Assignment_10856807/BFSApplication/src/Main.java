import exception.CustomerNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAccountException;
import exception.InvalidTransactionException;
import functional.BankingOperation;
import model.BankAccount;
import model.CurrentAccount;
import model.Customer;
import model.LoanAccount;
import model.SavingsAccount;
import model.Transaction;
import service.BankingService;
import util.BankingReport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

    private static final BankingService bankingService =
            new BankingService();

    private static final List<Customer> customers =
            new ArrayList<>();

    private static final List<BankAccount> accounts =
            new ArrayList<>();

    private static final List<Transaction> transactions =
            new ArrayList<>();

    private static int transactionIdCounter = 1;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        addSampleData();

        demonstrateJava8Features();
        demonstrateDateAndTimeApi();
        demonstrateOptional();

        int choice = 0;

        do {

            displayMenu();

            try {

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1 -> addCustomer(scanner);

                    case 2 -> displayAllCustomers();

                    case 3 -> searchCustomer(scanner);

                    case 4 -> addBankAccount(scanner);

                    case 5 -> displayAllAccounts();

                    case 6 -> depositMoney(scanner);

                    case 7 -> withdrawMoney(scanner);

                    case 8 -> transferMoney(scanner);

                    case 9 -> checkAccountBalance(scanner);

                    case 10 -> displayTransactionHistory();

                    case 11 -> displayBankingAnalytics();

                    case 12 -> displayCustomerReport();

                    case 13 -> displayAccountTypeReport();

                    case 14 -> displayTransactionReport();

                    case 15 -> generateAccountStatement(scanner);

                    case 16 -> System.out.println(
                            "Thank you for using the Banking Application."
                    );

                    default -> System.out.println(
                            "Invalid choice. Enter a number from 1 to 16."
                    );
                }

            } catch (InputMismatchException exception) {

                System.out.println(
                        "Invalid input. Please enter the correct data type."
                );

                scanner.nextLine();

            } catch (CustomerNotFoundException
                     | InvalidAccountException
                     | InsufficientBalanceException
                     | InvalidTransactionException exception) {

                System.out.println(
                        "Operation failed: "
                                + exception.getMessage()
                );

            } catch (Exception exception) {

                System.out.println(
                        "Unexpected error: "
                                + exception.getMessage()
                );
            }

        } while (choice != 16);

        scanner.close();
    }

    private static void displayMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("        BANKING MANAGEMENT SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Add Customer");
        System.out.println("2. Display All Customers");
        System.out.println("3. Search Customer");
        System.out.println("4. Add Bank Account");
        System.out.println("5. Display All Accounts");
        System.out.println("6. Deposit Money");
        System.out.println("7. Withdraw Money");
        System.out.println("8. Transfer Money");
        System.out.println("9. Check Account Balance");
        System.out.println("10. Display Transaction History");
        System.out.println("11. Banking Analytics");
        System.out.println("12. Customer Report");
        System.out.println("13. Account Type Report");
        System.out.println("14. Transaction Report");
        System.out.println("15. Generate Account Statement");
        System.out.println("16. Exit");
        System.out.println("========================================");
    }

    // 1. Add Customer
    private static void addCustomer(Scanner scanner)
            throws InvalidTransactionException {

        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        boolean customerExists =
                customers.stream()
                        .anyMatch(customer ->
                                customer.getCustomerId()
                                        == customerId
                        );

        if (customerExists) {
            throw new InvalidTransactionException(
                    "Customer ID already exists."
            );
        }

        System.out.print("Customer Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("City: ");
        String city = scanner.nextLine();

        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        System.out.print(
                "Customer Type (PREMIUM/REGULAR): "
        );

        String customerType =
                scanner.nextLine()
                        .trim()
                        .toUpperCase();

        if (name.isBlank()
                || email.isBlank()
                || city.isBlank()
                || phone.isBlank()) {

            throw new InvalidTransactionException(
                    "Customer fields cannot be empty."
            );
        }

        if (!email.contains("@")) {
            throw new InvalidTransactionException(
                    "Enter a valid email address."
            );
        }

        if (!phone.matches("\\d{10}")) {
            throw new InvalidTransactionException(
                    "Phone number must contain exactly 10 digits."
            );
        }

        if (!customerType.equals("PREMIUM")
                && !customerType.equals("REGULAR")) {

            throw new InvalidTransactionException(
                    "Customer type must be PREMIUM or REGULAR."
            );
        }

        Customer customer = new Customer(
                customerId,
                name,
                email,
                city,
                phone,
                customerType
        );

        customers.add(customer);

        System.out.println(
                "Customer added successfully."
        );
    }

    // 2. Display All Customers
    private static void displayAllCustomers() {

        if (customers.isEmpty()) {
            System.out.println(
                    "No customers available."
            );
            return;
        }

        Consumer<Customer> displayCustomer =
                System.out::println;

        customers.forEach(displayCustomer);
    }

    // 3. Search Customer
    private static void searchCustomer(Scanner scanner)
            throws CustomerNotFoundException {

        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();

        Customer customer =
                bankingService.findCustomer(
                        customers,
                        customerId
                );

        System.out.println("Customer found:");
        System.out.println(customer);
    }

    // 4. Add Bank Account
    private static void addBankAccount(Scanner scanner)
            throws CustomerNotFoundException,
            InvalidAccountException {

        System.out.print("Account Number: ");
        long accountNumber = scanner.nextLong();

        if (findAccountOrNull(accountNumber) != null) {

            throw new InvalidAccountException(
                    "Account number already exists."
            );
        }

        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();

        bankingService.findCustomer(
                customers,
                customerId
        );

        scanner.nextLine();

        System.out.print(
                "Account Type (SAVINGS/CURRENT/LOAN): "
        );

        String accountType =
                scanner.nextLine()
                        .trim()
                        .toUpperCase();

        System.out.print("Initial Balance: ");
        double balance = scanner.nextDouble();

        if (balance < 0) {
            throw new InvalidAccountException(
                    "Initial balance cannot be negative."
            );
        }

        // Java 17 Switch Expression
        BankAccount account = switch (accountType) {

            case "SAVINGS" -> {

                if (balance < 1000) {
                    throw new InvalidAccountException(
                            "Savings account requires minimum balance of 1000."
                    );
                }

                yield new SavingsAccount(
                        accountNumber,
                        customerId,
                        balance,
                        "ACTIVE"
                );
            }

            case "CURRENT" -> {

                if (balance < 5000) {
                    throw new InvalidAccountException(
                            "Current account requires minimum balance of 5000."
                    );
                }

                yield new CurrentAccount(
                        accountNumber,
                        customerId,
                        balance,
                        "ACTIVE"
                );
            }

            case "LOAN" -> new LoanAccount(
                    accountNumber,
                    customerId,
                    balance,
                    "ACTIVE"
            );

            default -> throw new InvalidAccountException(
                    "Invalid account type."
            );
        };

        accounts.add(account);

        if (balance > 0) {

            addTransaction(
                    accountNumber,
                    "DEPOSIT",
                    balance,
                    "Initial account deposit"
            );
        }

        System.out.println(
                "Bank account added successfully."
        );
    }

    // 5. Display All Accounts
    private static void displayAllAccounts() {

        if (accounts.isEmpty()) {
            System.out.println(
                    "No accounts available."
            );
            return;
        }

        accounts.forEach(account -> {

            // Java 17 Pattern Matching for instanceof
            if (account instanceof SavingsAccount savings) {

                System.out.println(
                        "Savings Account: " + savings
                );

            } else if (account
                    instanceof CurrentAccount current) {

                System.out.println(
                        "Current Account: " + current
                );

            } else if (account
                    instanceof LoanAccount loan) {

                System.out.println(
                        "Loan Account: " + loan
                );
            }
        });
    }

    // 6. Deposit Money
    private static void depositMoney(Scanner scanner)
            throws InvalidAccountException,
            InvalidTransactionException {

        System.out.print("Account Number: ");
        long accountNumber = scanner.nextLong();

        System.out.print("Deposit Amount: ");
        double amount = scanner.nextDouble();

        validateAmount(amount);

        BankAccount account =
                findAccount(accountNumber);

        bankingService.deposit(
                account,
                amount
        );

        addTransaction(
                accountNumber,
                "DEPOSIT",
                amount,
                "Money deposited"
        );

        System.out.println(
                "Deposit successful."
        );

        System.out.println(
                "Updated Balance: "
                        + bankingService.checkBalance(account)
        );
    }

    // 7. Withdraw Money
    private static void withdrawMoney(Scanner scanner)
            throws InvalidAccountException,
            InvalidTransactionException,
            InsufficientBalanceException {

        System.out.print("Account Number: ");
        long accountNumber = scanner.nextLong();

        System.out.print("Withdrawal Amount: ");
        double amount = scanner.nextDouble();

        validateAmount(amount);

        BankAccount account =
                findAccount(accountNumber);

        bankingService.withdraw(
                account,
                amount
        );

        addTransaction(
                accountNumber,
                "WITHDRAW",
                amount,
                "Money withdrawn"
        );

        System.out.println(
                "Withdrawal successful."
        );

        System.out.println(
                "Updated Balance: "
                        + bankingService.checkBalance(account)
        );
    }

    // 8. Transfer Money
    private static void transferMoney(Scanner scanner)
            throws InvalidAccountException,
            InvalidTransactionException,
            InsufficientBalanceException {

        System.out.print("Source Account Number: ");
        long sourceAccountNumber =
                scanner.nextLong();

        System.out.print("Target Account Number: ");
        long targetAccountNumber =
                scanner.nextLong();

        if (sourceAccountNumber
                == targetAccountNumber) {

            throw new InvalidTransactionException(
                    "Source and target accounts cannot be the same."
            );
        }

        System.out.print("Transfer Amount: ");
        double amount = scanner.nextDouble();

        validateAmount(amount);

        BankAccount sourceAccount =
                findAccount(sourceAccountNumber);

        BankAccount targetAccount =
                findAccount(targetAccountNumber);

        bankingService.transfer(
                sourceAccount,
                targetAccount,
                amount
        );

        addTransaction(
                sourceAccountNumber,
                "TRANSFER",
                amount,
                "Transferred to account "
                        + targetAccountNumber
        );

        addTransaction(
                targetAccountNumber,
                "DEPOSIT",
                amount,
                "Received from account "
                        + sourceAccountNumber
        );

        System.out.println(
                "Money transferred successfully."
        );

        System.out.println(
                "Source Account Balance: "
                        + sourceAccount.getBalance()
        );

        System.out.println(
                "Target Account Balance: "
                        + targetAccount.getBalance()
        );
    }

    // 9. Check Account Balance
    private static void checkAccountBalance(
            Scanner scanner)
            throws InvalidAccountException {

        System.out.print("Account Number: ");
        long accountNumber = scanner.nextLong();

        BankAccount account =
                findAccount(accountNumber);

        System.out.println(
                "Available Balance: "
                        + bankingService.checkBalance(account)
        );
    }

    // 10. Transaction History
    private static void displayTransactionHistory() {

        if (transactions.isEmpty()) {

            System.out.println(
                    "No transactions available."
            );

            return;
        }

        System.out.println(
                "\n========== ALL TRANSACTIONS =========="
        );

        transactions.stream()
                .sorted(
                        Comparator.comparing(
                                Transaction::getTransactionDate
                        ).reversed()
                )
                .forEach(System.out::println);

        System.out.println(
                "\n========== DEPOSITS =========="
        );

        transactions.stream()
                .filter(transaction ->
                        transaction.getTransactionType()
                                .equalsIgnoreCase("DEPOSIT")
                )
                .forEach(System.out::println);

        System.out.println(
                "\n========== WITHDRAWALS =========="
        );

        transactions.stream()
                .filter(transaction ->
                        transaction.getTransactionType()
                                .equalsIgnoreCase("WITHDRAW")
                )
                .forEach(System.out::println);

        System.out.println(
                "\n========== TRANSFERS =========="
        );

        transactions.stream()
                .filter(transaction ->
                        transaction.getTransactionType()
                                .equalsIgnoreCase("TRANSFER")
                )
                .forEach(System.out::println);

        System.out.println(
                "\n========== ABOVE 50000 =========="
        );

        transactions.stream()
                .filter(transaction ->
                        transaction.getAmount() > 50000
                )
                .forEach(System.out::println);

        System.out.println(
                "\n========== LATEST FIVE =========="
        );

        transactions.stream()
                .sorted(
                        Comparator.comparing(
                                Transaction::getTransactionDate
                        ).reversed()
                )
                .limit(5)
                .forEach(System.out::println);
    }

    // 11. Banking Analytics
    private static void displayBankingAnalytics() {

        bankingService.generateBankingDashboard(
                customers,
                accounts
        );

        BankingReport.generateDashboard(
                customers.size(),
                accounts.size(),
                bankingService.totalBalance(accounts),
                bankingService.averageBalance(accounts),
                bankingService.countPremiumCustomers(
                        customers
                )
        );

        System.out.println(
                "Highest Balance Account:"
        );

        bankingService.maximumBalanceAccount(accounts)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println(
                                "No account available."
                        )
                );

        System.out.println(
                "\nLowest Balance Account:"
        );

        bankingService.minimumBalanceAccount(accounts)
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println(
                                "No account available."
                        )
                );

        System.out.println(
                "\nPremium Customers:"
        );

        bankingService.premiumCustomers(customers)
                .forEach(System.out::println);

        System.out.println(
                "\nCustomers Sorted by Name:"
        );

        bankingService.sortByName(customers)
                .forEach(System.out::println);

        System.out.println(
                "\nDistinct Cities:"
        );

        bankingService.distinctCities(customers)
                .forEach(System.out::println);

        System.out.println(
                "\nCustomers Grouped by City:"
        );

        bankingService.groupCustomersByCity(customers)
                .forEach(
                        (city, customerList) ->
                                System.out.println(
                                        city
                                                + " = "
                                                + customerList.size()
                                )
                );

        System.out.println(
                "\nPremium and Non-Premium Partition:"
        );

        bankingService.partitionCustomers(customers)
                .forEach(
                        (premium, customerList) ->
                                System.out.println(
                                        (premium
                                                ? "PREMIUM"
                                                : "NON-PREMIUM")
                                                + " = "
                                                + customerList
                                )
                );

        System.out.println(
                "\nTop Three Accounts:"
        );

        bankingService.topThreeAccounts(accounts)
                .forEach(System.out::println);

        System.out.println(
                "\nJoined Customer Names: "
                        + bankingService
                        .customerNamesJoined(customers)
        );

        System.out.println(
                "\nTransaction Summary:"
        );

        bankingService.transactionSummary(transactions)
                .forEach(
                        (type, total) ->
                                System.out.println(
                                        type
                                                + " Total = "
                                                + total
                                )
                );
    }

    // 12. Customer Report
    private static void displayCustomerReport() {

        BankingReport.generateCustomerReport(
                customers
        );

        System.out.println(
                "\n========== CUSTOMER JSON =========="
        );

        customers.stream()
                .map(Main::generateCustomerJson)
                .forEach(System.out::println);
    }

    // 13. Account Type Report
    private static void displayAccountTypeReport() {

        if (accounts.isEmpty()) {

            System.out.println(
                    "No account data available."
            );

            return;
        }

        Map<String, Long> accountCountByType =
                accounts.stream()
                        .collect(
                                Collectors.groupingBy(
                                        BankAccount::getAccountType,
                                        Collectors.counting()
                                )
                        );

        Map<String, Double> balanceByType =
                bankingService
                        .totalBalanceByAccountType(
                                accounts
                        );

        System.out.println(
                "\n========== ACCOUNT TYPE REPORT =========="
        );

        accountCountByType.forEach(
                (accountType, count) -> {

                    double totalBalance =
                            balanceByType.getOrDefault(
                                    accountType,
                                    0.0
                            );

                    double averageBalance =
                            accounts.stream()
                                    .filter(account ->
                                            account.getAccountType()
                                                    .equals(accountType)
                                    )
                                    .collect(
                                            Collectors.averagingDouble(
                                                    BankAccount::getBalance
                                            )
                                    );

                    System.out.println(
                            "Account Type    : "
                                    + accountType
                    );

                    System.out.println(
                            "Account Count   : "
                                    + count
                    );

                    System.out.println(
                            "Total Balance   : "
                                    + totalBalance
                    );

                    System.out.println(
                            "Average Balance : "
                                    + averageBalance
                    );

                    System.out.println(
                            "-----------------------------------------"
                    );
                }
        );

        System.out.println(
                "\n========== ACCOUNT JSON =========="
        );

        accounts.stream()
                .map(Main::generateAccountJson)
                .forEach(System.out::println);
    }

    // 14. Transaction Report
    private static void displayTransactionReport() {

        BankingReport.generateTransactionReport(
                transactions
        );

        if (transactions.isEmpty()) {
            return;
        }

        System.out.println(
                "\n========== TRANSACTION CLASSIFICATION =========="
        );

        transactions.forEach(transaction -> {

            String category =
                    bankingService.classifyTransaction(
                            transaction.getTransactionType()
                    );

            System.out.println(
                    "Transaction ID: "
                            + transaction.getTransactionId()
                            + ", Type: "
                            + transaction.getTransactionType()
                            + ", Category: "
                            + category
            );
        });

        System.out.println(
                "\n========== TRANSACTION TOTAL BY TYPE =========="
        );

        bankingService.transactionSummary(transactions)
                .forEach(
                        (type, total) ->
                                System.out.println(
                                        type
                                                + " = "
                                                + total
                                )
                );

        Map<String, Long> countByType =
                transactions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Transaction::getTransactionType,
                                        Collectors.counting()
                                )
                        );

        Map<String, Double> averageByType =
                transactions.stream()
                        .collect(
                                Collectors.groupingBy(
                                        Transaction::getTransactionType,
                                        Collectors.averagingDouble(
                                                Transaction::getAmount
                                        )
                                )
                        );

        System.out.println(
                "\n========== COUNT AND AVERAGE BY TYPE =========="
        );

        countByType.forEach(
                (type, count) -> {

                    System.out.println(
                            "Type    : " + type
                    );

                    System.out.println(
                            "Count   : " + count
                    );

                    System.out.println(
                            "Average : "
                                    + averageByType.get(type)
                    );

                    System.out.println(
                            "----------------------------"
                    );
                }
        );

        System.out.println(
                "\n========== TRANSACTION JSON =========="
        );

        transactions.stream()
                .map(Main::generateTransactionJson)
                .forEach(System.out::println);
    }

    // 15. Generate Account Statement
    private static void generateAccountStatement(
            Scanner scanner)
            throws InvalidAccountException,
            CustomerNotFoundException {

        System.out.print("Account Number: ");
        long accountNumber = scanner.nextLong();

        BankAccount account =
                findAccount(accountNumber);

        Customer customer =
                bankingService.findCustomer(
                        customers,
                        account.getCustomerId()
                );

        String statement =
                BankingReport.generateAccountStatement(
                        account,
                        customer.getName()
                );

        System.out.println(statement);
    }

    private static BankAccount findAccount(
            long accountNumber)
            throws InvalidAccountException {

        return accounts.stream()
                .filter(account ->
                        account.getAccountNumber()
                                == accountNumber
                )
                .findFirst()
                .orElseThrow(
                        () ->
                                new InvalidAccountException(
                                        "Account not found: "
                                                + accountNumber
                                )
                );
    }

    private static BankAccount findAccountOrNull(
            long accountNumber) {

        return accounts.stream()
                .filter(account ->
                        account.getAccountNumber()
                                == accountNumber
                )
                .findFirst()
                .orElse(null);
    }

    private static void validateAmount(double amount)
            throws InvalidTransactionException {

        if (amount <= 0) {

            throw new InvalidTransactionException(
                    "Transaction amount must be greater than zero."
            );
        }
    }

    private static void addTransaction(
            long accountNumber,
            String transactionType,
            double amount,
            String description) {

        Transaction transaction =
                new Transaction(
                        transactionIdCounter++,
                        accountNumber,
                        transactionType,
                        amount,
                        LocalDateTime.now(),
                        description
                );

        transactions.add(transaction);
    }

    // Java 17 JSON Text Block
    private static String generateCustomerJson(
            Customer customer) {

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

    // Java 17 JSON Text Block
    private static String generateAccountJson(
            BankAccount account) {

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

    // Java 17 JSON Text Block
    private static String generateTransactionJson(
            Transaction transaction) {

        return """
                {
                  "transactionId": %d,
                  "accountNumber": %d,
                  "transactionType": "%s",
                  "category": "%s",
                  "amount": %.2f,
                  "transactionDate": "%s",
                  "description": "%s"
                }
                """.formatted(
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getTransactionType(),
                bankingService.classifyTransaction(
                        transaction.getTransactionType()
                ),
                transaction.getAmount(),
                transaction.getTransactionDate(),
                transaction.getDescription()
        );
    }

    private static void demonstrateDateAndTimeApi() {

        LocalDate currentDate =
                LocalDate.now();

        LocalTime currentTime =
                LocalTime.now();

        LocalDateTime currentDateTime =
                LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        System.out.println(
                "\n========== DATE AND TIME API =========="
        );

        System.out.println(
                "Current Date: " + currentDate
        );

        System.out.println(
                "Current Time: " + currentTime
        );

        System.out.println(
                "Formatted Date and Time: "
                        + currentDateTime.format(formatter)
        );
    }

    private static void demonstrateOptional() {

        System.out.println(
                "\n========== OPTIONAL DEMONSTRATION =========="
        );

        Optional<Customer> optionalCustomer =
                customers.stream()
                        .filter(customer ->
                                customer.getCustomerId()
                                        == 101
                        )
                        .findFirst();

        optionalCustomer.ifPresent(
                customer ->
                        System.out.println(
                                "ifPresent: "
                                        + customer.getName()
                        )
        );

        Customer orElseCustomer =
                optionalCustomer.orElse(
                        new Customer(
                                0,
                                "Unknown",
                                "unknown@gmail.com",
                                "Unknown",
                                "0000000000",
                                "REGULAR"
                        )
                );

        System.out.println(
                "orElse: "
                        + orElseCustomer.getName()
        );

        Customer orElseGetCustomer =
                optionalCustomer.orElseGet(
                        () ->
                                new Customer(
                                        0,
                                        "Default Customer",
                                        "default@gmail.com",
                                        "Unknown",
                                        "0000000000",
                                        "REGULAR"
                                )
                );

        System.out.println(
                "orElseGet: "
                        + orElseGetCustomer.getName()
        );

        try {

            customers.stream()
                    .filter(customer ->
                            customer.getCustomerId()
                                    == 999
                    )
                    .findFirst()
                    .orElseThrow(
                            () ->
                                    new RuntimeException(
                                            "Customer 999 not found"
                                    )
                    );

        } catch (RuntimeException exception) {

            System.out.println(
                    "orElseThrow: "
                            + exception.getMessage()
            );
        }
    }

    private static void addSampleData() {

        customers.add(
                new Customer(
                        101,
                        "Rahul",
                        "rahul@gmail.com",
                        "Bangalore",
                        "9876543210",
                        "PREMIUM"
                )
        );

        customers.add(
                new Customer(
                        102,
                        "Priya",
                        "priya@gmail.com",
                        "Mangalore",
                        "9876543211",
                        "REGULAR"
                )
        );

        customers.add(
                new Customer(
                        103,
                        "Arun",
                        "arun@gmail.com",
                        "Mysore",
                        "9876543212",
                        "PREMIUM"
                )
        );

        customers.add(
                new Customer(
                        104,
                        "Sneha",
                        "sneha@gmail.com",
                        "Udupi",
                        "9876543213",
                        "REGULAR"
                )
        );

        customers.add(
                new Customer(
                        105,
                        "Kiran",
                        "kiran@gmail.com",
                        "Bangalore",
                        "9876543214",
                        "PREMIUM"
                )
        );

        accounts.add(
                new SavingsAccount(
                        100001,
                        101,
                        85000,
                        "ACTIVE"
                )
        );

        accounts.add(
                new CurrentAccount(
                        100002,
                        102,
                        150000,
                        "ACTIVE"
                )
        );

        accounts.add(
                new SavingsAccount(
                        100003,
                        103,
                        65000,
                        "ACTIVE"
                )
        );

        addTransaction(
                100001,
                "DEPOSIT",
                85000,
                "Opening balance"
        );

        addTransaction(
                100002,
                "DEPOSIT",
                150000,
                "Opening balance"
        );

        addTransaction(
                100003,
                "DEPOSIT",
                65000,
                "Opening balance"
        );
    }

    private static void demonstrateJava8Features() {

        System.out.println(
                "\n========== JAVA 8 FEATURES =========="
        );

        BankingOperation depositOperation =
                (amount, balance) ->
                        balance + amount;

        BankingOperation withdrawalOperation =
                (amount, balance) ->
                        balance - amount;

        BankingOperation interestOperation =
                (rate, balance) ->
                        balance
                                + (balance * rate / 100);

        System.out.println(
                "Lambda Deposit Result: "
                        + depositOperation.execute(
                        1000,
                        50000
                )
        );

        System.out.println(
                "Lambda Withdrawal Result: "
                        + withdrawalOperation.execute(
                        1000,
                        50000
                )
        );

        System.out.println(
                "Lambda Interest Result: "
                        + interestOperation.execute(
                        5,
                        50000
                )
        );

        Predicate<Customer> premiumPredicate =
                customer ->
                        customer.getCustomerType()
                                .equalsIgnoreCase(
                                        "PREMIUM"
                                );

        Consumer<Customer> displayCustomer =
                System.out::println;

        Function<Customer, String> nameFunction =
                Customer::getName;

        Supplier<Long> accountNumberGenerator =
                System::currentTimeMillis;

        System.out.println(
                "\nPremium Customers using Predicate:"
        );

        customers.stream()
                .filter(premiumPredicate)
                .forEach(displayCustomer);

        System.out.println(
                "Customer Name using Function: "
                        + nameFunction.apply(
                        customers.get(0)
                )
        );

        System.out.println(
                "Generated Account Number using Supplier: "
                        + accountNumberGenerator.get()
        );
    }
}