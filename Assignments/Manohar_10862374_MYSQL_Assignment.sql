/******************************************************************************
PROJECT  : Employee Management Database
TOPICS   : Joins, Subqueries, Stored Procedures, Functions
******************************************************************************/

-- ============================================================================
-- CREATING DATABASE
-- ============================================================================

CREATE DATABASE IF NOT EXISTS EmployeeManagement;
s
USE EmployeeManagement;

-- ============================================================================
-- CREATION OF TABLES
-- ============================================================================

-- Department Table
CREATE TABLE IF NOT EXISTS Department (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL,
    location VARCHAR(50)
);

-- Employee Table
CREATE TABLE IF NOT EXISTS Employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2),
    hire_date DATE,
    dept_id INT,
    manager_id INT,

    CONSTRAINT fk_employee_department
        FOREIGN KEY (dept_id)
        REFERENCES Department(dept_id),

    CONSTRAINT fk_employee_manager
        FOREIGN KEY (manager_id)
        REFERENCES Employee(emp_id)
);

-- Project Table
CREATE TABLE IF NOT EXISTS Project (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    budget DECIMAL(12,2)
);

-- Employee_Project Table
CREATE TABLE IF NOT EXISTS Employee_Project (
    emp_id INT,
    project_id INT,
    hours_worked INT,

    PRIMARY KEY (emp_id, project_id),

    CONSTRAINT fk_emp_project_employee
        FOREIGN KEY (emp_id)
        REFERENCES Employee(emp_id),

    CONSTRAINT fk_emp_project_project
        FOREIGN KEY (project_id)
        REFERENCES Project(project_id)
);

-- ============================================================================
-- INSERT SAMPLE DATA
-- ============================================================================

INSERT INTO Department (dept_id, dept_name, location)
VALUES
(1,'HR','Hyderabad'),
(2,'IT','Bangalore'),
(3,'Finance','Mumbai')
ON DUPLICATE KEY UPDATE dept_name = VALUES(dept_name);

INSERT INTO Employee
(emp_id, emp_name, salary, hire_date, dept_id, manager_id)
VALUES
(101,'Manohar',60000,'2022-01-10',2,NULL),
(102,'Rakesh',50000,'2023-02-15',2,101),
(103,'Abhi',40000,'2023-03-20',1,101),
(104,'Kiran',70000,'2021-05-18',3,NULL)
ON DUPLICATE KEY UPDATE emp_name = VALUES(emp_name);

INSERT INTO Project
(project_id, project_name, budget)
VALUES
(201,'JIO Network',500000),
(202,'Banking Portal',1000000)
ON DUPLICATE KEY UPDATE project_name = VALUES(project_name);

INSERT INTO Employee_Project
(emp_id, project_id, hours_worked)
VALUES
(101,201,40),
(102,202,60),
(103,201,30),
(104,202,50)
ON DUPLICATE KEY UPDATE hours_worked = VALUES(hours_worked);

-- ============================================================================
-- JOINS
-- ============================================================================

-- 1. Employee and Department Details

SELECT
    e.emp_id,
    e.emp_name,
    d.dept_name
FROM Employee e
INNER JOIN Department d
ON e.dept_id = d.dept_id;

-- 2. Employee, Project and Hours Worked

SELECT
    e.emp_name,
    p.project_name,
    ep.hours_worked
FROM Employee e
INNER JOIN Employee_Project ep
ON e.emp_id = ep.emp_id
INNER JOIN Project p
ON ep.project_id = p.project_id;

-- 3. Employee and Manager (Self Join)

SELECT
    e.emp_name AS Employee_Name,
    m.emp_name AS Manager_Name
FROM Employee e
LEFT JOIN Employee m
ON e.manager_id = m.emp_id;

-- ============================================================================
-- SUBQUERIES
-- ============================================================================

-- 1. Employees Earning More Than Average Salary

SELECT
    emp_name,
    salary
FROM Employee
WHERE salary >
(
    SELECT AVG(salary)
    FROM Employee
);

-- 2. Employees Working on Highest Budget Project

SELECT emp_name
FROM Employee
WHERE emp_id IN
(
    SELECT emp_id
    FROM Employee_Project
    WHERE project_id =
    (
        SELECT project_id
        FROM Project
        WHERE budget =
        (
            SELECT MAX(budget)
            FROM Project
        )
    )
);

-- 3. Department Having Highest Average Salary

SELECT
    dept_id,
    AVG(salary) AS avg_salary
FROM Employee
GROUP BY dept_id
HAVING AVG(salary) =
(
    SELECT MAX(avg_sal)
    FROM
    (
        SELECT AVG(salary) AS avg_sal
        FROM Employee
        GROUP BY dept_id
    ) temp
);

-- ============================================================================
-- STORED PROCEDURES
-- ============================================================================

DROP PROCEDURE IF EXISTS GetEmployeesByDepartment;
DROP PROCEDURE IF EXISTS UpdateEmployeeSalary;

DELIMITER //

-- Procedure 1 : Get Employees by Department

CREATE PROCEDURE GetEmployeesByDepartment
(
    IN p_dept_id INT
)
BEGIN
    SELECT *
    FROM Employee
    WHERE dept_id = p_dept_id;
END //

-- Procedure 2 : Update Employee Salary

CREATE PROCEDURE UpdateEmployeeSalary
(
    IN p_emp_id INT,
    IN p_new_salary DECIMAL(10,2)
)
BEGIN
    UPDATE Employee
    SET salary = p_new_salary
    WHERE emp_id = p_emp_id;

    SELECT 'Salary Updated Successfully' AS Message;
END //

DELIMITER ;

-- Procedure Callings

CALL GetEmployeesByDepartment(2);

CALL UpdateEmployeeSalary(102,55000);

-- ============================================================================
-- FUNCTIONS
-- ============================================================================

DROP FUNCTION IF EXISTS AnnualSalary;
DROP FUNCTION IF EXISTS ExperienceYears;

DELIMITER //

--  Calculate Annual Salary

CREATE FUNCTION AnnualSalary
(
    monthly_salary DECIMAL(10,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN
    RETURN monthly_salary * 12;
END //

--  Calculate Experience in Years

CREATE FUNCTION ExperienceYears
(
    joining_date DATE
)
RETURNS INT
DETERMINISTIC
BEGIN
    RETURN TIMESTAMPDIFF(YEAR, joining_date, CURDATE());
END //

DELIMITER ;

-- ============================================================================
-- FUNCTION USAGE
-- ============================================================================

-- Annual Salary of Employees

SELECT
    emp_name,
    salary,
    AnnualSalary(salary) AS Annual_Salary
FROM Employee;

-- Experience of Employees

SELECT
    emp_name,
    hire_date,
    ExperienceYears(hire_date) AS Experience_Years
FROM Employee;
