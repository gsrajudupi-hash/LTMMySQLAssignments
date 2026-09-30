package com.banking.service;

import com.banking.model.Customer;

import java.math.BigDecimal;
import java.util.List;

public interface BankingService {

    void addCustomer(Customer customer);

    void viewAllCustomers();

    void findCustomerById(int customerId);

    void updateCustomer(Customer customer);

    void deleteCustomer(int customerId);

    void viewCustomerAccountDetails();

    void viewCustomerAccountSummary();

    void depositMoney(int accountId, BigDecimal amount);

    void withdrawMoney(int accountId, BigDecimal amount);

    void transferMoney(
            int sourceAccountId,
            int destinationAccountId,
            BigDecimal amount
    );

    void addCustomersBatch(List<Customer> customers);
}