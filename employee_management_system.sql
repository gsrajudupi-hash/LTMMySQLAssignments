-- ============================================================
-- PROJECT TITLE: 
-- EMPLOYEE MANAGEMENT DATABASE DESIGN AND QUERY DEVELOPMENT---Employee name : Vikranth // Employe num - 10858573 // Project DATE : 23/09/2026
-- CONCEPTS:
-- Joins, Subqueries, Procedures, Functions, Views,
-- Triggers, Transactions, Constraints and Indexes
-- ============================================================


-- ============================================================
-- 1. DATABASE CREATION
-- ============================================================

DROP DATABASE IF EXISTS employee_management;

CREATE DATABASE employee_management;

USE employee_management;


-- ============================================================
-- 2. TABLE CREATION
-- ============================================================

-- ------------------------------------------------------------
-- Department table
-- ------------------------------------------------------------

CREATE TABLE departments (
    department_id INT PRIMARY KEY AUTO_INCREMENT,
    department_name VARCHAR(100) NOT NULL UNIQUE,
    location VARCHAR(100) NOT NULL
);


-- ------------------------------------------------------------
-- Job table
-- ------------------------------------------------------------

CREATE TABLE jobs (
    job_id INT PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(100) NOT NULL UNIQUE,
    minimum_salary DECIMAL(10,2) NOT NULL,
    maximum_salary DECIMAL(10,2) NOT NULL,

    CONSTRAINT chk_job_salary
        CHECK (
            minimum_salary >= 0
            AND maximum_salary >= minimum_salary
        )
);


-- ------------------------------------------------------------
-- Employee table
-- ------------------------------------------------------------

