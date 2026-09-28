use e_commerce;

create table Users(
id int primary key auto_increment,
first_name varchar(50) not null,
last_name varchar(50) not null,
email varchar(100) unique not null
);

create table Orders(
id int primary key auto_increment,
user_id int not null,
foreign key (user_id) references Users(id),
shipping_address varchar(150) not null,
city varchar(50) not null,
total_bill decimal(10,2) not null,
status enum('pending', 'shipped', 'delivered')
);

create table products(
product_id int primary key auto_increment,
name varchar(100) not null,
price decimal(10,2)
);

CREATE TABLE Order_Items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

show tables;




select * from users u inner join orders o on u.id = o.user_id where u.id =1;

select CONCAT(u.first_name, ' ' , u.last_name), o.id from users u left join orders o on o.user_id = u.id;

delete o from orders o join users u on u.id = o.user_id where u.id = 6;

select sum(oi.unit_price) total_bill , CONCAT(u.first_name, ' ', u.last_name) from order_items oi join orders o on oi.order_id = o.id join users u on u.id = o.user_id group by oi.order_id having total_bill>300;

DELIMITER //

CREATE PROCEDURE getOrdersForUser(
    IN u_email VARCHAR(150)
)
BEGIN
    SELECT o.*
    FROM Orders o
    JOIN Users u
        ON u.id = o.user_id
    WHERE u.email = u_email;
END ;

DELIMITER ;
DELIMITER ;

call getOrdersForUser('john.doe@gmail.com');

DELIMITER //

CREATE FUNCTION total_spend(
    u_id INT
)
RETURNS DECIMAL(12,2)
READS SQL DATA
BEGIN
    DECLARE total DECIMAL(12,2);

    SELECT COALESCE(SUM(total_bill), 0)
    INTO total
    FROM Orders
    WHERE user_id = u_id;

    RETURN total;
END //

DELIMITER ;

DELIMITER //

CREATE FUNCTION total_spend(
    u_id INT
)
RETURNS DECIMAL(12,2)
READS SQL DATA
BEGIN
    DECLARE total DECIMAL(12,2);

    SELECT COALESCE(SUM(total_bill), 0)
    INTO total
    FROM Orders
    WHERE user_id = u_id;

    RETURN total;
END //

DELIMITER ;

SELECT total_spend(1);