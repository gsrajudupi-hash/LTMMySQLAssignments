-- Creating a database
CREATE DATABASE employee_management;

-- Using the database
USE employee_management;

-- Create the Department Table 
CREATE TABLE departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(50) NOT NULL UNIQUE,
    location VARCHAR(50) NOT NULL,
    dept_head VARCHAR(100),
    budget DECIMAL(12,2),
    phone VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    established_date DATE,
    status ENUM('Active','Inactive') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
               ON UPDATE CURRENT_TIMESTAMP
);

-- Insert some data in Departments Table

INSERT INTO departments
(dept_name, location, dept_head, budget, phone, email, established_date)
VALUES
('IT', 'Kolkata', 'Amit Sharma', 5000000,
 '9876543210', 'it@company.com', '2018-01-10'),

('HR', 'Hyderabad', 'Priya Nair', 3000000,
 '9876543211', 'hr@company.com', '2019-03-15'),

('Finance', 'Mumbai', 'Ravi Kumar', 4000000,
 '9876543212', 'finance@company.com', '2017-06-20');
 
-- Create the table employees
 
 CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    gender ENUM('Male','Female','Other'),
    dob DATE,
    hire_date DATE NOT NULL,
    job_title VARCHAR(50),
    salary DECIMAL(10,2) NOT NULL,
    commission DECIMAL(10,2) DEFAULT 0,
    status ENUM('Active','Inactive') DEFAULT 'Active',
    dept_id INT NOT NULL,
    manager_id INT,
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (dept_id) REFERENCES departments(dept_id),
);

-- Insert Data in Employees Table

INSERT INTO employees
(first_name, last_name, email, phone, gender, dob,
 hire_date, job_title, salary, commission,
 status, dept_id, address)
VALUES

('Amit', 'Sharma', 'amit@company.com', '9876543210',
 'Male', '1985-03-12', '2018-01-10',
 'IT Manager', 95000, 5000,
 'Active', 1, 'Kolkata'),

('Priya', 'Nair', 'priya@company.com', '9876543211',
 'Female', '1990-07-25', '2019-03-15',
 'HR Manager', 85000, 4000,
 'Active', 2, 'Hyderabad'),

('Ravi', 'Kumar', 'ravi@company.com', '9876543212',
 'Male', '1988-11-08', '2020-05-20',
 'Senior Developer', 70000, 3000,
 'Active', 1, 'Kolkata'),

('Sneha', 'Patel', 'sneha@company.com', '9876543213',
 'Female', '1992-05-18', '2021-07-12',
 'Software Engineer', 60000, 2000,
 'Active', 1, 'Bangalore'),

('Arjun', 'Mehta', 'arjun@company.com', '9876543214',
 'Male', '1995-09-09', '2022-02-14',
 'HR Executive', 45000, 1500,
 'Active', 2, 'Hyderabad'),

('Neha', 'Singh', 'neha@company.com', '9876543215',
 'Female', '1994-01-22', '2022-11-01',
 'Accountant', 50000, 1800,
 'Active', 3, 'Mumbai');
 
 -- Create a Projects Table
 CREATE TABLE projects (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    start_date DATE NOT NULL,
    end_date DATE,
    budget DECIMAL(12,2),
    status ENUM('Planned','In Progress','Completed') DEFAULT 'Planned',
    dept_id INT NOT NULL,

    FOREIGN KEY (dept_id) REFERENCES departments(dept_id)
);

-- Insert Data In Projects
INSERT INTO projects
(project_name, description, start_date, end_date, budget, status, dept_id)
VALUES

('Employee Portal','Employee management application','2024-01-15','2024-08-30',500000,'Completed',1),
('Attendance Management System','Track employee attendance and leave','2024-02-01','2024-09-30',
 350000,'In Progress',1),
('Recruitment Portal','Online hiring and interview management','2024-03-01','2024-12-31',300000,
 'In Progress',2),
('Employee Training System','Training and certification platform','2024-04-10','2024-10-15',
 250000,'Completed',2),
('Payroll Management System','Salary and payroll processing','2024-01-20','2024-07-31',400000,'Completed',
 3),
('Expense Tracking System', 'Track employee reimbursements and expenses','2024-05-01',NULL,200000,
 'Planned',3);
 -- create table employee_projects
 CREATE TABLE employee_projects (
    emp_id INT,
    project_id INT,
    role_name VARCHAR(50),
    assigned_date DATE,
    PRIMARY KEY(emp_id, project_id),
    FOREIGN KEY (emp_id) REFERENCES employees(emp_id),
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
);
-- Insert Data in Employee Projects
INSERT INTO employee_projects
(emp_id, project_id, role_name, assigned_date)
VALUES
(1,1,'Project Manager','2024-01-15'),
(3,1,'Developer','2024-01-20'),
(4,2,'Software Engineer','2024-02-01'),
(2,3,'HR Manager','2024-03-01'),
(5,3,'HR Executive','2024-03-05'),
(6,5,'Accountant','2024-04-01');

