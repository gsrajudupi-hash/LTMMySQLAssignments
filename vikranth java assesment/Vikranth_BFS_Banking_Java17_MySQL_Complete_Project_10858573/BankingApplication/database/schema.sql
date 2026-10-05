DROP DATABASE IF EXISTS bfs_bank; CREATE DATABASE bfs_bank; USE bfs_bank;
CREATE TABLE customers(customer_id INT PRIMARY KEY,name VARCHAR(100) NOT NULL,email VARCHAR(120) UNIQUE NOT NULL,city VARCHAR(80),phone VARCHAR(15) UNIQUE,customer_type ENUM('PREMIUM','REGULAR') NOT NULL);
CREATE TABLE accounts(account_number BIGINT PRIMARY KEY,customer_id INT NOT NULL,account_type ENUM('SAVINGS','CURRENT','LOAN') NOT NULL,balance DECIMAL(15,2) NOT NULL,status ENUM('ACTIVE','INACTIVE','BLOCKED') DEFAULT 'ACTIVE',FOREIGN KEY(customer_id) REFERENCES customers(customer_id));
CREATE TABLE transactions(transaction_id INT PRIMARY KEY,account_number BIGINT NOT NULL,transaction_type ENUM('DEPOSIT','WITHDRAW','TRANSFER','INTEREST','LOAN_PAYMENT') NOT NULL,amount DECIMAL(15,2) NOT NULL,transaction_date DATETIME NOT NULL,description VARCHAR(255),FOREIGN KEY(account_number) REFERENCES accounts(account_number));
DELIMITER //
CREATE PROCEDURE GetAccountTransactions(IN p_account BIGINT) BEGIN SELECT * FROM transactions WHERE account_number=p_account ORDER BY transaction_date DESC; END//
CREATE FUNCTION CustomerTotalBalance(p_customer INT) RETURNS DECIMAL(15,2) DETERMINISTIC READS SQL DATA BEGIN DECLARE t DECIMAL(15,2);SELECT COALESCE(SUM(balance),0) INTO t FROM accounts WHERE customer_id=p_customer;RETURN t;END//
DELIMITER ;