/*
Employee Name : Boddu Venkata Narendra
PSI Number    : 10858829
Case Study    : Employee Management System
Topics Used   : Joins, Subqueries, Procedures, Functions
*/

/* CREATE DATABASE */


CREATE DATABASE employee_management;

USE employee_management;

/* Creating the department table with fields dept_id, dept_name,location */


CREATE TABLE department (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50) NOT NULL,
    location VARCHAR(50)
);

/* Creating employee table with fields
	emp_id, emp_name, email, phone, salary, hire_date, dept_id, manager_id */

CREATE TABLE employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    salary DECIMAL(10,2),
    hire_date DATE,
    dept_id INT,
    manager_id INT,
 
    CONSTRAINT fk_employee_department
        FOREIGN KEY (dept_id)
        REFERENCES department(dept_id),
 
    CONSTRAINT fk_employee_manager
        FOREIGN KEY (manager_id)
        REFERENCES employee(emp_id)
);

/* Creating project table with fields project_id,project_name,start_date,end_date */

CREATE TABLE project (
    project_id INT PRIMARY KEY,
    project_name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE
);

/* one employee can work on multiple projects,
   and one project can have multiple employees 
   */
/* The combination of employee + project must be unique */

/* One composite primary key made from two columns */

CREATE TABLE employee_project (
    emp_id INT,
    project_id INT,
    role VARCHAR(50),
    assigned_date DATE,
 
    PRIMARY KEY (emp_id, project_id),
 
    CONSTRAINT fk_ep_employee
        FOREIGN KEY (emp_id)
        REFERENCES employee(emp_id),
 
    CONSTRAINT fk_ep_project
        FOREIGN KEY (project_id)
        REFERENCES project(project_id)
);


/* Insert data into department table */

INSERT INTO department
(dept_id, dept_name, location)
VALUES
(1, 'IT', 'Hyderabad'),
(2, 'HR', 'Bangalore'),
(3, 'Finance', 'Chennai'),
(4, 'Sales', 'Mumbai'),
(5, 'Operations', 'Pune');


/*Insert data into project table */

INSERT INTO project
(project_id, project_name, start_date, end_date)
VALUES
(201, 'Banking Application', '2026-01-01', '2028-12-31'),
(202, 'E-Commerce System', '2026-03-01', '2027-03-31'),
(203, 'HR Management', '2026-06-01', '2026-12-30'),
(204, 'CRM Application', '2026-09-01', '2027-07-31');


/*
   INSERT MANAGER EMPLOYEES
   First insert employees whose manager_id is NULL.
   This is important because employee has a self-reference.
*/
 
INSERT INTO employee
(emp_id, emp_name, email, phone, salary, hire_date, dept_id, manager_id)
VALUES
(101, 'Ravi Kumar', 'ravi@gmail.com',
'9876543210', 60000, '2022-06-10', 1, NULL),
 
(103, 'Priya Sharma', 'priya@gmail.com',
'9876543212', 55000, '2022-08-20', 2, NULL),
 
(104, 'Rahul Verma', 'rahul@gmail.com',
'9876543213', 70000, '2021-03-12', 3, NULL),
 
(105, 'Sneha Reddy', 'sneha@gmail.com',
'9876543214', 45000, '2024-02-10', 4, NULL),
 
(107, 'Arjun Singh', 'arjun@gmail.com',
'9876543216', 48000, '2023-06-18', 5, NULL);

/* INSERT EMPLOYEES WITH MANAGERS */
 
INSERT INTO employee
(emp_id, emp_name, email, phone, salary, hire_date, dept_id, manager_id)
VALUES
(102, 'Anil Kumar', 'anil@gmail.com',
'9876543211', 50000, '2023-01-15', 1, 101),
 
(106, 'Kiran Rao', 'kiran@gmail.com',
'9876543215', 65000, '2022-11-05', 1, 101),
 
(108, 'Divya Patel', 'divya@gmail.com',
'9876543217', 52000, '2024-01-25', 2, 103),
 
(109, 'Suresh Babu', 'suresh@gmail.com',
'9876543218', 58000, '2022-04-15', 4, 105),
 
(110, 'Meena Devi', 'meena@gmail.com',
'9876543219', 75000, '2021-09-10', 3, 104);


/* INSERT EMPLOYEE-PROJECT DATA */
 
