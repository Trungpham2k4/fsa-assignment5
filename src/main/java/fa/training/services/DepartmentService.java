package fa.training.services;

import fa.training.entities.Department;
import fa.training.entities.Employee;
import fa.training.utils.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentService {
    private final List<Department> departments;
    public DepartmentService() {
        departments = new ArrayList<>();
    }
    public boolean addEmployeeToDepartment(String departmentName, Employee employee) {
        Department department = departments.stream()
                .filter(dep -> dep.getDepartmentName().equals(departmentName))
                .findFirst().orElse(null);
        if(department == null) {
            return false;
        }
        department.getListOfEmployee().add(employee);
        return true;
    }
    public void loadDepartments() {
        List<String> departmentNames = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(Constants.INPUT_DEPARTMENT_PATH))) {
            br.lines().forEach(departmentNames::add);
        }catch (IOException e){
            e.printStackTrace();
        }
        departmentNames.forEach(departmentName -> {
            Department department = new Department(departmentName);
            departments.add(department);
        });
        System.out.println("Loaded " + departments.size() + " departments");
    }
    public List<String> findAllNames(){
        return departments.stream().map(Department::getDepartmentName).collect(Collectors.toList());
    }
    public List<Employee> findAllEmployees(String departmentName){
        Department department = departments.stream().filter(dep -> dep.getDepartmentName().equals(departmentName)).findFirst().orElse(null);
        if(department == null) {
            return new ArrayList<>();
        }
        return department.getListOfEmployee();
    }
}
