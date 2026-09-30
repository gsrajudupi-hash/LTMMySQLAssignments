CREATE DATABASE employee_management;

SHOW DATABASES;

USE employee_management;

CREATE TABLE department (
   dept_id INT PRIMARY KEY,
   dept_name VARCHAR(50) NOT NULL 
);

CREATE TABLE job_role (
   role_id INT PRIMARY KEY,
   role_name VARCHAR(100) NOT NULL,
   role_level VARCHAR(20)
);

SHOW TABLES;

CREATE TABLE employee (
    emp_id INT PRIMARY KEY,
    emp_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15),
    hire_date DATE NOT NULL,
    dept_id INT NOT NULL,
    role_id INT NOT NULL,

      FOREIGN KEY (dept_id)
      REFERENCES department(dept_id),

      FOREIGN KEY (role_id)
      REFERENCES job_role(role_id)
);


CREATE TABLE salary (
    salary_id INT PRIMARY KEY,
    emp_id INT NOT NULL,
    basic_salary DECIMAL(10,2) NOT NULL,

      FOREIGN KEY (emp_id)
      REFERENCES employee(emp_id)
);


INSERT INTO department (dept_id,dept_name) VALUES
   (101,'IT'),
   (102,'HR'),
   (103,'Finace'),
   (104,'Operations'),
   (105,'Sales');

SELECT * FROM department;

SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE job_role
MODIFY role_id INT AUTO_INCREMENT; 

SET FOREIGN_KEY_CHECKS = 1;

DESC job_role;


INSERT INTO job_role (role_name, role_level) VALUES
 ('Software Engineer', 'L1'),
 ('Senior Software Engineer', 'L2'),
 ('Technical Lead', 'L3'),
 ('Manager', 'L4'),
 ('HR Executive', 'L1'),
 ('Financial Analyst', 'L2'),
 ('Operations Executive', 'L1'),
 ('Sales Executive', 'L1');

SELECT * FROM job_role;

INSERT INTO employee (emp_id, emp_name, email, phone, hire_date, dept_id, role_id) 
VALUES
  (1, 'Harish', 'harish@example.com', '9876543210','2022-01-10', 101, 2),
  
  (2, 'Ravi', 'ravi@example.com', '9876543211','2023-03-15', 101, 1),

  (3, 'Priya', 'priya@example.com', '9876543212','2021-07-20', 102, 5),

  (4, 'Arun', 'arun@example.com', '9876543213','2020-11-05', 103, 6),

  (5, 'Sneha', 'sneha@example.com', '9876543214','2022-09-12', 103, 6),

  (6, 'Kiran', 'kiran@example.com', '9876543215','2019-06-18', 101, 3),

  (7, 'Rahul', 'rahul@example.com', '9876543216','2024-02-01', 104, 7),

  (8, 'Anjali', 'anjali@example.com', '9876543217','2023-08-25', 105, 8);
  
  
SELECT * FROM employee;  


INSERT INTO salary (salary_id, emp_id, basic_salary)
VALUES
 (1001, 1, 75000.00),
 (1002, 2, 50000.00),
 (1003, 3, 55000.00),
 (1004, 4, 70000.00),
 (1005, 5, 65000.00),
 (1006, 6, 95000.00),
 (1007, 7, 48000.00),
 (1008, 8, 52000.00);


SELECT * FROM salary;

SHOW TABLES;

-----------------------------------JOINS

SELECT * FROM employee; 

----Display employee name along with department NAME. 
SELECT e.emp_id,
       e.emp_name,
		 d.dept_name FROM employee e
INNER JOIN department d
ON e.dept_id = d.dept_id;


----Display each employee and their designation.
SELECT
    e.emp_name,
    j.role_name,
    j.role_level
FROM employee e
INNER JOIN job_role j
ON e.role_id = j.role_id;


----Display employee name and salary.
SELECT
    e.emp_name,
    s.basic_salary
FROM employee e
INNER JOIN salary s
ON e.emp_id = s.emp_id;


----Four-Table JOIN
SELECT
    e.emp_id,
    e.emp_name,
    d.dept_name,
    j.role_name,
    j.role_level,
    s.basic_salary
FROM employee e
INNER JOIN department d
    ON e.dept_id = d.dept_id
INNER JOIN job_role j
    ON e.role_id = j.role_id
INNER JOIN salary s
    ON e.emp_id = s.emp_id;
    
    
-------------------------------------SUBQUERIES    

----Find employees earning above-average salary
SELECT
    e.emp_name,
    s.basic_salary
FROM employee e
JOIN salary s
ON e.emp_id = s.emp_id
WHERE s.basic_salary > (
    SELECT AVG(basic_salary)
    FROM salary
);


----Find employees working in IT department.
SELECT
    emp_id,
    emp_name,
    email
FROM employee
WHERE dept_id = (
    SELECT dept_id
    FROM department
    WHERE dept_name = 'IT'
);


-----------------------------------STORED PROCEDURE

----Get all employees belonging to a particular department.

DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartment(
    IN p_dept_id INT
)
BEGIN

    SELECT
        e.emp_id,
        e.emp_name,
        e.email,
        d.dept_name
    FROM employee e
    JOIN department d
        ON e.dept_id = d.dept_id
    WHERE e.dept_id = p_dept_id;

END //

DELIMITER ;

CALL GetEmployeesByDepartment(101);

SELECT * FROM employee;


----Get all employees salary BY id.

DELIMITER //

CREATE PROCEDURE GetEmployeeSalary(
    IN p_emp_id INT
)
BEGIN

    SELECT
        e.emp_name,
        s.basic_salary
    FROM employee e
    JOIN salary s
        ON e.emp_id = s.emp_id
    WHERE e.emp_id = p_emp_id;

END //

DELIMITER ;

CALL GetEmployeeSalary(1);

------------------------------------FUNCTION

----create a function to calculate annual salary

DELIMITER //

CREATE FUNCTION CalculateAnnualSalary(
    p_salary DECIMAL(10,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN

    RETURN p_salary * 12;

END //

DELIMITER ;

SELECT CalculateAnnualSalary(75000);


----Function with Employee DATA
SELECT
    e.emp_name,
    s.basic_salary,
    CalculateAnnualSalary(s.basic_salary) AS annual_salary
FROM employee e
JOIN salary s
ON e.emp_id = s.emp_id;