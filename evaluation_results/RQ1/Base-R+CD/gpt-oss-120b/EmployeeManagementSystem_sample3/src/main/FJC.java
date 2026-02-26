package com.company.ems;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Base abstract class for all employees.
 */
public abstract class Employee {

    /** Department where the employee works (e.g. PRODUCTION, CONTROL, DELIVERY). */
    private String department;
    /** Full name of the employee. */
    private String name;
    /** Date of birth of the employee. */
    private Date birthDate;
    /** Social Insurance Number (unique identifier). */
    private String socialInsuranceNumber;

    /** Default constructor. */
    public Employee() {
    }

    /** Getters and setters */
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
     * Calculates the monthly/weekly salary of the employee.
     * Concrete subclasses must provide the specific calculation.
     *
     * @return the calculated salary
     */
    public abstract double calculateSalary();
}

/**
 * Manager employee. A manager receives a fixed salary and may have subordinates.
 */
class Manager extends Employee {

    private double salary;
    private String position;
    /** Direct subordinates of this manager. */
    private List<Employee> subordinates = new ArrayList<>();

    /** Default constructor. */
    public Manager() {
    }

    /** Getters and setters */
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
     * @return count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates != null ? subordinates.size() : 0;
    }

    /**
     * Adds a subordinate to this manager.
     *
     * @param e employee to be added as subordinate
     */
    public void addSubordinate(Employee e) {
        if (e != null) {
            subordinates.add(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateSalary() {
        return salary;
    }
}

/**
 * SalesPeople employee. Receives a fixed salary plus commission based on sales.
 */
class SalesPeople extends Employee {

    private double salary;
    private double amountOfSales;
    private double commissionPercentage; // expressed as a decimal (e.g., 0.05 for 5%)

    /** Default constructor. */
    public SalesPeople() {
    }

    /** Getters and setters */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public double calculateSalary() {
        return salary + getTotalCommission();
    }
}

/**
 * Abstract class for workers (both shift and off‑shift).
 */
abstract class Worker extends Employee {

    private int weeklyWorkingHour;
    private double hourlyRates;

    /** Default constructor. */
    public Worker() {
    }

    /** Getters and setters */
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
     * Calculates the base salary for a worker (without any premium).
     *
     * @return weeklyWorkingHour * hourlyRates
     */
    protected double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

/**
 * Shift worker – works in delivery department and receives holiday premiums.
 */
class ShiftWorker extends Worker {

    private double holidayPremium; // extra amount for working on holidays

    /** Default constructor. */
    public ShiftWorker() {
    }

    /** Getters and setters */
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Returns the holiday premium amount for this shift worker.
     *
     * @return holiday premium
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }

    /**
     * {@inheritDoc}
     * Includes holiday premium in the salary.
     *
     * @return base salary + holiday premium
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary() + calculateHolidayPremium();
    }
}

/**
 * Off‑shift worker – regular worker without holiday premium.
 */
class OffShiftWorker extends Worker {

    /** Default constructor. */
    public OffShiftWorker() {
    }

    /**
     * {@inheritDoc}
     *
     * @return base salary (no premium)
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary();
    }
}

/**
 * Enumeration of possible department types.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Department class. Holds a type, a manager and the employees that belong to it.
 */
class Department {

    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    /** Default constructor. */
    public Department() {
    }

    /** Getters and setters */
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
     * Adds an employee to this department.
     *
     * @param e employee to add
     */
    public void addEmployee(Employee e) {
        if (e != null) {
            employees.add(e);
        }
    }

    /**
     * Calculates the average weekly working hours of all workers in this department.
     *
     * @return average working hours, or 0 if there are no workers in the department
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
 * Company class – aggregates employees and departments and provides
 * various aggregate calculations required by the functional specifications.
 */
class Company {

    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    /** Default constructor. */
    public Company() {
    }

    /** Getters and setters */
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
     * Adds an employee to the company.
     *
     * @param e employee to add
     */
    public void addEmployee(Employee e) {
        if (e != null) {
            employees.add(e);
        }
    }

    /**
     * Adds a department to the company.
     *
     * @param d department to add
     */
    public void addDepartment(Department d) {
        if (d != null) {
            departments.add(d);
        }
    }

    /**
     * Calculates the total salary of all employees in the company.
     *
     * <p>Total salary = sum of each employee's calculated salary.
     *
     * @return total salary of all employees
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
     * Calculates total holiday premiums paid to all shift workers in the company.
     *
     * @return sum of holiday premiums for all shift workers; 0 if none exist
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremium = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                totalPremium += ((ShiftWorker) e).calculateHolidayPremium();
            }
        }
        return totalPremium;
    }

    /**
     * Finds the average weekly working hours for all workers in the DELIVERY department.
     *
     * @return average working hours, or 0 if there are no workers in the delivery department
     */
    public double calculateAverageWorkingHoursInDeliveryDepartment() {
        for (Department d : departments) {
            if (d.getType() == DepartmentType.DELIVERY) {
                return d.calculateAverageWorkerWorkingHours();
            }
        }
        return 0.0;
    }
}