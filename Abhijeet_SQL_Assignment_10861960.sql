-- Abhijeet Pandey
-- PS No: 10861960
-- Email: abhijeet.pandey@ltm.com

-- SQL ASSIGNMENT

-- =======================================================================================================================

-- creating a database

CREATE DATABASE abhijeetdb;

-- using the specified db e.g., using abhijeetdb

USE abhijeetdb;

-- creating a table employee

CREATE TABLE employee(
	empId INT PRIMARY KEY AUTO_INCREMENT,
	empFirstName VARCHAR(50),
	empLastName VARCHAR(50),
	empPhoneNumber VARCHAR(10),
	empAddress VARCHAR(100),
	empBloodGroup VARCHAR(2),
	empDeptId INT
)

-- to see all the tables created in the current database

SHOW TABLES;

-- inserting data in employee table

INSERT INTO employee VALUES(1,'Abhijeet', 'Pandey', '8340354478', 'Bhubaneshwar, Odisha', 'O+',3);
INSERT INTO employee VALUES(2,'Ankit', 'Yadav', '8988979759', 'Bhubaneshwar, Odisha', 'B-',4);
INSERT INTO employee VALUES(3,'John', 'Doe', '3453553456', 'Bangalore, Karnataka', 'A+',2);
INSERT INTO employee VALUES(4,  'Alice',   'Smith',    '9876543210', 'Mumbai, Maharashtra',  'B+', 1);
INSERT INTO employee VALUES(5,  'Robert',  'Johnson',  '9123456780', 'Hyderabad, Telangana', 'O+', 3);
INSERT INTO employee VALUES(6,  'Emily',   'Brown',    '9988776655', 'Chennai, Tamil Nadu',  'B+', 2);
INSERT INTO employee VALUES(7,  'Michael', 'Davis',    '8877665544', 'Pune, Maharashtra',    'A-', 4);
INSERT INTO employee VALUES(8,  'Sophia',  'Wilson',   '7766554433', 'Delhi, Delhi',         'B-', 1);
INSERT INTO employee VALUES(9,  'Daniel',  'Miller',   '6655443322', 'Kolkata, West Bengal', 'O-', 5);
INSERT INTO employee VALUES(10, 'Olivia',  'Moore',    '5544332211', 'Ahmedabad, Gujarat',   'A+', 3);
INSERT INTO employee VALUES(11, 'James',   'Taylor',   '4433221100', 'Jaipur, Rajasthan',    'B-', 2);
INSERT INTO employee VALUES(12, 'Emma',    'Anderson', '3322110099', 'Noida, Uttar Pradesh', 'B+', 4);
INSERT INTO employee VALUES(13, 'William', 'Thomas',   '2211009988', 'Bhubaneswar, Odisha',  'O+', 5);
INSERT INTO employee VALUES(14,  'John',    'Doe',      '3453553456', 'Bangalore, Karnataka', 'A+', 2);

-- checking records in employee table

SELECT * FROM employee;

-- altering the field of table to fix it, blood 
-- group can be upto 3 characters long AB+, AB- can't be inserted
ALTER TABLE employee
	MODIFY empBloodGroup VARCHAR(4);
	
-- checking the updated table structrue
DESC employee;

-- inserting new record with specified columns
-- now blood groups like AB+, AB- can be added because the column was altered
INSERT INTO employee
	(empFirstName, empLastName, empPhoneNumber, empAddress, empBloodGroup, empDeptId)
VALUES
	('Fran', 'Garcia', '3453553456', 'Bangalore, Karnataka', 'AB+', 2);
	
-- updating a record in a table
-- Fran Garcia says he lives in Spain and wants to change his address

UPDATE employee SET empAddress='Seville, Spain' WHERE empId=15;

-- now Fran Garcia's address is updated

-- deleting a record from the table
-- Ankit Yadav is leaving the organization and wants his data removed

DELETE FROM employee WHERE empId=2;

-- Ankit Yadav's data is deleted from our db(table)

-- =======================================================================================================================

-- creating another table department to link employees with their respective departments
CREATE TABLE department(
deptId INT PRIMARY KEY AUTO_INCREMENT,
deptName VARCHAR(100),
deptCode VARCHAR(100) UNIQUE,
deptLocation VARCHAR(100),
deptStatus VARCHAR(2)
)

-- inserting department data
INSERT INTO department (deptName, deptCode, deptLocation, deptStatus) VALUES ('Software Development', 'SDE', 'Bangalore', 'Y');

-- using single insert statement to insert multiple records
INSERT INTO department
(deptName, deptCode, deptLocation, deptStatus)
VALUES
('Human Resources', 'HR', 'Hyderabad', 'Y'),
('Finance', 'FIN', 'Mumbai', 'Y'),
('Marketing', 'MKT', 'Pune', 'N'),
('Quality Assurance', 'QA', 'Chennai', 'Y');

