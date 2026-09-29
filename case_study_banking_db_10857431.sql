CREATE DATABASE banking_db;

USE banking_db;

-- =========================================================
--  CUSTOMER TABLE
-- Stores customer personal and contact information
-- =========================================================

CREATE TABLE customer
(
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    mobile VARCHAR(15) NOT NULL UNIQUE,
    city VARCHAR(50)
);

-- ======================================================
-- Branch TABLE --
-- Stores bank branch information
-- ======================================================

CREATE TABLE branch
(
    branch_id INT PRIMARY KEY AUTO_INCREMENT,
    branch_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    ifsc_code VARCHAR(20) NOT NULL UNIQUE
);

-- =========================================================
-- ACCOUNT TABLE
-- Each account belongs to one customer and one branch
-- One customer can have multiple accounts
-- =========================================================

CREATE TABLE account
(
    account_id INT PRIMARY KEY AUTO_INCREMENT,

    customer_id INT NOT NULL,

    branch_id INT NOT NULL,

    account_number VARCHAR(20) NOT NULL UNIQUE,

    account_type VARCHAR(20)NOT NULL ,

    balance DECIMAL(12,2) NOT NULL DEFAULT 0.0,

    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    opened_date DATE NOT NULL,

    FOREIGN KEY (customer_id)
        REFERENCES customer(customer_id),

    FOREIGN KEY (branch_id)
        REFERENCES branch(branch_id)
);


-- =====================================================================
-- 4. TRANSACTIONS TABLE
-- TRANSACTION is a reserved keyword in MySQL. SO name it as Transactions
-- ======================================================================

CREATE TABLE transactions
(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,

    account_id INT NOT NULL,

    transaction_type VARCHAR(20),

    amount DECIMAL(12,2),

    transaction_date DATETIME,

    remarks VARCHAR(200),

    FOREIGN KEY (account_id)
        REFERENCES account(account_id)
);

DESC customer;

DESC branch;

DESC account;

DESC transactions;

INSERT INTO branch
(branch_name, city, ifsc_code)
VALUES
('SBI Hyderabad', 'Hyderabad', 'SBI0001'),
('SBI Mumbai', 'Mumbai', 'SBI0002');

SELECT * FROM branch;

INSERT INTO customer
(customer_name, email, mobile, city)
VALUES
('Chaitanya', 'chai@gmail.com', '9876543210', 'Hyderabad'),

('Triveni', 'triveni@gmail.com', '9876543211', 'Mumbai'),

('Mokshi', 'mokshi@gmail.com', '9876543212', 'Bangalore');

SELECT * FROM customer;

INSERT INTO account
(
customer_id,
branch_id,
account_number,
account_type,
balance,
status,
opened_date
)
VALUES

(1,1,'ACC10001','Savings',50000,'ACTIVE','2025-01-01'),

(1,1,'ACC10002','Current',75000,'ACTIVE','2025-03-15'),

(2,2,'ACC10003','Savings',20000,'ACTIVE','2025-02-10'),

(3,1,'ACC10004','Savings',150000,'ACTIVE','2025-04-05');

SELECT * FROM ACCOUNT;


INSERT INTO transactions
(
account_id,
transaction_type,
amount,
transaction_date,
remarks
)
VALUES

(1,'DEPOSIT',5000,NOW(),'Cash Deposit'),

(1,'WITHDRAW',1000,NOW(),'ATM Withdrawal'),

(2,'DEPOSIT',15000,NOW(),'Online Transfer'),

(3,'WITHDRAW',500,NOW(),'UPI Payment'),

(4,'DEPOSIT',25000,NOW(),'Salary Credit');

SELECT * FROM transactions;

-- Join Queries --

-- 1 Customer With Accounts

SELECT
c.customer_name,
a.account_number,
a.balance
FROM customer c
JOIN account a
ON c.customer_id = a.customer_id;

-- 2 Customer + Account + Branch

SELECT
c.customer_name,
a.account_number,
a.balance,
b.branch_name
FROM customer c
JOIN account a
ON c.customer_id = a.customer_id
JOIN branch b
ON a.branch_id = b.branch_id;

-- 3 Customer Transactions

SELECT
c.customer_name,
t.transaction_type,
t.amount
FROM customer c
JOIN account a
ON c.customer_id = a.customer_id
JOIN transactions t
ON a.account_id = t.account_id;

-- Aggregate Queries--

-- Total Balance Per Customer --

SELECT
c.customer_name,
SUM(a.balance) total_balance
FROM customer c
JOIN account a
ON c.customer_id = a.customer_id
GROUP BY c.customer_name;



-- Top Customer

SELECT
c.customer_name,
SUM(a.balance) total_balance
FROM customer c
JOIN account a
ON c.customer_id = a.customer_id
GROUP BY c.customer_name
ORDER BY total_balance DESC
LIMIT 1;


-- Subqueries --

-- Balance Greater Than Average --
SELECT *
FROM account
WHERE balance >
(
    SELECT AVG(balance)
    FROM account
);


-- Maximum Balance

SELECT *
FROM account
WHERE balance =
(
    SELECT MAX(balance)
    FROM account
);

-- Customers Without Account
SELECT *
FROM customer
WHERE customer_id NOT IN
(
    SELECT customer_id
    FROM account
);

-- Function --

-- Interest Calculation
DELIMITER $$

CREATE FUNCTION calculate_interest
(
    p_balance DECIMAL(12,2)
)
RETURNS DECIMAL(12,2)

DETERMINISTIC

BEGIN

    RETURN p_balance * 0.05;

END $$

DELIMITER ;

-- Usage
SELECT
account_number,
balance,
calculate_interest(balance) AS yearly_interest
FROM ACCOUNT;


-- Procedure 1: Deposit
DELIMITER $$

CREATE PROCEDURE deposit_money
(
    IN p_account_id INT,
    IN p_amount DECIMAL(12,2)
)
BEGIN

    UPDATE account
    SET balance = balance + p_amount
    WHERE account_id = p_account_id;

END $$

DELIMITER ;

-- usage
CALL deposit_money(1,5000);

-- Procedure 2: Withdraw --
DELIMITER $$

CREATE PROCEDURE withdraw_money
(
    IN p_account_id INT,
    IN p_amount DECIMAL(12,2)
)
BEGIN

    UPDATE account
    SET balance = balance - p_amount
    WHERE account_id = p_account_id
    AND balance >= p_amount;

END $$

DELIMITER ;

-- usage
CALL withdraw_money(1,2000);

-- Procedure 3: Transfer Money
-- usage
DELIMITER $$

CREATE PROCEDURE transfer_money
(
    IN p_source_account INT,
    IN p_destination_account INT,
    IN p_amount DECIMAL(12,2)
)
BEGIN

    UPDATE account
    SET balance = balance - p_amount
    WHERE account_id = p_source_account;

    UPDATE account
    SET balance = balance + p_amount
    WHERE account_id = p_destination_account;

END $$

DELIMITER ;

-- usage
CALL transfer_money(1,2,1000);

