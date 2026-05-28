package fa.training.entities;

public class HourlyEmployee extends Employee{
    private double wage;
    private double workingHours;

    public HourlyEmployee(){}

    public HourlyEmployee(String ssn, String firstName, String lastName,
                          String birthDate, String phoneNumber, String email,
                          double wage, double workingHours) {
        super(ssn, firstName, lastName, birthDate, phoneNumber, email);
        this.wage = wage;
        this.workingHours = workingHours;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public double getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(double workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Wage: " + wage);
        System.out.println("WorkingHours: " + workingHours);
        System.out.println("Payment amount: " + getPaymentAmount());
    }

    @Override
    public double getPaymentAmount() {
        return wage * workingHours;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "ssn='" + getSsn() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", birthDate='" + getBirthDate() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", wage=" + wage +
                ", workingHours=" + workingHours +
                '}';
    }
}
