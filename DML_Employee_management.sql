# Joins

# Query to displays employee information along with their departments

SELECT E.EmployeeName, D.DepartmentName
FROM Employees E
INNER JOIN Departments D
ON E.DepartmentID = D.DepartmentID;


# Query to know manager of each employee

SELECT E.EmployeeName AS Employee,
       M.EmployeeName AS Manager
FROM Employees E
LEFT JOIN Employees M
ON E.ManagerID = M.EmployeeID;


# Query to display which employee is working on which project.

SELECT E.EmployeeName, P.ProjectName
FROM Employees E
INNER JOIN Projects P
ON E.EmployeeID = P.EmployeeID;

# Subquery

# Query to display employees above avg salary 

SELECT EmployeeName, Salary
FROM Employees
WHERE Salary >
(
   SELECT AVG(Salary)
   FROM Employees
);


# Query to display Highest Paid employee department wise


SELECT EmployeeName, Salary
FROM Employees E
WHERE Salary =
(
   SELECT MAX(Salary)
   FROM Employees E2
   WHERE E.DepartmentID = E2.DepartmentID
);


# Query to display departments with more than one employee


SELECT DepartmentName
FROM Departments
WHERE DepartmentID IN
(
   SELECT DepartmentID
   FROM Employees
   GROUP BY DepartmentID
   HAVING COUNT(*) > 1
);






