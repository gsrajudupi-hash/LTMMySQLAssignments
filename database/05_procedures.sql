DELIMITER //

CREATE PROCEDURE GetEmployeesByDepartment(IN deptId INT)
BEGIN

SELECT *
FROM employee
WHERE department_id = deptId;

END //

DELIMITER ;