package com.banking.service;

import com.banking.dao.BankingDAO;
import com.banking.dao.BankingDAOImpl;
import com.banking.model.Customer;

import java.math.BigDecimal;
import java.sql.Statement;
import java.util.List;

public class BankingServiceImpl implements BankingService {

    private final BankingDAO bankingDAO;

    // Initialize DAO implementation
    public BankingServiceImpl() {
        this.bankingDAO = new BankingDAOImpl();
    }

    // Adds a new customer after validating customer details
    @Override
    public void addCustomer(Customer customer) {

        if (!isValidCustomer(customer)) {
            return;
        }

        if (bankingDAO.addCustomer(customer)) {
            System.out.println("Customer added successfully.");
        } else {
            System.out.println("Failed to add customer.");
        }
    }

    // Retrieves and displays all customers
    @Override
    public void viewAllCustomers() {

        List<Customer> customers =
                bankingDAO.getAllCustomers();

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        for (Customer customer : customers) {
            System.out.println(customer);
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );
    }

    // Finds and displays customer details using customer ID
    @Override
    public void findCustomerById(int customerId) {

        if (customerId <= 0) {
            System.out.println(
                    "Customer ID must be greater than zero."
            );
            return;
        }

        Customer customer =
                bankingDAO.getCustomerById(customerId);

        if (customer == null) {
            System.out.println(
                    "Customer with ID "
                            + customerId
                            + " was not found."
            );
        } else {
            System.out.println(customer);
        }
    }

    // Updates existing customer details
    @Override
    public void updateCustomer(Customer customer) {

        if (customer.getCustomerId() <= 0) {
            System.out.println(
                    "Customer ID must be greater than zero."
            );
            return;
        }

        if (!isValidCustomer(customer)) {
            return;
        }

        if (bankingDAO.updateCustomer(customer)) {
            System.out.println(
                    "Customer updated successfully."
            );
        } else {
            System.out.println(
                    "Customer was not found or update failed."
            );
        }
    }

    // Deletes a customer using customer ID
    @Override
    public void deleteCustomer(int customerId) {

        if (customerId <= 0) {
            System.out.println(
                    "Customer ID must be greater than zero."
            );
            return;
        }

        if (bankingDAO.deleteCustomer(customerId)) {
            System.out.println(
                    "Customer deleted successfully."
            );
        } else {
            System.out.println(
                    "Customer was not found or cannot be deleted."
            );
        }
    }

    // Displays customer account details using JOIN query
    @Override
    public void viewCustomerAccountDetails() {
        bankingDAO.showCustomerAccountDetails();
    }
    // Displays customer-wise balance summary using aggregate query
    @Override
    public void viewCustomerAccountSummary() {
        bankingDAO.showCustomerAccountSummary();
    }

    // Executes deposit stored procedure
    @Override
    public void depositMoney(
            int accountId,
            BigDecimal amount) {

        if (!isValidAccountOperation(accountId, amount)) {
            return;
        }

        if (bankingDAO.depositMoney(accountId, amount)) {
            System.out.println(
                    "Deposit stored procedure executed successfully."
            );
        } else {
            System.out.println("Deposit failed.");
        }
    }

    // Executes withdrawal stored procedure
    @Override
    public void withdrawMoney(
            int accountId,
            BigDecimal amount) {

        if (!isValidAccountOperation(accountId, amount)) {
            return;
        }

        if (bankingDAO.withdrawMoney(accountId, amount)) {
            System.out.println(
                    "Withdrawal stored procedure executed successfully."
            );
        } else {
            System.out.println("Withdrawal failed.");
        }
    }

    // Transfers money between accounts using JDBC transaction handling
    @Override
    public void transferMoney(
            int sourceAccountId,
            int destinationAccountId,
            BigDecimal amount) {

        if (sourceAccountId <= 0 ||
                destinationAccountId <= 0) {

            System.out.println(
                    "Account IDs must be greater than zero."
            );
            return;
        }

        if (sourceAccountId == destinationAccountId) {
            System.out.println(
                    "Source and destination accounts "
                            + "cannot be the same."
            );
            return;
        }

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println(
                    "Transfer amount must be greater than zero."
            );
            return;
        }

        if (bankingDAO.transferMoney(
                sourceAccountId,
                destinationAccountId,
                amount)) {

            System.out.println(
                    "Money transferred successfully."
            );
        } else {
            System.out.println("Money transfer failed.");
        }
    }

    // Inserts multiple customers using JDBC batch processing
    @Override
    public void addCustomersBatch(
            List<Customer> customers) {

        if (customers == null || customers.isEmpty()) {
            System.out.println(
                    "No customers supplied for batch processing."
            );
            return;
        }

        int[] results =
                bankingDAO.addCustomersBatch(customers);

        int successfulRecords = 0;

        for (int result : results) {
            if (result > 0 ||
                    result == Statement.SUCCESS_NO_INFO) {

                successfulRecords++;
            }
        }

        System.out.println(
                successfulRecords
                        + " customer records processed successfully."
        );
    }

    // Validates mandatory customer information
    private boolean isValidCustomer(Customer customer) {

        if (customer == null) {
            System.out.println(
                    "Customer information cannot be null."
            );
            return false;
        }

        if (isBlank(customer.getCustomerName())) {
            System.out.println(
                    "Customer name is required."
            );
            return false;
        }

        if (isBlank(customer.getEmail())) {
            System.out.println(
                    "Customer email is required."
            );
            return false;
        }

        if (isBlank(customer.getMobile())) {
            System.out.println(
                    "Customer mobile number is required."
            );
            return false;
        }

        return true;
    }

    // Validates account ID and amount before banking operations
    private boolean isValidAccountOperation(
            int accountId,
            BigDecimal amount) {

        if (accountId <= 0) {
            System.out.println(
                    "Account ID must be greater than zero."
            );
            return false;
        }

        if (amount == null ||
                amount.compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println(
                    "Amount must be greater than zero."
            );
            return false;
        }

        return true;
    }

    // Checks whether a string is null or empty
    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}