CREATE TABLE employees (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) UNIQUE,
    hire_date DATE NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    department_id INT,
    job_id INT,
    manager_id INT,

    CONSTRAINT chk_employee_salary
        CHECK (salary > 0),

    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id)
        REFERENCES departments(department_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT fk_employee_job
        FOREIGN KEY (job_id)
        REFERENCES jobs(job_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

    CONSTRAINT fk_employee_manager
        FOREIGN KEY (manager_id)
        REFERENCES employees(employee_id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);


-- ------------------------------------------------------------
-- Project table
-- ------------------------------------------------------------

CREATE TABLE projects (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(100) NOT NULL UNIQUE,
    start_date DATE NOT NULL,
    end_date DATE,
    budget DECIMAL(12,2),

    CONSTRAINT chk_project_budget
        CHECK (budget IS NULL OR budget >= 0),

    CONSTRAINT chk_project_date
        CHECK (
            end_date IS NULL
            OR end_date >= start_date
        )
);


-- ------------------------------------------------------------
-- Employee-project mapping table
-- Many-to-many relationship
-- ------------------------------------------------------------

CREATE TABLE employee_projects (
    employee_id INT,
    project_id INT,
    assigned_date DATE NOT NULL,
    employee_role VARCHAR(100) NOT NULL,
    allocation_percentage DECIMAL(5,2) NOT NULL,

    PRIMARY KEY (employee_id, project_id),

    CONSTRAINT chk_allocation
        CHECK (
            allocation_percentage > 0
            AND allocation_percentage <= 100
        ),

    CONSTRAINT fk_ep_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_ep_project
        FOREIGN KEY (project_id)
        REFERENCES projects(project_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


-- ------------------------------------------------------------
-- Salary history table
-- ------------------------------------------------------------

CREATE TABLE salary_history (
    salary_history_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_id INT NOT NULL,
    old_salary DECIMAL(10,2),
    new_salary DECIMAL(10,2),
    changed_date DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_salary_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(employee_id)
        ON DELETE CASCADE
);


-- ============================================================
-- 3. INSERT SAMPLE DATA
-- ============================================================

INSERT INTO departments (
    department_name,
    location
)
VALUES
    ('Human Resources', 'Hyderabad'),
    ('Information Technology', 'Chennai'),
    ('Finance', 'Mumbai'),
    ('Sales', 'Bengaluru'),
    ('Quality Assurance', 'Pune');


INSERT INTO jobs (
    job_title,
    minimum_salary,
    maximum_salary
)
VALUES
    ('Software Engineer', 40000, 120000),
    ('Senior Software Engineer', 70000, 180000),
    ('HR Executive', 30000, 80000),
    ('Finance Analyst', 45000, 100000),
    ('Sales Executive', 35000, 90000),
    ('QA Engineer', 40000, 110000),
    ('Project Manager', 80000, 200000);


-- Insert managers first

INSERT INTO employees (
    first_name,
    last_name,
    email,
    phone,
    hire_date,
    salary,
    department_id,
    job_id,
    manager_id
)
VALUES
    (
        'Arun',
        'Kumar',
        'arun@example.com',
        '9000000001',
        '2021-01-10',
        120000,
        2,
        7,
        NULL
    ),
    (
        'Rahul',
        'Verma',
        'rahul@example.com',
        '9000000002',
        '2020-08-20',
        70000,
        1,
        3,
        NULL
    ),
    (
        'Neha',
        'Singh',
        'neha@example.com',
        '9000000003',
        '2022-02-12',
        85000,
        3,
        4,
        NULL
    ),
    (
        'Kiran',
        'Rao',
        'kiran@example.com',
        '9000000004',
        '2021-11-01',
        75000,
        4,
        5,
        NULL
    );


-- Insert employees with managers

INSERT INTO employees (
    first_name,
    last_name,
    email,
    phone,
    hire_date,
    salary,
    department_id,
    job_id,
    manager_id
)
VALUES
    (
        'Priya',
        'Sharma',
        'priya@example.com',
        '9000000005',
        '2023-05-15',
        70000,
        2,
        1,
        1
    ),
    (
        'Divya',
        'Nair',
        'divya@example.com',
        '9000000006',
        '2023-07-10',
        70000,
        2,
        1,
        1
    ),
    (
        'Sanjay',
        'Patel',
        'sanjay@example.com',
        '9000000007',
        '2024-01-20',
        55000,
        5,
        6,
        1
    ),
    (
        'Meena',
        'Iyer',
        'meena@example.com',
        '9000000008',
        '2024-04-13',
        50000,
        1,
        3,
        2
    ),
    (
        'Vijay',
        'Das',
        'vijay@example.com',
        '9000000009',
        '2025-03-18',
        60000,
        4,
        5,
        4
    );


INSERT INTO projects (
    project_name,
    start_date,
    end_date,
    budget
)
VALUES
    (
        'Employee Portal',
        '2026-01-01',
        '2026-12-31',
        500000
    ),
    (
        'Payroll Automation',
        '2026-03-01',
        NULL,
        350000
    ),
    (
        'Sales Dashboard',
        '2026-04-15',
        '2026-10-31',
        250000
    ),
    (
        'Quality Management System',
        '2026-06-01',
        NULL,
        300000
    );


INSERT INTO employee_projects (
    employee_id,
    project_id,
    assigned_date,
    employee_role,
    allocation_percentage
)
VALUES
    (1, 1, '2026-01-01', 'Project Manager', 40),
    (5, 1, '2026-01-05', 'Developer', 100),
    (6, 1, '2026-01-05', 'Developer', 100),
    (1, 2, '2026-03-01', 'Architect', 30),
    (3, 2, '2026-03-10', 'Finance Analyst', 50),
    (4, 3, '2026-04-15', 'Sales Lead', 40),
    (9, 3, '2026-04-20', 'Sales Analyst', 100),
    (7, 4, '2026-06-01', 'QA Engineer', 100);


-- ============================================================
-- 4. BASIC SELECT QUERIES
-- ============================================================

SELECT * FROM departments;

SELECT * FROM jobs;

SELECT * FROM employees;

SELECT * FROM projects;

SELECT * FROM employee_projects;


-- Select specific columns

SELECT
    employee_id,
    first_name,
    last_name,
    email,
    salary
FROM employees;


-- Employees earning more than 