-- now that we have department data let's perform some join operation

-- Getting department name for all employees

-- left join tells that table that show me employees
-- whether their department data is there or not
-- all employees data is visible if present or not

SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
LEFT JOIN department d
ON
e.empDeptId=d.deptId;

-- let's perform a right join and see the result
-- the result is same because there are no null values in employee table

SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
RIGHT JOIN department d
ON
e.empDeptId=d.deptId;

-- let's perform an inner join
SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
INNER JOIN department d
ON
e.empDeptId=d.deptId;

-- the result is same because every employee has a deptId and corresponding to that
-- there is a record for each deptId in department table

-- let's try to enter some null values in the department id field of the employee table
INSERT INTO employee VALUES(16,  'Jane',    'Doe',      '3453553456', 'Bangalore, Karnataka', 'AB-', NULL);
INSERT INTO employee VALUES(17,  'Antoni',    'Starr',      '345355778', 'NewZealand', 'O-', NULL);

SELECT * FROM employee; -- 15  records


-- checking join queries on-by-on after inserting null values
-- starting with left join

SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
LEFT JOIN department d
ON
e.empDeptId=d.deptId;

-- shows 15 records with last 2 records' department id as null because left join
-- prioratizes the left table data no matter the data in right table is present or not..abhijeetdb

-- right join now
SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
RIGHT JOIN department d
ON
e.empDeptId=d.deptId;

-- shows 13 records as 2 employees in employee table have no department id
-- right join emphasizes on showing all records from right table

-- now inner join
SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
INNER JOIN department d
ON
e.empDeptId=d.deptId;

-- shows 13 records as 13 employees have department id

-- =========================================================================================================================

-- ORDER BY and GROUP BY

-- manager wants the data of employees namewise-- what should be used?
-- answer is "order by" the order by clause shows sorted data based on a column(alphabetical or numerical) either
-- asecndingly or descendingly

-- for eg.,
-- sorting all employees namewise asecndingly
SELECT empId, CONCAT(empFirstName, ' ', empLastName) as empName FROM employee ORDER BY empName;
-- sorts all the  employees namewise(a->z)

-- can also sort reversely(descendingly)(z->a)
SELECT empId, CONCAT(empFirstName, ' ', empLastName) as empName FROM employee ORDER BY empName DESC;

-- we can also group results like:

-- counting employees by blood group:
--  CONCAT(empFirstName, ' ', empLastName) AS 'Employee Name'

SELECT count(empBloodGroup), empBloodGroup AS 'Blood Group' 
FROM employee 
GROUP BY empBloodGroup
ORDER BY empBloodGroup;

-- sub-query implementation(subquery/subqueries)
-- getting employees who work in software development department


SELECT empId AS 'Employee ID', CONCAT(empFirstName, ' ', empLastName) AS 'Employee Name' 
FROM employee
WHERE empDeptId 
IN (SELECT deptId FROM department WHERE deptId=1); 

-- 2 employees work in software development(Alice and Sophia)

-- checking employees who don't work in software development

SELECT empId AS 'Employee ID', CONCAT(empFirstName, ' ', empLastName) AS 'Employee Name' 
FROM employee
WHERE empDeptId 
NOT IN (SELECT deptId FROM department WHERE deptId=1);

-- 11 people don't work in software development

-- =========================================================================================================================

-- aggregate functions(always used with group by clause)

-- finding highest salary in the employee table by department

-- need to add salary column to the employee table

ALTER TABLE employee 
	ADD empSalary DOUBLE(10,2);
	
DESC employee;
-- salary row is added

-- inserting/updating salary for employees
UPDATE employee SET empSalary = 50000 WHERE empId = 1;
UPDATE employee SET empSalary = 25000 WHERE empId = 2;
UPDATE employee SET empSalary = 20000 WHERE empId = 3;
UPDATE employee SET empSalary = 27000 WHERE empId = 4;
UPDATE employee SET empSalary = 95000 WHERE empId = 5;
UPDATE employee SET empSalary = 34000 WHERE empId = 6;
UPDATE employee SET empSalary = 29000 WHERE empId = 7;
UPDATE employee SET empSalary = 28000 WHERE empId = 8;
UPDATE employee SET empSalary = 80000 WHERE empId = 9;
UPDATE employee SET empSalary = 23500 WHERE empId = 10;
UPDATE employee SET empSalary = 10000 WHERE empId = 11;
UPDATE employee SET empSalary = 40000 WHERE empId = 12;
UPDATE employee SET empSalary = 76000 WHERE empId = 13;
UPDATE employee SET empSalary = 90000 WHERE empId = 14;
UPDATE employee SET empSalary = 34000 WHERE empId = 15;
UPDATE employee SET empSalary = 34000 WHERE empId = 16;
UPDATE employee SET empSalary = 29000 WHERE empId = 17;

