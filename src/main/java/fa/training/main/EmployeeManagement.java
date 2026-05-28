package fa.training.main;

import fa.training.entities.Department;
import fa.training.entities.Employee;
import fa.training.entities.HourlyEmployee;
import fa.training.entities.SalariedEmployee;
import fa.training.services.DepartmentService;
import fa.training.services.EmployeeService;
import fa.training.utils.Validator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;


public class EmployeeManagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EmployeeService employeeService = new EmployeeService();
    private static final DepartmentService departmentService = new DepartmentService();

    public static void showMenu(){
        String menu = """
                =====EMPLOYEE MANAGEMENT SYSTEM=====;
                1. Add an employee
                2. Display employees
                3. Classify employees
                4. Search book by(department, emp's name)
                5. Report
                6. Exit
                
                Please choose function you'd like to do:
                """;
        System.out.print(menu);
    }

    public static void showEmployeeType(){
        String menu = """
                You are employee:
                1. Salaried employee
                2. Hourly employee
                """;
        System.out.print(menu);
    }

    public static void showSearchOption(){
        String menu = """
                You want to search by:
                1. Department name
                2. Employee name
                """;
        System.out.print(menu);
    }

    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() {
        if(departmentService.loadDepartments()){
            System.out.println("Department loaded successfully");
        }else{
            System.out.println("Failed to load departments");
            return;
        }
        int choice;
        do{
            showMenu();
            choice = inputValidOption(1,6);
            switch(choice){
                case 1 -> addEmployee();
                case 2 -> displayEmployees();
                case 3 -> classifyEmployees();
                case 4 -> searchEmployees();
                case 5 -> report();
            }
        }while (choice != 6);
        saveData();
    }

    private static void addEmployee(){
        System.out.println("----Enter employee information----");
        showEmployeeType();
        int employeeType = inputValidOption(1,2);
        String departmentName = inputValidDepartmentName("Enter department name: ", "Department name does not exist. Please try again.");
        String ssn = inputNonExistSSN("Enter SSN: ", "This employee does exist. Please try again.");
        String firstName = inputValidStringField("Enter First Name: ", "Invalid First Name. Please try again", Validator::isNotBlank);
        String lastName = inputValidStringField("Enter Last Name: ", "Invalid Last Name. Please try again", Validator::isNotBlank);
        String birthDate = inputValidStringField("Enter birth date (dd/MM/yyyy): ", "Invalid birth date. Please try again.", Validator::isValidDate);
        String phoneNumber = inputValidStringField("Enter Phone Number: ", "Invalid phone number. Please try again", Validator::isValidPhoneNumber);
        String email = inputValidStringField("Enter Email: ", "Invalid email. Please try again.", Validator::isValidEmail);
        switch(employeeType){
            case 1 -> addSalariedEmployee(departmentName, ssn, firstName, lastName, birthDate, phoneNumber, email);
            case 2 -> addHourlyEmployee(departmentName, ssn, firstName, lastName, birthDate, phoneNumber, email);
        }
    }
    private static void addSalariedEmployee(
            String departmentName,
            String ssn,
            String firstName,
            String lastName,
            String birthDate,
            String phoneNumber,
            String email
    ){
        double commissionRate = inputValidDoubleField("Enter commission rate: ", "Invalid commission rate. Please try again", Validator::isValidRate);
        double grossSales = inputValidDoubleField("Enter gross sales: ", "Invalid gross sales. Please try again", Validator::isValidPositiveNumber);
        double basicSalary = inputValidDoubleField("Enter basic salary: ", "Invalid basic salary. Please try again", Validator::isValidPositiveNumber);
        Employee employee = new SalariedEmployee(
                ssn,
                firstName,
                lastName,
                birthDate,
                phoneNumber,
                email,
                commissionRate,
                grossSales,
                basicSalary
        );
        employeeService.add(employee);
        if(departmentService.addEmployeeToDepartment(departmentName, employee)){
            System.out.println("Employee added successfully");
        }
    }
    private static void addHourlyEmployee(
            String departmentName,
            String ssn,
            String firstName,
            String lastName,
            String birthDate,
            String phoneNumber,
            String email
    ){
        double wage = inputValidDoubleField("Enter wage: ", "Invalid wage. Please try again", Validator::isValidPositiveNumber);
        double workingHours = inputValidDoubleField("Enter working hours: ", "Invalid working hours. Please try again", Validator::isValidPositiveNumber);
        Employee employee = new HourlyEmployee(
                ssn,
                firstName,
                lastName,
                birthDate,
                phoneNumber,
                email,
                wage,
                workingHours
        );
        employeeService.add(employee);
        if(departmentService.addEmployeeToDepartment(departmentName, employee)){
            System.out.println("Employee added successfully.");
        }
    }

    private static void displayEmployees(){
        System.out.println("----List of employees----");
        List<Employee> employees = employeeService.findAll();
        if(employees.isEmpty()){
            System.out.println("No employee found");
        }else{
            for(Employee employee : employees){
                employee.display();
            }
        }
    }
    private static void classifyEmployees(){
        System.out.println("----Classification of employees----");
        List<Employee> employees = employeeService.findAll();
        for(Employee employee : employees){
            if(employee instanceof SalariedEmployee salariedEmployee){
                System.out.println("Employee name: " + employee.getFirstName() + " " + employee.getLastName());
                System.out.println("Type: " + salariedEmployee.getClass().getName());
            }else if(employee instanceof HourlyEmployee hourlyEmployee){
                System.out.println("Employee name: " + employee.getFirstName() + " " + employee.getLastName());
                System.out.println("Type: " + hourlyEmployee.getClass().getName());
            }
        }
        System.out.println("Use instance of to classify employees successfully");
    }
    private static void searchEmployees(){
        System.out.println("----Search employee----");
        showSearchOption();
        int searchOption = inputValidOption(1,2);
        switch (searchOption){
            case 1 -> searchEmployeesInDepartment();
            case 2 -> searchEmployeesByFullName();
        }
    }

    private static void searchEmployeesInDepartment(){
        String departmentName = inputValidDepartmentName("Enter department name: ", "Invalid department name. Please try again");
        List<Employee> employees = departmentService.findAllEmployees(departmentName);
        if(employees.isEmpty()){
            System.out.println("No employee found in department: " + departmentName);
        }else{
            for(Employee employee : employees){
                employee.display();
            }
        }
    }

    private static void searchEmployeesByFullName(){
        String firstName = inputValidStringField("Enter First Name: ", "Invalid First Name. Please try again", Validator::isNotBlank);
        String lastName = inputValidStringField("Enter Last Name: ", "Invalid Last Name. Please try again", Validator::isNotBlank);
        List<Employee> employees = employeeService.findByFullName(firstName, lastName);
        if(employees.isEmpty()){
            System.out.println("No employee found with full name: " + firstName + " " + lastName);
        }else{
            for(Employee employee : employees){
                employee.display();
            }
        }
    }

    private static void report(){
        List<Department> departments = departmentService.findAll();
        for(Department department : departments){
            department.display();
        }
    }

    private static void saveData() {
        if(employeeService.saveToFile()){
            System.out.println("Saved data successfully");
        }else{
            System.out.println("Failed to save file");
        }
    }

    private static int inputValidOption(int min, int max){
        while(true){
            int option = getIntInput("Please input an option from " + min + " to " + max + ": ");
            if(option < min || option > max){
                System.out.println("Invalid option. Please provide a number between " + min + " and " + max);
            }else{
                return option;
            }
        }
    }

    private static String inputValidStringField(String prompt, String message, Function<String, Boolean> validator){
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static double inputValidDoubleField(String prompt, String message, Function<Double, Boolean> validator){
        while(true){
            double input = getDoubleInput(prompt);
            if(validator.apply(input)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static String inputValidDepartmentName(String prompt, String message){
        while(true){
            String input = getStringInput(prompt);
            List<String> names = departmentService.findAllNames();
            if(Validator.isExistName(input, names)){
                return input;
            }
            System.out.println(message);
        }
    }
    private static String inputNonExistSSN(String prompt, String message){
        while(true){
            String input = getStringInput(prompt);
            List<String> ssns = employeeService.findAllSSNs();
            if(!Validator.isExistName(input, ssns)){
                return input;
            }
            System.out.println(message);
        }
    }

    private static int getIntInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter an integer");
            }
        }
    }

    private static double getDoubleInput(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Please enter a double value");
            }
        }
    }

    private static String getStringInput(String prompt) {
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine();
            if(Validator.isBlank(input)) {
                System.out.println("Don't let the input blank");
            }else{
                return input.trim();
            }
        }
    }


}