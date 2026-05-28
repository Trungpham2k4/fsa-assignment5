package fa.training.entities;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentName;
    List<Employee> listOfEmployee;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Employee> getListOfEmployee() {
        return listOfEmployee;
    }

    public void display(){
        System.out.println("Department Name: " + departmentName);
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.listOfEmployee = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentName='" + departmentName + '\'' +
                '}';
    }
}
