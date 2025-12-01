import java.util.*;
import java.util.stream.Collectors;

enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;
    
    public Employee() {}
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    
    public String getSocialInsuranceNumber() { return socialInsuranceNumber; }
    public void setSocialInsuranceNumber(String socialInsuranceNumber) { this.socialInsuranceNumber = socialInsuranceNumber; }
    
    /**
     * Calculates the salary for this employee based on their specific type
     * @return the calculated salary for this employee
     */
    public abstract double calculateSalary();
}

class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;
    
    public Manager() {
        subordinates = new ArrayList<>();
    }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public List<Employee> getSubordinates() { return subordinates; }
    public void setSubordinates(List<Employee> subordinates) { this.subordinates = subordinates; }
    
    /**
     * Calculates the manager's salary (fixed salary)
     * @return the fixed salary of the manager
     */
    @Override
    public double calculateSalary() {
        return salary;
    }
    
    /**
     * Gets the number of direct subordinate employees for this manager
     * @return the count of direct subordinate employees
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;
    
    public SalesPeople() {}
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public double getAmountOfSales() { return amountOfSales; }
    public void setAmountOfSales(double amountOfSales) { this.amountOfSales = amountOfSales; }
    
    public double getCommissionPercentage() { return commissionPercentage; }
    public void setCommissionPercentage(double commissionPercentage) { this.commissionPercentage = commissionPercentage; }
    
    /**
     * Calculates the salesperson's salary (fixed salary + commission)
     * @return the total salary including commission
     */
    @Override
    public double calculateSalary() {
        return salary + (amountOfSales * commissionPercentage);
    }
    
    /**
     * Calculates the commission amount for this salesperson
     * @return the commission amount (amountOfSales * commissionPercentage)
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;
    
    public Worker() {}
    
    public int getWeeklyWorkingHour() { return weeklyWorkingHour; }
    public void setWeeklyWorkingHour(int weeklyWorkingHour) { this.weeklyWorkingHour = weeklyWorkingHour; }
    
    public double getHourlyRates() { return hourlyRates; }
    public void setHourlyRates(double hourlyRates) { this.hourlyRates = hourlyRates; }
    
    /**
     * Calculates the base salary for workers (weeklyWorkingHour * hourlyRates)
     * @return the base salary without any premiums
     */
    protected double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

class ShiftWorker extends Worker {
    private double holidayPremium;
    
    public ShiftWorker() {}
    
    public double getHolidayPremium() { return holidayPremium; }
    public void setHolidayPremium(double holidayPremium) { this.holidayPremium = holidayPremium; }
    
    /**
     * Calculates the shift worker's salary including holiday premium
     * @return the total salary (base salary + holiday premium)
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary() + holidayPremium;
    }
    
    /**
     * Calculates the holiday premium for this shift worker
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

class OffShiftWorker extends Worker {
    public OffShiftWorker() {}
    
    /**
     * Calculates the off-shift worker's salary (base salary only)
     * @return the base salary without any premiums
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary();
    }
}

class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;
    
    public Department() {
        employees = new ArrayList<>();
    }
    
    public DepartmentType getType() { return type; }
    public void setType(DepartmentType type) { this.type = type; }
    
    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }
    
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    
    /**
     * Calculates the average working hours per week for all workers in this department
     * @return the average working hours, or 0 if there are no workers in the delivery department
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0.0;
        }
        
        List<Worker> workers = employees.stream()
            .filter(e -> e instanceof Worker)
            .map(e -> (Worker) e)
            .collect(Collectors.toList());
            
        if (workers.isEmpty()) {
            return 0.0;
        }
        
        double totalHours = workers.stream()
            .mapToInt(Worker::getWeeklyWorkingHour)
            .sum();
            
        return totalHours / workers.size();
    }
}

class Company {
    private List<Employee> employees;
    private List<Department> departments;
    
    public Company() {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
    }
    
    public List<Employee> getEmployees() { return employees; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }
    
    public List<Department> getDepartments() { return departments; }
    public void setDepartments(List<Department> departments) { this.departments = departments; }
    
    /**
     * Calculates the total salary of all employees in the company
     * @return the sum of all employees' salaries
     */
    public double calculateTotalEmployeeSalary() {
        return employees.stream()
            .mapToDouble(Employee::calculateSalary)
            .sum();
    }
    
    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return the average working hours, or 0 if there are no workers in the delivery department
     */
    public double calculateAverageWorkerWorkingHoursInDeliveryDepartment() {
        Optional<Department> deliveryDept = departments.stream()
            .filter(d -> d.getType() == DepartmentType.DELIVERY)
            .findFirst();
            
        if (deliveryDept.isPresent()) {
            return deliveryDept.get().calculateAverageWorkerWorkingHours();
        }
        return 0.0;
    }
    
    /**
     * Determines the total commission amount for all salespeople in the company
     * @return the sum of commission amounts for all salespeople
     */
    public double calculateTotalSalesPeopleCommission() {
        return employees.stream()
            .filter(e -> e instanceof SalesPeople)
            .map(e -> (SalesPeople) e)
            .mapToDouble(SalesPeople::getTotalCommission)
            .sum();
    }
    
    /**
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return the sum of holiday premiums for all shift workers, or 0 if there are no shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        List<ShiftWorker> shiftWorkers = employees.stream()
            .filter(e -> e instanceof ShiftWorker)
            .map(e -> (ShiftWorker) e)
            .collect(Collectors.toList());
            
        if (shiftWorkers.isEmpty()) {
            return 0.0;
        }
        
        return shiftWorkers.stream()
            .mapToDouble(ShiftWorker::calculateHolidayPremium)
            .sum();
    }
}