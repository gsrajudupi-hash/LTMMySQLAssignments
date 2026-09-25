SELECT
    d.department_name,
    MAX(e.salary) AS maximum_salary
FROM departments d
INNER JOIN employees e
    ON d.department_id = e.department_id
GROUP BY
    d.department_id,
    d.department_name;