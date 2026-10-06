package com.bank;

import com.bank.exception.BankingException;
import com.bank.exception.CustomerNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAccountException;
import com.bank.exception.InvalidCustomerException;
import com.bank.exception.InvalidTransactionException;
import com.bank.functional.BankingOperation;
import com.bank.model.AccountType;
import com.bank.model.BankAccount;
import com.bank.model.Customer;
import com.bank.model.CustomerType;
import com.bank.record.TransactionRecord;
import com.bank.service.BankingService;
import com.bank.util.BankingReport;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    private static final int EXIT_OPTION = 16;

    private final Scanner scanner;
    private final BankingService service = new BankingService();
    private final Map<Integer, BankingOperation> operations = new LinkedHashMap<>();

    public Main(Scanner scanner) {
        this.scanner = scanner;
        operations.put(1, this::addCustomer);
        operations.put(2, () -> BankingReport.printCustomers(service.getCustomers()));
        operations.put(3, this::searchCustomer);
        operations.put(4, this::addAccount);
        operations.put(5, () -> BankingReport.printAccounts(service.getAccounts()));
        operations.put(6, this::deposit);
        operations.put(7, this::withdraw);
        operations.put(8, this::transfer);
        operations.put(9, this::checkBalance);
        operations.put(10, () -> BankingReport.printTransactionHistory(service.getTransactions()));
        operations.put(11, () -> BankingReport.printAnalytics(
                service.getCustomers(), service.getAccounts(), service.getTransactions()));
        operations.put(12, () -> BankingReport.printCustomerReport(service.getCustomerSummaries()));
        operations.put(13, () -> BankingReport.printAccountTypeReport(service.getAccounts()));
        operations.put(14, () -> BankingReport.printTransactionReport(service.getTransactions()));
        operations.put(15, this::generateStatement);
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            new Main(scanner).run();
        }
    }

    public void run() {
        System.out.println("Data is stored in memory for this session only.");
        while (true) {
            printMenu();
            try {
                int choice = readInt("Choose an option: ");
                if (choice == EXIT_OPTION) {
                    System.out.println("Thank you for banking with us.");
                    return;
                }
                BankingOperation operation = operations.get(choice);
                if (operation == null) {
                    System.out.println("Invalid option. Please choose a number from 1 to " + EXIT_OPTION + ".");
                } else {
                    operation.execute();
                }
            } catch (CustomerNotFoundException e) {
                System.out.println("Customer not found: " + e.getMessage() + " Add the customer first (option 1).");
            } catch (InvalidCustomerException e) {
                System.out.println("Invalid customer details: " + e.getMessage());
            } catch (InvalidAccountException e) {
                System.out.println("Invalid account: " + e.getMessage() + " Use option 5 to view accounts.");
            } catch (InsufficientBalanceException e) {
                System.out.printf("Insufficient balance: %s Shortfall: %.2f.%n", e.getMessage(), e.getShortfall());
            } catch (InvalidTransactionException e) {
                System.out.println("Transaction rejected: " + e.getMessage());
            } catch (BankingException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("\nInput closed. Exiting banking application.");
                return;
            }
        }
    }

    private void printMenu() {
        System.out.println("""

                ========= BANKING APPLICATION =========
                 1. Add customer
                 2. Display all customers
                 3. Search customer
                 4. Add bank account
                 5. Display all accounts
                 6. Deposit money
                 7. Withdraw money
                 8. Transfer money
                 9. Check account balance
                10. Display transaction history
                11. Banking analytics
                12. Customer report
                13. Account type report
                14. Transaction report
                15. Generate account statement
                16. Exit""");
    }

    private void addCustomer() throws InvalidCustomerException {
        Customer customer = service.addCustomer(
                readInt("Customer ID: "),
                readText("Name: "),
                readText("Email: "),
                readText("City: "),
                readText("Phone: "),
                readEnum("Customer type", CustomerType.class));
        System.out.println("Customer " + customer.getName() + " added successfully.");
    }

    private void searchCustomer() throws CustomerNotFoundException {
        Customer customer = service.findCustomer(readInt("Customer ID: "));
        BankingReport.printCustomers(List.of(customer));
        BankingReport.printAccounts(service.getAccountsForCustomer(customer.getCustomerId()));
    }

    private void addAccount() throws CustomerNotFoundException, InvalidAccountException {
        long accountNumber = readLong("Account number: ");
        int customerId = readInt("Customer ID: ");
        service.findCustomer(customerId);
        AccountType type = readEnum("Account type", AccountType.class);
        String prompt = type == AccountType.LOAN ? "Loan amount: " : "Opening balance: ";
        BankAccount account = service.openAccount(type, accountNumber, customerId, readAmount(prompt));
        System.out.printf("%s account %d opened with balance %.2f.%n",
                account.getAccountType(), account.getAccountNumber(), account.getBalance());
    }

    private void deposit() throws InvalidAccountException, InvalidTransactionException {
        long accountNumber = readLong("Account number: ");
        service.findAccount(accountNumber);
        TransactionRecord result = service.deposit(accountNumber, readAmount("Deposit amount: "));
        System.out.printf("Deposit successful. Balance: %.2f%n", result.balanceAfter());
    }

    private void withdraw() throws InvalidAccountException, InvalidTransactionException,
            InsufficientBalanceException {
        long accountNumber = readLong("Account number: ");
        service.findAccount(accountNumber);
        TransactionRecord result = service.withdraw(accountNumber, readAmount("Withdrawal amount: "));
        System.out.printf("Withdrawal successful. Balance: %.2f%n", result.balanceAfter());
    }

    private void transfer() throws InvalidAccountException, InvalidTransactionException,
            InsufficientBalanceException {
        long source = readLong("Source account number: ");
        service.findAccount(source);
        long target = readLong("Target account number: ");
        service.findAccount(target);
        List<TransactionRecord> records = service.transfer(source, target, readAmount("Transfer amount: "));
        System.out.printf("Transfer successful. Source balance: %.2f, target balance: %.2f%n",
                records.get(0).balanceAfter(), records.get(1).balanceAfter());
    }

    private void checkBalance() throws InvalidAccountException {
        long accountNumber = readLong("Account number: ");
        System.out.printf("Current balance of account %d: %.2f%n",
                accountNumber, service.checkBalance(accountNumber));
    }

    private void generateStatement() throws InvalidAccountException, CustomerNotFoundException {
        BankAccount account = service.findAccount(readLong("Account number: "));
        Customer customer = service.findCustomer(account.getCustomerId());
        BankingReport.printAccountStatement(customer, account, service.getTransactions(account.getAccountNumber()));
    }

    private String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("A value is required.");
        }
    }

    private long readLong(String prompt) {
        while (true) {
            try {
                return Long.parseLong(readText(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            long value = readLong(prompt);
            if (value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE) {
                return (int) value;
            }
            System.out.println("Please enter a smaller whole number.");
        }
    }

    private double readAmount(String prompt) {
        while (true) {
            try {
                double amount = Double.parseDouble(readText(prompt));
                if (Double.isFinite(amount)) {
                    return amount;
                }
            } catch (NumberFormatException ignored) {
                // Fall through to the retry message.
            }
            System.out.println("Please enter a valid numeric amount.");
        }
    }

    private <E extends Enum<E>> E readEnum(String label, Class<E> type) {
        String options = Arrays.toString(type.getEnumConstants());
        while (true) {
            String value = readText(label + " " + options + ": ").toUpperCase(Locale.ROOT);
            try {
                return Enum.valueOf(type, value);
            } catch (IllegalArgumentException e) {
                System.out.println("Please choose one of " + options + ".");
            }
        }
    }
}
