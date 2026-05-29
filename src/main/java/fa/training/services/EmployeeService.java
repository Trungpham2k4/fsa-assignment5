package fa.training.services;

import fa.training.entities.Department;
import fa.training.entities.Employee;
import fa.training.entities.HourlyEmployee;
import fa.training.entities.SalariedEmployee;
import fa.training.utils.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private final List<Employee> employeeList;
    public EmployeeService() {
        employeeList = new ArrayList<>();
    }

    public void add(Employee employee) {
        employeeList.add(employee);
    }

    public List<Employee> findAll() {
        return employeeList;
    }

    public List<Employee> findByFullName(String firstName, String lastName) {
        return employeeList.stream()
                .filter(employee -> employee.getFirstName().equals(firstName) && employee.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public List<String> findAllSSNs() {
        return employeeList.stream().map(Employee::getSsn).collect(Collectors.toList());
    }

    public boolean loadFromFile(DepartmentService departmentService) {
        Path path = Paths.get(Constants.DATA_EMPLOYEE_PATH);
        if(!Files.exists(path)) {
            return false;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.DATA_EMPLOYEE_PATH))){
            br.lines().forEach(line -> {;
                String[] parts = line.split(",");
                if(parts.length > 0) {
                    String type = parts[0];
                    Employee employee = null;
                    switch (type) {
                        case "HourlyEmployee":
                            if(parts.length == 10) {
                                employee = new HourlyEmployee(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6],
                                        Double.parseDouble(parts[7]), Double.parseDouble(parts[8]));
                                String departmentName = parts[9];
                                departmentService.addEmployeeToDepartment(departmentName, employee);
                            }
                            break;
                        case "SalariedEmployee":
                            if(parts.length == 11) {
                                employee = new SalariedEmployee(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6],
                                        Double.parseDouble(parts[7]), Double.parseDouble(parts[8]), Double.parseDouble(parts[9]));
                                String departmentName = parts[10];
                                departmentService.addEmployeeToDepartment(departmentName, employee);
                            }
                            break;
                    }
                    if(employee != null) {
                        employeeList.add(employee);
                    }
                }
            });
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public boolean saveToFile(DepartmentService departmentService){
        Path path = Paths.get(Constants.DATA_EMPLOYEE_PATH);
        try{
            if(path.getParent() != null && !Files.exists(path.getParent())){
                Files.createDirectories(path.getParent());
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.DATA_EMPLOYEE_PATH))){
                for (Department department : departmentService.findAll()) {
                    for(Employee employee : department.getListOfEmployee()){
                        bw.write(employee.toString());
                        bw.write("," + department.getDepartmentName());
                        bw.newLine();
                    }
                }
                return true;
            }
        }catch (IOException e){
            return false;
        }
    }

}
