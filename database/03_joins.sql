SELECT e.employee_name,
       d.department_name
FROM employee e
INNER JOIN department d
ON e.department_id = d.department_id;