SELECT * FROM employee;


-- finding highest salary in the employee table by departmentId
SELECT MAX(empSalary), empDeptId 
FROM employee
GROUP BY empDeptId;

-- Max salary from each department has been obtained.

-- we can also find minimum salary in by departmentId
SELECT MIN(empSalary), empDeptId 
FROM employee
GROUP BY empDeptId;

-- similarly we can also find avg salary in each department
SELECT AVG(empSalary), empDeptId 
FROM employee
GROUP BY empDeptId;

-- ========================================================================================================================
-- SELF JOIN

-- our manager wants us to find the reporting structure of the organization
-- we can use self join to find that
-- but before that we need to modify our table with a column

ALTER TABLE employee 
	ADD COLUMN reportsTo INT;
-- now that the column has been added we need to add/update the reporting data
-- CEO reports to none
-- we can also avoid running this query as it won't have any impact on the empployee
-- with id 1
UPDATE employee SET reportsTo = NULL WHERE empId = 1;
 
-- managers reporting to CEO
UPDATE employee SET reportsTo = 1 WHERE empId = 2;
UPDATE employee SET reportsTo = 1 WHERE empId = 3;
UPDATE employee SET reportsTo = 1 WHERE empId = 4;
 
-- employees under manager 2
UPDATE employee SET reportsTo = 2 WHERE empId = 5;
UPDATE employee SET reportsTo = 2 WHERE empId = 6;
UPDATE employee SET reportsTo = 2 WHERE empId = 7;
 
-- employees under manager 3
UPDATE employee SET reportsTo = 3 WHERE empId = 8;
UPDATE employee SET reportsTo = 3 WHERE empId = 9;
UPDATE employee SET reportsTo = 3 WHERE empId = 10;
 
-- employees under manager 4
UPDATE employee SET reportsTo = 4 WHERE empId = 11;
UPDATE employee SET reportsTo = 4 WHERE empId = 12;
UPDATE employee SET reportsTo = 4 WHERE empId = 13;
 
-- employees under employee 5
UPDATE employee SET reportsTo = 5 WHERE empId = 14;
UPDATE employee SET reportsTo = 5 WHERE empId = 15;
 
-- employees under employee 8
UPDATE employee SET reportsTo = 8 WHERE empId = 16;
UPDATE employee SET reportsTo = 8 WHERE empId = 17;

-- checking the table with reporting data 
SELECT * FROM employee;
-- reportsTo column has been updated now we can see who's reporting to whom

-- applying self join
-- we need to create a copy of employee table
-- then we can use join on the copy
-- joining the Employee table with itself based on empId and reportsTo column
-- as both contain employee ids

SELECT e1.empId 'Employee ID',
CONCAT(e1.empFirstName, ' ', e1.empLastName) AS 'Employee Name',
CONCAT(e2.empFirstName, ' ', e2.empLastName) 'Reports To'  
FROM Employee e1
LEFT JOIN Employee e2
ON e1.reportsTo=e2.empId;

-- Full Outer join in MYSQL is not supported by default we can use UNION
-- operator with LEFT and Right Join to perform that

SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
LEFT JOIN department d
ON
e.empDeptId=d.deptId
UNION
SELECT e.empId 'EMP_ID', CONCAT(e.empFirstName, ' ', e.empLastName) 'EMP_NAME', d.deptName 
FROM employee e
RIGHT JOIN department d
ON
e.empDeptId=d.deptId;

-- ===========================================================================================================================

-- creating a copy of a exisiting table with all its data 
CREATE TABLE employee_copy 
AS SELECT * FROM employee;

SHOW TABLES;

-- deleting a table's data
-- TRUNCATE can be used
TRUNCATE TABLE employee_copy;

SELECT * FROM employee_copy;

-- deleting a table altogether
-- DROP can be used

DROP TABLE employee_copy;
-- table is deleted from the db
-- ==================================================================================================================================================

-- implementing procedures and functions
-- PROCEDURE: A procedure in sql is a collection of queries that can be executed on-demand.
-- use-case: Frequently used queries can be added in a procedure and executed just by callng.

-- creating a PROCEDURE to get count of employees by their blood group

delimiter //
CREATE PROCEDURE getEmployeeCountByBloodGroup()
BEGIN 
 SELECT empBloodGroup AS 'Blood Group',
  COUNT(empBloodGroup) AS 'Count of Employees'
  FROM employee
  GROUP BY empBloodGroup
  ORDER BY 2;
  
  END //
  delimiter ;
  
