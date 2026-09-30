-- Run in HeidiSQL/MySQL only if these objects are missing from your earlier assessment.
USE employee_management;

CREATE TABLE IF NOT EXISTS department (
 department_id INT PRIMARY KEY AUTO_INCREMENT,
 department_name VARCHAR(100) NOT NULL UNIQUE,
 location VARCHAR(100) NOT NULL
);
CREATE TABLE IF NOT EXISTS employee (
 employee_id INT PRIMARY KEY AUTO_INCREMENT,
 employee_name VARCHAR(120) NOT NULL,
 email VARCHAR(150) NOT NULL UNIQUE,
 salary DECIMAL(12,2) NOT NULL CHECK (salary >= 0),
 department_id INT NOT NULL,
 hire_date DATE NOT NULL,
 CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES department(department_id)
);
CREATE TABLE IF NOT EXISTS employee_transfer_log (
 log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
 employee_id INT NOT NULL,
 old_department_id INT NOT NULL,
 new_department_id INT NOT NULL,
 transferred_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_log_employee FOREIGN KEY (employee_id) REFERENCES employee(employee_id),
 CONSTRAINT fk_log_old_dept FOREIGN KEY (old_department_id) REFERENCES department(department_id),
 CONSTRAINT fk_log_new_dept FOREIGN KEY (new_department_id) REFERENCES department(department_id)
);
CREATE TABLE IF NOT EXISTS salary_audit (
 audit_id BIGINT PRIMARY KEY AUTO_INCREMENT,
 department_id INT NOT NULL,
 percentage_applied DECIMAL(7,2) NOT NULL,
 affected_rows INT NOT NULL,
 changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
 CONSTRAINT fk_audit_department FOREIGN KEY (department_id) REFERENCES department(department_id)
);

INSERT IGNORE INTO department(department_id, department_name, location) VALUES
(1,'Engineering','Pune'),(2,'Sales','Mumbai'),(3,'HR','Hyderabad');

DROP PROCEDURE IF EXISTS sp_employees_by_department;
DELIMITER $$
CREATE PROCEDURE sp_employees_by_department(IN p_department_id INT)
BEGIN
 SELECT e.employee_id,e.employee_name,e.email,e.salary,e.department_id,e.hire_date
 FROM employee e WHERE e.department_id=p_department_id ORDER BY e.employee_id;
END$$

DROP PROCEDURE IF EXISTS sp_raise_department_salary;
CREATE PROCEDURE sp_raise_department_salary(IN p_department_id INT, IN p_percentage DECIMAL(7,2), OUT p_affected_rows INT)
BEGIN
 IF p_percentage <= 0 THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Percentage must be greater than zero'; END IF;
 UPDATE employee SET salary=salary+(salary*p_percentage/100) WHERE department_id=p_department_id;
 SET p_affected_rows=ROW_COUNT();
END$$
DELIMITER ;
