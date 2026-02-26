import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Enum representing different department types in the company
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Abstract base class representing an employee in the company
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;
    
    public Employee() {
        this.department = "";
        this.name = "";
        this.birthDate = new Date();
        this.socialInsuranceNumber = "";
    }
    
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
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }
    
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }
    
    /**
     * Abstract method to calculate salary for different employee types
     * @return calculated salary for the employee
     */
    public abstract double calculateSalary();
}

/**
 * Class representing a department in the company
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;
    
    public Department() {
        this.type = DepartmentType.PRODUCTION;
        this.manager = null;
        this.employees = new ArrayList<>();
    }
    
    public DepartmentType getType() {
        return type;
    }
    
    public void setType(DepartmentType type) {
        this.type = type;
    }
    
    public Manager getManager() {
        return manager;
    }
    
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    /**
     * Calculates the average working hours per week for all workers in this department
     * @return average working hours per week, returns 0 if no workers in delivery department
     */
    public double calculateAverageWorkerWorkingHours() {
        if (this.type != DepartmentType.DELIVERY) {
            return 0.0;
        }
        
        int workerCount = 0;
        double totalHours = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                workerCount++;
                totalHours += worker.getWeeklyWorkingHour();
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }
}

/**
 * Class representing a manager employee
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
     * Calculates the salary for the manager (fixed salary)
     * @return fixed salary amount
     */
    @Override
    public double calculateSalary() {
        return this.salary;
    }
    
    /**
     * Gets the number of direct subordinate employees for this manager
     * @return count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return this.subordinates.size();
    }
}

/**
 * Class representing a sales person employee
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
    
    /**
     * Calculates the total commission earned by the sales person
     * @return commission amount calculated as amountOfSales * commissionPercentage
     */
    public double getTotalCommission() {
        return this.amountOfSales * this.commissionPercentage;
    }
    
    /**
     * Calculates the total salary for the sales person (fixed salary + commission)
     * @return total salary including commission
     */
    @Override
    public double calculateSalary() {
        return this.salary + getTotalCommission();
    }
}

/**
 * Abstract class representing a worker employee
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;
    
    public Worker() {
        super();
        this.weeklyWorkingHour = 0;
        this.hourlyRates = 0.0;
    }
    
    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }
    
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }
    
    public double getHourlyRates() {
        return hourlyRates;
    }
    
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }
    
    /**
     * Calculates the base salary for the worker (weeklyWorkingHour * hourlyRates)
     * @return base salary amount
     */
    @Override
    public double calculateSalary() {
        return this.weeklyWorkingHour * this.hourlyRates;
    }
}

/**
 * Class representing a shift worker employee
 */
class ShiftWorker extends Worker {
    private double holidayPremium;
    
    public ShiftWorker() {
        super();
        this.holidayPremium = 0.0;
    }
    
    public double getHolidayPremium() {
        return holidayPremium;
    }
    
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }
    
    /**
     * Calculates the holiday premium for the shift worker
     * @return holiday premium amount
     */
    public double calculateHolidayPremium() {
        return this.holidayPremium;
    }
    
    /**
     * Calculates the total salary for the shift worker (base salary + holiday premium)
     * @return total salary including holiday premium
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + calculateHolidayPremium();
    }
}

/**
 * Class representing an off-shift worker employee
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
        super();
    }
    
    /**
     * Off-shift workers use the base worker salary calculation
     * @return base salary amount
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary();
    }
}

/**
 * Main class representing the company
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;
    
    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public List<Department> getDepartments() {
        return departments;
    }
    
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    /**
     * Calculates the total salary of all employees in the company
     * @return sum of all employees' salaries
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;
        for (Employee employee : employees) {
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }
    
    /**
     * Determines the total commission amount for all salespeople in the company
     * @return sum of commission amounts for all salespeople
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) employee;
                totalCommission += salesPerson.getTotalCommission();
            }
        }
        return totalCommission;
    }
    
    /**
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return sum of holiday premiums for all shift workers, returns 0 if no shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasShiftWorkers = false;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.calculateHolidayPremium();
                hasShiftWorkers = true;
            }
        }
        
        return hasShiftWorkers ? totalPremiums : 0.0;
    }
    
    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return average working hours, returns 0 if no workers in delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        for (Department department : departments) {
            if (department.getType() == DepartmentType.DELIVERY) {
                return department.calculateAverageWorkerWorkingHours();
            }
        }
        return 0.0;
    }
    
    /**
     * Gets the number of direct subordinate employees for each manager
     * @return list of manager names with their subordinate counts
     */
    public List<String> getManagerSubordinateCounts() {
        List<String> results = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                results.add(manager.getName() + ": " + manager.getDirectSubordinateEmployeesCount() + " subordinates");
            }
        }
        return results;
    }
}