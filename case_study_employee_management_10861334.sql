
-- Creted Employee DB

CREATE DATABASE employee_db;

---Use employee_db
USE employee_db;


-- Department Table


CREATE TABLE employee_department (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(50) NOT NULL
);

-- Address Table

CREATE TABLE employee_address (
    address_id INT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL
);


-- Employee Table


CREATE TABLE employee_details (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone CHAR(10),
    salary DECIMAL(12,2),
    department_id INT,
    address_id INT,

    FOREIGN KEY (department_id)
    REFERENCES employee_department(department_id),

    FOREIGN KEY (address_id)
    REFERENCES employee_address(address_id)
);


-- Insert Department Data

INSERT INTO employee_department(department_name)
VALUES
('IT'),
('Finance'),
('HR'),
('Operations');

-- Insert Address Data


INSERT INTO employee_address(city,country)
VALUES
('Delhi','India'),
('Pune','India'),
('Jabalpur','India'),
('Bhopal','India');

-- Insert Employee Data


INSERT INTO employee_details
(employee_name,email,phone,salary,department_id,address_id)
VALUES
('Rahul Verma','rahul.verma@gmail.com','9876543210',45000,1,2),
('Priya Sharma','priya.sharma@gmail.com','9876543211',55000,2,1),
('Aman Gupta','aman.gupta@gmail.com','9876543212',62000,1,3),
('Neha Singh','neha.singh@gmail.com','9876543213',70000,3,4),
('Vikas Yadav','vikas.yadav@gmail.com','9876543214',85000,4,2);


-- Display Data


SELECT * FROM employee_department;

SELECT * FROM employee_address;

SELECT * FROM employee_details;

-- JOIN Queries


-- Employee Details with Department and Address

SELECT e.employee_id,
       e.employee_name,
       e.salary,
       d.department_name,
       a.city,
       a.country
FROM employee_details e
INNER JOIN employee_department d
ON e.department_id = d.department_id
INNER JOIN employee_address a
ON e.address_id = a.address_id;

-- Employees belonging to IT Department

SELECT e.employee_name,
       d.department_name
FROM employee_details e
INNER JOIN employee_department d
ON e.department_id = d.department_id
WHERE d.department_name = 'IT';


-- Using Subqueries


-- Employees earning above average salary

SELECT employee_name,
       salary
FROM employee_details
WHERE salary >
(
   SELECT AVG(salary)
   FROM employee_details
);

-- Highest Salary

SELECT employee_name,
       salary
FROM employee_details
WHERE salary =
(
   SELECT MAX(salary)
   FROM employee_details
);

-- Second Highest Salary

SELECT MAX(salary) AS second_highest_salary
FROM employee_details
WHERE salary <
(
   SELECT MAX(salary)
   FROM employee_details
);


-- Stored Procedure 1
-- Fetch Employee Salary by emp id

DELIMITER $$

CREATE PROCEDURE fetchEmployeeSalary
(
 IN empId INT
)
BEGIN

SELECT employee_name,
       salary
FROM employee_details
WHERE employee_id = empId;

END $$

DELIMITER ;

CALL fetchEmployeeSalary(1);

-- Stored Procedure 2
-- Fetch Employee Name using IN and OUT


DELIMITER $$

CREATE PROCEDURE fetchEmployeeName
(
 IN empId INT,
 OUT empName VARCHAR(100)
)
BEGIN

SELECT employee_name
INTO empName
FROM employee_details
WHERE employee_id = empId;

END $$

DELIMITER ;

SET @empName='';

CALL fetchEmployeeName(1,@empName);

SELECT @empName;

-- ==========================================
-- Function
-- Get Annual Salary
-- ==========================================

DELIMITER $$

CREATE FUNCTION fetchAnnualSalary
(
 empId INT
)

RETURNS DECIMAL(12,2)

DETERMINISTIC

BEGIN

DECLARE annualSalary DECIMAL(12,2);

SELECT salary*12
INTO annualSalary
FROM employee_details
WHERE employee_id = empId;

RETURN annualSalary;

END $$

DELIMITER ;

-- Execute Function

SELECT employee_name,
       fetchAnnualSalary(employee_id) AS annual_salary
FROM employee_details;
