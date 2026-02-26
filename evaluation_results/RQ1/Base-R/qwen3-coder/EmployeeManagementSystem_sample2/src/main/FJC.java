import java.util.*;

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    protected String name;
    protected Date dateOfBirth;
    protected String socialInsuranceNumber;
    protected String department;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
        this.name = "";
        this.dateOfBirth = new Date();
        this.socialInsuranceNumber = "";
        this.department = "";
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getSocialInsuranceNumber() { return socialInsuranceNumber; }
    public void setSocialInsuranceNumber(String socialInsuranceNumber) { this.socialInsuranceNumber = socialInsuranceNumber; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}

/**
 * Represents a worker employee.
 */
class Worker extends Employee {
    protected double weeklyWorkingHours;
    protected double hourlyRate;
    
    /**
     * Default constructor for Worker.
     */
    public Worker() {
        super();
        this.weeklyWorkingHours = 0.0;
        this.hourlyRate = 0.0;
    }
    
    // Getters and setters
    public double getWeeklyWorkingHours() { return weeklyWorkingHours; }
    public void setWeeklyWorkingHours(double weeklyWorkingHours) { this.weeklyWorkingHours = weeklyWorkingHours; }
    
    public double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(double hourlyRate) { this.hourlyRate = hourlyRate; }
    
    /**
     * Calculates the salary for this worker.
     * For shift workers, holiday premiums should be added separately.
     * @return the base salary (weeklyWorkingHours * hourlyRate)
     */
    public double calculateSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
}

/**
 * Represents an off-shift worker who gets weekend and holiday permits.
 */
class OffShiftWorker extends Worker {
    private boolean weekendPermit;
    private boolean holidayPermit;
    
    /**
     * Default constructor for OffShiftWorker.
     */
    public OffShiftWorker() {
        super();
        this.weekendPermit = false;
        this.holidayPermit = false;
    }
    
    // Getters and setters
    public boolean isWeekendPermit() { return weekendPermit; }
    public void setWeekendPermit(boolean weekendPermit) { this.weekendPermit = weekendPermit; }
    
    public boolean isHolidayPermit() { return holidayPermit; }
    public void setHolidayPermit(boolean holidayPermit) { this.holidayPermit = holidayPermit; }
}

/**
 * Represents a shift worker who works in delivery department and receives holiday premiums.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;
    
    /**
     * Default constructor for ShiftWorker.
     */
    public ShiftWorker() {
        super();
        this.holidayPremium = 0.0;
        // Shift workers can only work in delivery department
        this.department = "delivery";
    }
    
    // Getters and setters
    public double getHolidayPremium() { return holidayPremium; }
    public void setHolidayPremium(double holidayPremium) { this.holidayPremium = holidayPremium; }
    
    /**
     * Calculates the salary for this shift worker including holiday premium.
     * @return the total salary (base salary + holiday premium)
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + holidayPremium;
    }
}

/**
 * Represents a salesperson employee.
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;
    
    /**
     * Default constructor for SalesPerson.
     */
    public SalesPerson() {
        super();
        this.fixedSalary = 0.0;
        this.amountOfSales = 0.0;
        this.commissionPercentage = 0.0;
    }
    
    // Getters and setters
    public double getFixedSalary() { return fixedSalary; }
    public void setFixedSalary(double fixedSalary) { this.fixedSalary = fixedSalary; }
    
    public double getAmountOfSales() { return amountOfSales; }
    public void setAmountOfSales(double amountOfSales) { this.amountOfSales = amountOfSales; }
    
    public double getCommissionPercentage() { return commissionPercentage; }
    public void setCommissionPercentage(double commissionPercentage) { this.commissionPercentage = commissionPercentage; }
    
    /**
     * Calculates the salary for this salesperson.
     * @return the total salary (fixed salary + commission)
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }
    
    /**
     * Calculates the commission amount for this salesperson.
     * @return the commission (amountOfSales * commissionPercentage)
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager employee.
 */
class Manager extends Employee {
    private double fixedSalary;
    private String position;
    private List<Employee> subordinates;
    
    /**
     * Default constructor for Manager.
     */
    public Manager() {
        super();
        this.fixedSalary = 0.0;
        this.position = "";
        this.subordinates = new ArrayList<>();
    }
    
    // Getters and setters
    public double getFixedSalary() { return fixedSalary; }
    public void setFixedSalary(double fixedSalary) { this.fixedSalary = fixedSalary; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public List<Employee> getSubordinates() { return subordinates; }
    public void setSubordinates(List<Employee> subordinates) { this.subordinates = subordinates; }
    
    /**
     * Adds a subordinate to this manager.
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }
    
    /**
     * Removes a subordinate from this manager.
     * @param employee the employee to remove
     */
    public void removeSubordinate(Employee employee) {
        subordinates.remove(employee);
    }
    
    /**
     * Calculates the salary for this manager.
     * @return the fixed salary
     */
    public double calculateSalary() {
        return fixedSalary;
    }
    
    /**
     * Gets the number of direct subordinates for this manager.
     * @return the count of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company with employees.
 */
class Company {
    private List<Employee> employees;
    
    /**
     * Default constructor for Company.
     */
    public Company() {
        this.employees = new ArrayList<>();
    }
    
    // Getter and setter
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    
    /**
     * Adds an employee to the company.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    /**
     * Removes an employee from the company.
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    /**
     * Calculates the total salary of all employees in the company.
     * Total salary = sum(workers' salary + sales people's salary + managers' salary).
     * Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers].
     * Sales people's salary = salary + amountOfSales * commissionPercentage.
     * Managers' salary = salary.
     * 
     * @return the total salary of all employees
     */
    public double calculateTotalSalary() {
        double totalSalary = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                totalSalary += ((Worker) employee).calculateSalary();
            } else if (employee instanceof SalesPerson) {
                totalSalary += ((SalesPerson) employee).calculateSalary();
            } else if (employee instanceof Manager) {
                totalSalary += ((Manager) employee).calculateSalary();
            }
        }
        
        return totalSalary;
    }
    
    /**
     * Finds average working hours per week for all workers in the delivery department.
     * Return 0 if there are no workers in the delivery department.
     * 
     * @return the average working hours per week for delivery workers
     */
    public double findAverageWorkingHoursInDelivery() {
        double totalHours = 0.0;
        int deliveryWorkerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker && "delivery".equals(employee.getDepartment())) {
                totalHours += ((Worker) employee).getWeeklyWorkingHours();
                deliveryWorkerCount++;
            }
        }
        
        if (deliveryWorkerCount == 0) {
            return 0.0;
        }
        
        return totalHours / deliveryWorkerCount;
    }
    
    /**
     * Determines the total commission amount for all salespeople in the company.
     * Sum(amountOfSales * commissionPercentage) for all salespeople.
     * 
     * @return the total commission amount
     */
    public double calculateTotalCommission() {
        double totalCommission = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                totalCommission += ((SalesPerson) employee).calculateCommission();
            }
        }
        
        return totalCommission;
    }
    
    /**
     * Calculates total holiday premiums paid to all shift workers in the company.
     * Return 0 if there are no shift workers in the delivery department.
     * 
     * @return the total holiday premiums paid to shift workers
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) employee).getHolidayPremium();
            }
        }
        
        return totalPremiums;
    }
}