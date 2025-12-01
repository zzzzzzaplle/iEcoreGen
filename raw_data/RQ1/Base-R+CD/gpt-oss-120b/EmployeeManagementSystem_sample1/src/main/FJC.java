package com.company.ems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of the possible department types in the company.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Abstract base class for all employees.
 */
public abstract class Employee {

    /** Department where the employee works. */
    private DepartmentType department;

    /** Full name of the employee. */
    private String name;

    /** Date of birth of the employee. */
    private Date birthDate;

    /** Social insurance number of the employee. */
    private String socialInsuranceNumber;

    /** No‑argument constructor required by the specification. */
    public Employee() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public DepartmentType getDepartment() {
        return department;
    }

    /**
     * Sets the department for the employee.
     *
     * @param department the department to assign
     */
    public void setDepartment(DepartmentType department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the employee's full name.
     *
     * @param name the name to assign
     */
    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the employee's date of birth.
     *
     * @param birthDate the birth date to assign
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    /**
     * Sets the employee's social insurance number.
     *
     * @param socialInsuranceNumber the number to assign
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }
}

/**
 * Abstract class for workers (shift and off‑shift).
 */
public abstract class Worker extends Employee {

    /** Weekly working hours. */
    private int weeklyWorkingHour;

    /** Hourly rate. */
    private double hourlyRates;

    /** No‑argument constructor. */
    public Worker() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    /**
     * Sets the weekly working hours.
     *
     * @param weeklyWorkingHour number of hours per week
     */
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rate.
     *
     * @param hourlyRates hourly remuneration
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }
}

/**
 * Shift worker – can only belong to the DELIVERY department and receives a
 * holiday premium.
 */
 class ShiftWorker extends Worker {

    /** Premium amount paid for each holiday worked. */
    private double holidayPremium;

    /** No‑argument constructor. */
    public ShiftWorker() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public double getHolidayPremium() {
        return holidayPremium;
    }

    /**
     * Sets the holiday premium.
     *
     * @param holidayPremium premium amount
     */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Overrides the department setter to guarantee that a shift worker can only
     * be assigned to the DELIVERY department.
     *
     * @param department the department to assign
     * @throws IllegalArgumentException if the department is not DELIVERY
     */
    @Override
    public void setDepartment(DepartmentType department) {
        if (department != DepartmentType.DELIVERY) {
            throw new IllegalArgumentException(
                "ShiftWorker can only be assigned to the DELIVERY department.");
        }
        super.setDepartment(department);
    }

    /**
     * Returns the holiday premium that this shift worker receives.
     *
     * @return holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Off‑shift worker – works regular hours without holiday premiums.
 */
 class OffShiftWorker extends Worker {

    /** No‑argument constructor. */
    public OffShiftWorker() {
    }

    // No additional fields – inherits everything from Worker.
}

/**
 * Manager – receives a fixed salary and may have subordinates.
 */
 class Manager extends Employee {

    /** Fixed salary. */
    private double salary;

    /** Position title (e.g., "Production Manager"). */
    private String position;

    /** Direct subordinates of this manager. */
    private List<Employee> subordinates = new ArrayList<>();

    /** No‑argument constructor. */
    public Manager() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public double getSalary() {
        return salary;
    }

