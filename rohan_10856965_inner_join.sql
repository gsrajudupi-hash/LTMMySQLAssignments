SELECT
    e.employee_id,
    e.employee_name,
    e.salary,
    d.department_name,
    d.location
FROM employees e
INNER JOIN departments d
    ON e.department_id = d.department_id;