package service;

import dao.EmployeeDao;
import dao.EmployeeDaoImpl;
import model.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class EmployeeService {

    private EmployeeDao employeeDao;

    public EmployeeService() {
        employeeDao = new EmployeeDaoImpl();
    }

    // 1. Add Employee
    public void addEmployee(Employee employee)
            throws SQLException {

        employeeDao.addEmployee(employee);
    }

    // 2. View All Employees
    public List<Employee> getAllEmployees()
            throws SQLException {

        return employeeDao.getAllEmployees();
    }

    // 3. Find Employee
    public Employee getEmployeeById(int employeeId)
            throws SQLException {

        return employeeDao.getEmployeeById(employeeId);
    }

    // 4. Update Employee
    public void updateEmployee(Employee employee)
            throws SQLException {

        employeeDao.updateEmployee(employee);
    }

    // 5. Delete Employee
    public boolean deleteEmployee(int employeeId)
            throws SQLException {

        return employeeDao.deleteEmployee(employeeId);
    }

    // 6. Employee Details with Department
    public List<String> getEmployeeDetailsWithDepartment()
            throws SQLException {

        return employeeDao
                .getEmployeeDetailsWithDepartment();
    }

    // 7. Department-wise Employee Count
    public Map<String, Integer>
    getDepartmentWiseEmployeeCount()
            throws SQLException {

        return employeeDao
                .getDepartmentWiseEmployeeCount();
    }

    // 8. Stored Procedure - Salary Update
    public void executeStoredProcedure(
            int departmentId,
            double percentage)
            throws SQLException {

        employeeDao.executeSalaryUpdateProcedure(
                departmentId,
                percentage
        );
    }

    // 8. Stored Procedure - Employee Count
    public int getEmployeeCountByDepartment(
            int departmentId)
            throws SQLException {

        return employeeDao
                .executeEmployeeCountProcedure(
                        departmentId
                );
    }

    // 9. Transfer Employee
    public boolean transferEmployee(
            int employeeId,
            int newDepartmentId)
            throws SQLException {

        return employeeDao.transferEmployee(
                employeeId,
                newDepartmentId
        );
    }

    // 10. Transaction Demonstration
    public void transactionDemo(
            int employeeId,
            int oldDepartmentId,
            int newDepartmentId)
            throws SQLException {

        employeeDao.transferEmployeeWithTransaction(
                employeeId,
                oldDepartmentId,
                newDepartmentId
        );
    }
}