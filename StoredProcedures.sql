# Stored Procedures



# Procedure 1: Get Employees By Department
DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartments(IN deptId INT)
BEGIN
    SELECT
        EmployeeID,
        EmployeeName,
        JobTitle,
        Salary,
        HireDate
    FROM Employees
    WHERE DepartmentID = deptId;
END //

DELIMITER ;

CALL GetEmployeesByDepartments(1);


# Procedure 2: Get Employee Salary


DELIMITER //

CREATE PROCEDURE GetEmployeeSalary(IN empId INT)
BEGIN
    SELECT
        EmployeeName,
        Salary
    FROM Employees
    WHERE EmployeeID = empId;
END //

DELIMITER ;

CALL GetEmployeeSalary(102);


#Procedure 3: Get Employees With Their Departments

DELIMITER //

CREATE PROCEDURE GetEmployeeDepartmentDetails()
BEGIN
    SELECT
        E.EmployeeID,
        E.EmployeeName,
        E.JobTitle,
        D.DepartmentName,
        D.Location
    FROM Employees E
    INNER JOIN Departments D
        ON E.DepartmentID = D.DepartmentID;
END //

DELIMITER ;

CALL GetEmployeeDepartmentDetails();


# Procedure 4: Get Projects Assigned To Employees

DELIMITER //

CREATE PROCEDURE GetProjectDetails()
BEGIN
    SELECT
        E.EmployeeName,
        P.ProjectName,
        P.StartDate
    FROM Employees E
    INNER JOIN Projects P
        ON E.EmployeeID = P.EmployeeID;
END //

DELIMITER ;

CALL GetProjectDetails();