import java.util.*;
import java.util.stream.Collectors;

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

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * Gets the department
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the birth date
     * @return the birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date
     * @param birthDate the birth date to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the social insurance number
     * @return the social insurance number
     */
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    /**
     * Sets the social insurance number
     * @param socialInsuranceNumber the social insurance number to set
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Abstract method to calculate salary for the employee
     * @return the calculated salary
     */
    public abstract double calculateSalary();
}

/**
 * Class representing a manager employee
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    /**
     * Default constructor
     */
    public Manager() {
    }

    /**
     * Gets the salary
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the position
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the list of subordinates
     * @return the list of subordinates
     */
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    /**
     * Sets the list of subordinates
     * @param subordinates the list of subordinates to set
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Adds a subordinate to the manager
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    /**
     * Removes a subordinate from the manager
     * @param employee the employee to remove from subordinates
     */
    public void removeSubordinate(Employee employee) {
        this.subordinates.remove(employee);
    }

    /**
     * Calculates the manager's salary
     * @return the manager's fixed salary
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

/**
 * Class representing a salesperson employee
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor
     */
    public SalesPeople() {
    }

    /**
     * Gets the fixed salary
     * @return the fixed salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the fixed salary
     * @param salary the fixed salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the amount of sales
     * @return the amount of sales
     */
    public double getAmountOfSales() {
        return amountOfSales;
    }

    /**
     * Sets the amount of sales
     * @param amountOfSales the amount of sales to set
     */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    /**
     * Gets the commission percentage
     * @return the commission percentage
     */
    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    /**
     * Sets the commission percentage
     * @param commissionPercentage the commission percentage to set
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the salesperson's total salary
     * @return the total salary (fixed salary + commission)
     */
    @Override
    public double calculateSalary() {
        return salary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the total commission for this salesperson
     * @return the total commission amount
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Abstract class representing a worker employee
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    /**
     * Default constructor
     */
    public Worker() {
    }

    /**
     * Gets the weekly working hours
     * @return the weekly working hours
     */
    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    /**
     * Sets the weekly working hours
     * @param weeklyWorkingHour the weekly working hours to set
     */
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    /**
     * Gets the hourly rates
     * @return the hourly rates
     */
    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rates
     * @param hourlyRates the hourly rates to set
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    /**
     * Calculates the base salary for the worker
     * @return the base salary (weekly working hours * hourly rates)
     */
    public double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

/**
 * Class representing a shift worker employee
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Default constructor
     */
    public ShiftWorker() {
    }

    /**
     * Gets the holiday premium
     * @return the holiday premium
     */
    public double getHolidayPremium() {
        return holidayPremium;
    }

    /**
     * Sets the holiday premium
     * @param holidayPremium the holiday premium to set
     */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the total salary including holiday premium
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

/**
 * Class representing an off-shift worker employee
 */
class OffShiftWorker extends Worker {
    /**
     * Default constructor
     */
    public OffShiftWorker() {
    }

    /**
     * Calculates the total salary (base salary only)
     * @return the total salary
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary();
    }
}

/**
 * Class representing a department in the company
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    /**
     * Default constructor
     */
    public Department() {
    }

    /**
     * Gets the department type
     * @return the department type
     */
    public DepartmentType getType() {
        return type;
    }

    /**
     * Sets the department type
     * @param type the department type to set
     */
    public void setType(DepartmentType type) {
        this.type = type;
    }

    /**
     * Gets the department manager
     * @return the department manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets the department manager
     * @param manager the department manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Gets the list of employees in the department
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the department
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the department
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Removes an employee from the department
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    /**
     * Calculates the average working hours per week for all workers in this department
     * @return the average working hours per week, or 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        List<Worker> workers = employees.stream()
                .filter(e -> e instanceof Worker)
                .map(e -> (Worker) e)
                .collect(Collectors.toList());

        if (workers.isEmpty()) {
            return 0;
        }

        double totalHours = workers.stream()
                .mapToInt(Worker::getWeeklyWorkingHour)
                .sum();

        return totalHours / workers.size();
    }
}

/**
 * Class representing the company
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    /**
     * Default constructor
     */
    public Company() {
    }

    /**
     * Gets the list of employees
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of departments
     * @return the list of departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments
     * @param departments the list of departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds an employee to the company
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Removes an employee from the company
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    /**
     * Adds a department to the company
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Removes a department from the company
     * @param department the department to remove
     */
    public void removeDepartment(Department department) {
        this.departments.remove(department);
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return the total salary of all employees
     */
    public double calculateTotalEmployeeSalary() {
        return employees.stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
    }

    /**
     * Calculates the total commission amount for all salespeople in the company
     * @return the total commission amount
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
     * @return the total holiday premiums amount, or 0 if there are no shift workers in the delivery department
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        List<ShiftWorker> shiftWorkers = employees.stream()
                .filter(e -> e instanceof ShiftWorker)
                .map(e -> (ShiftWorker) e)
                .collect(Collectors.toList());

        if (shiftWorkers.isEmpty()) {
            return 0;
        }

        return shiftWorkers.stream()
                .mapToDouble(ShiftWorker::calculateHolidayPremium)
                .sum();
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return the average working hours per week, or 0 if there are no workers in the delivery department
     */
    public double findAverageDeliveryDepartmentWorkingHours() {
        Optional<Department> deliveryDepartment = departments.stream()
                .filter(d -> d.getType() == DepartmentType.DELIVERY)
                .findFirst();

        if (deliveryDepartment.isPresent()) {
            return deliveryDepartment.get().calculateAverageWorkerWorkingHours();
        }

        return 0;
    }

    /**
     * Gets the number of direct subordinate employees for each manager
     * @return a map containing manager names and their subordinate counts
     */
    public Map<String, Integer> getManagerSubordinateCounts() {
        return employees.stream()
                .filter(e -> e instanceof Manager)
                .map(e -> (Manager) e)
                .collect(Collectors.toMap(
                        Manager::getName,
                        Manager::getDirectSubordinateEmployeesCount
                ));
    }
}