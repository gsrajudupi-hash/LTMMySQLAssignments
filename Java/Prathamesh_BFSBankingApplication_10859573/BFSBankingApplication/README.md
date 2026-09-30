# BFS Banking Application

Console-based Core Java 17 assignment demonstrating Java 8 functional programming and Java 17 language features.

## Requirements
- JDK 17
- Maven 3.8+
- Optional: MySQL 8+ for the supplied SQL database

## Run
```bash
mvn clean compile
mvn exec:java
```
Or run `com.bank.Main` from IntelliJ IDEA.

## Database
Run `database/bfs_banking_db.sql` in HeidiSQL or MySQL Workbench. It creates `bfs_banking_db` with 10 customers, 15 accounts, and 30 transactions. The console app is intentionally in-memory, so database setup is optional and demonstrates the requested schema/sample data.

## Features
OOP, sealed account hierarchy, records, pattern matching for instanceof, switch expressions, text blocks, lambdas, functional interface, Predicate, Consumer, Function, Supplier, Optional, method references, Date-Time API, custom exceptions, deposit/withdraw/transfer, analytics, reports, and all mandatory stream operations.

## Important
This is an educational sample. `double` is used because the assignment explicitly specifies it. Production banking software should normally use `BigDecimal`, persistence, authentication, audit controls, locking, and transactional integrity.
