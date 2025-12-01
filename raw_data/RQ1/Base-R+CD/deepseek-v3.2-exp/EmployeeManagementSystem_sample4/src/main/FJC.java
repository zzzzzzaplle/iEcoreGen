import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * Enum representing different types of departments in the company
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
     * @param department the department name to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Gets the name of the employee
     * @return the employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee
     * @param name the employee's name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the birth date of the employee
     * @return the employee's birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the employee
     * @param birthDate the employee's birth date to set
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
     * Abstract method to calculate salary for different types of employees
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
     * @return the manager's salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the fixed salary of the manager
     * @param salary the manager's salary to set
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
     * @param position the manager's position to set
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
     * @param subordinates list of subordinate employees to set
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Adds a subordinate employee to the manager
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    /**
     * Removes a subordinate employee from the manager
     * @param employee the employee to remove from subordinates
     */
    public void removeSubordinate(Employee employee) {
        this.subordinates.remove(employee);
    }

    /**
     * Calculates the manager's salary
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

    /**
     * Default constructor
     */
    public SalesPeople() {
    }

    /**
     * Gets the fixed salary of the sales person
     * @return the sales person's fixed salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the fixed salary of the sales person
     * @param salary the sales person's fixed salary to set
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
     * Calculates the sales person's total salary
     * @return total salary = fixed salary + (amount of sales * commission percentage)
     */
    @Override
    public double calculateSalary() {
        return salary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the total commission earned by the sales person
     * @return total commission = amount of sales * commission percentage
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
     * Gets the weekly working hours of the worker
     * @return weekly working hours
     */
    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    /**
     * Sets the weekly working hours of the worker
     * @param weeklyWorkingHour weekly working hours to set
     */
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    /**
     * Gets the hourly rates of the worker
     * @return hourly rates
     */
    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rates of the worker
     * @param hourlyRates hourly rates to set
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    /**
     * Calculates the base salary for workers
     * @return base salary = weekly working hours * hourly rates
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

    /**
     * Default constructor
     */
    public ShiftWorker() {
    }

    /**
     * Gets the holiday premium amount for the shift worker
     * @return holiday premium amount
     */
    public double getHolidayPremium() {
        return holidayPremium;
    }

    /**
     * Sets the holiday premium amount for the shift worker
     * @param holidayPremium holiday premium amount to set
     */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the shift worker's total salary including holiday premium
     * @return total salary = base salary + holiday premium
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

    /**
     * Default constructor
     */
    public OffShiftWorker() {
    }

    /**
     * Calculates the off-shift worker's salary
     * @return base salary = weekly working hours * hourly rates
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
     * @param manager the department manager to set
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
     * @param employees list of department employees to set
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
     * Calculates the average working hours per week for all workers in the delivery department
     * @return average working hours per week, or 0 if there are no workers in the delivery department
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0.0;
        }

        int totalWorkers = 0;
        int totalHours = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHour();
                totalWorkers++;
            }
        }

        if (totalWorkers == 0) {
            return 0.0;
        }

        return (double) totalHours / totalWorkers;
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
     * @param employees list of all employees to set
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
     * @param departments list of all departments to set
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
     * @return total salary = sum of all employees' salaries
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
     * @return total commission = sum of (amount of sales * commission percentage) for all salespeople
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
     * @return total holiday premiums, or 0 if there are no shift workers in the delivery department
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasDeliveryShiftWorkers = false;

        for (Department department : departments) {
            if (department.getType() == DepartmentType.DELIVERY) {
                for (Employee employee : department.getEmployees()) {
                    if (employee instanceof ShiftWorker) {
                        ShiftWorker shiftWorker = (ShiftWorker) employee;
                        totalPremiums += shiftWorker.calculateHolidayPremium();
                        hasDeliveryShiftWorkers = true;
                    }
                }
            }
        }

        return hasDeliveryShiftWorkers ? totalPremiums : 0.0;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return average working hours per week, or 0 if there are no workers in the delivery department
     */
    public double findAverageDeliveryDepartmentWorkerHours() {
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
        List<String> managerCounts = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                managerCounts.add(manager.getName() + ": " + manager.getDirectSubordinateEmployeesCount() + " subordinates");
            }
        }
        return managerCounts;
    }
}