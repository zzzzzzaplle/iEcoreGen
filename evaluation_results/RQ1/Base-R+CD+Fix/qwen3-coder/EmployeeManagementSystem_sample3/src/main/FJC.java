import java.util.*;

/**
 * Abstract base class representing an employee in the company.
 */
abstract class Employee {
    protected String department;
    protected String name;
    protected Date birthDate;
    protected String socialInsuranceNumber;

    public Employee() {
        this.department = "";
        this.name = "";
        this.birthDate = new Date();
        this.socialInsuranceNumber = "";
    }

    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    
    public String getSocialInsuranceNumber() { return socialInsuranceNumber; }
    public void setSocialInsuranceNumber(String socialInsuranceNumber) { this.socialInsuranceNumber = socialInsuranceNumber; }
}

/**
 * Class representing a manager who has subordinates and a specific position.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;

    public Manager() {
        super();
        this.salary = 0.0;
        this.position = "";
        this.subordinates = new ArrayList<>();
    }

    // Getters and Setters
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public List<Employee> getSubordinates() { return subordinates; }
    public void setSubordinates(List<Employee> subordinates) { this.subordinates = subordinates; }

    /**
     * Gets the count of direct subordinate employees.
     * @return The number of direct subordinates.
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

/**
 * Class representing a salesperson who earns a fixed salary plus commissions.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {
        super();
        this.salary = 0.0;
        this.amountOfSales = 0.0;
        this.commissionPercentage = 0.0;
    }

    // Getters and Setters
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public double getAmountOfSales() { return amountOfSales; }
    public void setAmountOfSales(double amountOfSales) { this.amountOfSales = amountOfSales; }
    
    public double getCommissionPercentage() { return commissionPercentage; }
    public void setCommissionPercentage(double commissionPercentage) { this.commissionPercentage = commissionPercentage; }

    /**
     * Calculates the total commission earned by this salesperson.
     * @return The total commission amount.
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Abstract class representing a worker with working hours and hourly rates.
 */
abstract class Worker extends Employee {
    protected int weeklyWorkingHour;
    protected double hourlyRates;

    public Worker() {
        super();
        this.weeklyWorkingHour = 0;
        this.hourlyRates = 0.0;
    }

    // Getters and Setters
    public int getWeeklyWorkingHour() { return weeklyWorkingHour; }
    public void setWeeklyWorkingHour(int weeklyWorkingHour) { this.weeklyWorkingHour = weeklyWorkingHour; }
    
    public double getHourlyRates() { return hourlyRates; }
    public void setHourlyRates(double hourlyRates) { this.hourlyRates = hourlyRates; }
}

/**
 * Class representing a shift worker who receives holiday premiums.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
        super();
        this.holidayPremium = 0.0;
    }

    // Getters and Setters
    public double getHolidayPremium() { return holidayPremium; }
    public void setHolidayPremium(double holidayPremium) { this.holidayPremium = holidayPremium; }

    /**
     * Calculates the holiday premium for this shift worker.
     * @return The calculated holiday premium.
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Class representing an off-shift worker who has weekend and holiday permits.
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
        super();
    }
}

/**
 * Enum representing different department types in the company.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Class representing a department within the company.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;

    public Department() {
        this.type = DepartmentType.PRODUCTION;
        this.manager = new Manager();
        this.employees = new ArrayList<>();
    }

    // Getters and Setters
    public DepartmentType getType() { return type; }
    public void setType(DepartmentType type) { this.type = type; }
    
    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }
    
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    /**
     * Calculates the average working hours per week for all workers in the delivery department.
     * @return Average working hours, or 0 if there are no workers.
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0;
        }
        
        int totalHours = 0;
        int workerCount = 0;
        
        for (Employee emp : employees) {
            if (emp instanceof Worker) {
                totalHours += ((Worker) emp).getWeeklyWorkingHour();
                workerCount++;
            }
        }
        
        return workerCount == 0 ? 0 : (double) totalHours / workerCount;
    }
}

/**
 * Main class representing the company with its employees and departments.
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    // Getters and Setters
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    
    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }

    /**
     * Calculates the total salary of all employees in the company.
     * For workers: salary = weeklyWorkingHour * hourlyRates + holiday premiums (for shift workers)
     * For sales people: salary = fixed salary + amountOfSales * commissionPercentage
     * For managers: salary = fixed salary
     * @return The total salary of all employees.
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;
        
        for (Employee emp : employees) {
            if (emp instanceof Worker) {
                Worker worker = (Worker) emp;
                double workerSalary = worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                
                if (worker instanceof ShiftWorker) {
                    workerSalary += ((ShiftWorker) worker).getHolidayPremium();
                }
                
                totalSalary += workerSalary;
            } else if (emp instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) emp;
                totalSalary += salesPerson.getSalary() + 
                              (salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage());
            } else if (emp instanceof Manager) {
                totalSalary += ((Manager) emp).getSalary();
            }
        }
        
        return totalSalary;
    }

    /**
     * Calculates the total commission amount for all salespeople in the company.
     * @return The total commission amount.
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;
        
        for (Employee emp : employees) {
            if (emp instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) emp;
                totalCommission += salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage();
            }
        }
        
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return The total holiday premiums, or 0 if there are no shift workers.
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        
        for (Employee emp : employees) {
            if (emp instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) emp).getHolidayPremium();
            }
        }
        
        return totalPremiums;
    }
}