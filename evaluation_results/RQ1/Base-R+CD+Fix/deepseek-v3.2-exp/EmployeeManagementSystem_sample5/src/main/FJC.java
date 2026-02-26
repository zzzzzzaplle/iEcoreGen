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

    /**
     * Default constructor
     */
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
     * @param department the department name
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
     * @param name the employee name
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
     * @param birthDate the birth date
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
     * @param socialInsuranceNumber the social insurance number
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Abstract method to calculate salary for different employee types
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

    /**
     * Default constructor
     */
    public Manager() {
        this.subordinates = new ArrayList<>();
    }

    /**
     * Gets the fixed salary of the manager
     * @return the fixed salary amount
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the fixed salary of the manager
     * @param salary the fixed salary amount
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
     * @param position the manager's position
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
     * @param subordinates list of subordinate employees
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Adds a subordinate employee to the manager
     * @param employee the subordinate employee to add
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Removes a subordinate employee from the manager
     * @param employee the subordinate employee to remove
     */
    public void removeSubordinate(Employee employee) {
        subordinates.remove(employee);
    }

    /**
     * Calculates the manager's salary
     * @return the fixed salary amount
     */
    @Override
    public double calculateSalary() {
        return salary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager
     * @return the count of direct subordinates
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

    /**
     * Default constructor
     */
    public SalesPeople() {
    }

    /**
     * Gets the fixed salary of the sales person
     * @return the fixed salary amount
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the fixed salary of the sales person
     * @param salary the fixed salary amount
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the amount of sales made by the sales person
     * @return the sales amount
     */
    public double getAmountOfSales() {
        return amountOfSales;
    }

    /**
     * Sets the amount of sales made by the sales person
     * @param amountOfSales the sales amount
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
     * @param commissionPercentage the commission percentage
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the total commission for the sales person
     * @return the calculated commission amount
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }

    /**
     * Calculates the sales person's total salary
     * @return the total salary (fixed salary + commission)
     */
    @Override
    public double calculateSalary() {
        return salary + getTotalCommission();
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
     * Gets the weekly working hours of the worker
     * @return the weekly working hours
     */
    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    /**
     * Sets the weekly working hours of the worker
     * @param weeklyWorkingHour the weekly working hours
     */
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    /**
     * Gets the hourly rates of the worker
     * @return the hourly rate
     */
    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rates of the worker
     * @param hourlyRates the hourly rate
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    /**
     * Calculates the base salary for the worker
     * @return the base salary (weekly hours * hourly rate)
     */
    public double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

/**
 * Class representing a shift worker
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Default constructor
     */
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
     * @param holidayPremium the holiday premium amount
     */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for the shift worker
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }

    /**
     * Calculates the shift worker's total salary
     * @return the total salary (base salary + holiday premium)
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary() + calculateHolidayPremium();
    }
}

/**
 * Class representing an off-shift worker
 */
class OffShiftWorker extends Worker {

    /**
     * Default constructor
     */
    public OffShiftWorker() {
    }

    /**
     * Calculates the off-shift worker's salary
     * @return the base salary (weekly hours * hourly rate)
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

    /**
     * Default constructor
     */
    public Department() {
        this.employees = new ArrayList<>();
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
     * @param type the department type
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
     * @param manager the department manager
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
     * @param employees list of department employees
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
     * @return the average working hours, or 0 if there are no workers
     */
    public double calculateAverageWorkerWorkingHours() {
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
}

/**
 * Class representing the company
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    /**
     * Default constructor
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
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
     * @param employees list of all employees
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
     * @param departments list of all departments
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
     * @return the total salary amount
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
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return the total holiday premiums amount, or 0 if there are no shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.calculateHolidayPremium();
            }
        }
        return totalPremiums;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return the average working hours, or 0 if there are no workers in delivery department
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
    public List<String> getDirectSubordinateCountsForManagers() {
        List<String> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                result.add(manager.getName() + ": " + manager.getDirectSubordinateEmployeesCount() + " subordinates");
            }
        }
        return result;
    }
}