
package assessment1.model;

import java.math.BigDecimal;

public class Employee {

    private int employeeId;
    private String employeeName;
    private String email;
    private BigDecimal salary;
    private int departmentId;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName,
                    String email, BigDecimal salary,
                    int departmentId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", departmentId=" + departmentId +
                '}';
    }
}

