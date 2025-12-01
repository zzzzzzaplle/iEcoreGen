import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

 /**
 * Represents an Employee in the company.
 */
abstract class Employee {
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;
    private String department;

    /**
     * Constructor for Employee.
     */
    public Employee() {
    }

    // Getters and Setters
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Calculates the salary of the employee.
     * @return the salary of the employee
     */
    public abstract double calculateSalary();

    /**
     * Checks if the employee is a shift worker.
     * @return true if the employee is a shift worker, false otherwise
     */
    public abstract boolean isShiftWorker();
}

/**
 * Represents a Worker in the company.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHours;
    private double hourlyRate;

    /**
     * Constructor for Worker.
     */
    public Worker() {
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

    @Override
    public double calculateSalary() {
        return weeklyWorkingHours * hourlyRate + calculateHolidayPremiums();
    }

    /**
     * Calculates the holiday premiums for the worker.
     * @return the holiday premiums
     */
    protected abstract double calculateHolidayPremiums();
}

/**
 * Represents a Shift Worker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Constructor for ShiftWorker.
     */
    public ShiftWorker() {
    }

    // Getters and Setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    @Override
    protected double calculateHolidayPremiums() {
        return holidayPremium;
    }

    @Override
    public boolean isShiftWorker() {
        return true;
    }
}

/**
 * Represents a NonShift Worker in the company.
 */
class NonShiftWorker extends Worker {
    private boolean hasWeekendPermit;
    private boolean hasHolidayPermit;

    /**
     * Constructor for NonShiftWorker.
     */
    public NonShiftWorker() {
    }

    // Getters and Setters
    public boolean hasWeekendPermit() {
        return hasWeekendPermit;
    }

    public void setHasWeekendPermit(boolean hasWeekendPermit) {
        this.hasWeekendPermit = hasWeekendPermit;
    }

    public boolean hasHolidayPermit() {
        return hasHolidayPermit;
    }

    public void setHasHolidayPermit(boolean hasHolidayPermit) {
        this.hasHolidayPermit = hasHolidayPermit;
    }

    @Override
    protected double calculateHolidayPremiums() {
        return 0;
    }

    @Override
    public boolean isShiftWorker() {
        return false;
    }
}

/**
 * Represents a SalesPerson in the company.
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Constructor for SalesPerson.
     */
    public SalesPerson() {
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
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    @Override
    public boolean isShiftWorker() {
        return false;
    }
}

/**
 * Represents a Manager in the company.
 */
class Manager extends Employee {
    private String position;
    private List<Employee> subordinates;

    /**
     * Constructor for Manager.
     */
    public Manager() {
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

    /**
     * Adds a subordinate to the manager.
     * @param employee the employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    @Override
    public double calculateSalary() {
        return 0; // Fixed salary to be set via setFixedSalary
    }

    @Override
    public boolean isShiftWorker() {
        return false;
    }
}

/**
 * Represents the Company.
 */
class Company {
    private List<Employee> employees;

    /**
     * Constructor for Company.
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
        this.employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return the total salary
     */
    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }

    /**
     * Finds the average working hours per week for all workers in the delivery department.
     * @return the average working hours per week
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
     * Determines the total commission amount for all salespeople in the company.
     * @return the total commission amount
     */
    public double calculateTotalCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                SalesPerson salesPerson = (SalesPerson) employee;
                totalCommission += salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company.
     * @return the total holiday premiums
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0;
        for (Employee employee : employees) {
            if (employee.isShiftWorker() && employee instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) employee).getHolidayPremium();
            }
        }
        return totalPremiums;
    }

    /**
     * Gets the number of direct subordinate employees for each manager.
     * @return a map of manager names to the number of direct subordinates
     */
    public java.util.Map<String, Integer> getNumberOfDirectSubordinatesPerManager() {
        java.util.Map<String, Integer> subordinatesMap = new java.util.HashMap<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                subordinatesMap.put(manager.getName(), manager.getSubordinates().size());
            }
        }
        return subordinatesMap;
    }
}