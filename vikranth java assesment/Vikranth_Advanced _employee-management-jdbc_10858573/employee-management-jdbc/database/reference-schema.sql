-- Run in MySQL only if your previous assessment database does not already contain equivalent objects.
CREATE DATABASE IF NOT EXISTS employee_management_system;
USE employee_management_system;
CREATE TABLE IF NOT EXISTS department (department_id INT PRIMARY KEY AUTO_INCREMENT, department_name VARCHAR(100) NOT NULL UNIQUE);
CREATE TABLE IF NOT EXISTS employee (employee_id INT PRIMARY KEY AUTO_INCREMENT, employee_name VARCHAR(100) NOT NULL, email VARCHAR(150) NOT NULL UNIQUE, salary DECIMAL(12,2) NOT NULL CHECK(salary>=0), hire_date DATE NOT NULL, department_id INT NOT NULL, CONSTRAINT fk_emp_dept FOREIGN KEY(department_id) REFERENCES department(department_id));
CREATE TABLE IF NOT EXISTS employee_transfer (transfer_id BIGINT PRIMARY KEY AUTO_INCREMENT, employee_id INT NOT NULL, new_department_id INT NOT NULL, transferred_at TIMESTAMP NOT NULL, FOREIGN KEY(employee_id) REFERENCES employee(employee_id), FOREIGN KEY(new_department_id) REFERENCES department(department_id));
INSERT IGNORE INTO department(department_id,department_name) VALUES (1,'Engineering'),(2,'Finance'),(3,'HR');
DELIMITER //
DROP PROCEDURE IF EXISTS sp_employees_by_department//
CREATE PROCEDURE sp_employees_by_department(IN p_department_id INT) BEGIN SELECT * FROM employee WHERE department_id=p_department_id ORDER BY employee_id; END//
DROP PROCEDURE IF EXISTS sp_annual_salary//
CREATE PROCEDURE sp_annual_salary(IN p_employee_id INT,OUT p_annual_salary DECIMAL(14,2)) BEGIN SELECT salary*12 INTO p_annual_salary FROM employee WHERE employee_id=p_employee_id; END//
DELIMITER ;
