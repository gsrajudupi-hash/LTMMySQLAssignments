CREATE DATABASE ecommerce_db;
USE ecommerce_db;

CREATE TABLE categories (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(50),
    registration_date DATE NOT NULL
);

CREATE TABLE products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_product_category
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id),

    CONSTRAINT chk_product_price
        CHECK (price >= 0),

    CONSTRAINT chk_product_stock
        CHECK (stock_quantity >= 0)
);


CREATE TABLE orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    order_date DATE NOT NULL,
    order_status VARCHAR(30) NOT NULL DEFAULT 'PLACED',

    CONSTRAINT fk_order_customer
        FOREIGN KEY (customer_id)
        REFERENCES customers(customer_id)
);

CREATE TABLE order_items (
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_item_order
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id),

    CONSTRAINT fk_item_product
        FOREIGN KEY (product_id)
        REFERENCES products(product_id),

    CONSTRAINT chk_item_quantity
        CHECK (quantity > 0),

    CONSTRAINT chk_item_price
        CHECK (unit_price >= 0)
);

CREATE TABLE payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL UNIQUE,
    payment_date DATE,
    payment_method VARCHAR(30),
    payment_status VARCHAR(30) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id)
        REFERENCES orders(order_id),

    CONSTRAINT chk_payment_amount
        CHECK (amount >= 0)
);

INSERT INTO categories (category_name)
VALUES
    ('Electronics'),
    ('Books'),
    ('Clothing'),
    ('Home Appliances');
    
    
    INSERT INTO customers
    (customer_name, email, city, registration_date)
VALUES
    ('Ravi Kumar', 'ravi@gmail.com', 'Hyderabad', '2026-01-10'),
    ('Priya Sharma', 'priya@gmail.com', 'Mumbai', '2026-02-15'),
    ('Amit Patel', 'amit@gmail.com', 'Pune', '2026-03-20'),
    ('Sneha Reddy', 'sneha@gmail.com', 'Hyderabad', '2026-04-12'),
    ('Rahul Verma', 'rahul@gmail.com', 'Delhi', '2026-05-18');
    
    INSERT INTO products
    (product_name, category_id, price, stock_quantity)
VALUES
    ('Laptop', 1, 65000.00, 10),
    ('Mobile Phone', 1, 25000.00, 20),
    ('Java Programming Book', 2, 800.00, 50),
    ('Formal Shirt', 3, 1500.00, 30),
    ('Microwave Oven', 4, 12000.00, 8),
    ('Wireless Mouse', 1, 1000.00, 40);
    
    INSERT INTO orders
    (customer_id, order_date, order_status)
VALUES
    (1, '2026-09-01', 'DELIVERED'),
    (2, '2026-09-03', 'SHIPPED'),
    (1, '2026-09-05', 'PLACED'),
    (3, '2026-09-08', 'DELIVERED');
    
    INSERT INTO order_items
    (order_id, product_id, quantity, unit_price)
VALUES
    (1, 1, 1, 65000.00),
    (1, 6, 2, 1000.00),
    (2, 2, 1, 25000.00),
    (2, 3, 2, 800.00),
    (3, 4, 3, 1500.00),
    (4, 5, 1, 12000.00);
    
    INSERT INTO payments
    (order_id, payment_date, payment_method, payment_status, amount)
VALUES
    (1, '2026-09-01', 'CARD', 'PAID', 67000.00),
    (2, '2026-09-03', 'UPI', 'PAID', 26600.00),
    (4, '2026-09-08', 'CARD', 'PAID', 12000.00);
    
    
    /*Queries Using Joins*/
    
    SELECT
    o.order_id,
    c.customer_name,
    c.city,
    o.order_date,
    o.order_status
FROM orders o
INNER JOIN customers c
    ON o.customer_id = c.customer_id;
    
    
    /* Display complete order details */
    
    SELECT
    o.order_id,
    c.customer_name,
    p.product_name,
    oi.quantity,
    oi.unit_price,
    oi.quantity * oi.unit_price AS item_total
FROM orders o
INNER JOIN customers c
    ON o.customer_id = c.customer_id
INNER JOIN order_items oi
    ON o.order_id = oi.order_id
INNER JOIN products p
    ON oi.product_id = p.product_id
ORDER BY o.order_id;


/* Display products with category */

SELECT
p.product_id,
p.product_name,
c.category_name,
p.price,
p.stock_quantity
FROM products p
INNER JOIN categories c
ON p.category_id = c.category_id;

/* Find customers who have not placed orders */

SELECT
    c.customer_id,
    c.customer_name,
    c.city
FROM customers c
LEFT JOIN orders o
    ON c.customer_id = o.customer_id
WHERE o.order_id IS NULL;


/* Find products that have never been ordered */

SELECT
    p.product_id,
    p.product_name,
    p.price
FROM products p
LEFT JOIN order_items oi
    ON p.product_id = oi.product_id
WHERE oi.order_item_id IS NULL;

/* Calculate total value of every order */

SELECT
    o.order_id,
    c.customer_name,
    SUM(oi.quantity * oi.unit_price) AS order_total
FROM orders o
INNER JOIN customers c
    ON o.customer_id = c.customer_id
INNER JOIN order_items oi
    ON o.order_id = oi.order_id
GROUP BY
    o.order_id,
    c.customer_name;
    
    
    /* Subqueries */
    
    SELECT
product_id,
product_name,
price
FROM products
WHERE price > (
SELECT AVG(price)
FROM products
);

