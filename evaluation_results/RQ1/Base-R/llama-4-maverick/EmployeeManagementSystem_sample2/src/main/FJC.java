import java.util.ArrayList;
import java.util.List;

/**
 * Represents a department in the company.
 */
class Department {
    private String name;
    private Manager manager;
    private List<Employee> employees;

    /**
     * Default constructor for Department.
     */
    public Department() {
        this.employees = new ArrayList<>();
    }

    /**
     * Gets the name of the department.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the department.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the manager of the department.
     * @return the manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Gets the employees in the department.
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees in the department.
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the department.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }
}

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    protected String name;
    protected String dateOfBirth;
    protected String socialInsuranceNumber;
    protected Department department;

    /**
     * Default constructor for Employee.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the date of birth of the employee.
     * @return the dateOfBirth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the employee.
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the social insurance number of the employee.
     * @return the socialInsuranceNumber
     */
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    /**
     * Sets the social insurance number of the employee.
     * @param socialInsuranceNumber the socialInsuranceNumber to set
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Gets the department of the employee.
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Sets the department of the employee.
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Calculates the salary of the employee.
     * @return the salary
     */
    public abstract double calculateSalary();
}

/**
 * Represents a worker in the company.
 */
abstract class Worker extends Employee {
    protected double weeklyWorkingHours;
    protected double hourlyRates;

    /**
     * Default constructor for Worker.
     */
    public Worker() {}

    /**
     * Gets the weekly working hours of the worker.
     * @return the weeklyWorkingHours
     */
    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    /**
     * Sets the weekly working hours of the worker.
     * @param weeklyWorkingHours the weeklyWorkingHours to set
     */
    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    /**
     * Gets the hourly rates of the worker.
     * @return the hourlyRates
     */
    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rates of the worker.
     * @param hourlyRates the hourlyRates to set
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    /**
     * Calculates the salary of the worker.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return weeklyWorkingHours * hourlyRates;
    }
}

/**
 * Represents a shift worker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremiums;

    /**
     * Default constructor for ShiftWorker.
     */
    public ShiftWorker() {}

    /**
     * Gets the holiday premiums of the shift worker.
     * @return the holidayPremiums
     */
    public double getHolidayPremiums() {
        return holidayPremiums;
    }

    /**
     * Sets the holiday premiums of the shift worker.
     * @param holidayPremiums the holidayPremiums to set
     */
    public void setHolidayPremiums(double holidayPremiums) {
        this.holidayPremiums = holidayPremiums;
    }

    /**
     * Calculates the salary of the shift worker.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + holidayPremiums;
    }
}

/**
 * Represents a non-shift worker in the company.
 */
class NonShiftWorker extends Worker {
    /**
     * Default constructor for NonShiftWorker.
     */
    public NonShiftWorker() {}
}

/**
 * Represents a salesperson in the company.
 */
class Salesperson extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor for Salesperson.
     */
    public Salesperson() {}

    /**
     * Gets the salary of the salesperson.
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the salesperson.
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the amount of sales made by the salesperson.
     * @return the amountOfSales
     */
    public double getAmountOfSales() {
        return amountOfSales;
    }

    /**
     * Sets the amount of sales made by the salesperson.
     * @param amountOfSales the amountOfSales to set
     */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    /**
     * Gets the commission percentage of the salesperson.
     * @return the commissionPercentage
     */
    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    /**
     * Sets the commission percentage of the salesperson.
     * @param commissionPercentage the commissionPercentage to set
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the salary of the salesperson.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return salary + amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private List<Employee> subordinates;
    private String position;

    /**
     * Default constructor for Manager.
     */
    public Manager() {
        this.subordinates = new ArrayList<>();
    }

    /**
     * Gets the salary of the manager.
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the manager.
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the subordinates of the manager.
     * @return the subordinates
     */
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    /**
     * Sets the subordinates of the manager.
     * @param subordinates the subordinates to set
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Adds a subordinate to the manager.
     * @param subordinate the subordinate to add
     */
    public void addSubordinate(Employee subordinate) {
        this.subordinates.add(subordinate);
    }

    /**
     * Gets the position of the manager.
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the manager.
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Calculates the salary of the manager.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return salary;
    }

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @return the number of subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company.
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    /**
     * Gets the employees in the company.
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees in the company.
     * @param employees the employees to set
     */
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
     * Gets the departments in the company.
     * @return the departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the departments in the company.
     * @param departments the departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds a department to the company.
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        this.departments.add(department);
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
     * @return the average working hours
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalWorkingHours = 0;
        int workerCount = 0;
        for (Department department : departments) {
            if (department.getName().equals("Delivery")) {
                for (Employee employee : department.getEmployees()) {
                    if (employee instanceof Worker) {
                        totalWorkingHours += ((Worker) employee).getWeeklyWorkingHours();
                        workerCount++;
                    }
                }
            }
        }
        return workerCount == 0 ? 0 : totalWorkingHours / workerCount;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * @return the total commission amount
     */
    public double calculateTotalCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof Salesperson) {
                Salesperson salesperson = (Salesperson) employee;
                totalCommission += salesperson.getAmountOfSales() * salesperson.getCommissionPercentage();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return the total holiday premiums
     */
    public double calculateTotalHolidayPremiums() {
        double totalHolidayPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                totalHolidayPremiums += ((ShiftWorker) employee).getHolidayPremiums();
            }
        }
        return totalHolidayPremiums;
    }
}