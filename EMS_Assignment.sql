-- ============================================================================
-- LTIMindtree Full Stack Training
-- Assignment: Employee Management Database Design and Query Development
-- Topics: Joins, Subqueries, Stored Procedures, Functions
-- MySQL
-- ============================================================================

DROP DATABASE IF EXISTS emp_mgmt_db;
CREATE DATABASE emp_mgmt_db;
USE emp_mgmt_db;

-- ----------------------------------------------------------------------------
-- 1. SCHEMA (DDL)
-- ----------------------------------------------------------------------------

CREATE TABLE department (
    dept_id     INT PRIMARY KEY AUTO_INCREMENT,
    dept_name   VARCHAR(80) NOT NULL UNIQUE,
    location    VARCHAR(80) NOT NULL,
    budget      DECIMAL(12,2) NOT NULL CHECK (budget >= 0)
);

CREATE TABLE employee (
    emp_id      INT PRIMARY KEY AUTO_INCREMENT,
    first_name  VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    email       VARCHAR(120) NOT NULL UNIQUE,
    hire_date   DATE NOT NULL,
    job_title   VARCHAR(80) NOT NULL,
    salary      DECIMAL(10,2) NOT NULL CHECK (salary > 0),
    manager_id  INT NULL,
    dept_id     INT NOT NULL,
    CONSTRAINT fk_emp_dept
        FOREIGN KEY (dept_id) REFERENCES department(dept_id),
    CONSTRAINT fk_emp_mgr
        FOREIGN KEY (manager_id) REFERENCES employee(emp_id)
);