/* Customers who placed at least one order */

SELECT
    customer_id,
    customer_name
FROM customers
WHERE customer_id IN (
    SELECT customer_id
    FROM orders
);


/* Customers who have not placed orders */

SELECT
    c.customer_id,
    c.customer_name
FROM customers c
WHERE NOT EXISTS (
    SELECT 1
    FROM orders o
    WHERE o.customer_id = c.customer_id
);

/* Most expensive product */

SELECT
    product_id,
    product_name,
    price
FROM products
WHERE price = (
    SELECT MAX(price)
    FROM products
);

/* Customer who spent the most */

SELECT
    c.customer_id,
    c.customer_name,
    SUM(oi.quantity * oi.unit_price) AS total_spent
FROM customers c
JOIN orders o
    ON c.customer_id = o.customer_id
JOIN order_items oi
    ON o.order_id = oi.order_id
GROUP BY
    c.customer_id,
    c.customer_name
HAVING total_spent = (
    SELECT MAX(customer_total)
    FROM (
        SELECT
            SUM(oi2.quantity * oi2.unit_price) AS customer_total
        FROM orders o2
        JOIN order_items oi2
            ON o2.order_id = oi2.order_id
        GROUP BY o2.customer_id
    ) AS customer_totals
);


/* Stored Procedures */

/* Get orders by customer */


DELIMITER //

CREATE PROCEDURE GetOrdersByCustomer(
    IN p_customer_id INT
)
BEGIN
    SELECT
        o.order_id,
        o.order_date,
        o.order_status,
        SUM(oi.quantity * oi.unit_price) AS order_total
    FROM orders o
    JOIN order_items oi
        ON o.order_id = oi.order_id
    WHERE o.customer_id = p_customer_id
    GROUP BY
        o.order_id,
        o.order_date,
        o.order_status;
END //

DELIMITER ;

CALL GetOrdersByCustomer(1);


/* Add a new customer */

DELIMITER //

CREATE PROCEDURE AddCustomer(
    IN p_name VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_city VARCHAR(50)
)
BEGIN
    INSERT INTO customers (
        customer_name,
        email,
        city,
        registration_date
    )
    VALUES (
        p_name,
        p_email,
        p_city,
        CURRENT_DATE()
    );

    SELECT LAST_INSERT_ID() AS new_customer_id;
END //

DELIMITER ;



CALL AddCustomer(
    'Kiran Rao',
    'kiran@gmail.com',
    'Bengaluru'
);


/* Update product stock using INOUT */

DELIMITER //

CREATE PROCEDURE UpdateProductStock(
    IN p_product_id INT,
    IN p_quantity_sold INT,
    INOUT p_remaining_stock INT
)
BEGIN
    DECLARE current_stock INT;

    SELECT stock_quantity
    INTO current_stock
    FROM products
    WHERE product_id = p_product_id;

    IF current_stock >= p_quantity_sold THEN

        UPDATE products
        SET stock_quantity = stock_quantity - p_quantity_sold
        WHERE product_id = p_product_id;

        SET p_remaining_stock =
            current_stock - p_quantity_sold;
    ELSE
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Insufficient product stock';
    END IF;
END //

DELIMITER ;


SET @remaining_stock = 0;

CALL UpdateProductStock(
    2,
    3,
    @remaining_stock
);

SELECT @remaining_stock;


/* Stored Functions */

/* Calculate order total */

DELIMITER //

CREATE FUNCTION CalculateOrderTotal(
    p_order_id INT
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE total_amount DECIMAL(12,2);

    SELECT COALESCE(
        SUM(quantity * unit_price),
        0
    )
    INTO total_amount
    FROM order_items
    WHERE order_id = p_order_id;

    RETURN total_amount;
END //

DELIMITER ;

SELECT CalculateOrderTotal(1) AS order_total;


SELECT
    order_id,
    order_date,
    CalculateOrderTotal(order_id) AS order_total
FROM orders;

/* Calculate discount */

DELIMITER //

CREATE FUNCTION CalculateDiscount(
    p_amount DECIMAL(12,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
NO SQL
BEGIN
    DECLARE discount DECIMAL(12,2);

    IF p_amount >= 50000 THEN
        SET discount = p_amount * 0.10;
    ELSEIF p_amount >= 20000 THEN
        SET discount = p_amount * 0.05;
    ELSE
        SET discount = 0;
    END IF;

    RETURN discount;
END //

DELIMITER ;


SELECT
    CalculateDiscount(67000) AS discount;
    
    /*Final Report Query */
    
    SELECT
    o.order_id,
    c.customer_name,
    c.city,
    o.order_date,
    o.order_status,
    COUNT(oi.order_item_id) AS number_of_items,
    CalculateOrderTotal(o.order_id) AS gross_amount,
    CalculateDiscount(
        CalculateOrderTotal(o.order_id)
    ) AS discount,
    CalculateOrderTotal(o.order_id)
        - CalculateDiscount(
            CalculateOrderTotal(o.order_id)
        ) AS final_amount,
    COALESCE(pay.payment_status, 'NOT PAID')
        AS payment_status
FROM orders o
JOIN customers c
    ON o.customer_id = c.customer_id
JOIN order_items oi
    ON o.order_id = oi.order_id
LEFT JOIN payments pay
    ON o.order_id = pay.order_id
GROUP BY
    o.order_id,
    c.customer_name,
    c.city,
    o.order_date,
    o.order_status,
    pay.payment_status
ORDER BY o.order_date DESC;