CALL getEmployeeCountByBloodGroup();
-- calling  the procedure using call keyword


-- we can also pass arguments in the procedure
-- for e.g., we can look for employees working in a particular location like Mumbai, Pune

delimiter //
CREATE PROCEDURE getEmployeeByLocation(IN locationName VARCHAR(100))
BEGIN 
 SELECT e.empId AS 'Employee ID',
  CONCAT(e.empFirstName, ' ', e.empLastName) AS 'Employee Name',
  d.deptName,
  d.deptLocation
  FROM employee e
  LEFT JOIN department d
  ON e.empDeptId=d.deptId
  WHERE d.deptLocation=locationName;
END //
delimiter ;

-- calling procedure to check employees working in mumbai, pune and bangalore locations

CALL getEmployeeByLocation('Mumbai'); -- 3 employees work in mumbai
CALL getEmployeeByLocation('Pune'); -- 2 people work in Pune
CALL getEmployeeByLocation('Bangalore'); -- 2 people work in Bangalore


-- we can also pass an argument as well as recieve an output from the procedure 
-- by using in and out
-- for e.g., trying to get count of employees working in a particular location

delimiter //
CREATE PROCEDURE getEmployeeCountByLocation(IN locationName VARCHAR(100), OUT total INT)
BEGIN 
 SELECT COUNT(e.empId) INTO total 
  FROM employee e
  LEFT JOIN department d
  ON e.empDeptId=d.deptId
  WHERE d.deptLocation=locationName;
END //
delimiter ;

-- storing the count value in total using INTO

-- calling the method and storing the value using @variable_name
CALL getEmployeeCountByLocation('Mumbai', @total);
SELECT @total AS 'Total Employees in Mumbai';

CALL getEmployeeCountByLocation('Bangalore', @total);
SELECT @total AS 'Total Employees in Bangalore';

-- using INOUT to send a argument and returning the updated value

delimiter //
CREATE PROCEDURE getSquare(INOUT inputNumber INT)
BEGIN 
 SET inputNumber=inputNumber*inputNumber;
  
END //
delimiter ;


SET @inputNumber=2;
CALL getSquare(@inputNumber);

SELECT @inputNumber AS 'Square of 2';

-- using INOUT to input a value x and getting y after the exceution of procedure

-- viewing all procedures in current db
SHOW PROCEDURE STATUS WHERE db = 'abhijeetdb'; -- 4 procedures

-- ==========================================================================================================================
-- function in sql
-- a function in sql can perform a logical or calculation operation and return a single result

-- Creating a function to retrieve name of an employee from its employee id

DELIMITER $$

CREATE FUNCTION getEmployeeName(p_employeeNumber INT)
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
    DECLARE employeeName VARCHAR(100);
    
    SELECT CONCAT(empFirstName, ' ', empLastName)
    INTO employeeName
    FROM employee
    WHERE employee.empId = p_employeeNumber;
    
    RETURN employeeName;
END $$

DELIMITER ;

-- executing the function in a query
SELECT getEmployeeName(3);

-- the function executes SELECT CONCAT(empFirstName, ' ', empLastName) FROM employee WHERE empId = 3;

-- creating a function that takes blood group and returns the name of first employee

DELIMITER $$

CREATE FUNCTION getEmployeeNameByBloodGroup(p_empbloodgroup VARCHAR(4))
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
    DECLARE employeeName VARCHAR(100);
    
    SELECT CONCAT(empFirstName, ' ', empLastName)
    INTO employeeName
    FROM employee
    WHERE employee.empBloodGroup = p_empbloodgroup
    LIMIT 1;
    
    RETURN employeeName;
END $$

DELIMITER ;

select getEmployeeNameByBloodGroup('B+'); -- returns first person it finds with the blood group


-- calculating bmi of a person/employee by accepting weight in kg and height in metres

DELIMITER $$

CREATE FUNCTION calculate_bmi(
    p_weight_kg DECIMAL(6,2),
    p_height_m DECIMAL(4,2)
)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    DECLARE v_bmi DECIMAL(5,2);

    -- Prevent division by zero or invalid height
    IF p_height_m IS NULL OR p_height_m <= 0 
       OR p_weight_kg IS NULL OR p_weight_kg <= 0 THEN
        RETURN NULL;
    END IF;

    SET v_bmi = p_weight_kg / POW(p_height_m, 2);

    RETURN ROUND(v_bmi, 2);
END $$

DELIMITER ;

-- using this function
SELECT calculate_bmi(120, 2.5) AS BMI_INDEX;

-- ==============================================END OF ASSESSMENT===========================================================================























  
	






















