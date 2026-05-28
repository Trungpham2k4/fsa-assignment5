package fa.training.services;

import fa.training.entities.Employee;
import fa.training.utils.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private List<Employee> employeeList;
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

    public boolean saveToFile(){
        Path path = Paths.get(Constants.OUTPUT_EMPLOYEE_PATH);
        try{
            if(path.getParent() != null && !Files.exists(path.getParent())){
                Files.createDirectories(path.getParent());
            }
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.OUTPUT_EMPLOYEE_PATH))){
                for (Employee employee : employeeList) {
                    bw.write(employee.toString());
                    bw.newLine();
                }
                return true;
            }
        }catch (IOException e){
            return false;
        }
    }

}
