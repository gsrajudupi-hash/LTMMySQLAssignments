DROP DATABASE IF EXISTS bfs_banking_db;

CREATE DATABASE bfs_banking_db;

USE bfs_banking_db;

CREATE TABLE customers(customer_id INT PRIMARY KEY,name VARCHAR(80) NOT NULL,email VARCHAR(120) NOT NULL UNIQUE,city VARCHAR(60) NOT NULL,phone VARCHAR(15) NOT NULL UNIQUE,customer_type ENUM('PREMIUM','REGULAR') NOT NULL);

CREATE TABLE bank_accounts(account_number BIGINT PRIMARY KEY,customer_id INT NOT NULL,account_type ENUM('SAVINGS','CURRENT','LOAN') NOT NULL,balance DECIMAL(15,2) NOT NULL CHECK(balance>=0),status ENUM('ACTIVE','INACTIVE','BLOCKED') NOT NULL,FOREIGN KEY(customer_id) REFERENCES customers(customer_id));

CREATE TABLE transactions(transaction_id INT PRIMARY KEY,account_number BIGINT NOT NULL,transaction_type ENUM('DEPOSIT','WITHDRAW','TRANSFER','INTEREST','LOAN_PAYMENT') NOT NULL,amount DECIMAL(15,2) NOT NULL CHECK(amount>0),transaction_date DATETIME NOT NULL,description VARCHAR(255),FOREIGN KEY(account_number) REFERENCES bank_accounts(account_number));

INSERT INTO customers VALUES
(101,'Rahul','rahul@gmail.com','Bangalore','9876543210','PREMIUM'),
(102,'Priya','priya@gmail.com','Mangalore','9876543211','REGULAR'),
(103,'Arun','arun@gmail.com','Mysore','9876543212','PREMIUM'),
(104,'Sneha','sneha@gmail.com','Udupi','9876543213','REGULAR'),
(105,'Kiran','kiran@gmail.com','Bangalore','9876543214','PREMIUM'),
(106,'Anita','anita@gmail.com','Bangalore','9876543215','PREMIUM'),
(107,'Ramesh','ramesh@gmail.com','Udupi','9876543216','REGULAR'),
(108,'Deepa','deepa@gmail.com','Mysore','9876543217','PREMIUM'),
(109,'Suresh','suresh@gmail.com','Mangalore','9876543218','REGULAR'),
(110,'Meena','meena@gmail.com','Bangalore','9876543219','PREMIUM');

INSERT INTO bank_accounts VALUES
(100001,101,'SAVINGS',85000.00,'ACTIVE'),
(100002,102,'CURRENT',150000.00,'ACTIVE'),
(100003,103,'SAVINGS',95000.00,'ACTIVE'),
(100004,104,'SAVINGS',65000.00,'ACTIVE'),
(100005,105,'CURRENT',125000.00,'ACTIVE'),
(100006,106,'SAVINGS',180000.00,'ACTIVE'),
(100007,107,'CURRENT',85000.00,'ACTIVE'),
(100008,108,'SAVINGS',220000.00,'ACTIVE'),
(100009,109,'CURRENT',95000.00,'ACTIVE'),
(100010,110,'SAVINGS',125000.00,'ACTIVE'),
(100011,101,'LOAN',50000.00,'ACTIVE'),
(100012,102,'SAVINGS',40000.00,'ACTIVE'),
(100013,103,'CURRENT',110000.00,'ACTIVE'),
(100014,104,'LOAN',30000.00,'ACTIVE'),
(100015,105,'SAVINGS',70000.00,'ACTIVE');

INSERT INTO transactions VALUES
(2001,100001,'DEPOSIT',5000.00,'2026-09-01 10:00:00','Sample deposit'),
(2002,100002,'WITHDRAW',7750.00,'2026-09-02 10:01:00','Sample withdraw'),
(2003,100003,'DEPOSIT',10500.00,'2026-09-03 10:02:00','Sample deposit'),
(2004,100004,'TRANSFER',13250.00,'2026-09-04 10:03:00','Sample transfer'),
(2005,100005,'INTEREST',16000.00,'2026-09-05 10:04:00','Sample interest'),
(2006,100006,'DEPOSIT',18750.00,'2026-09-06 10:05:00','Sample deposit'),
(2007,100007,'WITHDRAW',21500.00,'2026-09-07 10:06:00','Sample withdraw'),
(2008,100008,'DEPOSIT',24250.00,'2026-09-08 10:07:00','Sample deposit'),
(2009,100009,'TRANSFER',27000.00,'2026-09-09 10:08:00','Sample transfer'),
(2010,100010,'INTEREST',29750.00,'2026-09-10 10:09:00','Sample interest'),
(2011,100011,'DEPOSIT',32500.00,'2026-09-11 10:10:00','Sample deposit'),
(2012,100012,'WITHDRAW',35250.00,'2026-09-12 10:11:00','Sample withdraw'),
(2013,100013,'DEPOSIT',38000.00,'2026-09-13 10:12:00','Sample deposit'),
(2014,100014,'TRANSFER',40750.00,'2026-09-14 10:13:00','Sample transfer'),
(2015,100015,'INTEREST',43500.00,'2026-09-15 10:14:00','Sample interest'),
(2016,100001,'DEPOSIT',46250.00,'2026-09-16 10:15:00','Sample deposit'),
(2017,100002,'WITHDRAW',49000.00,'2026-09-17 10:16:00','Sample withdraw'),
(2018,100003,'DEPOSIT',51750.00,'2026-09-18 10:17:00','Sample deposit'),
(2019,100004,'TRANSFER',54500.00,'2026-09-19 10:18:00','Sample transfer'),
(2020,100005,'INTEREST',57250.00,'2026-09-20 10:19:00','Sample interest'),
(2021,100006,'DEPOSIT',60000.00,'2026-09-21 10:20:00','Sample deposit'),
(2022,100007,'WITHDRAW',62750.00,'2026-09-22 10:21:00','Sample withdraw'),
(2023,100008,'DEPOSIT',65500.00,'2026-09-23 10:22:00','Sample deposit'),
(2024,100009,'TRANSFER',68250.00,'2026-09-24 10:23:00','Sample transfer'),
(2025,100010,'INTEREST',71000.00,'2026-09-25 10:24:00','Sample interest'),
(2026,100011,'DEPOSIT',73750.00,'2026-09-26 10:25:00','Sample deposit'),
(2027,100012,'WITHDRAW',76500.00,'2026-09-27 10:26:00','Sample withdraw'),
(2028,100013,'DEPOSIT',79250.00,'2026-09-28 10:27:00','Sample deposit'),
(2029,100014,'TRANSFER',82000.00,'2026-09-01 10:28:00','Sample transfer'),
(2030,100015,'INTEREST',84750.00,'2026-09-02 10:29:00','Sample interest');

CREATE OR REPLACE VIEW customer_total_balance AS SELECT c.customer_id,c.name,SUM(a.balance) total_balance FROM customers c JOIN bank_accounts a ON c.customer_id=a.customer_id GROUP BY c.customer_id,c.name;

SELECT * FROM customer_total_balance ORDER BY total_balance DESC;