#Functions:

# Function to calculate Annual Salary

DELIMITER //

CREATE FUNCTION CalculateAnnualSalary(
    MonthlySalary DECIMAL(10,2)
)
RETURNS DECIMAL(12,2)
DETERMINISTIC
BEGIN
    RETURN MonthlySalary * 12;
END //

DELIMITER ;



SELECT
    EmployeeName,
    Salary,
    CalculateAnnualSalary(Salary) AS AnnualSalary
FROM Employees;


# Function to calculate Employee Bonus (10% of Salary)

DELIMITER //

CREATE FUNCTION CalculateBonus(
    MonthlySalary DECIMAL(10,2)
)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN MonthlySalary * 0.10;
END //

DELIMITER ;

SELECT
    EmployeeName,
    Salary,
    CalculateBonus(Salary) AS Bonus
FROM Employees;