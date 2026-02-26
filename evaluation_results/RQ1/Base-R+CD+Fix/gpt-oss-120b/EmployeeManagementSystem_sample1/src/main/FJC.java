import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Base class for all employees.
 */
abstract class Employee {

    private String department;                     // e.g. "PRODUCTION", "CONTROL", "DELIVERY"
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    /** No‑argument constructor */
    public Employee() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
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
}

/**
 * Manager employee. A manager has a fixed salary, a position and a list of direct subordinates.
 */
class Manager extends Employee {

    private double salary;
    private String position;
    private final List<Employee> subordinates = new ArrayList<>();

    /** No‑argument constructor */
    public Manager() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
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
        return Collections.unmodifiableList(subordinates);
    }

    /**
     * Adds a direct subordinate to this manager.
     *
     * @param e the employee to be added as a subordinate
     */
    public void addSubordinate(Employee e) {
        if (e != null && !subordinates.contains(e)) {
            subordinates.add(e);
        }
    }

    /**
     * Returns the number of direct subordinate employees.
     *
     * @return the count of subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

/**
 * Sales person employee.
 */
class SalesPeople extends Employee {

    private double salary;                 // fixed salary
    private double amountOfSales;
    private double commissionPercentage;   // expressed as a decimal (e.g. 0.05 for 5%)

    /** No‑argument constructor */
    public SalesPeople() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
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
     * Calculates the total commission earned by this salesperson.
     *
     * @return commission = amountOfSales * commissionPercentage
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Abstract worker employee.
 */
abstract class Worker extends Employee {

    private int weeklyWorkingHour;
    private double hourlyRates;

    /** No‑argument constructor */
    public Worker() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
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
}

/**
 * Shift worker – can only be placed in the DELIVERY department.
 */
class ShiftWorker extends Worker {

    private double holidayPremium;   // premium amount for each holiday shift

    /** No‑argument constructor */
    public ShiftWorker() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Returns the holiday premium for this shift worker.
     *
     * @return the premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Off‑shift worker – regular employee.
 */
class OffShiftWorker extends Worker {

    /** No‑argument constructor */
    public OffShiftWorker() {
    }

    // No additional fields.
}

/**
 * Department type enumeration.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Department entity. Holds a type, a manager and the employees belonging to the department.
 */
class Department {

    private DepartmentType type;
    private Manager manager;
    private final List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor */
    public Department() {
    }

    // -------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------
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
        return Collections.unmodifiableList(employees);
    }

    /**
     * Adds an employee to this department.
     *
     * @param e employee to add
     */
    public void addEmployee(Employee e) {
        if (e != null && !employees.contains(e)) {
            employees.add(e);
        }
    }

    /**
     * Calculates the average weekly working hours of all workers in this department.
     * If the department has no workers, returns 0.
     *
     * @return average working hours per week
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
        return workerCount == 0 ? 0.0 : (double) totalHours / workerCount;
    }
}

/**
 * Company entity – aggregates employees and departments and provides various calculations.
 */
class Company {

    private final List<Employee> employees = new ArrayList<>();
    private final List<Department> departments = new ArrayList<>();

    /** No‑argument constructor */
    public Company() {
    }

    // -------------------------------------------------------
    // Getters
    // -------------------------------------------------------
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    /**
     * Adds an employee to the company.
     *
     * @param e employee to add
     */
    public void addEmployee(Employee e) {
        if (e != null && !employees.contains(e)) {
            employees.add(e);
        }
    }

    /**
     * Adds a department to the company.
     *
     * @param d department to add
     */
    public void addDepartment(Department d) {
        if (d != null && !departments.contains(d)) {
            departments.add(d);
        }
    }

    /**
     * Calculates the total salary of all employees in the company.
     * <p>
     * Workers' salary = weeklyWorkingHour * hourlyRates + holiday premium (if shift worker).<br>
     * SalesPeople's salary = fixed salary + amountOfSales * commissionPercentage.<br>
     * Managers' salary = fixed salary.
     *
     * @return total salary amount
     */
    public double calculateTotalEmployeeSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof Manager) {
                total += ((Manager) e).getSalary();
            } else if (e instanceof SalesPeople) {
                SalesPeople sp = (SalesPeople) e;
                total += sp.getSalary() + sp.getTotalCommission();
            } else if (e instanceof Worker) {
                Worker w = (Worker) e;
                double salary = w.getWeeklyWorkingHour() * w.getHourlyRates();
                if (w instanceof ShiftWorker) {
                    salary += ((ShiftWorker) w).calculateHolidayPremium();
                }
                total += salary;
            }
        }
        return total;
    }

    /**
     * Calculates the average weekly working hours of all workers that belong to the
     * DELIVERY department. Returns 0 if there are no such workers.
     *
     * @return average working hours per week for delivery workers
     */
    public double calculateAverageDeliveryWorkerHours() {
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
        return workerCount == 0 ? 0.0 : (double) totalHours / workerCount;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     *
     * @return sum of (amountOfSales * commissionPercentage) for all salespeople
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
     * Calculates the total holiday premiums paid to all shift workers in the company.
     *
     * @return sum of holiday premiums; 0 if no shift workers exist
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
}