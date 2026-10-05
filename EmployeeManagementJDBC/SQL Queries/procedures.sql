SHOW DATABASES;
USE employeemanagementdb;
SHOW TABLES;

DESC departments;
DESC employees;
DESC projects;
DROP PROCEDURE IF EXISTS getEmployeesByDepartment;

DELIMITER $$

CREATE PROCEDURE getEmployeesByDepartment(
    IN departmentIdInput INT
)
BEGIN
    SELECT EmployeeID,
           EmployeeName,
           JobTitle,
           Salary,
           HireDate,
           DepartmentID,
           ManagerID
    FROM employees
    WHERE DepartmentID = departmentIdInput
    ORDER BY EmployeeID;
END $$

DELIMITER ;

CALL getEmployeesByDepartment(1);


DROP PROCEDURE IF EXISTS increaseEmployeeSalary;

DELIMITER $$

CREATE PROCEDURE increaseEmployeeSalary(
    IN employeeIdInput INT,
    IN percentageInput DECIMAL(5,2)
)
BEGIN
    UPDATE employees
    SET Salary =
        Salary + (Salary * percentageInput / 100)
    WHERE EmployeeID = employeeIdInput;

    SELECT ROW_COUNT() AS affectedRows;
END $$

DELIMITER ;


CALL increaseEmployeeSalary(101, 5);
