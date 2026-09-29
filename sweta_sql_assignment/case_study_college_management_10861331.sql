----- creating college Management Database ------

CREATE DATABASE college_db;


----- use college_db -----
USE college_db;

------ Create the Department Table -----------------------------
CREATE TABLE departments(
	department_id INT AUTO_INCREMENT PRIMARY KEY,
	department_name VARCHAR (40) NOT NULL UNIQUE,
	hod_name VARCHAR(30),
	location VARCHAR(30)
);


------ Create the Student Table -----------------------------
CREATE TABLE students(
	student_id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR (40) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	email VARCHAR(30) UNIQUE,
	phone VARCHAR(15),
	gender VARCHAR(10),
	dob DATE,
	city VARCHAR(25),
	department_id INT,
	
	FOREIGN KEY(department_id) REFERENCES departments(department_id)

);


------ Create the Tracher Table -----------------------------
CREATE TABLE teachers(
	teacher_id INT AUTO_INCREMENT PRIMARY KEY,
	first_name VARCHAR (40) NOT NULL,
	last_name VARCHAR(30) NOT NULL,
	email VARCHAR(30) UNIQUE,
	salary BIGINT(100),
	department_id INT,
	
	FOREIGN KEY(department_id) REFERENCES departments(department_id)
	
);


------ Create the Course Table -----------------------------
CREATE TABLE courses(
	course_id INT AUTO_INCREMENT PRIMARY KEY,
	course_name VARCHAR(50) NOT NULL,
	credits INT,
	department_id INT,
	
	FOREIGN KEY(department_id) REFERENCES departments(department_id)
	
);


------ Create the Enrollment Table -----------------------------
CREATE TABLE enrollments(
	enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
	student_id INT,
	course_id INT,
	emrollment_date DATE,
	marks INT,
	
	FOREIGN KEY(student_id) REFERENCES students(student_id),
 	FOREIGN KEY(course_id) REFERENCES courses(course_id)
);


------ Create the Fees Table -----------------------------
CREATE TABLE fees(
	fee_id INT AUTO_INCREMENT PRIMARY KEY,
	student_id INT,
	amount BIGINT(150),
	payment_date DATE,
	payment_status VARCHAR(20),
	
	FOREIGN KEY(student_id) REFERENCES students(student_id)
);


DESC departments;
DESC students;
DESC enrollments;
DESC teachers;
DESC courses;
DESC fees;

DROP TABLE departments;
DROP TABLE students;
DROP TABLE teachers;
DROP TABLE courses;
DROP TABLE enrollments;
DROP TABLE fees;


-- inserting data into Department table --

INSERT INTO departments VALUES(1,'Computer Science','R.Rawat','Block A');
INSERT INTO departments VALUES(2,'Information Technology','S.Sharma','Block B');
INSERT INTO departments VALUES(3,'Electronics','S.Ghosh','Block C');
INSERT INTO departments VALUES(4,'Mechanical','R.Verma','Block D');


select * FROM departments;
DELETE FROM departments WHERE department_id =1;


-- inserting data into student table --
INSERT INTO students VALUES(1,'Priya','Das','priya@gmail.com','9876544676','Female','2001-06-12','Delhi',2);
INSERT INTO students VALUES(2,'sneha','gupta','sneha@gmail.com','9832544676','Female','2002-03-12','kolkata',1);
INSERT INTO students VALUES(3,'Rahul','sah','rahul@gmail.com','7676544676','Male','1999-06-10','mumbai',3);
INSERT INTO students VALUES(4,'Akshat','Mani','akshat@gmail.com','8876544676','Male','2002-10-05','Luchnow',4);
INSERT INTO students VALUES(5,'Ritu','Bharti','ritu@gmail.com','9876004676','Female','2003-07-07','Kolkata',2);
INSERT INTO students VALUES(6,'Saloni','Das','Saloni@gmail.com','7654344676','Female','2003-06-12','kolkata',1);


select * FROM students;


-- inserting data into Teacher table --
INSERT INTO teachers(first_name, last_name, email, salary, department_id) VALUES('Amit','Sharma','amit@gmail.com',80000,1);
INSERT INTO teachers(first_name, last_name, email, salary, department_id) VALUES('Sumit','Kumar','sumit@gmail.com',65000,2);
INSERT INTO teachers(first_name, last_name, email, salary, department_id) VALUES('shanu','verma','shanu@gmail.com',85000,3);
INSERT INTO teachers(first_name, last_name, email, salary, department_id) VALUES('snehil','pandey','snehil@gmail.com',75000,4);

select * FROM teachers;


-- inserting data into Course table --
INSERT INTO courses VALUES(1,'Java Programming',5,1);
INSERT INTO courses VALUES(2,'Python',2,4);
INSERT INTO courses VALUES(3,'DataBase Managemnet',4,3);
INSERT INTO courses VALUES(4,'Framework',4,4);
INSERT INTO courses VALUES(5,'Azure Development',3,2);
INSERT INTO courses VALUES(6,'Microservices',3,3);

