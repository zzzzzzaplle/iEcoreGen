import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

import java.util.stream.Collectors;

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    private String department;
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;

    /**
     * Constructs a new Employee with default values.
     */
    public Employee() {
        this.department = "";
        this.name = "";
        this.dateOfBirth = LocalDate.now();
        this.socialInsuranceNumber = "";
    }

    // Getters and Setters
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Calculates the salary of the employee.
     * @return the salary of the employee
     */
    public abstract double calculateSalary();
}

/**
 * Represents a worker in the company.
 */
class Worker extends Employee {
    private int weeklyWorkingHours;
    private double hourlyRate;
    private boolean isShiftWorker;
    private double holidayPremium;

    /**
     * Constructs a new Worker with default values.
     */
    public Worker() {
        super();
        this.weeklyWorkingHours = 0;
        this.hourlyRate = 0.0;
        this.isShiftWorker = false;
        this.holidayPremium = 0.0;
    }

    // Getters and Setters
    public int getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(int weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public boolean isShiftWorker() {
        return isShiftWorker;
    }

    public void setShiftWorker(boolean shiftWorker) {
        isShiftWorker = shiftWorker;
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    @Override
    public double calculateSalary() {
        double baseSalary = weeklyWorkingHours * hourlyRate;
        return isShiftWorker ? baseSalary + holidayPremium : baseSalary;
    }
}

/**
 * Represents a salesperson in the company.
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Constructs a new SalesPerson with default values.
     */
    public SalesPerson() {
        super();
        this.fixedSalary = 0.0;
        this.amountOfSales = 0.0;
        this.commissionPercentage = 0.0;
    }

    // Getters and Setters
    public double getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(double fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    public double getAmountOfSales() {
        return amountOfSales;
    }

    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    @Override
    public double calculateSalary() {
        return fixedSalary + amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private String position;
    private List<Employee> subordinates;

    /**
     * Constructs a new Manager with default values.
     */
    public Manager() {
        super();
        this.position = "";
        this.subordinates = new ArrayList<>();
    }

    // Getters and Setters
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public double calculateSalary() {
        // Managers' salary is fixed and not calculated here
        return 0.0;
    }

    /**
     * Adds a subordinate to the manager.
     * @param employee the employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @return the number of direct subordinate employees
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company.
 */
class Company {
    private List<Employee> employees;

    /**
     * Constructs a new Company with an empty list of employees.
     */
    public Company() {
        this.employees = new ArrayList<>();
    }

    // Getters and Setters
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the company.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return the total salary of all employees
     */
    public double calculateTotalSalary() {
        return employees.stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
    }

    /**
     * Finds the average working hours per week for all workers in the delivery department.
     * @return the average working hours per week, or 0 if there are no workers in the delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        List<Worker> deliveryWorkers = employees.stream()
                .filter(employee -> employee instanceof Worker)
                .map(employee -> (Worker) employee)
                .filter(worker -> "delivery".equalsIgnoreCase(worker.getDepartment()))
                .collect(Collectors.toList());

        if (deliveryWorkers.isEmpty()) {
            return 0.0;
        }

        double totalHours = deliveryWorkers.stream()
                .mapToDouble(Worker::getWeeklyWorkingHours)
                .sum();

        return totalHours / deliveryWorkers.size();
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * @return the total commission amount
     */
    public double calculateTotalCommission() {
        return employees.stream()
                .filter(employee -> employee instanceof SalesPerson)
                .map(employee -> (SalesPerson) employee)
                .mapToDouble(salesPerson -> salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage())
                .sum();
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return the total holiday premiums, or 0 if there are no shift workers
     */
    public double calculateTotalHolidayPremiums() {
        return employees.stream()
                .filter(employee -> employee instanceof Worker)
                .map(employee -> (Worker) employee)
                .filter(Worker::isShiftWorker)
                .mapToDouble(Worker::getHolidayPremium)
                .sum();
    }

    /**
     * Gets the number of direct subordinate employees for each manager.
     * @return a list of pairs containing the manager's name and the number of direct subordinates
     */
    public List<String> getNumberOfDirectSubordinatesForEachManager() {
        return employees.stream()
                .filter(employee -> employee instanceof Manager)
                .map(employee -> (Manager) employee)
                .map(manager -> manager.getName() + ": " + manager.getNumberOfDirectSubordinates())
                .collect(Collectors.toList());
    }
}