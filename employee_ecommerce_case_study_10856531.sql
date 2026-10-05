-- Employee Management / E-Commerce Database Case Study
-- MySQL 8+

DROP DATABASE IF EXISTS ecommerce_management;
CREATE DATABASE ecommerce_management;
USE ecommerce_management;

-- 1. TABLES
CREATE TABLE departments (
  department_id INT PRIMARY KEY AUTO_INCREMENT,
  department_name VARCHAR(100) NOT NULL UNIQUE,
  location VARCHAR(100) NOT NULL
);

CREATE TABLE employees (
  employee_id INT PRIMARY KEY AUTO_INCREMENT,
  employee_name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  salary DECIMAL(10,2) NOT NULL CHECK (salary > 0),
  hire_date DATE NOT NULL,
  department_id INT,
  manager_id INT,
  FOREIGN KEY (department_id) REFERENCES departments(department_id) ON DELETE SET NULL,
  FOREIGN KEY (manager_id) REFERENCES employees(employee_id) ON DELETE SET NULL
);

CREATE TABLE customers (
  customer_id INT PRIMARY KEY AUTO_INCREMENT,
  customer_name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  city VARCHAR(80),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
  category_id INT PRIMARY KEY AUTO_INCREMENT,
  category_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE products (
  product_id INT PRIMARY KEY AUTO_INCREMENT,
  product_name VARCHAR(120) NOT NULL,
  category_id INT,
  price DECIMAL(10,2) NOT NULL CHECK (price > 0),
  stock INT NOT NULL DEFAULT 0 CHECK (stock >= 0),
  FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL
);

CREATE TABLE orders (
  order_id INT PRIMARY KEY AUTO_INCREMENT,
  customer_id INT NOT NULL,
  handled_by INT,
  order_date DATE NOT NULL,
  status ENUM('PLACED','SHIPPED','DELIVERED','CANCELLED') DEFAULT 'PLACED',
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
  FOREIGN KEY (handled_by) REFERENCES employees(employee_id) ON DELETE SET NULL
);

CREATE TABLE order_items (
  order_id INT,
  product_id INT,
  quantity INT NOT NULL CHECK (quantity > 0),
  unit_price DECIMAL(10,2) NOT NULL CHECK (unit_price > 0),
  PRIMARY KEY (order_id, product_id),
  FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- 2. SAMPLE DATA
INSERT INTO departments (department_name, location) VALUES
('IT','Chennai'),('Sales','Bengaluru'),('HR','Hyderabad');

INSERT INTO employees (employee_name,email,salary,hire_date,department_id,manager_id) VALUES
('Arun Kumar','arun@company.com',120000,'2021-01-10',1,NULL),
('Kiran Rao','kiran@company.com',90000,'2022-02-12',2,NULL),
('Priya Sharma','priya@company.com',70000,'2023-05-15',1,1),
('Vijay Das','vijay@company.com',65000,'2024-03-18',2,2),
('Meena Iyer','meena@company.com',50000,'2024-04-13',3,NULL);

INSERT INTO customers (customer_name,email,city) VALUES
('Rahul Verma','rahul@mail.com','Chennai'),
('Divya Nair','divya@mail.com','Hyderabad'),
('Sanjay Patel','sanjay@mail.com','Bengaluru'),
('Neha Singh','neha@mail.com','Pune');

INSERT INTO categories (category_name) VALUES ('Electronics'),('Books'),('Home Appliances');

INSERT INTO products (product_name,category_id,price,stock) VALUES
('Laptop',1,65000,10),('Headphones',1,2500,30),
('Java Programming',2,900,25),('Database Design',2,750,20),
('Mixer Grinder',3,4500,12);

INSERT INTO orders (customer_id,handled_by,order_date,status) VALUES
(1,4,'2026-09-20','DELIVERED'),(2,4,'2026-09-21','SHIPPED'),
(1,2,'2026-09-22','PLACED'),(3,2,'2026-09-23','CANCELLED');

INSERT INTO order_items VALUES
(1,1,1,65000),(1,2,2,2500),(2,3,2,900),
(2,4,1,750),(3,5,1,4500),(4,2,1,2500);

-- 3. JOINS
-- Employee with department
SELECT e.employee_id, e.employee_name, d.department_name, e.salary
FROM employees e
LEFT JOIN departments d ON e.department_id = d.department_id;

-- Employee with manager using self join
SELECT e.employee_name AS employee, COALESCE(m.employee_name,'No Manager') AS manager
FROM employees e
LEFT JOIN employees m ON e.manager_id = m.employee_id;

-- Complete order report using multiple joins
SELECT o.order_id, c.customer_name, p.product_name, oi.quantity,
       oi.unit_price, oi.quantity * oi.unit_price AS line_total,
       e.employee_name AS handled_by, o.status
FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
JOIN products p ON oi.product_id = p.product_id
LEFT JOIN employees e ON o.handled_by = e.employee_id
ORDER BY o.order_id;

-- Customers without orders
SELECT c.customer_id, c.customer_name
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL;

-- 4. SUBQUERIES
-- Employees earning above company average
SELECT employee_name, salary
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

-- Highest-paid employee in each department
SELECT e.employee_name, e.salary, e.department_id
FROM employees e
WHERE e.salary = (
  SELECT MAX(e2.salary) FROM employees e2
  WHERE e2.department_id = e.department_id
);

-- Products priced above category average
SELECT p.product_name, p.price
FROM products p
WHERE p.price > (
  SELECT AVG(p2.price) FROM products p2
  WHERE p2.category_id = p.category_id
);

-- Customers whose spending is above average customer spending
SELECT c.customer_id, c.customer_name,
       SUM(oi.quantity * oi.unit_price) AS total_spent
FROM customers c
JOIN orders o ON c.customer_id = o.customer_id
JOIN order_items oi ON o.order_id = oi.order_id
WHERE o.status <> 'CANCELLED'
GROUP BY c.customer_id, c.customer_name
HAVING total_spent > (
  SELECT AVG(customer_total)
  FROM (
    SELECT SUM(oi2.quantity * oi2.unit_price) AS customer_total
    FROM orders o2
    JOIN order_items oi2 ON o2.order_id = oi2.order_id
    WHERE o2.status <> 'CANCELLED'
    GROUP BY o2.customer_id
  ) x
);

-- 5. FUNCTIONS
DELIMITER //
CREATE FUNCTION fn_order_total(p_order_id INT)
RETURNS DECIMAL(12,2)
DETERMINISTIC
READS SQL DATA
BEGIN
  DECLARE v_total DECIMAL(12,2);
  SELECT COALESCE(SUM(quantity * unit_price),0)
  INTO v_total
  FROM order_items
  WHERE order_id = p_order_id;
  RETURN v_total;
END //

CREATE FUNCTION fn_employee_grade(p_salary DECIMAL(10,2))
RETURNS VARCHAR(20)
DETERMINISTIC
BEGIN
  RETURN CASE
    WHEN p_salary >= 100000 THEN 'A'
    WHEN p_salary >= 70000 THEN 'B'
    WHEN p_salary >= 50000 THEN 'C'
    ELSE 'D'
  END;
END //

-- 6. PROCEDURES
CREATE PROCEDURE sp_get_employees_by_department(IN p_department_id INT)
BEGIN
  SELECT employee_id, employee_name, salary
  FROM employees
  WHERE department_id = p_department_id
  ORDER BY salary DESC;
END //

CREATE PROCEDURE sp_place_order(
  IN p_customer_id INT,
  IN p_employee_id INT,
  IN p_product_id INT,
  IN p_quantity INT
)
BEGIN
  DECLARE v_stock INT;
  DECLARE v_price DECIMAL(10,2);
  DECLARE v_order_id INT;

  START TRANSACTION;
  SELECT stock, price INTO v_stock, v_price
  FROM products WHERE product_id = p_product_id FOR UPDATE;

  IF v_stock IS NULL THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Product not found';
  ELSEIF p_quantity <= 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Quantity must be positive';
  ELSEIF v_stock < p_quantity THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Insufficient stock';
  ELSE
    INSERT INTO orders(customer_id,handled_by,order_date,status)
    VALUES(p_customer_id,p_employee_id,CURDATE(),'PLACED');
    SET v_order_id = LAST_INSERT_ID();
    INSERT INTO order_items(order_id,product_id,quantity,unit_price)
    VALUES(v_order_id,p_product_id,p_quantity,v_price);
    UPDATE products SET stock = stock - p_quantity WHERE product_id = p_product_id;
    COMMIT;
    SELECT v_order_id AS new_order_id, fn_order_total(v_order_id) AS order_total;
  END IF;
END //

CREATE PROCEDURE sp_update_employee_salary(
  IN p_employee_id INT,
  IN p_percentage DECIMAL(5,2),
  OUT p_new_salary DECIMAL(10,2)
)
BEGIN
  IF p_percentage <= 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Percentage must be positive';
  END IF;
  UPDATE employees
  SET salary = salary + (salary * p_percentage / 100)
  WHERE employee_id = p_employee_id;
  SELECT salary INTO p_new_salary FROM employees WHERE employee_id = p_employee_id;
END //
DELIMITER ;

-- 7. EXECUTION EXAMPLES
SELECT fn_order_total(1) AS order_1_total;
SELECT employee_name, fn_employee_grade(salary) AS salary_grade FROM employees;
CALL sp_get_employees_by_department(1);
CALL sp_place_order(2,4,2,2);
CALL sp_update_employee_salary(3,10,@new_salary);
SELECT @new_salary AS updated_salary;

-- 8. REPORT QUERIES
SELECT c.customer_name, COUNT(DISTINCT o.order_id) AS total_orders,
       COALESCE(SUM(CASE WHEN o.status <> 'CANCELLED' THEN oi.quantity*oi.unit_price ELSE 0 END),0) AS total_spent
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
LEFT JOIN order_items oi ON o.order_id = oi.order_id
GROUP BY c.customer_id, c.customer_name
ORDER BY total_spent DESC;

SELECT cat.category_name, SUM(oi.quantity) AS units_sold,
       SUM(oi.quantity * oi.unit_price) AS revenue
FROM categories cat
JOIN products p ON cat.category_id = p.category_id
JOIN order_items oi ON p.product_id = oi.product_id
JOIN orders o ON oi.order_id = o.order_id
WHERE o.status <> 'CANCELLED'
GROUP BY cat.category_id, cat.category_name
ORDER BY revenue DESC;
