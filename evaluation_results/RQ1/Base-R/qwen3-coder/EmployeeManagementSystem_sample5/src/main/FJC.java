import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    private String name;
    private String dateOfBirth;
    private String socialInsuranceNumber;
    private String department;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
        this.name = "";
        this.dateOfBirth = "";
        this.socialInsuranceNumber = "";
        this.department = "";
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
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
     * Abstract method to calculate salary for different types of employees.
     * @return the calculated salary
     */
    public abstract double calculateSalary();
}

/**
 * Represents a worker employee.
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRates;

    /**
     * Default constructor for Worker.
     */
    public Worker() {
        super();
        this.weeklyWorkingHours = 0.0;
        this.hourlyRates = 0.0;
    }

    // Getters and setters
    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public double getHourlyRates() {
        return hourlyRates;
    }

    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    @Override
    public double calculateSalary() {
        return weeklyWorkingHours * hourlyRates;
    }
}

/**
 * Represents an off-shift worker.
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
    public boolean isWeekendPermit() {
        return weekendPermit;
    }

    public void setWeekendPermit(boolean weekendPermit) {
        this.weekendPermit = weekendPermit;
    }

    public boolean isHolidayPermit() {
        return holidayPermit;
    }

    public void setHolidayPermit(boolean holidayPermit) {
        this.holidayPermit = holidayPermit;
    }
}

/**
 * Represents a shift worker.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Default constructor for ShiftWorker.
     */
    public ShiftWorker() {
        super();
        this.holidayPremium = 0.0;
    }

    // Getters and setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    @Override
    public double calculateSalary() {
        return super.calculateSalary() + holidayPremium;
    }
}

/**
 * Represents a sales person employee.
 */
class SalesPerson extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor for SalesPerson.
     */
    public SalesPerson() {
        super();
        this.salary = 0.0;
        this.amountOfSales = 0.0;
        this.commissionPercentage = 0.0;
    }

    // Getters and setters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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
        return salary + (amountOfSales * commissionPercentage / 100);
    }

    /**
     * Calculates the commission amount for this salesperson.
     * @return the commission amount (amountOfSales * commissionPercentage / 100)
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage / 100;
    }
}

/**
 * Represents a manager employee.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;

    /**
     * Default constructor for Manager.
     */
    public Manager() {
        super();
        this.salary = 0.0;
        this.position = "";
        this.subordinates = new ArrayList<>();
    }

    // Getters and setters
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

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
     * Adds a subordinate to this manager.
     * @param employee the employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    /**
     * Gets the number of direct subordinate employees for this manager.
     * @return the count of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return this.subordinates.size();
    }

    @Override
    public double calculateSalary() {
        return salary;
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
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department.
     * Return 0 if there are no workers in the delivery department.
     * 
     * @return the average working hours per week for delivery department workers
     */
    public double findAverageWorkingHoursInDelivery() {
        double totalHours = 0.0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker && "delivery".equalsIgnoreCase(employee.getDepartment())) {
                totalHours += ((Worker) employee).getWeeklyWorkingHours();
                workerCount++;
            }
        }
        
        if (workerCount == 0) {
            return 0.0;
        }
        
        return totalHours / workerCount;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * Sum(amountOfSales * commissionPercentage) for all salespeople.
     * 
     * @return the total commission amount for all salespeople
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