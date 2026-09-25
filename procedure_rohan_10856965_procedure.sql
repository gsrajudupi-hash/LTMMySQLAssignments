DELIMITER $$

CREATE PROCEDURE sp_increase_salary(
    IN p_employee_id INT,
    IN p_percentage DECIMAL(5, 2)
)
BEGIN
    IF p_percentage <= 0 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT =
                'Increment percentage must be greater than zero';
    END IF;

    IF NOT EXISTS (
        SELECT 1
        FROM employees
        WHERE employee_id = p_employee_id
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Employee does not exist';
    END IF;

    UPDATE employees
    SET salary = salary + (salary * p_percentage / 100)
    WHERE employee_id = p_employee_id;

    SELECT
        employee_id,
        employee_name,
        salary
    FROM employees
    WHERE employee_id = p_employee_id;
END$$

DELIMITER ;