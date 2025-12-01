import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base abstract class for all employees.
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    public Employee() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Calculates the total salary for this employee.
     *
     * @return the salary amount
     */
    public abstract double calculateSalary();
}

/**
 * Represents a manager employee.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    public Manager() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Returns the number of direct subordinate employees.
     *
     * @return count of direct subordinates, 0 if none
     */
    public int getDirectSubordinateEmployeesCount() {
        return (subordinates != null) ? subordinates.size() : 0;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }
}

/**
 * Represents a sales person employee.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Calculates total commission earned by this sales person.
     *
     * @return amountOfSales * commissionPercentage
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }

    @Override
    public double calculateSalary() {
        return salary + getTotalCommission();
    }
}

/**
 * Abstract class for workers.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    public Worker() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Base salary for a worker (without holiday premium).
     *
     * @return weeklyWorkingHour * hourlyRates
     */
    protected double baseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }

    @Override
    public double calculateSalary() {
        return baseSalary();
    }
}

/**
 * Worker that works in shifts and receives holiday premiums.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Returns the holiday premium for this shift worker.
     *
     * @return holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }

    @Override
    public double calculateSalary() {
        return baseSalary() + calculateHolidayPremium();
    }
}

/**
 * Worker that does not work in shifts.
 */
class OffShiftWorker extends Worker {

    public OffShiftWorker() {
        // No‑arg constructor
    }

    // No additional fields; inherits everything from Worker
}

/**
 * Enumeration of department types.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Represents a department within the company.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    public Department() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Calculates the average weekly working hours for all workers in this department.
     *
     * @return average working hours, or 0 if there are no workers
     */
    public double calculateAverageWorkerWorkingHours() {
        int totalHours = 0;
        int workerCount = 0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                totalHours += ((Worker) e).getWeeklyWorkingHour();
                workerCount++;
            }
        }
        return (workerCount > 0) ? ((double) totalHours / workerCount) : 0.0;
    }
}

/**
 * Represents the whole company.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    public Company() {
        // No‑arg constructor
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
     * Calculates the total salary of all employees in the company.
     *
     * @return sum of salaries of workers, sales people and managers
     */
    public double calculateTotalEmployeeSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            total += e.calculateSalary();
        }
        return total;
    }

    /**
     * Calculates the total commission amount for all salespeople in the company.
     *
     * @return sum of (amountOfSales * commissionPercentage) for each sales person
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;
        for (Employee e : employees) {
            if (e instanceof SalesPeople) {
                totalCommission += ((SalesPeople) e).getTotalCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company.
     *
     * @return sum of holiday premiums for each shift worker, 0 if none exist
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) e).calculateHolidayPremium();
            }
        }
        return totalPremiums;
    }

    /**
     * Finds the average working hours per week for all workers in the DELIVERY department.
     *
     * @return average weekly working hours, or 0 if there are no workers in that department
     */
    public double calculateAverageWorkingHoursInDeliveryDepartment() {
        int totalHours = 0;
        int workerCount = 0;
        for (Department d : departments) {
            if (d.getType() == DepartmentType.DELIVERY) {
                for (Employee e : d.getEmployees()) {
                    if (e instanceof Worker) {
                        totalHours += ((Worker) e).getWeeklyWorkingHour();
                        workerCount++;
                    }
                }
            }
        }
        return (workerCount > 0) ? ((double) totalHours / workerCount) : 0.0;
    }
}