CREATE DATABASE employee_management;

USE employee_management;

CREATE TABLE department (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(100)
);

CREATE TABLE employee (
    employee_id INT PRIMARY KEY,
    employee_name VARCHAR(100),
    salary DECIMAL(10,2),
    hire_date DATE,
    department_id INT,
    email VARCHAR(100)
);


INSERT INTO department VALUES
(1,'IT'),
(2,'HR'),
(3,'Finance'),
(4,'Sales');

INSERT INTO employee VALUES
(1,'Jitendra',60000,'2024-01-10',1,'jitendra@gmail.com'),
(2,'Rahul',55000,'2024-02-15',1,'rahul@gmail.com'),
(3,'Priya',45000,'2024-03-20',2,'priya@gmail.com'),
(4,'Amit',70000,'2024-04-05',3,'amit@gmail.com'),
(5,'Neha',50000,'2024-05-01',4,'neha@gmail.com');

//stored procedure
DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartment(
IN p_department_id INT
)
BEGIN
    SELECT employee_id,
           employee_name,
           email,
           salary,
           department_id
    FROM employee
    WHERE department_id = p_department_id;
END //

CREATE PROCEDURE IncreaseDepartmentSalary(
IN p_department_id INT,
IN p_percentage DECIMAL(5,2)
)
BEGIN
    UPDATE employee
    SET salary = salary + (salary * p_percentage / 100)
    WHERE department_id = p_department_id;
END //

DELIMITER ;