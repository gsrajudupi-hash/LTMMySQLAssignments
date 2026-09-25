#Database Creation

CREATE DATABASE EmployeeManagementDB;
USE EmployeeManagementDB;

#tables creation


# 1. Department Table 
CREATE TABLE Departments (
DepartmentID INT PRIMARY KEY,
DepartmentName VARCHAR(50) NOT NULL,
Location VARCHAR(50)
);

# 2. Employee Table

CREATE TABLE Employees (
EmployeeID INT PRIMARY KEY,
EmployeeName VARCHAR(100) NOT NULL,
JobTitle VARCHAR(50),
Salary DECIMAL(10,2) CHECK (Salary > 0),
HireDate DATE,
DepartmentID INT,
ManagerID INT NULL,
FOREIGN KEY (DepartmentID)
REFERENCES Departments(DepartmentID),
FOREIGN KEY (ManagerID)
REFERENCES Employees(EmployeeID)
);


# 2. Projects Table

CREATE TABLE Projects (
ProjectID INT PRIMARY KEY,
ProjectName VARCHAR(100) NOT NULL,
EmployeeID INT,
StartDate DATE,
FOREIGN KEY (EmployeeID)
REFERENCES Employees(EmployeeID)
);



# Data Insertion in tables


INSERT INTO Departments VALUES
(1, 'IT', 'Mumbai'),
(2, 'Human Resources', 'Pune'),
(3, 'Finance', 'Bengaluru');

SELECT * FROM Departments;


INSERT INTO Employees
VALUES
(101, 'Ananya Sharma', 'IT Manager', 95000, '2021-02-10', 1, NULL),
(104, 'Meera Joshi', 'HR Manager', 85000, '2020-06-15', 2, NULL),
(106, 'Arjun Rao', 'Finance Manager', 90000, '2019-09-20', 3, NULL);

INSERT INTO Employees VALUES
(102, 'Rahul Verma', 'Software Engineer', 65000, '2023-01-12', 1, 101),
(103, 'Priya Nair', 'Database Developer', 72000, '2022-08-05', 1, 101),
(105, 'Karan Singh', 'HR Executive', 48000, '2024-03-01', 2, 104),
(107, 'Neha Patel', 'Financial Analyst', 60000, '2023-07-18', 3, 106);

INSERT INTO Projects VALUES
(201, 'Employee Portal', 102, '2025-01-10'),
(202, 'Database Migration', 103, '2025-02-15'),
(203, 'Recruitment Automation', 105, '2025-03-01'),
(204, 'Budget Analysis', 107, '2025-01-20');