CREATE TABLE project (
    project_id   INT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(100) NOT NULL,
    start_date   DATE NOT NULL,
    end_date     DATE NULL,
    status       ENUM('PLANNED','ACTIVE','ON_HOLD','COMPLETED') NOT NULL DEFAULT 'PLANNED',
    dept_id      INT NOT NULL,
    CONSTRAINT fk_proj_dept
        FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

CREATE TABLE employee_project (
    emp_id       INT NOT NULL,
    project_id   INT NOT NULL,
    role_on_proj VARCHAR(60) NOT NULL,
    allocated_hrs INT NOT NULL CHECK (allocated_hrs > 0),
    PRIMARY KEY (emp_id, project_id),
    CONSTRAINT fk_ep_emp FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    CONSTRAINT fk_ep_proj FOREIGN KEY (project_id) REFERENCES project(project_id)
);

CREATE TABLE performance_review (
    review_id   INT PRIMARY KEY AUTO_INCREMENT,
    emp_id      INT NOT NULL,
    review_year YEAR NOT NULL,
    rating      DECIMAL(3,1) NOT NULL CHECK (rating BETWEEN 1.0 AND 5.0),
    comments    VARCHAR(255),
    CONSTRAINT fk_rev_emp FOREIGN KEY (emp_id) REFERENCES employee(emp_id),
    CONSTRAINT uq_emp_year UNIQUE (emp_id, review_year)
);

-- ----------------------------------------------------------------------------
-- 2. SAMPLE DATA
-- ----------------------------------------------------------------------------

INSERT INTO department (dept_name, location, budget) VALUES
('Engineering',     'Bengaluru', 25000000.00),
('Human Resources', 'Bengaluru',  4000000.00),
('Finance',         'Mumbai',     8000000.00),
('Sales',           'Delhi',      6000000.00),
('Operations',      'Hyderabad',  5500000.00);

INSERT INTO employee (first_name, last_name, email, hire_date, job_title, salary, manager_id, dept_id) VALUES
('Anita',  'Rao',     'anita.rao@corp.com',     '2018-03-12', 'Engineering Manager', 1800000.00, NULL, 1),
('Rahul',  'Mehta',   'rahul.mehta@corp.com',   '2019-07-01', 'HR Manager',          1400000.00, NULL, 2),
('Priya',  'Nair',    'priya.nair@corp.com',    '2017-11-20', 'Finance Manager',     1600000.00, NULL, 3),
('Vikram', 'Singh',   'vikram.singh@corp.com',  '2018-06-15', 'Sales Manager',       1500000.00, NULL, 4),
('Neha',   'Iyer',    'neha.iyer@corp.com',     '2020-01-10', 'Operations Manager',  1450000.00, NULL, 5);

INSERT INTO employee (first_name, last_name, email, hire_date, job_title, salary, manager_id, dept_id) VALUES
('Karan',  'Patel',   'karan.patel@corp.com',   '2021-02-01', 'Senior Developer',     1200000.00, 1, 1),
('Sneha',  'Reddy',   'sneha.reddy@corp.com',   '2022-04-18', 'Developer',             900000.00, 1, 1),
('Arjun',  'Das',     'arjun.das@corp.com',     '2023-08-05', 'Junior Developer',      650000.00, 1, 1),
('Meera',  'Shah',    'meera.shah@corp.com',    '2021-09-12', 'HR Executive',          700000.00, 2, 2),
('Rohit',  'Kumar',   'rohit.kumar@corp.com',   '2020-05-22', 'Accountant',            850000.00, 3, 3),
('Divya',  'Menon',   'divya.menon@corp.com',   '2022-11-03', 'Sales Executive',       720000.00, 4, 4),
('Amit',   'Joshi',   'amit.joshi@corp.com',    '2024-01-15', 'Ops Analyst',           680000.00, 5, 5),
('Pooja',  'Verma',   'pooja.verma@corp.com',   '2023-03-20', 'Developer',             880000.00, 1, 1);

INSERT INTO project (project_name, start_date, end_date, status, dept_id) VALUES
('Payments Gateway Revamp', '2025-01-10', NULL,         'ACTIVE',     1),
('HR Self-Service Portal',  '2024-08-01', '2025-06-30', 'COMPLETED',  2),
('GL Close Automation',     '2025-03-01', NULL,         'ACTIVE',     3),
('Q3 Sales Campaign',       '2025-07-01', '2025-09-30', 'COMPLETED',  4),
('Warehouse Optimization',  '2025-09-01', NULL,         'ACTIVE',     5),
('Mobile App Rewrite',      '2026-01-15', NULL,         'PLANNED',    1);

INSERT INTO employee_project (emp_id, project_id, role_on_proj, allocated_hrs) VALUES
(1, 1, 'Tech Lead',        160),
(6, 1, 'Backend Dev',      140),
(7, 1, 'Backend Dev',      120),
(8, 1, 'Support Dev',       80),
(13,1, 'Frontend Dev',     100),
(2, 2, 'Product Owner',     80),
(9, 2, 'Business Analyst', 100),
(3, 3, 'Sponsor',           40),
(10,3, 'Developer',        120),
(4, 4, 'Campaign Lead',     60),
(11,4, 'Executive',        100),
(5, 5, 'Lead',              80),
(12,5, 'Analyst',          120),
(6, 6, 'Architect',         40),
(7, 6, 'Developer',         40);

INSERT INTO performance_review (emp_id, review_year, rating, comments) VALUES
(1,  2024, 4.6, 'Strong delivery ownership'),
(2,  2024, 4.2, 'Stable people operations'),
(3,  2024, 4.4, 'Accurate close cycles'),
(4,  2024, 4.0, 'Met target, stretch pending'),
(5,  2024, 4.1, 'Process improvements shipped'),
(6,  2024, 4.7, 'High impact on critical path'),
(7,  2024, 4.3, 'Reliable sprint contributor'),
(8,  2024, 3.8, 'Needs mentoring on design'),
(9,  2024, 4.0, 'Good stakeholder handling'),
(10, 2024, 4.5, 'Automation mindset'),
(11, 2024, 3.6, 'Pipeline conversion weak'),
(12, 2024, 3.9, 'Solid first-year ops work'),
(13, 2024, 4.2, 'UI quality consistent'),
(6,  2025, 4.8, 'Promotion recommended'),
(7,  2025, 4.4, 'Ready for senior track'),
(8,  2025, 4.0, 'Improved code reviews'),
(11, 2025, 3.9, 'Better than prior year'),
(13, 2025, 4.5, 'Owns frontend module');

-- ----------------------------------------------------------------------------
-- 3. JOINS
-- ----------------------------------------------------------------------------

-- Q1 INNER JOIN: employee with department
-- Business: directory of employees with their department
SELECT e.emp_id,
       CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
       e.job_title,
       e.salary,
       d.dept_name,
       d.location
FROM employee e
INNER JOIN department d ON e.dept_id = d.dept_id
ORDER BY d.dept_name, e.emp_id;

-- Q2 LEFT JOIN: departments even if they currently have no extra staff pattern
-- Business: all departments and headcount
SELECT d.dept_id,
       d.dept_name,
       COUNT(e.emp_id) AS headcount,
       IFNULL(AVG(e.salary), 0) AS avg_salary
FROM department d
LEFT JOIN employee e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name
ORDER BY headcount DESC;

-- headCount > 2
SELECT d.dept_id,
       d.dept_name,
       COUNT(e.emp_id) AS headcount,
       IFNULL(AVG(e.salary), 0) AS avg_salary
FROM department d
LEFT JOIN employee e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name
HAVING headcount > 2
ORDER BY headcount DESC;

-- Q3 SELF JOIN: employee and manager
-- Business: reporting hierarchy
SELECT e.emp_id,
       CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
       e.job_title,
       CONCAT(m.first_name, ' ', m.last_name) AS manager_name
FROM employee e
LEFT JOIN employee m ON e.manager_id = m.emp_id
ORDER BY e.emp_id;

-- Q4 MULTI JOIN: who works on which project, under which department
SELECT CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
       p.project_name,
       ep.role_on_proj,
       ep.allocated_hrs,
       d.dept_name,
       p.status
FROM employee e
INNER JOIN employee_project ep ON e.emp_id = ep.emp_id
INNER JOIN project p ON ep.project_id = p.project_id
INNER JOIN department d ON p.dept_id = d.dept_id
ORDER BY p.project_name, employee_name;

-- Q5 RIGHT JOIN demonstration: projects and owning department
SELECT p.project_name, p.status, d.dept_name
FROM department d
RIGHT JOIN project p ON p.dept_id = d.dept_id
ORDER BY p.project_id;

-- ----------------------------------------------------------------------------
-- 4. SUBQUERIES
-- ----------------------------------------------------------------------------

-- Q6 Scalar subquery: employees earning above company average
SELECT emp_id,
       CONCAT(first_name, ' ', last_name) AS employee_name,
       salary
FROM employee
WHERE salary > (SELECT AVG(salary) FROM employee)
ORDER BY salary DESC;

-- Q7 IN subquery: employees who have at least one ACTIVE project
SELECT emp_id,
       CONCAT(first_name, ' ', last_name) AS employee_name
FROM employee
WHERE emp_id IN (
    SELECT ep.emp_id
    FROM employee_project ep
    INNER JOIN project p ON ep.project_id = p.project_id
    WHERE p.status = 'ACTIVE'
);

-- Q8 Correlated subquery: highest paid employee in each department
SELECT e.emp_id,
       CONCAT(e.first_name, ' ', e.last_name) AS employee_name,
       e.salary,
       e.dept_id
FROM employee e
WHERE e.salary = (
    SELECT MAX(e2.salary)
    FROM employee e2
    WHERE e2.dept_id = e.dept_id
)
ORDER BY e.dept_id;

-- Q9 EXISTS subquery: employees who never received a 2025 review
SELECT e.emp_id,
       CONCAT(e.first_name, ' ', e.last_name) AS employee_name
FROM employee e
WHERE NOT EXISTS (
    SELECT 1
    FROM performance_review pr
    WHERE pr.emp_id = e.emp_id
      AND pr.review_year = 2025
);

-- Q10 Derived table / FROM subquery: department vs company avg salary
SELECT dept_name, dept_avg, company_avg,
       ROUND(dept_avg - company_avg, 2) AS variance_vs_company
FROM (
    SELECT d.dept_name,
           AVG(e.salary) AS dept_avg,
           (SELECT AVG(salary) FROM employee) AS company_avg
    FROM employee e
    INNER JOIN department d ON e.dept_id = d.dept_id
    GROUP BY d.dept_name
) t
ORDER BY variance_vs_company DESC;

-- ----------------------------------------------------------------------------
-- 5. FUNCTIONS
-- ----------------------------------------------------------------------------

DELIMITER $$

-- F1: Annual CTC from monthly-equivalent stored annual salary already;
--     here salary is annual. Function returns grade band.
DROP FUNCTION IF EXISTS fn_salary_band $$
CREATE FUNCTION fn_salary_band(p_salary DECIMAL(10,2))
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
    DECLARE v_band VARCHAR(20);
    IF p_salary >= 1500000 THEN
        SET v_band = 'BAND-A';
    ELSEIF p_salary >= 1000000 THEN
        SET v_band = 'BAND-B';
    ELSEIF p_salary >= 750000 THEN
        SET v_band = 'BAND-C';
    ELSE
        SET v_band = 'BAND-D';
    END IF;
    RETURN v_band;
END $$

-- F2: Years of service as of today
DROP FUNCTION IF EXISTS fn_years_of_service $$
CREATE FUNCTION fn_years_of_service(p_hire_date DATE)
RETURNS DECIMAL(5,2)
DETERMINISTIC
BEGIN
    RETURN ROUND(DATEDIFF(CURDATE(), p_hire_date) / 365.25, 2);
END $$

-- F3: Latest performance rating for an employee (NULL if none)
DROP FUNCTION IF EXISTS fn_latest_rating $$
CREATE FUNCTION fn_latest_rating(p_emp_id INT)
RETURNS DECIMAL(3,1)
READS SQL DATA
BEGIN
    DECLARE v_rating DECIMAL(3,1);
    SELECT pr.rating
      INTO v_rating
    FROM performance_review pr
    WHERE pr.emp_id = p_emp_id
    ORDER BY pr.review_year DESC
    LIMIT 1;
    RETURN v_rating;
END $$

DELIMITER ;

-- Use functions
SELECT emp_id,
       CONCAT(first_name, ' ', last_name) AS employee_name,
       salary,
       fn_salary_band(salary) AS salary_band,
       fn_years_of_service(hire_date) AS yos,
       fn_latest_rating(emp_id) AS latest_rating
FROM employee
ORDER BY emp_id;

-- ----------------------------------------------------------------------------
-- 6. STORED PROCEDURES
-- ----------------------------------------------------------------------------

DELIMITER $$

-- P1: Transfer employee to another department
DROP PROCEDURE IF EXISTS sp_transfer_employee $$
CREATE PROCEDURE sp_transfer_employee(
    IN p_emp_id INT,
    IN p_new_dept_id INT
)
BEGIN
    DECLARE v_exists INT DEFAULT 0;

    SELECT COUNT(*) INTO v_exists FROM employee WHERE emp_id = p_emp_id;
    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Employee not found';
    END IF;

    SELECT COUNT(*) INTO v_exists FROM department WHERE dept_id = p_new_dept_id;
    IF v_exists = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Department not found';
    END IF;

    UPDATE employee
       SET dept_id = p_new_dept_id
     WHERE emp_id = p_emp_id;

    SELECT emp_id, first_name, last_name, dept_id
    FROM employee
    WHERE emp_id = p_emp_id;
END $$

-- P2: Give percentage hike to all employees in a department
DROP PROCEDURE IF EXISTS sp_dept_salary_hike $$
CREATE PROCEDURE sp_dept_salary_hike(
    IN p_dept_id INT,
    IN p_pct DECIMAL(5,2)
)
BEGIN
    IF p_pct <= 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Hike percent must be positive';
    END IF;

    UPDATE employee
       SET salary = ROUND(salary * (1 + p_pct / 100), 2)
     WHERE dept_id = p_dept_id;

    SELECT emp_id,
           CONCAT(first_name, ' ', last_name) AS employee_name,
           salary
    FROM employee
    WHERE dept_id = p_dept_id;
END $$

-- P3: Department dashboard (joins + aggregation inside procedure)
DROP PROCEDURE IF EXISTS sp_dept_dashboard $$
CREATE PROCEDURE sp_dept_dashboard(IN p_dept_id INT)
BEGIN
    SELECT d.dept_name,
           COUNT(e.emp_id) AS headcount,
           ROUND(AVG(e.salary), 2) AS avg_salary,
           MIN(e.salary) AS min_salary,
           MAX(e.salary) AS max_salary
    FROM department d
    LEFT JOIN employee e ON d.dept_id = e.dept_id
    WHERE d.dept_id = p_dept_id
    GROUP BY d.dept_name;

    SELECT p.project_name, p.status, p.start_date
    FROM project p
    WHERE p.dept_id = p_dept_id;
END $$

-- P4: Assign employee to project with validation
DROP PROCEDURE IF EXISTS sp_assign_to_project $$
CREATE PROCEDURE sp_assign_to_project(
    IN p_emp_id INT,
    IN p_project_id INT,
    IN p_role VARCHAR(60),
    IN p_hrs INT
)
BEGIN
    DECLARE v_status VARCHAR(20);

    SELECT status INTO v_status FROM project WHERE project_id = p_project_id;
    IF v_status IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Project not found';
    END IF;
    IF v_status = 'COMPLETED' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Cannot assign to completed project';
    END IF;

    INSERT INTO employee_project (emp_id, project_id, role_on_proj, allocated_hrs)
    VALUES (p_emp_id, p_project_id, p_role, p_hrs);

    SELECT * FROM employee_project
    WHERE emp_id = p_emp_id AND project_id = p_project_id;
END $$

DELIMITER ;

-- Sample procedure calls (safe / demo)
CALL sp_dept_dashboard(1);
CALL sp_transfer_employee(12, 1);
CALL sp_dept_salary_hike(4, 8.00);
CALL sp_assign_to_project(8, 6, 'Support Dev', 60);

-- ----------------------------------------------------------------------------
-- 7. VERIFICATION QUERIES (run after objects are created)
-- ----------------------------------------------------------------------------

SHOW TABLES;
SHOW FUNCTION STATUS WHERE Db = 'emp_mgmt_db';
SHOW PROCEDURE STATUS WHERE Db = 'emp_mgmt_db';
