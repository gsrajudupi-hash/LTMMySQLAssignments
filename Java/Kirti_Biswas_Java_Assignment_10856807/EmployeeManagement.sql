CREATE DATABASE employee_db;

USE employee_db;


CREATE TABLE department (
    department_id INT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL
);


CREATE TABLE employee (
    employee_id INT PRIMARY KEY,
    employee_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    salary DECIMAL(10,2),
    department_id INT,

    CONSTRAINT fk_department
    FOREIGN KEY (department_id)
    REFERENCES department(department_id)
);

CREATE TABLE employee_transfer_history (

    transfer_id INT AUTO_INCREMENT PRIMARY KEY,

    employee_id INT NOT NULL,

    old_department_id INT NOT NULL,

    new_department_id INT NOT NULL,

    transfer_date TIMESTAMP
    DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (employee_id)
    REFERENCES employee(employee_id),

    FOREIGN KEY (old_department_id)
    REFERENCES department(department_id),

    FOREIGN KEY (new_department_id)
    REFERENCES department(department_id)
);

INSERT INTO department
VALUES
(1,'HR'),
(2,'IT'),
(3,'Finance'),
(4,'Admin');


INSERT INTO employee
VALUES
(101,'Kirti','kirti@gmail.com',50000,2),
(102,'Rahul','rahul@gmail.com',45000,1),
(103,'Ankit','ankit@gmail.com',60000,2),
(104,'Priya','priya@gmail.com',55000,3);


DELIMITER //

CREATE PROCEDURE increase_department_salary(
    IN p_department_id INT,
    IN p_percentage DOUBLE
)
BEGIN

    UPDATE employee
    SET salary = salary +
                 (salary * p_percentage / 100)
    WHERE department_id =
          p_department_id;

END //

DELIMITER ;

DELIMITER //

CREATE PROCEDURE get_employee_count(
    IN p_department_id INT,
    OUT p_total INT
)
BEGIN

    SELECT COUNT(*)
    INTO p_total
    FROM employee
    WHERE department_id =
          p_department_id;

END //

DELIMITER ;

ALTER TABLE employee_transfer_history
DROP FOREIGN KEY employee_transfer_history_ibfk_1;

ALTER TABLE employee
MODIFY employee_id INT NOT NULL AUTO_INCREMENT;


ALTER TABLE employee_transfer_history
ADD CONSTRAINT employee_transfer_history_ibfk_1
FOREIGN KEY (employee_id)
REFERENCES employee(employee_id);

SHOW CREATE TABLE employee;
`