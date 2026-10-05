package com.employee.model;
/**
 * Author   : 10858829
 * Date     : 30 Sept 2026
 * Time     : 9:27:19 am
 * project  : EmployeeManagementJDBC
 */

public class Department {
 
    private int deptId;
    private String deptName;
    private String location;
 
    public Department() {
    }
 
    public Department(int deptId, String deptName, String location) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.location = location;
    }
 
    public int getDeptId() {
        return deptId;
    }
 
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
 
    public String getDeptName() {
        return deptName;
    }
 
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
 
    public String getLocation() {
        return location;
    }
 
    public void setLocation(String location) {
        this.location = location;
    }
 
    @Override
    public String toString() {
        return deptId + " - " + deptName + " - " + location;
    }
}