INSERT INTO employee_project
(emp_id, project_id, role, assigned_date)
VALUES
(101, 201, 'Team Lead', '2026-01-05'),
(102, 201, 'Developer', '2026-01-10'),
(106, 201, 'Developer', '2026-01-15'),
(105, 202, 'Sales Executive', '2026-03-10'),
(109, 202, 'Sales Manager', '2026-03-15'),
(103, 203, 'HR Lead', '2026-06-05'),
(108, 203, 'HR Executive', '2026-06-10'),
(104, 204, 'Finance Lead', '2026-07-05'),
(110, 204, 'Finance Manager', '2026-07-10');

/*  DISPLAY ALL TABLES */
 
SHOW TABLES;
 
/* DISPLAY TABLE STRUCTURE */
 
DESC department;
 
DESC employee;
 
DESC project;
 
DESC employee_project;
 
 
/* DISPLAY ALL DEPARTMENTS */
 
SELECT *
FROM department;

/* =========================================================
   DISPLAY ALL PROJECTS
   ========================================================= */
 
SELECT *
FROM project;

/* =========================================================
   DISPLAY ALL EMPLOYEE PROJECT ASSIGNMENTS
   ========================================================= */
 
SELECT *
FROM employee_project;
 
 
/* =========================================================
   SELECT SPECIFIC COLUMNS
   ========================================================= */
 
SELECT
    emp_id,
    emp_name,
    salary
FROM employee;
 
 
/* =========================================================
   WHERE CONDITION
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary > 60000;
 
 
/* =========================================================
   EQUAL TO
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id = 1;
 
 
/* =========================================================
   NOT EQUAL TO
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id <> 1;
 
 
/* =========================================================
   GREATER THAN OR EQUAL TO
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary >= 60000;
 
 
/* =========================================================
   LESS THAN
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary < 60000;
 
 
/* =========================================================
   AND OPERATOR
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary > 50000
AND dept_id = 1;
 
 
/* =========================================================
   OR OPERATOR
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id = 1
OR dept_id = 2;
 
 
/* =========================================================
   NOT OPERATOR
   ========================================================= */
 
SELECT *
FROM employee
WHERE NOT dept_id = 1;
 
 
/* =========================================================
   BETWEEN OPERATOR
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary BETWEEN 50000 AND 65000;
 
 
/* =========================================================
   IN OPERATOR
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id IN (1, 2, 3);
 
 
/* =========================================================
   NOT IN OPERATOR
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id NOT IN (1, 2);
 
 
/* =========================================================
   LIKE - STARTS WITH R
   ========================================================= */
 
SELECT *
FROM employee
WHERE emp_name LIKE 'R%';
 
 
/* =========================================================
   LIKE - ENDS WITH A
   ========================================================= */
 
SELECT *
FROM employee
WHERE emp_name LIKE '%a';
 
 
/* =========================================================
   LIKE - CONTAINS "ar"
   ========================================================= */
 
SELECT *
FROM employee
WHERE emp_name LIKE '%ar%';
 
 
/* =========================================================
   LIKE - SECOND CHARACTER IS A
   ========================================================= */
 
SELECT *
FROM employee
WHERE emp_name LIKE '_a%';
 
 
/* =========================================================
    IS NULL
   ========================================================= */
 
SELECT *
FROM employee
WHERE manager_id IS NULL;
 
 
/* =========================================================
   IS NOT NULL
   ========================================================= */
 
SELECT *
FROM employee
WHERE manager_id IS NOT NULL;
 
 
/* =========================================================
   DISTINCT
   ========================================================= */
 
SELECT DISTINCT dept_id
FROM employee;
 
 
/* =========================================================
   ORDER BY ASCENDING
   ========================================================= */
 
SELECT *
FROM employee
ORDER BY salary ASC;
 
 
/* =========================================================
   ORDER BY DESCENDING
   ========================================================= */
 
SELECT *
FROM employee
ORDER BY salary DESC;
 

/* =========================================================
   ORDER BY MULTIPLE COLUMNS
   ========================================================= */
 
SELECT *
FROM employee
ORDER BY dept_id ASC, salary DESC;
 
 
/* =========================================================
   LIMIT
   ========================================================= */
 
SELECT *
FROM employee
LIMIT 5;
 
 
/* =========================================================
   TOP 3 HIGHEST SALARIES
   ========================================================= */
 
