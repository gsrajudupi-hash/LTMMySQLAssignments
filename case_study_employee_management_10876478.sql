-- ==========================================
-- EMPLOYEE MANAGEMENT SYSTEM
-- MySQL / HeidiSQL Compatible Script
-- ==========================================

DROP DATABASE IF EXISTS EmployeeManagementDB;
CREATE DATABASE EmployeeManagementDB;
USE EmployeeManagementDB;

-- ==========================================
-- TABLES
-- ==========================================

CREATE TABLE Departments (
    DepartmentID INT AUTO_INCREMENT PRIMARY KEY,
    DepartmentName VARCHAR(100) NOT NULL,
    Location VARCHAR(100)
);

CREATE TABLE Employees (
    EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Email VARCHAR(100),
    Salary DECIMAL(10,2),
    HireDate DATE,
    DepartmentID INT,
    ManagerID INT NULL,
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID),
    FOREIGN KEY (ManagerID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Projects (
    ProjectID INT AUTO_INCREMENT PRIMARY KEY,
    ProjectName VARCHAR(100),
    Budget DECIMAL(12,2)
);

CREATE TABLE EmployeeProjects (
    EmployeeID INT,
    ProjectID INT,
    PRIMARY KEY(EmployeeID, ProjectID),
    FOREIGN KEY(EmployeeID) REFERENCES Employees(EmployeeID),
    FOREIGN KEY(ProjectID) REFERENCES Projects(ProjectID)
);

CREATE TABLE EmployeeAudit (
    AuditID INT AUTO_INCREMENT PRIMARY KEY,
    EmployeeID INT,
    ActionType VARCHAR(50),
    ActionDate DATETIME
);

-- ==========================================
-- INSERT DATA
-- ==========================================

INSERT INTO Departments (DepartmentName, Location)
VALUES
('IT','Mumbai'),
('HR','Pune'),
('Finance','Bangalore');

INSERT INTO Employees
(FirstName, LastName, Email, Salary, HireDate, DepartmentID, ManagerID)
VALUES
('Vinit','Angre','vinit@test.com',80000,'2022-01-10',1,NULL),
('Rahul','Patil','rahul@test.com',60000,'2023-03-15',1,1),
('Neha','Sharma','neha@test.com',75000,'2022-06-20',2,1),
('Amit','Kumar','amit@test.com',50000,'2024-01-05',3,1),
('Priya','Singh','priya@test.com',70000,'2023-09-11',2,3);

INSERT INTO Projects(ProjectName, Budget)
VALUES
('Banking Application',500000),
('HR Portal',150000),
('Loan Management System',300000);

INSERT INTO EmployeeProjects
VALUES
(1,1),
(2,1),
(3,2),
(4,3),
(5,2);

-- ==========================================
-- JOINS
-- ==========================================

-- INNER JOIN
SELECT
e.EmployeeID,
e.FirstName,
e.LastName,
d.DepartmentName
FROM Employees e
INNER JOIN Departments d
ON e.DepartmentID=d.DepartmentID;

-- LEFT JOIN
SELECT
e.FirstName,
p.ProjectName
FROM Employees e
LEFT JOIN EmployeeProjects ep
ON e.EmployeeID=ep.EmployeeID
LEFT JOIN Projects p
ON ep.ProjectID=p.ProjectID;

-- RIGHT JOIN
SELECT
d.DepartmentName,
e.FirstName
FROM Employees e
RIGHT JOIN Departments d
ON e.DepartmentID=d.DepartmentID;

-- SELF JOIN
SELECT
e.FirstName AS EmployeeName,
m.FirstName AS ManagerName
FROM Employees e
LEFT JOIN Employees m
ON e.ManagerID=m.EmployeeID;

-- ==========================================
-- AGGREGATE FUNCTIONS
-- ==========================================

SELECT
DepartmentID,
COUNT(*) AS EmployeeCount,
AVG(Salary) AS AvgSalary,
MAX(Salary) AS HighestSalary,
MIN(Salary) AS LowestSalary
FROM Employees
GROUP BY DepartmentID;

-- ==========================================
-- SUBQUERIES
-- ==========================================

-- Employees earning above average salary
SELECT *
FROM Employees
WHERE Salary >
(
SELECT AVG(Salary)
FROM Employees
);

-- Highest salary employee
SELECT *
FROM Employees
WHERE Salary =
(
SELECT MAX(Salary)
FROM Employees
);

-- Correlated subquery
SELECT *
FROM Employees e
WHERE Salary >
(
SELECT AVG(Salary)
FROM Employees
WHERE DepartmentID=e.DepartmentID
);

-- ==========================================
-- VIEW
-- ==========================================

CREATE VIEW EmployeeDepartmentView AS
SELECT
e.EmployeeID,
e.FirstName,
e.LastName,
e.Salary,
d.DepartmentName
FROM Employees e
JOIN Departments d
ON e.DepartmentID=d.DepartmentID;

SELECT * FROM EmployeeDepartmentView;

-- ==========================================
-- FUNCTION
-- ==========================================

DELIMITER $$ 
CREATE FUNCTION GetAnnualSalary(
monthlySalary DECIMAL(10,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN
RETURN monthlySalary * 12;
END $$ 
DELIMITER ;

SELECT
EmployeeID,
FirstName,
Salary,
GetAnnualSalary(Salary) AS AnnualSalary
FROM Employees;

-- ==========================================
-- STORED PROCEDURE
-- ==========================================

DELIMITER $$
CREATE PROCEDURE GetEmployeeById
(
IN empId INT
)
BEGIN
SELECT *
FROM Employees
WHERE EmployeeID=empId;
END$$
DELIMITER ;

CALL GetEmployeeById(1);

-- ==========================================
-- INSERT PROCEDURE
-- ==========================================

DELIMITER $$
CREATE PROCEDURE AddEmployee
(
IN p_FirstName VARCHAR(50),
IN p_LastName VARCHAR(50),
IN p_Email VARCHAR(100),
IN p_Salary DECIMAL(10,2),
IN p_DepartmentID INT
)
BEGIN

INSERT INTO Employees
(
FirstName,
LastName,
Email,
Salary,
HireDate,
DepartmentID
)
VALUES
(
p_FirstName,
p_LastName,
p_Email,
p_Salary,
CURDATE(),
p_DepartmentID
);
END$$
DELIMITER ;

CALL AddEmployee(
'Rohit',
'Verma',
'rohit@test.com',
65000,
1
);

-- ==========================================
-- TRIGGER
-- ==========================================

DELIMITER $$
CREATE TRIGGER EmployeeInsertTrigger
AFTER INSERT
ON Employees
FOR EACH ROW
BEGIN

INSERT INTO EmployeeAudit
(
EmployeeID,
ActionType,
ActionDate
)
VALUES
(
NEW.EmployeeID,
'INSERT',
NOW()
);
END$$
DELIMITER ;

-- ==========================================
-- TRIGGER TEST
-- ==========================================

INSERT INTO Employees
(
FirstName,
LastName,
Email,
Salary,
HireDate,
DepartmentID
)
VALUES
(
'Test',
'User',
'test@test.com',
55000,
CURDATE(),
1
);

SELECT * FROM EmployeeAudit;

-- ==========================================
-- UPDATE QUERY
-- ==========================================

UPDATE Employees
SET Salary = 85000
WHERE EmployeeID = 1;

-- ==========================================
-- DELETE QUERY
-- ==========================================

DELETE FROM Employees
WHERE EmployeeID = 6;

-- ==========================================
-- DISPLAY ALL TABLE DATA
-- ==========================================

SELECT * FROM Departments;
SELECT * FROM Employees;
SELECT * FROM Projects;
SELECT * FROM EmployeeProjects;
SELECT * FROM EmployeeAudit;

-- ==========================================
-- END OF SCRIPT
-- ==========================================