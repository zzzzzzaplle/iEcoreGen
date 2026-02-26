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
    }
    
    /**
     * Gets the department of the employee
     * @return the department name
     */
    public String getDepartment() {
        return department;
    }
    
    /**
     * Sets the department of the employee
     * @param department the department name to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }
    
    /**
     * Gets the name of the employee
     * @return the employee name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name of the employee
     * @param name the employee name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the birth date of the employee
     * @return the birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }
    
    /**
     * Sets the birth date of the employee
     * @param birthDate the birth date to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    /**
     * Gets the social insurance number of the employee
     * @return the social insurance number
     */
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }
    
    /**
     * Sets the social insurance number of the employee
     * @param socialInsuranceNumber the social insurance number to set
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }
    
    /**
     * Abstract method to calculate the salary of the employee
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
    private List<Employee> subordinates;
    
    public Manager() {
        subordinates = new ArrayList<>();
    }
    
    /**
     * Gets the fixed salary of the manager
     * @return the manager's salary
     */
    public double getSalary() {
        return salary;
    }
    
    /**
     * Sets the fixed salary of the manager
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    /**
     * Gets the position of the manager
     * @return the manager's position
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * Sets the position of the manager
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    /**
     * Gets the list of subordinate employees
     * @return list of subordinate employees
     */
    public List<Employee> getSubordinates() {
        return subordinates;
    }
    
    /**
     * Sets the list of subordinate employees
     * @param subordinates the list of subordinates to set
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }
    
    /**
     * Adds a subordinate employee to the manager
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }
    
    /**
     * Removes a subordinate employee from the manager
     * @param employee the employee to remove from subordinates
     */
    public void removeSubordinate(Employee employee) {
        subordinates.remove(employee);
    }
    
    /**
     * Calculates the salary of the manager
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

/**
 * Class representing a sales person employee
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;
    
    public SalesPeople() {
    }
    
    /**
     * Gets the fixed salary of the sales person
     * @return the fixed salary
     */
    public double getSalary() {
        return salary;
    }
    
    /**
     * Sets the fixed salary of the sales person
     * @param salary the fixed salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    /**
     * Gets the amount of sales made by the sales person
     * @return the amount of sales
     */
    public double getAmountOfSales() {
        return amountOfSales;
    }
    
    /**
     * Sets the amount of sales made by the sales person
     * @param amountOfSales the amount of sales to set
     */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }
    
    /**
     * Gets the commission percentage for the sales person
     * @return the commission percentage
     */
    public double getCommissionPercentage() {
        return commissionPercentage;
    }
    
    /**
     * Sets the commission percentage for the sales person
     * @param commissionPercentage the commission percentage to set
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }
    
    /**
     * Calculates the total salary of the sales person
     * Salary = fixed salary + (amount of sales * commission percentage)
     * @return the calculated total salary
     */
    @Override
    public double calculateSalary() {
        return salary + (amountOfSales * commissionPercentage);
    }
    
    /**
     * Calculates the total commission earned by the sales person
     * @return the calculated commission amount (amount of sales * commission percentage)
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
    
    public Worker() {
    }
    
    /**
     * Gets the weekly working hours of the worker
     * @return the weekly working hours
     */
    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }
    
    /**
     * Sets the weekly working hours of the worker
     * @param weeklyWorkingHour the weekly working hours to set
     */
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }
    
    /**
     * Gets the hourly rates of the worker
     * @return the hourly rates
     */
    public double getHourlyRates() {
        return hourlyRates;
    }
    
    /**
     * Sets the hourly rates of the worker
     * @param hourlyRates the hourly rates to set
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }
    
    /**
     * Calculates the base salary of the worker
     * Base salary = weekly working hours * hourly rates
     * @return the calculated base salary
     */
    protected double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

/**
 * Class representing a shift worker employee
 */
class ShiftWorker extends Worker {
    private double holidayPremium;
    
    public ShiftWorker() {
    }
    
    /**
     * Gets the holiday premium amount for the shift worker
     * @return the holiday premium amount
     */
    public double getHolidayPremium() {
        return holidayPremium;
    }
    
    /**
     * Sets the holiday premium amount for the shift worker
     * @param holidayPremium the holiday premium amount to set
     */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }
    
    /**
     * Calculates the salary of the shift worker
     * Salary = base salary (weekly hours * hourly rates) + holiday premium
     * @return the calculated total salary including holiday premium
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary() + holidayPremium;
    }
    
    /**
     * Calculates the holiday premium for the shift worker
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
    public OffShiftWorker() {
    }
    
    /**
     * Calculates the salary of the off-shift worker
     * Salary = base salary (weekly hours * hourly rates)
     * @return the calculated base salary
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
    private List<Employee> employees;
    
    public Department() {
        employees = new ArrayList<>();
    }
    
    /**
     * Gets the type of the department
     * @return the department type
     */
    public DepartmentType getType() {
        return type;
    }
    
    /**
     * Sets the type of the department
     * @param type the department type to set
     */
    public void setType(DepartmentType type) {
        this.type = type;
    }
    
    /**
     * Gets the manager of the department
     * @return the department manager
     */
    public Manager getManager() {
        return manager;
    }
    
    /**
     * Sets the manager of the department
     * @param manager the manager to set for the department
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }
    
    /**
     * Gets the list of employees in the department
     * @return list of department employees
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
        employees.add(employee);
    }
    
    /**
     * Removes an employee from the department
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    /**
     * Calculates the average working hours per week for all workers in the department
     * @return the average working hours per week, or 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        if (employees.isEmpty()) {
            return 0.0;
        }
        
        int totalHours = 0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHour();
                workerCount++;
            }
        }
        
        return workerCount > 0 ? (double) totalHours / workerCount : 0.0;
    }
    
    /**
     * Gets all workers in the delivery department
     * @return list of workers in the delivery department
     */
    public List<Worker> getWorkersInDeliveryDepartment() {
        List<Worker> deliveryWorkers = new ArrayList<>();
        if (type == DepartmentType.DELIVERY) {
            for (Employee employee : employees) {
                if (employee instanceof Worker) {
                    deliveryWorkers.add((Worker) employee);
                }
            }
        }
        return deliveryWorkers;
    }
}

/**
 * Class representing the company
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;
    
    public Company() {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
    }
    
    /**
     * Gets the list of all employees in the company
     * @return list of all employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }
    
    /**
     * Sets the list of all employees in the company
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    /**
     * Gets the list of all departments in the company
     * @return list of all departments
     */
    public List<Department> getDepartments() {
        return departments;
    }
    
    /**
     * Sets the list of all departments in the company
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
        employees.add(employee);
    }
    
    /**
     * Removes an employee from the company
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    /**
     * Adds a department to the company
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }
    
    /**
     * Removes a department from the company
     * @param department the department to remove
     */
    public void removeDepartment(Department department) {
        departments.remove(department);
    }
    
    /**
     * Calculates the total salary of all employees in the company
     * Total salary = sum of all employees' salaries
     * @return the total salary amount for all employees
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;
        for (Employee employee : employees) {
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }
    
    /**
     * Calculates the total commission amount for all salespeople in the company
     * @return the total commission amount
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
     * Calculates the total holiday premiums paid to all shift workers in the company
     * @return the total holiday premium amount, or 0 if there are no shift workers
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
     * @return the average working hours, or 0 if there are no workers in the delivery department
     */
    public double findAverageWorkingHoursForDeliveryDepartment() {
        for (Department department : departments) {
            if (department.getType() == DepartmentType.DELIVERY) {
                return department.calculateAverageWorkerWorkingHours();
            }
        }
        return 0.0;
    }
}