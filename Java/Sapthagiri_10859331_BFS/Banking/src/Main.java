
import data.BankingData;
import exception.CustomerNotFoundException;
import exception.InsufficientBalanceException;
import exception.InvalidAccountException;
import exception.InvalidTransactionException;
import model.BankAccount;
import model.Customer;
import model.Transaction;
import service.AnalyticsService;
import service.BankingService;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final List<Customer> customers =
            new ArrayList<>(
                    BankingData.createCustomers()
            );

    private static final BankingService bankingService =
            new BankingService(
                    BankingData.createAccounts(),
                    BankingData.createTransactions()
            );

    private static final AnalyticsService analyticsService =
            new AnalyticsService();


    public static void main(String[] args) {

        System.out.println(
                "========================================"
        );

        System.out.println(
                "       BFS BANKING APPLICATION"
        );

        System.out.println(
                "========================================"
        );

        boolean running = true;

        while (running) {

            displayMenu();

            int choice = readInt(
                    "Enter your choice: "
            );

            try {

                switch (choice) {

                    case 1 ->
                            addCustomer();

                    case 2 ->
                            displayAllCustomers();

                    case 3 ->
                            searchCustomer();

                    case 4 ->
                            addBankAccount();

                    case 5 ->
                            displayAllAccounts();

                    case 6 ->
                            depositMoney();

                    case 7 ->
                            withdrawMoney();

                    case 8 ->
                            transferMoney();

                    case 9 ->
                            checkBalance();

                    case 10 ->
                            displayTransactionHistory();

                    case 11 ->
                            bankingAnalytics();

                    case 12 ->
                            customerReport();

                    case 13 ->
                            accountTypeReport();

                    case 14 ->
                            transactionReport();

                    case 15 ->
                            generateAccountStatement();

                    case 16 -> {

                        System.out.println(
                                "Thank you for using BFS Banking."
                        );

                        running = false;
                    }

                    default ->
                            System.out.println(
                                    "Invalid choice."
                            );
                }

            } catch (InvalidAccountException |
                     InsufficientBalanceException |
                     InvalidTransactionException |
                     CustomerNotFoundException e) {

                System.out.println(
                        "\nERROR: "
                                + e.getMessage()
                );

            } catch (Exception e) {

                System.out.println(
                        "\nUnexpected error: "
                                + e.getMessage()
                );
            }
        }

        scanner.close();
    }


    // =========================================================
    // MENU
    // =========================================================

    private static void displayMenu() {

        System.out.println(
                """
                
                ========================================
                         BFS BANKING MENU
                ========================================
                1.  Add Customer
                2.  Display All Customers
                3.  Search Customer
                4.  Add Bank Account
                5.  Display All Accounts
                6.  Deposit Money
                7.  Withdraw Money
                8.  Transfer Money
                9.  Check Account Balance
                10. Display Transaction History
                11. Banking Analytics
                12. Customer Report
                13. Account Type Report
                14. Transaction Report
                15. Generate Account Statement
                16. Exit
                ========================================
                """
        );
    }


    // =========================================================
    // 1. ADD CUSTOMER
    // =========================================================

    private static void addCustomer() {

        System.out.println(
                "\n========== ADD CUSTOMER =========="
        );

        int id =
                readInt("Customer ID: ");

        String name =
                readString("Name: ");

        String email =
                readString("Email: ");

        String city =
                readString("City: ");

        String phone =
                readString("Phone: ");

        String customerType =
                readString(
                        "Customer Type (PREMIUM/REGULAR): "
                );

        Customer customer =
                new Customer(
                        id,
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


    // =========================================================
    // 2. DISPLAY CUSTOMERS
    // =========================================================

    private static void displayAllCustomers() {

        System.out.println(
                "\n========== ALL CUSTOMERS =========="
        );

        customers.forEach(
                System.out::println
        );
    }


    // =========================================================
    // 3. SEARCH CUSTOMER
    // =========================================================

    private static void searchCustomer() {

        int customerId =
                readInt("Enter Customer ID: ");

        Customer customer =
                customers.stream()
                        .filter(c ->
                                c.getCustomerId()
                                        == customerId)
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new CustomerNotFoundException(
                                                "Customer not found: "
                                                        + customerId
                                        )
                        );

        System.out.println(
                "\nCustomer Found:"
        );

        System.out.println(customer);
    }


    // =========================================================
    // 4. ADD BANK ACCOUNT
    // =========================================================

    private static void addBankAccount() {

        System.out.println(
                "\n========== ADD BANK ACCOUNT =========="
        );

        long accountNumber =
                readLong("Account Number: ");

        int customerId =
                readInt("Customer ID: ");

        Customer customer =
                customers.stream()
                        .filter(c ->
                                c.getCustomerId()
                                        == customerId)
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new CustomerNotFoundException(
                                                "Customer not found."
                                        )
                        );

        String accountType =
                readString(
                        "Account Type (SAVINGS/CURRENT): "
                );

        double balance =
                readDouble("Initial Balance: ");

        if (balance < 0) {

            throw new InvalidTransactionException(
                    "Initial balance cannot be negative."
            );
        }

        BankAccount account =
                new BankAccount(
                        accountNumber,
                        customerId,
                        accountType,
                        balance,
                        "ACTIVE"
                );

        bankingService.getAccounts()
                .add(account);

        System.out.println(
                "Account created for "
                        + customer.getName()
        );
    }


    // =========================================================
    // 5. DISPLAY ACCOUNTS
    // =========================================================

    private static void displayAllAccounts() {

        System.out.println(
                "\n========== ALL ACCOUNTS =========="
        );

        bankingService.getAccounts()
                .forEach(System.out::println);
    }


    // =========================================================
    // 6. DEPOSIT
    // =========================================================

    private static void depositMoney() {

        long accountNumber =
                readLong("Account Number: ");

        double amount =
                readDouble("Deposit Amount: ");

        bankingService.deposit(
                accountNumber,
                amount
        );

        System.out.println(
                "Deposit successful."
        );

        System.out.println(
                "New Balance: "
                        + bankingService.checkBalance(
                        accountNumber
                )
        );
    }


    // =========================================================
    // 7. WITHDRAW
    // =========================================================

    private static void withdrawMoney() {

        long accountNumber =
                readLong("Account Number: ");

        double amount =
                readDouble("Withdrawal Amount: ");

        bankingService.withdraw(
                accountNumber,
                amount
        );

        System.out.println(
                "Withdrawal successful."
        );

        System.out.println(
                "New Balance: "
                        + bankingService.checkBalance(
                        accountNumber
                )
        );
    }


    // =========================================================
    // 8. TRANSFER
    // =========================================================

    private static void transferMoney() {

        long sourceAccount =
                readLong(
                        "Source Account Number: "
                );

        long targetAccount =
                readLong(
                        "Target Account Number: "
                );

        double amount =
                readDouble("Transfer Amount: ");

        bankingService.transfer(
                sourceAccount,
                targetAccount,
                amount
        );

        System.out.println(
                "Transfer successful."
        );
    }


    // =========================================================
    // 9. CHECK BALANCE
    // =========================================================

    private static void checkBalance() {

        long accountNumber =
                readLong("Account Number: ");

        double balance =
                bankingService.checkBalance(
                        accountNumber
                );

        System.out.println(
                "Current Balance: "
                        + balance
        );
    }


    // =========================================================
    // 10. TRANSACTION HISTORY
    // =========================================================

    private static void displayTransactionHistory() {

        System.out.println(
                """
                
                ========================================
                    TRANSACTION HISTORY
                ========================================
                1. All Transactions
                2. Deposits
                3. Withdrawals
                4. Transfers
                5. Above 50,000
                6. Sorted Transactions
                7. Latest Five
                ========================================
                """
        );

        int choice =
                readInt("Choose option: ");

        List<Transaction> transactions;

        switch (choice) {

            case 1 ->
                    transactions =
                            bankingService
                                    .getAllTransactions();

            case 2 ->
                    transactions =
                            bankingService
                                    .getDeposits();

            case 3 ->
                    transactions =
                            bankingService
                                    .getWithdrawals();

            case 4 ->
                    transactions =
                            bankingService
                                    .getTransfers();

            case 5 ->
                    transactions =
                            bankingService
                                    .getTransactionsAbove50000();

            case 6 ->
                    transactions =
                            bankingService
                                    .getSortedTransactions();

            case 7 ->
                    transactions =
                            bankingService
                                    .getLatestFiveTransactions();

            default -> {

                System.out.println(
                        "Invalid option."
                );

                return;
            }
        }

        transactions.forEach(
                System.out::println
        );
    }


    // =========================================================
    // 11. BANKING ANALYTICS
    // =========================================================

    private static void bankingAnalytics() {

        String dashboard =
                analyticsService.generateBankingDashboard(
                        customers,
                        bankingService.getAccounts(),
                        bankingService.getTransactions()
                );

        System.out.println(dashboard);
    }


    // =========================================================
    // 12. CUSTOMER REPORT
    // =========================================================

    private static void customerReport() {

        analyticsService.customerReport(
                customers
        );
    }


    // =========================================================
    // 13. ACCOUNT TYPE REPORT
    // =========================================================

    private static void accountTypeReport() {

        analyticsService.accountTypeReport(
                bankingService.getAccounts()
        );
    }


    // =========================================================
    // 14. TRANSACTION REPORT
    // =========================================================

    private static void transactionReport() {

        analyticsService.transactionReport(
                bankingService.getTransactions()
        );
    }


    // =========================================================
    // 15. ACCOUNT STATEMENT
    // =========================================================

    private static void generateAccountStatement() {

        long accountNumber =
                readLong("Account Number: ");

        BankAccount account =
                bankingService.findAccount(
                        accountNumber
                );

        Customer customer =
                customers.stream()
                        .filter(c ->
                                c.getCustomerId()
                                        == account.getCustomerId())
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new CustomerNotFoundException(
                                                "Customer not found."
                                        )
                        );

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy HH:mm:ss"
                );

        String statement = """
                ========================================
                     BANK ACCOUNT STATEMENT
                ========================================
                Account Number : %d
                Customer       : %s
                Account Type   : %s
                Balance        : %.2f
                Status         : %s
                ========================================
                """.formatted(
                account.getAccountNumber(),
                customer.getName(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );

        System.out.println(statement);

        System.out.println(
                "TRANSACTIONS"
        );

        bankingService.getTransactions()
                .stream()
                .filter(transaction ->
                        transaction.getAccountNumber()
                                == accountNumber)
                .sorted(
                        java.util.Comparator.comparing(
                                Transaction::getTransactionDate
                        ).reversed()
                )
                .forEach(transaction ->
                        System.out.println(
                                transaction
                                        .getTransactionId()
                                        + " | "
                                        + transaction
                                        .getTransactionType()
                                        + " | "
                                        + transaction
                                        .getAmount()
                                        + " | "
                                        + transaction
                                        .getTransactionDate()
                                        .format(formatter)
                        )
                );
    }


    // =========================================================
    // INPUT METHODS
    // =========================================================

    private static int readInt(String message) {

        System.out.print(message);

        return Integer.parseInt(
                scanner.nextLine()
        );
    }


    private static long readLong(String message) {

        System.out.print(message);

        return Long.parseLong(
                scanner.nextLine()
        );
    }


    private static double readDouble(String message) {

        System.out.print(message);

        return Double.parseDouble(
                scanner.nextLine()
        );
    }


    private static String readString(String message) {

        System.out.print(message);

        return scanner.nextLine();
    }
}
