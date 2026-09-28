
/* =====================================================
E-COMMERCE DATABASE PROJECT

===================================================== */


/* Create and use database */
CREATE DATABASE ECommerceDB;

USE ECommerceDB;


/*  Create Customers table */
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY,
    customer_name VARCHAR(50),
    city VARCHAR(50)
);


/*  Create Products table */
CREATE TABLE Products (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(50),
    price DECIMAL(10,2)
);


/*  Create Orders table

Stores customer orders and references

Customers and Products tables using foreign keys.

*/

CREATE TABLE Orders (
    order_id INT PRIMARY KEY,
    customer_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);


/*  Insert sample customer data */
INSERT INTO Customers VALUES
(1,'Yash','Pune'),
(2,'Rahul','Mumbai'),
(3,'Priya','Delhi'),
(4,'Amit','Bangalore');

/*  Insert sample product data */
INSERT INTO Products VALUES
(101,'Laptop',50000),
(102,'Mobile',20000),
(103,'Headphones',3000),
(104,'Keyboard',1500);


/*  Insert sample order data */
INSERT INTO Orders VALUES
(1,1,101,1),
(2,2,102,2),
(3,3,103,3);


/* =====================================================
JOIN QUERY
Purpose:

Display customer name, product purchased,
and quantity ordered using INNER JOIN.
===================================================== */
SELECT c.customer_name,
       p.product_name,
       o.quantity
FROM Customers c
INNER JOIN Orders o
ON c.customer_id = o.customer_id
INNER JOIN Products p
ON o.product_id = p.product_id;


/* =====================================================
LEFT JOIN
Purpose:
Display all customers and their orders.
Customers without orders will also appear.
===================================================== */

SELECT c.customer_name,
       p.product_name,
       o.quantity
FROM Customers c
LEFT JOIN Orders o
ON c.customer_id = o.customer_id
LEFT JOIN Products p
ON o.product_id = p.product_id;

/* =====================================================
RIGHT JOIN
Purpose:
Display all products and related orders.
Products without orders will also appear.
===================================================== */

SELECT c.customer_name,
       p.product_name,
       o.quantity
FROM Orders o
RIGHT JOIN Products p
ON o.product_id = p.product_id
LEFT JOIN Customers c
ON o.customer_id = c.customer_id;

/* =====================================================
GROUP BY
Purpose:
Display total number of orders placed by each customer.
===================================================== */

SELECT c.customer_name,
       COUNT(o.order_id) AS Total_Orders
FROM Customers c
LEFT JOIN Orders o
ON c.customer_id = o.customer_id
GROUP BY c.customer_name;


/* =====================================================
HAVING CLAUSE
Purpose:
Display customers whose total order quantity is greater than 1.
===================================================== */

SELECT c.customer_name,
       SUM(o.quantity) AS Total_Quantity
FROM Customers c
JOIN Orders o
ON c.customer_id = o.customer_id
GROUP BY c.customer_name
HAVING SUM(o.quantity) > 1;


/* =================================*===================
EXISTS SUBQUER*
Purpose:
Display customers who ha*e placed orders.
=================*==================================* */

SELECT customer_name
FROM Customers c
WHERE EXISTS (
    SELECT 1
    FROM Orders o
    WHERE c.customer_id = o.customer_id
);


/* =====================================================
SUBQUERY

Purpose:
Display products whose price is greater than
the average product price.
===================================================== */
SELECT product_name, price
FROM Products
WHERE price >
(
    SELECT AVG(price)
    FROM Products
);


/* =====================================================
STORED PROCEDURE
Purpose:
Retrieve all customer records.
===================================================== */
DELIMITER $$

CREATE PROCEDURE GetCustomers()
BEGIN
    SELECT * FROM Customers;
END $$

DELIMITER ;



CALL GetCustomers();



/* =====================================================
FUNCTION

Purpose:
Calculate total order amount using
quantity × price.
===================================================== */

DELIMITER $$

CREATE FUNCTION CalculateAmount(
    qty INT,
    price DECIMAL(10,2)
)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN qty * price;
END $$

DELIMITER ;


/* Function Testing */
SELECT CalculateAmount(2,50000);


/* =====================================================
FUNCTION WITH JOIN
Purpose:
Display customer, product and total amount.
===================================================== */
SELECT c.customer_name,
       p.product_name,
       CalculateAmount(o.quantity,p.price) AS Total_Amount
FROM Customers c
JOIN Orders o
ON c.customer_id=o.customer_id
JOIN Products p
ON o.product_id=p.product_id;

SELECT * FROM Customers;