-- INNER JOIN
-- Employee and Project Details

SELECT
    e.first_name,
    p.project_name,
    ep.role_name,
    ep.assigned_date
FROM employees e
INNER JOIN employee_projects ep
ON e.emp_id = ep.emp_id
INNER JOIN projects p
ON ep.project_id = p.project_id;

-- Left Join
-- Department Wise Employee Count

SELECT
    d.dept_name,
    COUNT(e.emp_id) AS total_employees
FROM departments d
LEFT JOIN employees e
ON d.dept_id = e.dept_id
GROUP BY d.dept_name;

-- Right Join
-- Show All Employee-Project Assignments

SELECT
    e.first_name,
    ep.project_id,
    ep.role_name
FROM employee_projects ep
RIGHT JOIN employees e
ON ep.emp_id = e.emp_id;

-- SubQuery
-- Employees Earning More Than Average Salary

SELECT emp_id,
       first_name,
       last_name,
       salary
FROM employees
WHERE salary >
(
    SELECT AVG(salary)
    FROM employees
);

-- Employee With Highest Salary

SELECT *
FROM employees
WHERE salary =
(
    SELECT MAX(salary)
    FROM employees
);

-- Employees Working on Project "Employee Portal"

SELECT first_name,
       last_name
FROM employees
WHERE emp_id IN
(
    SELECT emp_id
    FROM employee_projects
    WHERE project_id =
    (
        SELECT project_id
        FROM projects
        WHERE project_name = 'Employee Portal'
    )
);

-- Departments Having More Than One Employee

SELECT dept_name
FROM departments
WHERE dept_id IN
(
    SELECT dept_id
    FROM employees
    GROUP BY dept_id
    HAVING COUNT(*) > 1
);
-- Procedure
-- Get Employees by Department

DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartment(
    IN p_dept_id INT
)
BEGIN
    SELECT *
    FROM employees
    WHERE dept_id = p_dept_id;
END //

DELIMITER ;

CALL GetEmployeesByDepartment(1);

-- Get Employee Details by ID

DELIMITER //

CREATE PROCEDURE GetEmployeeById(
    IN p_emp_id INT
)
BEGIN
    SELECT *
    FROM employees
    WHERE emp_id = p_emp_id;
END //

DELIMITER ;

CALL GetEmployeeById(3);

-- Increase Employee Salary
DELIMITER //

CREATE PROCEDURE IncreaseSalary(
    IN p_emp_id INT,
    IN p_amount DECIMAL(10,2)
)
BEGIN
    UPDATE employees
    SET salary = salary + p_amount
    WHERE emp_id = p_emp_id;
END //

DELIMITER ;

CALL IncreaseSalary(1,5000);

-- Add New Employee

DELIMITER //

CREATE PROCEDURE AddEmployee(
    IN p_first_name VARCHAR(50),
    IN p_last_name VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_dept_id INT
)
BEGIN
    INSERT INTO employees
    (
        first_name,
        last_name,
        email,
        hire_date,
        salary,
        dept_id
    )
    VALUES
    (
        p_first_name,
        p_last_name,
        p_email,
        CURDATE(),
        30000,
        p_dept_id
    );
END //

DELIMITER ;

CALL AddEmployee(
'Rahul',
'Sen',
'rahul@gmail.com',
1
);

-- Functions
-- Annual Salary Function
DELIMITER //

CREATE FUNCTION AnnualSalary(
    monthly_salary DECIMAL(10,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN
    RETURN monthly_salary * 12;
END //

DELIMITER ;

SELECT first_name,
       salary,
       AnnualSalary(salary) AS annual_salary
FROM employees;

-- Employee Full Name Function
DELIMITER //

CREATE FUNCTION FullName(
    fname VARCHAR(50),
    lname VARCHAR(50)
)
RETURNS VARCHAR(101)
DETERMINISTIC
BEGIN
    RETURN CONCAT(fname,' ',lname);
END //

DELIMITER ;

SELECT FullName(first_name,last_name) AS employee_name
FROM employees;

-- Salary Category Function

DELIMITER //

CREATE FUNCTION SalaryCategory(
    emp_salary DECIMAL(10,2)
)
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN

    DECLARE category VARCHAR(20);

    IF emp_salary >= 80000 THEN
        SET category = 'High';
    ELSEIF emp_salary >= 50000 THEN
        SET category = 'Medium';
    ELSE
        SET category = 'Low';
    END IF;

    RETURN category;

END //

DELIMITER ;

SELECT first_name,
       salary,
       SalaryCategory(salary) AS salary_category
FROM employees;

-- Bonus Calculation Function

DELIMITER //

CREATE FUNCTION CalculateBonus(
    salary DECIMAL(10,2)
)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN salary * 0.10;
END //

DELIMITER ;

SELECT first_name,
       salary,
       CalculateBonus(salary) AS bonus
FROM employees;


