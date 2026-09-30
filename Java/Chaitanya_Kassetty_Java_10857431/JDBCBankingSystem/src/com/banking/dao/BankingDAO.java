package com.banking.dao;

import com.banking.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface BankingDAO {

    // Customer CRUD operations
    boolean addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(int customerId);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(int customerId);

    // Join query
    void showCustomerAccountDetails();

    // Aggregate query
    void showCustomerAccountSummary();

    // Stored procedures
    boolean depositMoney(int accountId, BigDecimal amount);

    boolean withdrawMoney(int accountId, BigDecimal amount);

    // JDBC transaction
    boolean transferMoney(
            int sourceAccountId,
            int destinationAccountId,
            BigDecimal amount
    );

    // Batch processing
    int[] addCustomersBatch(List<Customer> customers);
}