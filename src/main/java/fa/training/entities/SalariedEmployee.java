package fa.training.entities;

public class SalariedEmployee extends Employee {
    private double commissionRate;
    private double grossSales;
    private double basicSalary;

    public SalariedEmployee() {}

    public SalariedEmployee(String ssn, String firstName, String lastName, String birthDate,
                            String phone, String email,
                            double commissionRate, double grossSales, double basicSalary) {
        super(ssn, firstName, lastName, birthDate, phone, email);
        this.commissionRate = commissionRate;
        this.grossSales = grossSales;
        this.basicSalary = basicSalary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public double getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(double grossSales) {
        this.grossSales = grossSales;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Commission Rate: " + commissionRate);
        System.out.println("Gross Sales: " + grossSales);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Payment amount: " + getPaymentAmount());
    }

    @Override
    public double getPaymentAmount() {
        return basicSalary + grossSales * commissionRate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SalariedEmployee,");
        sb.append(super.toString())
                .append(",")
                .append(getCommissionRate())
                .append(",")
                .append(getGrossSales())
                .append(",")
                .append(getBasicSalary());
        return sb.toString();
    }
}
