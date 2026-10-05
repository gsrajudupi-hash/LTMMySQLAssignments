package employee.model;

public class Department {

    private int departmentId;
    private String departmentName;
    private int employeeCount;

    public Department() {
    }

    public Department(int departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public Department(int departmentId,
                      String departmentName,
                      int employeeCount) {

        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    @Override
    public String toString() {
        return departmentId + " | "
                + departmentName + " | "
                + employeeCount;
    }
}