USE ecommerce_db;

CREATE TABLE Customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100),
    city VARCHAR(50)
);

CREATE TABLE Products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100),
    price DECIMAL(10,2)
);

CREATE TABLE Orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    order_date DATE,
    FOREIGN KEY (customer_id)
    REFERENCES Customers(customer_id)
);

CREATE TABLE Order_Items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT,
    product_id INT,
    quantity INT,
    FOREIGN KEY (order_id)
    REFERENCES Orders(order_id),
    FOREIGN KEY (product_id)
    REFERENCES Products(product_id)
);

INSERT INTO Customers(customer_name,city)
VALUES
('Ravi','Chennai'),
('Anusha','Pune'),
('Kiran','Mumbai'),
('Deepika','Delhi'),
('Vikram','Hyderabad'),
('Sneha','Bangalore');

INSERT INTO Products(product_name,price)
VALUES
('Monitor',12000),
('Printer',8000),
('SSD',4500),
('Webcam',2500),
('Speaker',3500),
('Tablet',25000);

INSERT INTO Orders(customer_id,order_date)
VALUES
(1,'2026-09-22'),
(3,'2026-09-22'),
(3,'2026-09-23'),
(4,'2026-09-24'),
(5,'2026-09-24'),
(1,'2026-09-25'),
(2,'2026-09-25');

INSERT INTO Order_Items(order_id,product_id,quantity)
VALUES
(1,1,1),
(1,2,2),

(2,3,1),

(3,4,1),
(3,5,2),

(4,2,1),

(5,1,1),
(5,6,1),

(6,3,2),

(7,5,3);


SELECT * FROM customers;
SELECT * FROM Products;
SELECT * FROM Orders;
SELECT * FROM customers;

DROP TABLE customers;

SELECT c.customer_name,
       o.order_id
FROM Customers c
INNER JOIN Orders o
ON c.customer_id = o.customer_id;

SELECT c.customer_name,
       o.order_id
FROM Customers c
LEFT JOIN Orders o
ON c.customer_id = o.customer_id;

SELECT c.customer_name,
       o.order_id
FROM Customers c
RIGHT JOIN Orders o
ON c.customer_id = o.customer_id;

SELECT c.customer_name,
       p.product_name
FROM Customers c
CROSS JOIN Products p;

# subquery
# Product above average price
SELECT *
FROM Products
WHERE price >
(
   SELECT AVG(price)
   FROM Products
);

#Highest priced product
SELECT *
FROM Products
WHERE price=
(
  SELECT MAX(price)
  FROM Products
);

#Second Highest Price
SELECT MAX(price)
FROM Products
WHERE price <
(
   SELECT MAX(price)
   FROM Products
);

#Stored procedure

DELIMITER //

CREATE PROCEDURE GetCustomerOrders(IN cid INT)
BEGIN
   SELECT *
   FROM Orders
   WHERE customer_id = cid;
END //

DELIMITER ;

#calling statement to call procedure
CALL GetCustomerOrders(1);


#Functions

DELIMITER //

CREATE FUNCTION CalculateTotal(
   qty INT,
   price DECIMAL(10,2)
)
RETURNS DECIMAL(10,2)

DETERMINISTIC

BEGIN
   RETURN qty * price;
END //

DELIMITER ;

#USING FUNCTION below
SELECT product_name,
       price,
       CalculateTotal(2,price) TotalAmount
FROM Products;