    /**
     * Sets the manager's salary.
     *
     * @param salary the fixed salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    /**
     * Sets the manager's position title.
     *
     * @param position the position description
     */
    public void setPosition(String position) {
        this.position = position;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    /**
     * Sets the list of direct subordinates.
     *
     * @param subordinates list of employees reporting directly to this manager
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Returns the number of direct subordinate employees for this manager.
     *
     * @return count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }

    /**
     * Adds a single subordinate to this manager.
     *
     * @param employee the employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        if (employee != null && !subordinates.contains(employee)) {
            subordinates.add(employee);
        }
    }
}

/**
 * Sales person – receives a fixed salary plus commission based on sales.
 */
 class SalesPeople extends Employee {

    /** Fixed salary. */
    private double salary;

    /** Total amount of sales made. */
    private double amountOfSales;

    /** Commission percentage (e.g., 0.05 for 5%). */
    private double commissionPercentage;

    /** No‑argument constructor. */
    public SalesPeople() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public double getSalary() {
        return salary;
    }

    /**
     * Sets the fixed salary.
     *
     * @param salary the base salary
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getAmountOfSales() {
        return amountOfSales;
    }

    /**
     * Sets the total amount of sales.
     *
     * @param amountOfSales total sales amount
     */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    /**
     * Sets the commission percentage.
     *
     * @param commissionPercentage commission rate expressed as a decimal
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the commission amount for this sales person.
     *
     * @return commission = amountOfSales * commissionPercentage
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Department – groups employees and is managed by a single manager.
 */
 class Department {

    /** Type of the department. */
    private DepartmentType type;

    /** Manager that controls this department. */
    private Manager manager;

    /** Employees assigned to this department. */
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor. */
    public Department() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public DepartmentType getType() {
        return type;
    }

    /**
     * Sets the department type.
     *
     * @param type the department type
     */
    public void setType(DepartmentType type) {
        this.type = type;
    }

    public Manager getManager() {
        return manager;
    }

    /**
     * Sets the manager for the department.
     *
     * @param manager the manager responsible for this department
     */
    public void setManager(Manager manager) {
        this.manager = manager;
        if (manager != null) {
            manager.setDepartment(type);
        }
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees belonging to the department.
     *
     * @param employees list of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to this department.
     *
     * @param employee employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
            employee.setDepartment(this.type);
        }
    }

    /**
     * Calculates the average weekly working hours of all workers in this
     * department. Returns 0 if there are no workers.
     *
     * @return average weekly working hours, or 0 if none
     */
    public double calculateAverageWorkerWorkingHours() {
        double totalHours = 0.0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                totalHours += ((Worker) e).getWeeklyWorkingHour();
                count++;
            }
        }
        return count == 0 ? 0.0 : totalHours / count;
    }
}

/**
 * Company – aggregates all employees, departments and provides global
 * calculations.
 */
 class Company {

    /** All employees employed by the company. */
    private List<Employee> employees = new ArrayList<>();

    /** All departments of the company. */
    private List<Department> departments = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees for the company.
     *
     * @param employees list of all employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the list of departments for the company.
     *
     * @param departments list of departments
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds an employee to the company-wide employee list.
     *
     * @param employee employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
        }
    }

    /**
     * Adds a department to the company.
     *
     * @param department department to add
     */
    public void addDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            departments.add(department);
        }
    }

    // -----------------------------------------------------------------------
    // Business Logic
    // -----------------------------------------------------------------------

    /**
     * Calculates the total salary of all employees in the company.
     *
     * <p>Total salary = sum of (workers' salary + sales people's salary + managers' salary).
     *
     * @return total salary paid to all employees
     */
    public double calculateTotalEmployeeSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                ShiftWorker sw = (ShiftWorker) e;
                total += sw.getWeeklyWorkingHour() * sw.getHourlyRates()
                        + sw.calculateHolidayPremium();
            } else if (e instanceof OffShiftWorker) {
                OffShiftWorker ow = (OffShiftWorker) e;
                total += ow.getWeeklyWorkingHour() * ow.getHourlyRates();
            } else if (e instanceof SalesPeople) {
                SalesPeople sp = (SalesPeople) e;
                total += sp.getSalary() + sp.getTotalCommission();
            } else if (e instanceof Manager) {
                Manager m = (Manager) e;
                total += m.getSalary();
            }
        }
        return total;
    }

    /**
     * Calculates the average weekly working hours of all workers in the DELIVERY
     * department. Returns 0 if there are no such workers.
     *
     * @return average weekly working hours for delivery‑department workers
     */
    public double calculateAverageWorkingHoursInDelivery() {
        double totalHours = 0.0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker && e.getDepartment() == DepartmentType.DELIVERY) {
                totalHours += ((Worker) e).getWeeklyWorkingHour();
                count++;
            }
        }
        return count == 0 ? 0.0 : totalHours / count;
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
     * Calculates the total holiday premiums paid to all shift workers in the
     * company. Returns 0 if there are no shift workers.
     *
     * @return sum of holiday premiums for all shift workers
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