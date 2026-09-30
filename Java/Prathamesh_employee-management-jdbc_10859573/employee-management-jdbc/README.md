# Employee Management JDBC Assessment

Complete Java 17 + JDBC + MySQL console application using Model-DAO-Service architecture.

## 1. Prerequisites
- JDK 17
- MySQL 8.x
- Maven 3.x
- IntelliJ IDEA

## 2. Database
Use your previous `employee_management` database. Compare its table/column names with `sql/assessment_support.sql`. Run only the missing table/procedure statements in HeidiSQL. The Java application never creates the database schema.

## 3. Configure
Edit `src/main/resources/db.properties` and set the MySQL username/password.

## 4. IntelliJ
Open the folder as a Maven project, allow Maven to download dependencies, set Project SDK to 17, and run `EmployeeManagementApp.main()`.

Command line alternative:
```bash
mvn clean compile
mvn exec:java
```

## 5. Requirements covered
- CRUD and primary-key lookup
- More than five PreparedStatement operations
- Parameterized salary query
- JOIN query and aggregate GROUP BY query
- Two CallableStatement stored procedures
- Manual commit/rollback transactions
- Batch processing with addBatch/executeBatch
- try-with-resources for Connection, Statement and ResultSet
- Model, DAO, Service and application layers
- Friendly handling for bad credentials, unavailable DB, duplicate values, FK violations, SQL syntax, invalid input and record-not-found

## Important schema adaptation
If your previous assessment used names such as `employees`, `departments`, `emp_id`, or different columns, modify the SQL constants and ResultSet column labels in `EmployeeDaoImpl`. Do not run a second conflicting schema.
