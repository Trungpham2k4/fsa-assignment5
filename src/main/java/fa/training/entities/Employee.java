package fa.training.entities;

public abstract class Employee implements Payable{
    private String ssn;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String phone;
    private String email;

    public Employee(){}

    public Employee(String ssn, String firstName, String lastName, String birthDate, String phone, String email) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void display(){
        System.out.println("----- Employee Information -----");
        System.out.println("SSN: " + ssn);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Birth Date: " + birthDate);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
    }

    @Override
    public abstract double getPaymentAmount();

    @Override
    public String toString() {
        return "Employee{" +
                "ssn='" + ssn + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
