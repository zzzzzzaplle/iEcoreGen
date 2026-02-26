import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

 /**
 * Represents an employee in the company.
 */
class Employee {
    private String department;
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;

    /**
     * Default constructor
     */
    public Employee() {}

    /**
     * Constructor with all fields
     * @param department The department of the employee
     * @param name The name of the employee
     * @param dateOfBirth The date of birth of the employee
     * @param socialInsuranceNumber The social insurance number of the employee
     */
    public Employee(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber) {
        this.department = department;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getSocialInsuranceNumber() { return socialInsuranceNumber; }
    public void setSocialInsuranceNumber(String socialInsuranceNumber) { this.socialInsuranceNumber = socialInsuranceNumber; }
}

/**
 * Represents a worker in the company.
 */
class Worker extends Employee {
    private int weeklyWorkingHours;
    private double hourlyRate;
    private boolean isShiftWorker;

    /**
     * Default constructor
     */
    public Worker() { super(); }

    /**
     * Constructor with all fields
     * @param department The department of the worker
     * @param name The name of the worker
     * @param dateOfBirth The date of birth of the worker
     * @param socialInsuranceNumber The social insurance number of the worker
     * @param weeklyWorkingHours The weekly working hours of the worker
     * @param hourlyRate The hourly rate of the worker
     * @param isShiftWorker Whether the worker is a shift worker
     */
    public Worker(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber,
                  int weeklyWorkingHours, double hourlyRate, boolean isShiftWorker) {
        super(department, name, dateOfBirth, socialInsuranceNumber);
        this.weeklyWorkingHours = weeklyWorkingHours;
        this.hourlyRate = hourlyRate;
        this.isShiftWorker = isShiftWorker;
    }

    // Getters and Setters
    public int getWeeklyWorkingHours() { return weeklyWorkingHours; }
    public void setWeeklyWorkingHours(int weeklyWorkingHours) { this.weeklyWorkingHours = weeklyWorkingHours; }
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    public boolean isShiftWorker() { return isShiftWorker; }
    public void setShiftWorker(boolean shiftWorker) { isShiftWorker = shiftWorker; }

    /**
     * Calculates the salary of the worker
     * @return The calculated salary
     */
    public double calculateSalary() {
        double salary = weeklyWorkingHours * hourlyRate;
        if (isShiftWorker) {
            salary += calculateHolidayPremiums();
        }
        return salary;
    }

    /**
     * Calculates the holiday premiums for the shift worker
     * @return The calculated holiday premiums
     */
    public double calculateHolidayPremiums() {
        // Assume 10% premium for each holiday worked
        return weeklyWorkingHours * hourlyRate * 0.1;
    }
}

/**
 * Represents a salesperson in the company.
 */
class Salesperson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor
     */
    public Salesperson() { super(); }

    /**
     * Constructor with all fields
     * @param department The department of the salesperson
     * @param name The name of the salesperson
     * @param dateOfBirth The date of birth of the salesperson
     * @param socialInsuranceNumber The social insurance number of the salesperson
     * @param fixedSalary The fixed salary of the salesperson
     * @param amountOfSales The amount of sales made by the salesperson
     * @param commissionPercentage The commission percentage of the salesperson
     */
    public Salesperson(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber,
                      double fixedSalary, double amountOfSales, double commissionPercentage) {
        super(department, name, dateOfBirth, socialInsuranceNumber);
        this.fixedSalary = fixedSalary;
        this.amountOfSales = amountOfSales;
        this.commissionPercentage = commissionPercentage;
    }

    // Getters and Setters
    public double getFixedSalary() { return fixedSalary; }
    public void setFixedSalary(double fixedSalary) { this.fixedSalary = fixedSalary; }
    public double getAmountOfSales() { return amountOfSales; }
    public void setAmountOfSales(double amountOfSales) { this.amountOfSales = amountOfSales; }
    public double getCommissionPercentage() { return commissionPercentage; }
    public void setCommissionPercentage(double commissionPercentage) { this.commissionPercentage = commissionPercentage; }

    /**
     * Calculates the salary of the salesperson
     * @return The calculated salary
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the commission amount for the salesperson
     * @return The calculated commission amount
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private String position;
    private List<Employee> subordinates;

    /**
     * Default constructor
     */
    public Manager() {
        super();
        this.subordinates = new ArrayList<>();
    }

    /**
     * Constructor with all fields
     * @param department The department of the manager
     * @param name The name of the manager
     * @param dateOfBirth The date of birth of the manager
     * @param socialInsuranceNumber The social insurance number of the manager
     * @param position The position of the manager
     */
    public Manager(String department, String name, LocalDate dateOfBirth, String socialInsuranceNumber, String position) {
        super(department, name, dateOfBirth, socialInsuranceNumber);
        this.position = position;
        this.subordinates = new ArrayList<>();
    }

    // Getters and Setters
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public List<Employee> getSubordinates() { return subordinates; }
    public void setSubordinates(List<Employee> subordinates) { this.subordinates = subordinates; }

    /**
     * Adds a subordinate to the manager
     * @param employee The employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Calculates the number of direct subordinate employees
     * @return The number of direct subordinate employees
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates.size();
    }

    /**
     * Calculates the salary of the manager
     * @return The calculated salary
     */
    public double calculateSalary() {
        return 0; // Assuming fixed salary is set directly
    }
}

/**
 * Represents the company.
 */
class Company {
    private List<Employee> employees;

    /**
     * Default constructor
     */
    public Company() {
        this.employees = new ArrayList<>();
    }

    // Getters and Setters
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    /**
     * Adds an employee to the company
     * @param employee The employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return The total salary
     */
    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                totalSalary += ((Worker) employee).calculateSalary();
            } else if (employee instanceof Salesperson) {
                totalSalary += ((Salesperson) employee).calculateSalary();
            } else if (employee instanceof Manager) {
                totalSalary += ((Manager) employee).calculateSalary();
            }
        }
        return totalSalary;
    }

    /**
     * Finds the average working hours per week for all workers in the delivery department
     * @return The average working hours
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        List<Worker> deliveryWorkers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Worker && "delivery".equalsIgnoreCase(employee.getDepartment())) {
                deliveryWorkers.add((Worker) employee);
            }
        }
        if (deliveryWorkers.isEmpty()) {
            return 0;
        }
        int totalHours = 0;
        for (Worker worker : deliveryWorkers) {
            totalHours += worker.getWeeklyWorkingHours();
        }
        return (double) totalHours / deliveryWorkers.size();
    }

    /**
     * Determines the total commission amount for all salespeople in the company
     * @return The total commission amount
     */
    public double calculateTotalCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof Salesperson) {
                totalCommission += ((Salesperson) employee).calculateCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company
     * @return The total holiday premiums
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker && ((Worker) employee).isShiftWorker()) {
                totalPremiums += ((Worker) employee).calculateHolidayPremiums();
            }
        }
        return totalPremiums;
    }

    /**
     * Gets the number of direct subordinate employees for each manager
     * @return A list of the number of direct subordinate employees for each manager
     */
    public List<Integer> getNumberOfDirectSubordinatesForEachManager() {
        List<Integer> subordinatesCount = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                subordinatesCount.add(((Manager) employee).getNumberOfDirectSubordinates());
            }
        }
        return subordinatesCount;
    }
}