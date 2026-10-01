package com.banking.dao;

import com.banking.model.Customer;
import com.banking.util.DBConnection;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankingDAOImpl implements BankingDAO {

    // 1. INSERT using PreparedStatement
    @Override
    public boolean addCustomer(Customer customer) {

        String sql = """
                INSERT INTO customer
                (customer_name, email, mobile, city)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getMobile());
            statement.setString(4, customer.getCity());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // 2. RETRIEVE ALL using PreparedStatement
    @Override
    public List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        String sql = """
                SELECT customer_id,
                       customer_name,
                       email,
                       mobile,
                       city
                FROM customer
                ORDER BY customer_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                customers.add(mapCustomer(resultSet));
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }

        return customers;
    }

    // 3. FIND BY PRIMARY KEY using PreparedStatement
    @Override
    public Customer getCustomerById(int customerId) {

        String sql = """
                SELECT customer_id,
                       customer_name,
                       email,
                       mobile,
                       city
                FROM customer
                WHERE customer_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return mapCustomer(resultSet);
                }
            }

        } catch (SQLException e) {
            handleSQLException(e);
        }

        return null;
    }

    // 4. UPDATE using PreparedStatement
    @Override
    public boolean updateCustomer(Customer customer) {

        String sql = """
                UPDATE customer
                SET customer_name = ?,
                    email = ?,
                    mobile = ?,
                    city = ?
                WHERE customer_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getMobile());
            statement.setString(4, customer.getCity());
            statement.setInt(5, customer.getCustomerId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // 5. DELETE using PreparedStatement
    @Override
    public boolean deleteCustomer(int customerId) {

        String sql = """
                DELETE FROM customer
                WHERE customer_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // JOIN QUERY
    @Override
    public void showCustomerAccountDetails() {

        String sql = """
                SELECT c.customer_id,
                       c.customer_name,
                       a.account_number,
                       a.account_type,
                       a.balance,
                       b.branch_name
                FROM customer c
                JOIN account a
                    ON c.customer_id = a.customer_id
                JOIN branch b
                    ON a.branch_id = b.branch_id
                ORDER BY c.customer_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println(
                    "--------------------------------------------------------------------------"
            );

            System.out.printf(
                    "%-5s | %-15s | %-12s | %-10s | %-12s | %-15s%n",
                    "ID",
                    "Customer",
                    "Account No.",
                    "Type",
                    "Balance",
                    "Branch"
            );

            System.out.println(
                    "--------------------------------------------------------------------------"
            );

            boolean recordFound = false;

            while (resultSet.next()) {
                recordFound = true;

                System.out.printf(
                        "%-5d | %-15s | %-12s | %-10s | %-12.2f | %-15s%n",
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("account_number"),
                        resultSet.getString("account_type"),
                        resultSet.getBigDecimal("balance"),
                        resultSet.getString("branch_name")
                );
            }

            if (!recordFound) {
                System.out.println("No customer account records found.");
            }

            System.out.println(
                    "--------------------------------------------------------------------------"
            );

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // AGGREGATE QUERY
    @Override
    public void showCustomerAccountSummary() {

        String sql = """
                SELECT c.customer_id,
                       c.customer_name,
                       COUNT(a.account_id) AS total_accounts,
                       COALESCE(SUM(a.balance), 0) AS total_balance,
                       COALESCE(AVG(a.balance), 0) AS average_balance
                FROM customer c
                LEFT JOIN account a
                    ON c.customer_id = a.customer_id
                GROUP BY c.customer_id, c.customer_name
                ORDER BY c.customer_id
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println(
                    "-------------------------------------------------------------------"
            );

            System.out.printf(
                    "%-5s | %-15s | %-10s | %-13s | %-13s%n",
                    "ID",
                    "Customer",
                    "Accounts",
                    "Total Balance",
                    "Avg Balance"
            );

            System.out.println(
                    "-------------------------------------------------------------------"
            );

            while (resultSet.next()) {
                System.out.printf(
                        "%-5d | %-15s | %-10d | %-13.2f | %-13.2f%n",
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getInt("total_accounts"),
                        resultSet.getBigDecimal("total_balance"),
                        resultSet.getBigDecimal("average_balance")
                );
            }

            System.out.println(
                    "-------------------------------------------------------------------"
            );

        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    // STORED PROCEDURE 1
    @Override
    public boolean depositMoney(int accountId, BigDecimal amount) {

        String sql = "{CALL deposit_money(?, ?)}";

        try (Connection connection = DBConnection.getConnection();
             CallableStatement statement =
                     connection.prepareCall(sql)) {

            statement.setInt(1, accountId);
            statement.setBigDecimal(2, amount);
            statement.execute();

            return true;

        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // STORED PROCEDURE 2
    @Override
    public boolean withdrawMoney(int accountId, BigDecimal amount) {

        String sql = "{CALL withdraw_money(?, ?)}";

        try (Connection connection = DBConnection.getConnection();
             CallableStatement statement =
                     connection.prepareCall(sql)) {

            statement.setInt(1, accountId);
            statement.setBigDecimal(2, amount);
            statement.execute();

            return true;

        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    // JDBC TRANSACTION
    @Override
    public boolean transferMoney(
            int sourceAccountId,
            int destinationAccountId,
            BigDecimal amount) {

        String debitSql = """
                UPDATE account
                SET balance = balance - ?
                WHERE account_id = ?
                  AND balance >= ?
                  AND status = 'ACTIVE'
                """;

        String creditSql = """
                UPDATE account
                SET balance = balance + ?
                WHERE account_id = ?
                  AND status = 'ACTIVE'
                """;

        String transactionSql = """
                INSERT INTO transactions
                (account_id, transaction_type, amount,
                 transaction_date, remarks)
                VALUES (?, ?, ?, NOW(), ?)
                """;

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            int debitRows;

            // Debit source account
            try (PreparedStatement debitStatement =
                         connection.prepareStatement(debitSql)) {

                debitStatement.setBigDecimal(1, amount);
                debitStatement.setInt(2, sourceAccountId);
                debitStatement.setBigDecimal(3, amount);

                debitRows = debitStatement.executeUpdate();
            }

            if (debitRows == 0) {
                connection.rollback();

                System.out.println(
                        "Transfer failed. Source account does not exist, "
                                + "is inactive, or has insufficient balance."
                );

                return false;
            }

            int creditRows;

            // Credit destination account
            try (PreparedStatement creditStatement =
                         connection.prepareStatement(creditSql)) {

                creditStatement.setBigDecimal(1, amount);
                creditStatement.setInt(2, destinationAccountId);

                creditRows = creditStatement.executeUpdate();
            }

            if (creditRows == 0) {
                connection.rollback();

                System.out.println(
                        "Transfer failed. Destination account does not "
                                + "exist or is inactive."
                );

                return false;
            }

            // Batch insert two transaction history records
            try (PreparedStatement transactionStatement =
                         connection.prepareStatement(transactionSql)) {

                transactionStatement.setInt(1, sourceAccountId);
                transactionStatement.setString(2, "WITHDRAW");
                transactionStatement.setBigDecimal(3, amount);
                transactionStatement.setString(
                        4,
                        "Transfer to account " + destinationAccountId
                );
                transactionStatement.addBatch();

                transactionStatement.setInt(1, destinationAccountId);
                transactionStatement.setString(2, "DEPOSIT");
                transactionStatement.setBigDecimal(3, amount);
                transactionStatement.setString(
                        4,
                        "Transfer from account " + sourceAccountId
                );
                transactionStatement.addBatch();

                transactionStatement.executeBatch();
            }

            connection.commit();
            return true;

        } catch (SQLException e) {

            if (connection != null) {
                try {
                    connection.rollback();
                    System.err.println(
                            "Transaction rolled back successfully."
                    );
                } catch (SQLException rollbackException) {
                    System.err.println(
                            "Rollback failed: "
                                    + rollbackException.getMessage()
                    );
                }
            }

            handleSQLException(e);
            return false;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println(
                            "Connection closing error: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }

    // SEPARATE BATCH PROCESSING DEMONSTRATION
    @Override
    public int[] addCustomersBatch(List<Customer> customers) {

        String sql = """
                INSERT INTO customer
                (customer_name, email, mobile, city)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Customer customer : customers) {

                statement.setString(
                        1,
                        customer.getCustomerName()
                );

                statement.setString(
                        2,
                        customer.getEmail()
                );

                statement.setString(
                        3,
                        customer.getMobile()
                );

                statement.setString(
                        4,
                        customer.getCity()
                );

                statement.addBatch();
            }

            return statement.executeBatch();

        } catch (SQLException e) {
            handleSQLException(e);
            return new int[0];
        }
    }

    private Customer mapCustomer(ResultSet resultSet)
            throws SQLException {

        return new Customer(
                resultSet.getInt("customer_id"),
                resultSet.getString("customer_name"),
                resultSet.getString("email"),
                resultSet.getString("mobile"),
                resultSet.getString("city")
        );
    }

    private void handleSQLException(SQLException e) {

        String sqlState = e.getSQLState();

        if (sqlState == null) {
            System.err.println(
                    "Database error: " + e.getMessage()
            );
        } else if (sqlState.startsWith("23")) {
            System.err.println(
                    "Constraint violation. Check duplicate email, "
                            + "mobile number, or foreign-key reference."
            );
        } else if (sqlState.startsWith("28")) {
            System.err.println(
                    "Database authentication failed. "
                            + "Check username and password."
            );
        } else if (sqlState.startsWith("08")) {
            System.err.println(
                    "Unable to connect to MySQL. "
                            + "Check whether MySQL Server is running."
            );
        } else if (sqlState.startsWith("42")) {
            System.err.println(
                    "SQL syntax or database object error."
            );
        } else {
            System.err.println(
                    "Database operation failed: " + e.getMessage()
            );
        }

        System.err.println("SQL State: " + e.getSQLState());
        System.err.println("Error Code: " + e.getErrorCode());
    }
}