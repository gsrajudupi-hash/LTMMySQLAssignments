package bank;

import bank.functional.BankingOperation;
import bank.model.*;
import bank.service.BankingService;
import bank.util.BankingAnalytics;
import bank.util.BankingReport;
import bank.util.SampleData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final List<Customer> CUSTOMERS =
            SampleData.createCustomers();

    private static final List<BankAccount> ACCOUNTS =
            SampleData.createAccounts();

    private static final List<Transaction> TRANSACTIONS =
            SampleData.createTransactions();

    private static final BankingService BANKING_SERVICE =
            new BankingService(
                    CUSTOMERS,
                    ACCOUNTS,
                    TRANSACTIONS
            );

    private static final Supplier<Long> ACCOUNT_NUMBER_GENERATOR =
            System::currentTimeMillis;

    public static void main(String[] args) {

        demonstrateJavaFeatures();

        boolean applicationRunning = true;

        while (applicationRunning) {

            displayMenu();

            try {
                int choice = readInteger("Enter your choice: ");

                switch (choice) {

                    case 1 -> addCustomer();

                    case 2 -> displayAllCustomers();

                    case 3 -> searchCustomer();

                    case 4 -> addBankAccount();

                    case 5 -> displayAllAccounts();

                    case 6 -> depositMoney();

                    case 7 -> withdrawMoney();

                    case 8 -> transferMoney();

                    case 9 -> checkAccountBalance();

                    case 10 -> displayTransactionHistory();

                    case 11 -> displayBankingAnalytics();

                    case 12 -> displayCustomerReport();

                    case 13 -> displayAccountTypeReport();

                    case 14 -> displayTransactionReport();

                    case 15 -> generateAccountStatement();

                    case 16 -> {
                        applicationRunning = false;
                        System.out.println(
                                "Thank you for using the banking application."
                        );
                    }

                    default -> System.out.println(
                            "Invalid option. Enter a number from 1 to 16."
                    );
                }

            } catch (NumberFormatException exception) {

                System.out.println(
                        "Please enter a valid numeric value."
                );

            } catch (RuntimeException exception) {

                System.out.println(
                        "Operation failed: " +
                                exception.getMessage()
                );
            }
        }

        SCANNER.close();
    }

    private static void demonstrateJavaFeatures() {

        System.out.println(
                "========== JAVA 8 AND JAVA 17 DEMONSTRATION =========="
        );

        BankingOperation depositOperation =
                (amount, balance) -> balance + amount;

        BankingOperation withdrawalOperation =
                (amount, balance) -> balance - amount;

        BankingOperation interestOperation =
                (interestRate, balance) ->
                        balance + (balance * interestRate / 100);

        System.out.println(
                "Lambda deposit result: " +
                        depositOperation.execute(5000, 10000)
        );

        System.out.println(
                "Lambda withdrawal result: " +
                        withdrawalOperation.execute(2000, 10000)
        );

        System.out.println(
                "Lambda interest result: " +
                        interestOperation.execute(5, 10000)
        );

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateTime = LocalDateTime.now();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        System.out.println("Current date: " + currentDate);
        System.out.println("Current time: " + currentTime);
        System.out.println(
                "Current date and time: " +
                        currentDateTime.format(formatter)
        );

        BankingAnalytics.demonstrateFunctionalInterfaces(
                CUSTOMERS
        );

        BankingAnalytics.displayCustomerFilters(CUSTOMERS);

        BankingAnalytics.displaySortedCustomers(CUSTOMERS);

        BankingAnalytics.displayAccountAnalytics(ACCOUNTS);

        BankingAnalytics.displayCustomerAnalytics(
                CUSTOMERS,
                ACCOUNTS
        );

        BankingAnalytics.displayTransactionAnalytics(
                TRANSACTIONS
        );

        BankingAnalytics.demonstrateOptional(CUSTOMERS);

        BankingAnalytics.demonstrateMandatoryStreams(
                CUSTOMERS,
                ACCOUNTS
        );

        BankingAnalytics.displayTopThreeCustomers(
                CUSTOMERS,
                ACCOUNTS
        );

        BankingAnalytics.demonstrateRecords(
                CUSTOMERS,
                ACCOUNTS,
                TRANSACTIONS
        );

        System.out.println(
                "====================================================="
        );
    }

    private static void displayMenu() {

        System.out.println("""
                
                =====================================
                    CUSTOMER ACCOUNT MANAGEMENT
                =====================================
                1. Add Customer
                2. Display All Customers
                3. Search Customer
                4. Add Bank Account
                5. Display All Accounts
                6. Deposit Money
                7. Withdraw Money
                8. Transfer Money
                9. Check Account Balance
                10. Display Transaction History
                11. Banking Analytics
                12. Customer Report
                13. Account Type Report
                14. Transaction Report
                15. Generate Account Statement
                16. Exit
                =====================================
                """);
    }

    private static void addCustomer() {

        int customerId =
                readInteger("Enter customer ID: ");

        boolean customerExists = CUSTOMERS.stream()
                .anyMatch(customer ->
                        customer.getCustomerId() == customerId);

        if (customerExists) {
            throw new IllegalArgumentException(
                    "Customer ID already exists."
            );
        }

        System.out.print("Enter customer name: ");
        String name = SCANNER.nextLine();

        System.out.print("Enter email: ");
        String email = SCANNER.nextLine();

        System.out.print("Enter city: ");
        String city = SCANNER.nextLine();

        System.out.print("Enter phone number: ");
        String phone = SCANNER.nextLine();

        System.out.print(
                "Enter customer type (PREMIUM/REGULAR): "
        );

        String customerType =
                SCANNER.nextLine().toUpperCase();

        if (!customerType.equals("PREMIUM") &&
                !customerType.equals("REGULAR")) {

            throw new IllegalArgumentException(
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

        CUSTOMERS.add(customer);

        System.out.println("Customer added successfully.");
    }

    private static void displayAllCustomers() {

        if (CUSTOMERS.isEmpty()) {
            System.out.println("No customers available.");
            return;
        }

        CUSTOMERS.forEach(System.out::println);
    }

    private static void searchCustomer() {

        int customerId =
                readInteger("Enter customer ID: ");

        Customer customer =
                BANKING_SERVICE.findCustomer(customerId);

        System.out.println(customer);
    }

    private static void addBankAccount() {

        int customerId =
                readInteger("Enter customer ID: ");

        BANKING_SERVICE.findCustomer(customerId);

        System.out.print(
                "Enter account type (SAVINGS/CURRENT/LOAN): "
        );

        String accountType =
                SCANNER.nextLine().toUpperCase();

        double openingBalance =
                readDouble("Enter opening balance: ");

        long accountNumber =
                ACCOUNT_NUMBER_GENERATOR.get();

        BankAccount account = switch (accountType) {

            case "SAVINGS" ->
                    new SavingsAccount(
                            accountNumber,
                            customerId,
                            openingBalance,
                            "ACTIVE"
                    );

            case "CURRENT" ->
                    new CurrentAccount(
                            accountNumber,
                            customerId,
                            openingBalance,
                            "ACTIVE"
                    );

            case "LOAN" ->
                    new LoanAccount(
                            accountNumber,
                            customerId,
                            openingBalance,
                            "ACTIVE"
                    );

            default -> throw new IllegalArgumentException(
                    "Invalid account type."
            );
        };

        if (openingBalance < account.getMinimumBalance()) {
            throw new IllegalArgumentException(
                    "Opening balance must be at least Rs." +
                            account.getMinimumBalance()
            );
        }

        ACCOUNTS.add(account);

        System.out.println(
                "Account created successfully."
        );

        System.out.println(
                "Account number: " + accountNumber
        );
    }

    private static void displayAllAccounts() {

        if (ACCOUNTS.isEmpty()) {
            System.out.println("No accounts available.");
            return;
        }

        ACCOUNTS.forEach(Main::displayAccountUsingPatternMatching);
    }

    private static void displayAccountUsingPatternMatching(
            BankAccount account) {

        if (account instanceof SavingsAccount savingsAccount) {

            System.out.println(
                    "Savings account | Minimum balance: " +
                            savingsAccount.getMinimumBalance()
            );

            System.out.println(savingsAccount);

        } else if (account instanceof CurrentAccount currentAccount) {

            System.out.println(
                    "Current account | Minimum balance: " +
                            currentAccount.getMinimumBalance()
            );

            System.out.println(currentAccount);

        } else if (account instanceof LoanAccount loanAccount) {

            System.out.println("Loan account");
            System.out.println(loanAccount);
        }
    }

    private static void depositMoney() {

        long accountNumber =
                readLong("Enter account number: ");

        double amount =
                readDouble("Enter deposit amount: ");

        BANKING_SERVICE.deposit(accountNumber, amount);
    }

    private static void withdrawMoney() {

        long accountNumber =
                readLong("Enter account number: ");

        double amount =
                readDouble("Enter withdrawal amount: ");

        BANKING_SERVICE.withdraw(accountNumber, amount);
    }

    private static void transferMoney() {

        long sourceAccount =
                readLong("Enter source account number: ");

        long targetAccount =
                readLong("Enter target account number: ");

        double amount =
                readDouble("Enter transfer amount: ");

        BANKING_SERVICE.transfer(
                sourceAccount,
                targetAccount,
                amount
        );
    }

    private static void checkAccountBalance() {

        long accountNumber =
                readLong("Enter account number: ");

        double balance =
                BANKING_SERVICE.checkBalance(accountNumber);

        System.out.printf(
                "Available balance: Rs.%,.2f%n",
                balance
        );
    }

    private static void displayTransactionHistory() {

        if (TRANSACTIONS.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        System.out.println("\nAll transactions:");

        TRANSACTIONS.forEach(transaction ->
                System.out.println(
                        transaction + " | Category: " +
                                classifyTransaction(
                                        transaction.getTransactionType())
                ));

        System.out.println("\nDeposits:");

        TRANSACTIONS.stream()
                .filter(transaction ->
                        "DEPOSIT".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .forEach(System.out::println);

        System.out.println("\nWithdrawals:");

        TRANSACTIONS.stream()
                .filter(transaction ->
                        "WITHDRAW".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .forEach(System.out::println);

        System.out.println("\nTransfers:");

        TRANSACTIONS.stream()
                .filter(transaction ->
                        "TRANSFER".equalsIgnoreCase(
                                transaction.getTransactionType()))
                .forEach(System.out::println);

        System.out.println(
                "\nTransactions above Rs.50,000:"
        );

        TRANSACTIONS.stream()
                .filter(transaction ->
                        transaction.getAmount() > 50000)
                .forEach(System.out::println);

        System.out.println(
                "\nTransactions sorted by amount:"
        );

        TRANSACTIONS.stream()
                .sorted(Comparator.comparingDouble(
                        Transaction::getAmount))
                .forEach(System.out::println);

        System.out.println("\nLatest five transactions:");

        TRANSACTIONS.stream()
                .sorted(
                        Comparator.comparing(
                                        Transaction::getTransactionDate)
                                .reversed()
                )
                .limit(5)
                .forEach(System.out::println);
    }

    private static String classifyTransaction(
            String transactionType) {

        return switch (transactionType.toUpperCase()) {

            case "DEPOSIT", "INTEREST" -> "CREDIT";

            case "WITHDRAW", "LOAN_PAYMENT" -> "DEBIT";

            case "TRANSFER" -> "TRANSFER";

            default -> "UNKNOWN";
        };
    }

    private static void displayBankingAnalytics() {

        String dashboard =
                BankingAnalytics.generateBankingDashboard(
                        CUSTOMERS,
                        ACCOUNTS,
                        TRANSACTIONS
                );

        System.out.println(dashboard);
    }

    private static void displayCustomerReport() {

        System.out.println("\nCustomer JSON report:");

        CUSTOMERS.forEach(customer ->
                System.out.println(
                        BankingReport.generateCustomerJson(customer)
                ));
    }

    private static void displayAccountTypeReport() {

        ACCOUNTS.stream()
                .collect(Collectors.groupingBy(
                        BankAccount::getAccountType))
                .forEach((accountType, accounts) -> {

                    System.out.println(
                            "\nAccount type: " + accountType
                    );

                    accounts.forEach(System.out::println);
                });
    }

    private static void displayTransactionReport() {

        BankingAnalytics.displayTransactionAnalytics(
                TRANSACTIONS
        );
    }

    private static void generateAccountStatement() {

        long accountNumber =
                readLong("Enter account number: ");

        BankAccount account =
                BANKING_SERVICE.findAccount(accountNumber);

        Customer customer =
                BANKING_SERVICE.findCustomer(
                        account.getCustomerId()
                );

        String statement =
                BankingReport.generateAccountStatement(
                        account,
                        customer
                );

        System.out.println(statement);

        System.out.println("Account JSON:");

        System.out.println(
                BankingReport.generateAccountJson(account)
        );

        System.out.println("Recent transactions:");

        TRANSACTIONS.stream()
                .filter(transaction ->
                        transaction.getAccountNumber() ==
                                accountNumber)
                .sorted(
                        Comparator.comparing(
                                        Transaction::getTransactionDate)
                                .reversed()
                )
                .limit(5)
                .forEach(transaction ->
                        System.out.println(
                                BankingReport
                                        .generateTransactionJson(
                                                transaction)
                        ));
    }

    private static int readInteger(String message) {

        System.out.print(message);
        return Integer.parseInt(SCANNER.nextLine());
    }

    private static long readLong(String message) {

        System.out.print(message);
        return Long.parseLong(SCANNER.nextLine());
    }

    private static double readDouble(String message) {

        System.out.print(message);
        return Double.parseDouble(SCANNER.nextLine());
    }
}