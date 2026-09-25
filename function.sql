DELIMITER $$

CREATE FUNCTION fn_calculate_discounted_amount(
    p_price DECIMAL(12, 2),
    p_quantity INT,
    p_discount_percentage DECIMAL(5, 2)
)
RETURNS DECIMAL(14, 2)
DETERMINISTIC
NO SQL
BEGIN
    DECLARE v_total DECIMAL(14, 2);

    SET v_total =
        p_price
        * p_quantity
        * (1 - p_discount_percentage / 100);

    RETURN ROUND(v_total, 2);
END$$

DELIMITER ;