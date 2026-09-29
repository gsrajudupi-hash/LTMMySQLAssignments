
CREATE DATABASE flight_booking_db;
 
USE flight_booking_db;

SHOW DATABASES;

----  Flights Table---

CREATE TABLE flights (
flight_id INT PRIMARY KEY AUTO_INCREMENT,
flight_number VARCHAR(10),
source_city VARCHAR(50),
destination_city VARCHAR(50),
departure_time DATETIME,
arrival_time DATETIME,
total_seats INT,
available_seats INT,
base_fare DECIMAL(10,2)
);

INSERT INTO flights
(flight_number,
source_city,
destination_city,
departure_time,
arrival_time,
total_seats,
available_seats,
base_fare) VALUES('AI101','Mumbai','Delhi','2026-10-10 08:00:00','2026-10-10 10:00:00',180,180,6500),
                 ('6E202','Hyderabad','Mumbai','2026-10-11 09:00:00','2026-10-11 10:30:00',150,150,4500);

SELECT * FROM flights;

------ Passengers TABLE ----

CREATE TABLE passengers (
passenger_id INT PRIMARY KEY AUTO_INCREMENT,
full_name VARCHAR(100),
email VARCHAR(100),
phone VARCHAR(15),
passport_number VARCHAR(20)
);

INSERT INTO passengers
(full_name,email,phone,passport_number) VALUES
('Aarav Sharma','aarav@gmail.com','9876543210','P1001'),
('Diya Patil','diya@gmail.com','9876543211','P1002');

SELECT * FROM passengers;

----- Bookings Table-----

CREATE TABLE bookings (
booking_id INT PRIMARY KEY AUTO_INCREMENT,
passenger_id INT,
flight_id INT,
booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
seats_booked INT,
total_amount DECIMAL(10,2),
booking_status VARCHAR(20)
);

INSERT INTO bookings
(passenger_id,flight_id,seats_booked,total_amount,booking_status)
VALUES
(1,1,2,13000,'CONFIRMED'),
(2,2,1,4500,'CONFIRMED');

SELECT * FROM bookings;

DROP TABLE airports;

SHOW Tables; 

--- join -----

SELECT
b.booking_id,
p.full_name,
f.flight_number
FROM bookings b
INNER JOIN passengers p
ON b.passenger_id = p.passenger_id
INNER JOIN flights f
ON b.flight_id = f.flight_id;

--- subquery ----

SELECT flight_number, base_fare
FROM flights
WHERE base_fare >
(
SELECT AVG(base_fare)
FROM flights
);

--- function --

DELIMITER $$
 
CREATE FUNCTION flight_revenue(fid INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
	BEGIN
	DECLARE revenue DECIMAL(10,2);
		SELECT SUM(total_amount)
		INTO revenue
		FROM bookings
		WHERE flight_id = fid;
	RETURN revenue;
	END$$
DELIMITER ;

SELECT flight_revenue(1);


--- storeProcedure ---

DELIMITER $$ 
CREATE PROCEDURE get_booking_details()
	BEGIN
		SELECT
		b.booking_id,
		p.full_name,
		f.flight_number,
		b.seats_booked,
		b.total_amount,
		b.booking_status
		FROM bookings b
		INNER JOIN passengers p
		ON b.passenger_id = p.passenger_id
		INNER JOIN flights f
		ON b.flight_id = f.flight_id;
	END$$
DELIMITER ;

CALL get_booking_details();