SELECT *
FROM employee
ORDER BY salary DESC
LIMIT 3;
 
 
/* =========================================================
   INSERT ONE NEW DEPARTMENT
   ========================================================= */
 
INSERT INTO department
(dept_id, dept_name, location)
VALUES
(6, 'Testing', 'Hyderabad');
 
 
/* =========================================================
   UPDATE EMPLOYEE SALARY
   ========================================================= */
 
UPDATE employee
SET salary = 65000
WHERE emp_id = 102;
 
 
/* =========================================================
   UPDATE MULTIPLE COLUMNS
   ========================================================= */
 
UPDATE employee
SET
    salary = 70000,
    phone = '9999999999'
WHERE emp_id = 102;
 
 
/* =========================================================
   UPDATE MULTIPLE RECORDS
   ========================================================= */
 
UPDATE employee
SET salary = salary + 5000
WHERE dept_id = 1;
 
 
/* =========================================================
   DELETE ONE EMPLOYEE
   ========================================================= */
 
/*
Do not execute this unless you really want to delete
employee 110.
*/
 
-- DELETE FROM employee
-- WHERE emp_id = 110;
 
 
/* =========================================================
   COUNT TOTAL EMPLOYEES
   ========================================================= */
 
SELECT COUNT(*) AS total_employees
FROM employee;
 
 
/* =========================================================
   SUM OF ALL SALARIES
   ========================================================= */
 
SELECT SUM(salary) AS total_salary
FROM employee;
 
 
/* =========================================================
   AVERAGE SALARY
   ========================================================= */
 
SELECT AVG(salary) AS average_salary
FROM employee;
 
 
/* =========================================================
   MINIMUM SALARY
   ========================================================= */
 
SELECT MIN(salary) AS minimum_salary
FROM employee;
 
 
/* =========================================================
   MAXIMUM SALARY
   ========================================================= */
 
SELECT MAX(salary) AS maximum_salary
FROM employee;
 
 
/* =========================================================
   COUNT EMPLOYEES BY DEPARTMENT
   ========================================================= */
 
SELECT
    dept_id,
    COUNT(*) AS employee_count
FROM employee
GROUP BY dept_id;
 
 
/* =========================================================
   AVERAGE SALARY BY DEPARTMENT
   ========================================================= */
 
SELECT
    dept_id,
    AVG(salary) AS average_salary
FROM employee
GROUP BY dept_id;
 
 
/* =========================================================
   TOTAL SALARY BY DEPARTMENT
   ========================================================= */
 
SELECT
    dept_id,
    SUM(salary) AS total_salary
FROM employee
GROUP BY dept_id;

/* =========================================================
   GROUP BY + HAVING
   Departments with more than one employee
   ========================================================= */
 
SELECT
    dept_id,
    COUNT(*) AS employee_count
FROM employee
GROUP BY dept_id
HAVING COUNT(*) > 1;
 
 
/* =========================================================
   HAVING WITH AVERAGE SALARY
   ========================================================= */
 
SELECT
    dept_id,
    AVG(salary) AS average_salary
FROM employee
GROUP BY dept_id
HAVING AVG(salary) > 55000;
 
 
/* =========================================================
   INNER JOIN
   Employee + Department
   ========================================================= */
 
SELECT
    e.emp_id,
    e.emp_name,
    e.salary,
    d.dept_name,
    d.location
FROM employee e
INNER JOIN department d
ON e.dept_id = d.dept_id;
 
 
/* =========================================================
   LEFT JOIN
   ========================================================= */
 
SELECT
    e.emp_name,
    d.dept_name
FROM employee e
LEFT JOIN department d
ON e.dept_id = d.dept_id;
 
 
/* =========================================================
   RIGHT JOIN
   ========================================================= */
 
SELECT
    e.emp_name,
    d.dept_name
FROM employee e
RIGHT JOIN department d
ON e.dept_id = d.dept_id;
 
 
/* =========================================================
   SELF JOIN
   Employee + Manager
   ========================================================= */
 
SELECT
    e.emp_name AS employee,
    m.emp_name AS manager
FROM employee e
LEFT JOIN employee m
ON e.manager_id = m.emp_id;
 
 
/* =========================================================
   THREE TABLE JOIN
   Employee + Department + Project
   ========================================================= */
 
