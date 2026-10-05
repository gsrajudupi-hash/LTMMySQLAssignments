# BFS Banking Application

Java 17 Maven console project demonstrating Java 8 functional programming and Java 17 features, with MySQL/JDBC support.

## Setup
1. Install JDK 17, Maven and MySQL 8.
2. Execute `database/schema.sql`, then `database/sample_data.sql`.
3. Update `src/main/resources/application.properties`.
4. Run `mvn clean compile` and `mvn exec:java`.

The console uses in-memory sample data so every menu works immediately. `DBConnection` and `BankingDao` demonstrate MySQL persistence and an atomic JDBC transfer. MySQL Connector/J is included in `pom.xml`, preventing `No suitable driver` when Maven is used.

Includes 10 customers, 15 accounts, 30 generated in-memory transactions and 30 SQL transactions. Features include OOP, lambdas, functional interface, Predicate, Consumer, Function, Supplier, Optional, streams, Date/Time, records, sealed classes, pattern matching, switch expressions, text blocks, validation and custom exceptions.