select * FROM courses;

-- inserting data into Enrollment table --
INSERT INTO enrollments VALUES(1,1,2,'2026-06-12',86);
INSERT INTO enrollments VALUES(2,1,1,'2026-06-10',82);
INSERT INTO enrollments VALUES(3,2,3,'2026-06-11',70);
INSERT INTO enrollments VALUES(4,2,4,'2026-06-12',88);
INSERT INTO enrollments VALUES(5,3,2,'2026-06-13',75);
INSERT INTO enrollments VALUES(6,3,4,'2026-06-15',79);
INSERT INTO enrollments VALUES(7,4,2,'2026-06-13',95);
INSERT INTO enrollments VALUES(8,4,1,'2026-06-11',88);
INSERT INTO enrollments VALUES(9,1,3,'2026-06-09',70);



select * FROM enrollments;


-- inserting data into fee table --
INSERT INTO fees VALUES(1,1,50000,'2026-05-12','Paid');
INSERT INTO fees VALUES(2,2,48000,'2026-07-11','Paid');
INSERT INTO fees VALUES(3,3,51000,'2026-09-12','Pending');
INSERT INTO fees VALUES(4,4,52000,'2026-08-09','Paid');
INSERT INTO fees VALUES(5,5,58000,'2026-08-10','Paid');
INSERT INTO fees VALUES(6,6,50000,'2026-09-11','Pending');


SELECT * FROM fees;


-- select query --

SELECT first_name, last_name, city FROM students;

SELECT * FROM students WHERE city='kolkata';


-- using AND Operator --
SELECT * FROM students WHERE city='kolkata' AND gender='female';

-- using Or Operator -- 
SELECT * FROM students WHERE city='kolkata' OR city='delhi';


-- using IN Operator -- 
SELECT * FROM students WHERE city IN('kolkata','delhi','mumbai');


-- using BETWEEN Operator -- 
SELECT * FROM enrollments WHERE marks BETWEEN 78 AND 88;


-- using LIKE Operator -- 
SELECT * FROM students WHERE first_name LIKE '%a';

SELECT * FROM students WHERE first_name LIKE 's%';

-- using DISTINCT Operator -- 
SELECT DISTINCT city FROM students;


-- using ORDER BY -- 
SELECT * FROM enrollments ORDER BY marks DESC;


-- using COUNT -- 
SELECT COUNT(*) AS total_students FROM students;

SELECT department_id, COUNT(*) AS total_students 
FROM students GROUP BY department_id;


SELECT department_id, COUNT(*) AS total_students FROM students 
GROUP BY department_id HAVING COUNT(*) > 1;



-- using AVG -- 
SELECT AVG(marks) AS average_marks FROM enrollments;

SELECT * FROM enrollments
WHERE marks >(SELECT AVG(marks) FROM enrollments);



-- using max -- 
SELECT max(marks) AS maximum_marks FROM enrollments;


-- using min -- 
SELECT min(marks) AS aminimum_marks FROM enrollments;

-- using sum -- 
SELECT sum(amount) AS total_fees FROM fees;


-- INNER JOIN --
SELECT s.student_id, s.first_name, s.last_name, d.department_name
FROM students s INNER JOIN departments d 
ON s.department_id = d.department_id;


-- LEFT JOIN --
SELECT s.student_id, s.first_name, s.last_name, e.course_id
FROM students s LEFT JOIN enrollments e 
ON s.department_id = e.student_id;


-- RIGHT JOIN --
SELECT s.first_name, s.last_name, d.department_name
FROM students s RIGHT JOIN departments d 
ON s.department_id = d.department_id;


-- SET Query --
UPDATE students SET city='Guwahati' WHERE student_id= 1;


SELECT * FROM students;


-- ALTER Query --
ALTER TABLE students RENAME COLUMN phone TO mobile_number;
	

----- Procedure ----
delimiter //

CREATE PROCEDURE GetStudentDetails(IN p_student_id INT)
BEGIN 
SELECT s.student_id, s.first_name, s.last_name, s.email, d.department_name
FROM students s
JOIN departments d ON s.department_id= d.department_id
WHERE s.student_id = p_student_id;
END //

delimiter;


CALL GetStudentDetails(2);


----- Function ---
delimiter $$
CREATE FUNCTION GetTotalFees(p_student_id INT)
RETURNS BIGINT(100)
DETERMINISTIC
BEGIN
DECLARE total BIGINT(100);
	SELECT COALESCE(SUM(amount), 0) 
	INTO total FROM fees 
	WHERE student_id = p_student_id
	AND payment_status = 'Paid';
	RETURN total;
	
END $$

delimiter;


CALL GetTotalFees(1);