SELECT
    e.emp_name,
    d.dept_name,
    p.project_name,
    ep.role
FROM employee e
JOIN department d
ON e.dept_id = d.dept_id
JOIN employee_project ep
ON e.emp_id = ep.emp_id
JOIN project p
ON ep.project_id = p.project_id;
 
 
/* =========================================================
   FIND EMPLOYEE BY PRIMARY KEY
   ========================================================= */
 
SELECT *
FROM employee
WHERE emp_id = 101;
 
 
/* =========================================================
   SUBQUERY
   Employees earning above average salary
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary > (
    SELECT AVG(salary)
    FROM employee
);
 
 
/* =========================================================
   SUBQUERY
   Employee with highest salary
   ========================================================= */
 
SELECT *
FROM employee
WHERE salary = (
    SELECT MAX(salary)
    FROM employee
);
 
 
/* =========================================================
   SECOND HIGHEST SALARY
   ========================================================= */
 
SELECT MAX(salary) AS second_highest_salary
FROM employee
WHERE salary < (
    SELECT MAX(salary)
    FROM employee
);
 
 
/* =========================================================
   EMPLOYEES IN IT DEPARTMENT USING SUBQUERY
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id = (
    SELECT dept_id
    FROM department
    WHERE dept_name = 'IT'
);
 
 
/* =========================================================
   EMPLOYEES IN IT OR HR USING SUBQUERY
   ========================================================= */
 
SELECT *
FROM employee
WHERE dept_id IN (
    SELECT dept_id
    FROM department
    WHERE dept_name IN ('IT', 'HR')
);
 
 
/* =========================================================
   STRING FUNCTION - UPPER
   ========================================================= */
 
SELECT
    emp_name,
    UPPER(emp_name) AS upper_name
FROM employee;
 
 
/* =========================================================
   STRING FUNCTION - LOWER
   ========================================================= */
 
SELECT
    emp_name,
    LOWER(emp_name) AS lower_name
FROM employee;
 
 
/* =========================================================
   STRING FUNCTION - LENGTH
   ========================================================= */
 
SELECT
    emp_name,
    LENGTH(emp_name) AS name_length
FROM employee;
 
 
/* =========================================================
   STRING FUNCTION - CONCAT
   ========================================================= */
 
SELECT
    CONCAT(emp_name, ' - ', email) AS employee_details
FROM employee;
 
 
/* =========================================================
   STRING FUNCTION - SUBSTRING
   ========================================================= */
 
SELECT
    emp_name,
    SUBSTRING(emp_name, 1, 5) AS short_name
FROM employee;
 
 
/* =========================================================
   STRING FUNCTION - TRIM
   ========================================================= */
 
SELECT
    TRIM(emp_name)
FROM employee;
 
 
 
/* =========================================================
   CURRENT DATE
   ========================================================= */
 
SELECT CURRENT_DATE();
/* =========================================================
   CURRENT TIMESTAMP
   ========================================================= */
 
SELECT CURRENT_TIMESTAMP();
 
 
/* =========================================================
   YEAR FUNCTION
   ========================================================= */
 
SELECT
    emp_name,
    YEAR(hire_date) AS joining_year
FROM employee;
 
 
/* =========================================================
   MONTH FUNCTION
   ========================================================= */
 
SELECT
    emp_name,
    MONTH(hire_date) AS joining_month
FROM employee;
 
 
/* =========================================================
   DAY FUNCTION
   ========================================================= */
 
SELECT
    emp_name,
    DAY(hire_date) AS joining_day
FROM employee;
 
 
/* =========================================================
   85. DATEDIFF
   Number of days since joining
   ========================================================= */
 
SELECT
    emp_name,
    DATEDIFF(CURRENT_DATE(), hire_date) AS days_worked
FROM employee;
 
 
/* =========================================================
   CREATE INDEX ON EMPLOYEE NAME
   ========================================================= */
 
CREATE INDEX idx_employee_name
ON employee(emp_name);
 
 
/* =========================================================
   CREATE INDEX ON SALARY
   ========================================================= */
 
CREATE INDEX idx_employee_salary
ON employee(salary);
 
 
/* =========================================================
   CREATE INDEX ON DEPARTMENT ID
   ========================================================= */
 
