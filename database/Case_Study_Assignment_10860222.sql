
-- ================ CREATE AND USE DATABASE ================
DROP DATABASE IF EXISTS dating_app_DB;
CREATE DATABASE dating_app_DB;
USE dating_app_DB;

-- ================= DDL: CREATE TABLES =================

-- Stores user profile details.
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    age INT NOT NULL,
    gender ENUM('Male', 'Female', 'Other') NOT NULL,
    city VARCHAR(60) NOT NULL,
    status ENUM('Active', 'Inactive') DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_user_age CHECK (age >= 18)
);

-- Master list of interests.
CREATE TABLE interests (
    interest_id INT PRIMARY KEY AUTO_INCREMENT,
    interest_name VARCHAR(60) NOT NULL UNIQUE
);

-- Many-to-many relationship between users and interests.
CREATE TABLE user_interests (
    user_id INT NOT NULL,
    interest_id INT NOT NULL,
    PRIMARY KEY (user_id, interest_id),
    CONSTRAINT fk_ui_user
        FOREIGN KEY (user_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_ui_interest
        FOREIGN KEY (interest_id) REFERENCES interests(interest_id)
        ON DELETE CASCADE
);

-- ================= DML: INSERT DATA =================

INSERT INTO users
    (full_name, email, age, gender, city)
VALUES
    ('Srikanth', 'srikanth@example.com', 28, 'Male', 'Hyderabad'),
    ('Siri', 'siri@example.com', 26, 'Female', 'Hyderabad'),
    ('Bindu', 'bindu@example.com', 27, 'Female', 'Bengaluru'),
    ('Nani', 'nani@example.com', 29, 'Male', 'Chennai'),
    ('Ramu', 'ramu@example.com', 30, 'Male', 'Vijayawada');

INSERT INTO interests (interest_name)
VALUES
    ('Movies'),
    ('Travel'),
    ('Music'),
    ('Cooking'),
    ('Books'),
    ('Photography'),
    ('Fitness'),
    ('Cricket'),
    ('Gardening');
    
INSERT INTO user_interests (user_id, interest_id)
VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 2), (2, 3), (2, 4), (2, 5),
    (3, 2), (3, 6), (3, 7),
    (4, 1), (4, 3), (4, 8),
    (5, 2), (5, 4), (5, 9);

-- ================= DML OPERATIONS =================

-- Display all active users.
SELECT *
FROM users
WHERE status = 'Active';

-- Update Ramu's city.
UPDATE users
SET city = 'Guntur'
WHERE email = 'ramu@example.com';

-- ================= JOINS =================

-- INNER JOIN: Display users with their interests.
SELECT
    u.user_id,
    u.full_name,
    i.interest_name
FROM users u
INNER JOIN user_interests ui
    ON u.user_id = ui.user_id
INNER JOIN interests i
    ON ui.interest_id = i.interest_id
ORDER BY u.user_id, i.interest_name;


-- JOIN with GROUP BY: Count shared interests between user pairs.
SELECT
    u1.full_name AS first_user,
    u2.full_name AS second_user,
    COUNT(*) AS common_interests
FROM user_interests ui1
INNER JOIN user_interests ui2
    ON ui1.interest_id = ui2.interest_id
   AND ui1.user_id < ui2.user_id
INNER JOIN users u1
    ON ui1.user_id = u1.user_id
INNER JOIN users u2
    ON ui2.user_id = u2.user_id
GROUP BY u1.user_id, u1.full_name, u2.user_id, u2.full_name
ORDER BY common_interests DESC, first_user, second_user;

-- ================= SUBQUERIES =================

-- Users older than the average user age.
SELECT user_id, full_name, age, city
FROM users
WHERE age > (SELECT AVG(age) FROM users);

-- User or users with the highest number of interests.
SELECT
    u.user_id,
    u.full_name,
    COUNT(ui.interest_id) AS interest_count
FROM users u
INNER JOIN user_interests ui
    ON u.user_id = ui.user_id
