import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Represents an employee in the company.
 */
class Employee {
    private String department;
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;

    /**
     * Default constructor.
     */
    public Employee() {
    }

    /**
     * Parameterized constructor.
     * @param department The department of the employee.
     * @param name The name of the employee.
     * @param dateOfBirth The date of birth of the employee.
     * @param socialInsuranceNumber The social insurance number of the employee.
     */
    public Employee(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber) {
        this.department = department;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    // Getters and setters
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
}

/**
 * Represents a worker in the company.
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;
    private boolean isShiftWorker;

    /**
     * Default constructor.
     */
    public Worker() {
    }

    /**
     * Parameterized constructor.
     * @param department The department of the worker.
     * @param name The name of the worker.
     * @param dateOfBirth The date of birth of the worker.
     * @param socialInsuranceNumber The social insurance number of the worker.
     * @param weeklyWorkingHours The weekly working hours of the worker.
     * @param hourlyRate The hourly rate of the worker.
     * @param isShiftWorker Indicates if the worker is a shift worker.
     */
    public Worker(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber,
                  double weeklyWorkingHours, double hourlyRate, boolean isShiftWorker) {
        super(department, name, dateOfBirth, socialInsuranceNumber);
        this.weeklyWorkingHours = weeklyWorkingHours;
        this.hourlyRate = hourlyRate;
        this.isShiftWorker = isShiftWorker;
    }

    // Getters and setters
    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
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

    /**
     * Calculates the salary of the worker.
     * @return The calculated salary.
     */
    public double calculateSalary() {
        double salary = weeklyWorkingHours * hourlyRate;
        if (isShiftWorker && getDepartment().equals("Delivery")) {
            salary += 50; // Assuming a fixed premium for working on holidays
        }
        return salary;
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
     * Default constructor.
     */
    public SalesPerson() {
    }

    /**
     * Parameterized constructor.
     * @param department The department of the salesperson.
     * @param name The name of the salesperson.
     * @param dateOfBirth The date of birth of the salesperson.
     * @param socialInsuranceNumber The social insurance number of the salesperson.
     * @param fixedSalary The fixed salary of the salesperson.
     * @param amountOfSales The amount of sales made by the salesperson.
     * @param commissionPercentage The commission percentage of the salesperson.
     */
    public SalesPerson(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber,
                      double fixedSalary, double amountOfSales, double commissionPercentage) {
        super(department, name, dateOfBirth, socialInsuranceNumber);
        this.fixedSalary = fixedSalary;
        this.amountOfSales = amountOfSales;
        this.commissionPercentage = commissionPercentage;
    }

    // Getters and setters
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

    /**
     * Calculates the salary of the salesperson.
     * @return The calculated salary.
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the commission amount for the salesperson.
     * @return The calculated commission amount.
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private List<Employee> subordinates;

    /**
     * Default constructor.
     */
    public Manager() {
        this.subordinates = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     * @param department The department of the manager.
     * @param name The name of the manager.
     * @param dateOfBirth The date of birth of the manager.
     * @param socialInsuranceNumber The social insurance number of the manager.
     * @param salary The salary of the manager.
     */
    public Manager(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber, double salary) {
        super(department, name, dateOfBirth, socialInsuranceNumber);
        this.salary = salary;
        this.subordinates = new ArrayList<>();
    }

    // Getters and setters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Adds a subordinate to the manager.
     * @param employee The employee to be added as a subordinate.
     */
    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    /**
     * Calculates the salary of the manager.
     * @return The calculated salary.
     */
    public double calculateSalary() {
        return salary;
    }

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @return The number of direct subordinates.
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company and provides methods to manage employees and calculate various metrics.
 */
class Company {
    private List<Employee> employees;

    /**
     * Default constructor.
     */
    public Company() {
        this.employees = new ArrayList<>();
    }

    /**
     * Adds an employee to the company.
     * @param employee The employee to be added.
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return The total salary.
     */
    public double calculateTotalSalary() {
        return employees.stream()
                .mapToDouble(employee -> {
                    if (employee instanceof Worker) {
                        return ((Worker) employee).calculateSalary();
                    } else if (employee instanceof SalesPerson) {
                        return ((SalesPerson) employee).calculateSalary();
                    } else if (employee instanceof Manager) {
                        return ((Manager) employee).calculateSalary();
                    }
                    return 0;
                })
                .sum();
    }

    /**
     * Finds the average working hours per week for all workers in the delivery department.
     * @return The average working hours. Returns 0 if there are no workers in the delivery department.
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        List<Worker> deliveryWorkers = employees.stream()
                .filter(employee -> employee instanceof Worker && employee.getDepartment().equals("Delivery"))
                .map(employee -> (Worker) employee)
                .collect(Collectors.toList());

        if (deliveryWorkers.isEmpty()) {
            return 0;
        }

        double totalHours = deliveryWorkers.stream()
                .mapToDouble(Worker::getWeeklyWorkingHours)
                .sum();

        return totalHours / deliveryWorkers.size();
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * @return The total commission amount.
     */
    public double calculateTotalCommission() {
        return employees.stream()
                .filter(employee -> employee instanceof SalesPerson)
                .map(employee -> (SalesPerson) employee)
                .mapToDouble(SalesPerson::calculateCommission)
                .sum();
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return The total holiday premiums. Returns 0 if there are no shift workers in the delivery department.
     */
    public double calculateTotalHolidayPremiums() {
        List<Worker> shiftWorkers = employees.stream()
                .filter(employee -> employee instanceof Worker && ((Worker) employee).isShiftWorker())
                .map(employee -> (Worker) employee)
                .collect(Collectors.toList());

        if (shiftWorkers.isEmpty()) {
            return 0;
        }

        return shiftWorkers.stream()
                .filter(worker -> worker.getDepartment().equals("Delivery"))
                .count() * 50; // Assuming a fixed premium for working on holidays
    }

    /**
     * Gets the number of direct subordinate employees for each manager.
     * @return A list of integers representing the number of direct subordinates for each manager.
     */
    public List<Integer> getNumberOfDirectSubordinatesForEachManager() {
        return employees.stream()
                .filter(employee -> employee instanceof Manager)
                .map(employee -> (Manager) employee)
                .map(Manager::getNumberOfDirectSubordinates)
                .collect(Collectors.toList());
    }
}