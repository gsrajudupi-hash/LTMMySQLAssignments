SELECT
    e.employee_id,
    e.employee_name,
    m.employee_name AS manager_name,
    d.department_name
FROM employees e
LEFT JOIN employees m
    ON e.manager_id = m.employee_id
INNER JOIN departments d
    ON e.department_id = d.department_id
ORDER BY e.employee_id;