GROUP BY u.user_id, u.full_name
HAVING COUNT(ui.interest_id) = (
    SELECT MAX(interest_total)
    FROM (
        SELECT COUNT(*) AS interest_total
        FROM user_interests
        GROUP BY user_id
    ) AS interest_counts
);

-- Users interested in Travel.
SELECT user_id, full_name, city
FROM users
WHERE user_id IN (
    SELECT ui.user_id
    FROM user_interests ui
    WHERE ui.interest_id = (
        SELECT interest_id
        FROM interests
        WHERE interest_name = 'Travel'
    )
);

-- ================= STORED PROCEDURES =================

DELIMITER //

-- Get active users by city.
DROP PROCEDURE IF EXISTS GetUsersByCity //
CREATE PROCEDURE GetUsersByCity(IN p_city VARCHAR(60))
BEGIN
    SELECT user_id, full_name, email, age, gender, city
    FROM users
    WHERE city = p_city
      AND status = 'Active'
    ORDER BY full_name;
END //

-- Get all interests of a specific user.
DROP PROCEDURE IF EXISTS GetUserInterests //
CREATE PROCEDURE GetUserInterests(IN p_user_id INT)
BEGIN
    SELECT u.full_name, i.interest_name
    FROM users u
    INNER JOIN user_interests ui
        ON u.user_id = ui.user_id
    INNER JOIN interests i
        ON ui.interest_id = i.interest_id
    WHERE u.user_id = p_user_id
    ORDER BY i.interest_name;
END //

-- Add a new adult user.
DROP PROCEDURE IF EXISTS AddNewUser //
CREATE PROCEDURE AddNewUser(
    IN p_full_name VARCHAR(100),
    IN p_email VARCHAR(120),
    IN p_age INT,
    IN p_gender VARCHAR(10),
    IN p_city VARCHAR(60)
)
BEGIN
    IF p_age < 18 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'User must be at least 18 years old.';
    ELSEIF p_gender NOT IN ('Male', 'Female', 'Other') THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Gender must be Male, Female, or Other.';
    ELSE
        INSERT INTO users
            (full_name, email, age, gender, city)
        VALUES
            (p_full_name, p_email, p_age, p_gender, p_city);
    END IF;
END //


DELIMITER ;

-- Procedure execution examples.
CALL GetUsersByCity('Hyderabad');
CALL GetUserInterests(1);

-- ================= USER-DEFINED FUNCTIONS =================

DELIMITER //

-- Return the number of interests selected by a user.
DROP FUNCTION IF EXISTS GetInterestCount //
CREATE FUNCTION GetInterestCount(p_user_id INT)
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE total_interests INT DEFAULT 0;

    SELECT COUNT(*)
    INTO total_interests
    FROM user_interests
    WHERE user_id = p_user_id;

    RETURN total_interests;
END //

-- Return a display label containing name, age, and city.
DROP FUNCTION IF EXISTS GetProfileLabel //
CREATE FUNCTION GetProfileLabel(
    p_name VARCHAR(100),
    p_age INT,
    p_city VARCHAR(60)
)
RETURNS VARCHAR(200)
DETERMINISTIC
BEGIN
    RETURN CONCAT(p_name, ', ', p_age, ' - ', p_city);
END //

DELIMITER ;

-- Function execution examples.
SELECT
    user_id,
    full_name,
    GetInterestCount(user_id) AS total_interests
FROM users;

SELECT
    GetProfileLabel(full_name, age, city) AS profile_label
FROM users;


-- ================= FINAL REPORT QUERY Displays each user, their interests =================

SELECT
    u.user_id,
    u.full_name,
    u.email,
    u.age,
    u.city,
    GROUP_CONCAT(DISTINCT i.interest_name
                 ORDER BY i.interest_name SEPARATOR ', ') AS interests
FROM users u
LEFT JOIN user_interests ui
    ON u.user_id = ui.user_id
LEFT JOIN interests i
    ON ui.interest_id = i.interest_id
GROUP BY
    u.user_id, u.full_name, u.email, u.age, u.city
ORDER BY u.user_id;

-- ================= END =================
