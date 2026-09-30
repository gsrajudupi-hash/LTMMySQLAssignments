# BFSI Customer Account Management System

## Project Overview

The BFSI Customer Account Management System is a console-based banking application developed using Core Java 17. The project simulates real-world banking operations such as customer management, account management, transaction processing, reporting, and analytics while demonstrating Java 8 Functional Programming features and Java 17 language enhancements.

This solution follows Object-Oriented Programming principles and maintains a modular structure for better readability, maintainability, and scalability.

---

## Business Scenario

A Banking & Financial Services organization requires an application to manage:

- Customer Information
- Bank Accounts
- Transaction Processing
- Banking Operations
- Analytical Reporting

The application provides a practical implementation of modern Java concepts in a banking domain.

---

## Key Features

### Customer Management

- Add Customer
- Search Customer
- Display All Customers

### Account Management

- Create Savings Account
- Create Current Account
- Create Loan Account
- Display All Accounts
- Balance Inquiry

### Transaction Management

- Deposit Money
- Withdraw Money
- Transfer Funds
- Transaction History

### Reporting & Analytics

- Customer Report
- Transaction Report
- Account Type Report
- Banking Analytics Dashboard
- Account Statement Generation

---

## Technologies Used

- Java 17
- Core Java
- Collections Framework
- Java Stream API
- Functional Programming Concepts
- Java Date & Time API

---

## Java 8 Features Implemented

### Functional Programming

- Functional Interface
- Lambda Expressions
- Method References

### Functional Interfaces

- Predicate
- Consumer
- Function
- Supplier

### Stream API Operations

- filter()
- map()
- flatMap()
- sorted()
- distinct()
- limit()
- skip()
- count()
- min()
- max()
- reduce()
- collect()
- groupingBy()
- partitioningBy()
- joining()
- summingDouble()

### Additional Features

- Optional
- Date & Time API

---

## Java 17 Features Implemented

### Records

- CustomerRecord
- TransactionRecord
- AccountSummary

### Sealed Classes

- BankAccount
- SavingsAccount
- CurrentAccount
- LoanAccount

### Pattern Matching

Used with instanceof for account hierarchy processing.

### Switch Expressions

Used for transaction classification.

### Text Blocks

Used for:

- Account Statements
- Customer JSON Generation
- Banking Reports

---

## Project Structure

```text
com.bank
│
├── Main.java
├── SampleData.java
│
├── model
│   ├── Customer.java
│   ├── BankAccount.java
│   ├── SavingsAccount.java
│   ├── CurrentAccount.java
│   ├── LoanAccount.java
│   └── Transaction.java
│
├── service
│   └── BankingService.java
│
├── util
│   └── BankingReport.java
│
├── functional
│   └── BankingOperation.java
│
├── record
│   ├── CustomerRecord.java
│   ├── TransactionRecord.java
│   └── AccountSummary.java
│
└── exception
    ├── InvalidAccountException.java
    ├── CustomerNotFoundException.java
    ├── InvalidTransactionException.java
    └── InsufficientBalanceException.java
    