CREATE INDEX idx_employee_department
ON employee(dept_id);
 
 
/* =========================================================
   CREATE INDEX ON PROJECT NAME
   ========================================================= */
 
CREATE INDEX idx_project_name
ON project(project_name);
 
 
/* =========================================================
   DISPLAY INDEXES
   ========================================================= */
 
SHOW INDEX FROM employee;
 
 
/* =========================================================
   DROP INDEX
   ========================================================= */
 
/*
Uncomment when you want to remove the index.
 
DROP INDEX idx_employee_name
ON employee;
*/
 
 
 
/* =========================================================
   CREATE EMPLOYEE DETAILS VIEW
   ========================================================= */
 
CREATE VIEW employee_details AS
SELECT
    e.emp_id,
    e.emp_name,
    e.email,
    e.salary,
    e.hire_date,
    d.dept_name,
    d.location
FROM employee e
JOIN department d
ON e.dept_id = d.dept_id;
 
 
/* =========================================================
   USE VIEW
   ========================================================= */
 
SELECT *
FROM employee_details;
 
 
/* =========================================================
   USE VIEW WITH WHERE
   ========================================================= */
 
SELECT *
FROM employee_details
WHERE salary > 60000;
 
 
/* =========================================================
   DROP VIEW
   ========================================================= */
 
/*
DROP VIEW employee_details;
*/
 
 
/* =========================================================
   STORED PROCEDURE
   Get Employee By ID
   ========================================================= */
 
DELIMITER //
 
CREATE PROCEDURE GetEmployee(IN employeeId INT)
BEGIN
 
    SELECT
        emp_id,
        emp_name,
        email,
        phone,
        salary,
        hire_date,
        dept_id,
        manager_id
    FROM employee
    WHERE emp_id = employeeId;
 
END //
 
DELIMITER ;
 
 
/* =========================================================
   CALL STORED PROCEDURE
   ========================================================= */
 
CALL GetEmployee(101);
 
 
/* =========================================================
   STORED PROCEDURE
   Get Employees By Department
   ========================================================= */
 
DELIMITER //
 
CREATE PROCEDURE GetEmployeesByDepartment(
    IN departmentId INT
)
BEGIN
 
    SELECT
        emp_id,
        emp_name,
        email,
        phone,
        salary,
        hire_date,
        dept_id,
        manager_id
    FROM employee
    WHERE dept_id = departmentId;
 
END //
 
DELIMITER ;
 
 
/* =========================================================
   CALL SECOND STORED PROCEDURE
   ========================================================= */
 
CALL GetEmployeesByDepartment(1);
 
 
/* =========================================================
   STORED PROCEDURE
   COUNT EMPLOYEES IN DEPARTMENT
   ========================================================= */
 
DELIMITER //
 
CREATE PROCEDURE GetDepartmentEmployeeCount(
    IN departmentId INT
)
BEGIN
 
    SELECT
        COUNT(*) AS employee_count
    FROM employee
    WHERE dept_id = departmentId;
 
END //
 
DELIMITER ;
 
 
/* =========================================================
   CALL COUNT PROCEDURE
   ========================================================= */
 
CALL GetDepartmentEmployeeCount(1);
 
 
/* =========================================================
   STORED FUNCTION
   Calculate 10% Bonus
   ========================================================= */
 
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
 
 
/* =========================================================
   CALL BONUS FUNCTION
   ========================================================= */
 
SELECT
    emp_name,
    salary,
    CalculateBonus(salary) AS bonus
FROM employee;
 
 
/* =========================================================
   STORED FUNCTION
   Calculate Annual Salary
   ========================================================= */
 
DELIMITER //
 
CREATE FUNCTION GetAnnualSalary(
    salary DECIMAL(10,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN
 
    RETURN salary * 12;
 
END //
 
DELIMITER ;
 
 
/* =========================================================
   CALL ANNUAL SALARY FUNCTION
   ========================================================= */
 
SELECT
    emp_name,
    salary,
    GetAnnualSalary(salary) AS annual_salary
FROM employee;
 
 
/* =========================================================
   SHOW PROCEDURES
   ========================================================= */
 
SHOW PROCEDURE STATUS
WHERE Db = 'employee_management';
 
 
/* =========================================================
   SHOW FUNCTIONS
   ========================================================= */
 
SHOW FUNCTION STATUS
WHERE Db = 'employee_management';