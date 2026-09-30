
/*
Employee Name : Prathamesh Swami Gotur
PSI Number    : 10859573
Case Study    : E-Commerce Database
Topics Used   : Joins, Subqueries, Procedures, Functions
Date          : 30-Sep-2026
*/

CREATE DATABASE ecommerce_db;

USE ecommerce_db;
ecommerce_dbecommerce_db
CREATE TABLE customers(
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    city VARCHAR(50)
);

CREATE TABLE products(
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE orders(
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date DATE,

    FOREIGN KEY(customer_id)
    REFERENCES customers(customer_id)
);

CREATE TABLE order_details(
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    product_id INT,
    quantity INT,

    FOREIGN KEY(order_id)
    REFERENCES orders(order_id),

    FOREIGN KEY(product_id)
    REFERENCES products(product_id)
);

INSERT INTO customers(customer_name,email,city)
VALUES
('Rahul Sharma','rahul@gmail.com','Mumbai'),
('Priya Patil','priya@gmail.com','Pune'),
('Amit Singh','amit@gmail.com','Delhi'),
('Sneha Kulkarni','sneha@gmail.com','Nagpur'),
('Vijay Kumar','vijay@gmail.com','Hyderabad'),
('Neha Verma','neha@gmail.com','Bangalore'),
('Rohit Mehta','rohit@gmail.com','Chennai'),
('Anjali Desai','anjali@gmail.com','Ahmedabad'),
('Karan Joshi','karan@gmail.com','Pune'),
('Pooja Nair','pooja@gmail.com','Kochi');


INSERT INTO products(product_name,price,stock)
VALUES
('Laptop',60000,20),
('Mobile',30000,40),
('Headphone',3000,80),
('Keyboard',1500,100),
('Mouse',800,120),
('Monitor',15000,25),
('Printer',12000,15),
('Tablet',25000,30),
('Smart Watch',7000,50),
('Webcam',2500,60);


INSERT INTO orders(customer_id,order_date)
VALUES
(1,'2026-09-01'),
(2,'2026-09-02'),
(3,'2026-09-03'),
(4,'2026-09-04'),
(5,'2026-09-05'),
(1,'2026-09-06'),
(2,'2026-09-07'),
(6,'2026-09-08'),
(7,'2026-09-09'),
(8,'2026-09-10'),
(9,'2026-09-11'),
(10,'2026-09-12'),
(3,'2026-09-13'),
(4,'2026-09-14'),
(5,'2026-09-15');


INSERT INTO order_details(order_id,product_id,quantity)
VALUES
(1,1,1),
(1,3,2),

(2,2,1),
(2,5,1),

(3,4,3),

(4,6,1),

(5,8,1),

(6,2,2),

(7,7,1),

(8,9,2),

(9,10,1),

(10,1,1),

(11,3,4),

(12,4,2),

(13,5,3),

(14,6,1),

(15,8,2);

--

-- JOIN Queries
-- Customer + Order Details

SELECT
c.customer_name,
o.order_id,
o.order_date
FROM customers c
INNER JOIN orders o
ON c.customer_id=o.customer_id;


-- Customer + Product Purchased

SELECT
c.customer_name,
p.product_name,
od.quantity
FROM customers c
INNER JOIN orders o
ON c.customer_id=o.customer_id

INNER JOIN order_details od
ON o.order_id=od.order_id

INNER JOIN products p
ON od.product_id=p.product_id;

-- Full E-Commerce Report

SELECT
c.customer_name,
o.order_id,
o.order_date,
p.product_name,
p.price,
od.quantity,
(p.price * od.quantity) AS TotalAmount
FROM customers c

INNER JOIN orders o
ON c.customer_id=o.customer_id

INNER JOIN order_details od
ON o.order_id=od.order_id

INNER JOIN products p
ON od.product_id=p.product_id;

-- Subqueries

-- Customers Average Orders

SELECT *
FROM customers
WHERE customer_id IN
(
    SELECT customer_id
    FROM orders
    GROUP BY customer_id
    HAVING COUNT(order_id) >
    (
        SELECT AVG(order_count)
        FROM
        (
            SELECT COUNT(*) AS order_count
            FROM orders
            GROUP BY customer_id
        ) x
    )
);

-- Most Expensive Product

SELECT *
FROM products
WHERE price=
(
    SELECT MAX(price)
    FROM products
);

-- Products Price Average

SELECT *
FROM products
WHERE price >
(
    SELECT AVG(price)
    FROM products
);

-- Stored Procedures

-- Get Orders By Customer

DELIMITER $$

CREATE PROCEDURE GetCustomerOrders(
IN custId INT
)

BEGIN

SELECT
o.order_id,
o.order_date
FROM orders o
WHERE o.customer_id=custId;

END $$

DELIMITER ;


-- Execute

CALL GetCustomerOrders(1);

-- Product Search Procedure

DELIMITER $$

CREATE PROCEDURE GetProduct(
IN pName VARCHAR(100)
)

BEGIN

SELECT *
FROM products
WHERE product_name=pName;

END $$

DELIMITER ;

-- Execute

CALL GetProduct('Laptop');


-- Functions

-- GST Function

DELIMITER $$

CREATE FUNCTION CalculateGST(
amount DECIMAL(10,2)
)

RETURNS DECIMAL(10,2)

DETERMINISTIC

BEGIN

RETURN amount * 0.18;

END $$

DELIMITER;


-- Execute

SELECT
product_name,
price,
CalculateGST(price) AS GST
FROM products;

-- Final Amount Function

DELIMITER $$

CREATE FUNCTION FinalAmount(
amount DECIMAL(10,2)
)

RETURNS DECIMAL(10,2)

DETERMINISTIC

BEGIN

RETURN amount + (amount*0.18);

END $$

DELIMITER;

-- Execute

SELECT
product_name,
price,
FinalAmount(price) AS FinalPrice
FROM products;



