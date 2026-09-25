CREATE DATABASE employee_db;

-- use employee_db
USE employee_db;

---- Employee Table -----------------------------

CREATE TABLE employee (
   employee_id INT PRIMARY KEY AUTO_INCREMENT,
   address_id INT NOT NULL,
   dept_id INT NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50),
   email VARCHAR(100) NOT NULL,
   phone VARCHAR(100) NOT NULL  CHECK (LENGTH (phone) = 10),
   salary BIGINT(255) NOT NULL, 
   FOREIGN KEY (dept_id)
     REFERENCES employee_department(dept_id),
   FOREIGN KEY (address_id)
     REFERENCES employee_address(address_id)
	     
);

------------------------- employee_department--------------------
CREATE TABLE employee_department (
   dept_id INT PRIMARY KEY AUTO_INCREMENT,
   dept_name VARCHAR(50) NOT NULL
  
);

-----------------------------employee_address----------------------
CREATE TABLE employee_address (
   address_id INT PRIMARY KEY AUTO_INCREMENT,
   street_name VARCHAR(50) NOT NULL,
   country  VARCHAR(50) NOT NULL
  
);

DESC employee;
DESC employee_department;
DESC employee_address;

DROP table employee;

USE employee_db;
INSERT INTO employee VALUES(1,1,1,'Saunak','Dutta','saunak@gmail.com', '8989898989',50000);
INSERT INTO employee VALUES(2,3,2,'Amit','Kiran','amit@gmail.com', '9003247891',450000);
INSERT INTO employee VALUES(3,2,1,'Ramya','','ramyak@gmail.com', '7003271341',60000);
INSERT INTO employee VALUES(4,1,3,'Raj','Shekhar','raj@gmail.com', '9340598652',75000000);


INSERT INTO employee VALUES(5,2,4,'Raju','Shekhar','raju@gmail.com', '9340598650',15000000);
INSERT INTO employee VALUES(6,4,2,'Souvik','Rana','souvik@gmail.com', '9340598651',35000000);
INSERT INTO employee VALUES(7,4,4,'Kirti','Singh','kirti@gmail.com', '9340598654',8000000);
INSERT INTO employee VALUES(8,2,3,'Shailendra','Kumar','shailendra@gmail.com', '8989898980',12000000);


INSERT INTO employee_address VALUES(4,'EFG Street','IND');
INSERT INTO employee_department VALUES(2,'INS-ADM');
INSERT INTO employee_department VALUES(3,'DNA');
INSERT INTO employee_department VALUES(4,'MFG-ADM');

SELECT * FROM employee; 
SELECT * FROM employee_department;
SELECT * FROM employee_address;


-------------------- Joins ------------------------------------------------------------
-- Find the  employee details , depart name & country for each employee
--- We can use inner join
SELECT e.* , d.dept_name , a.country FROM employee e INNER JOIN employee_department d ON d.dept_id = e.dept_id 
INNER JOIN employee_address  a ON a.address_id = e.address_id;




--------------------- Subquery ----------------------------------------------------------------
--- Find the second highest salary for each dept.
SELECT d.dept_name,MAX(e.salary) AS  second_highest_salary  FROM employee e INNER JOIN employee_department d ON  e.dept_id = d.dept_id WHERE  e.salary < (SELECT MAX(e.salary) FROM employee e) GROUP BY e.dept_id ;

---------------------- Stored Procedure --------------------------------------------------------
-- Get the salary  for each employee

delimiter //
CREATE PROCEDURE getEmployeeSalary(IN employeeId INT )
BEGIN
SELECT e.salary FROM employee e WHERE e.employee_id = employeeId;
END //
delimiter ;

CALL getEmployeeSalary(1);
CALL getEmployeeSalary(2);
CALL getEmployeeSalary(3);
CALL getEmployeeSalary(4);


-- Get the full Name  for each employee using IN and OUT parameters
delimiter $$
CREATE PROCEDURE getEmployeeFullname(IN employeeID INT,OUT fullName VARCHAR(100))
BEGIN
SELECT CONCAT(e.first_name,e.last_name) INTO fullName FROM employee e WHERE e.employee_id = employeeID;
END $$
delimiter ;

SET @fullName1 = '';
CALL getEmployeeFullname(1,@fullName1);
SELECT @fullName as full_name1;
SET @fullName2 = '';
CALL getEmployeeFullname(2,@fullName);
SELECT @fullName2 as full_name2;
SET @fullName3 = '';
CALL getEmployeeFullname(3,@fullName3);
SELECT @fullName3 as full_name3;
SET @fullName4 = '';
CALL getEmployeeFullname(4,@fullName4);
SELECT @fullName4 as full_name4;





------------------------ Functions -------------------------------------------------------------
-- Get the full name for each employee
-- deterministic function

delimiter $$
CREATE FUNCTION getUppercaseFullName(employeeId INT )
RETURNS  VARCHAR (1000)
DETERMINISTIC 
BEGIN
DECLARE upperCaseFullName VARCHAR(100);
SELECT CONCAT(UPPER(e.first_name),'-',UPPER(e.last_name)) INTO upperCaseFullName FROM employee e WHERE e.employee_id = employeeId;
RETURN upperCaseFullName;
END $$
delimiter ;


SELECT getUppercaseFullName(e.employee_id) 'full_name_uppercase' FROM employee e ;



