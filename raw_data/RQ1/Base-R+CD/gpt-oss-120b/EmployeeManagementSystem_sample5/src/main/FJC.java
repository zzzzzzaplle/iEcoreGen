import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of the possible department types.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Abstract base class for all employees.
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    /** Unparameterized constructor */
    public Employee() {
    }

    /** Getters and Setters */
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
     * Calculates the monthly (or weekly) salary for this employee.
     *
     * @return the salary amount as defined by the concrete subclass.
     */
    public abstract double calculateSalary();
}

/**
 * Manager class – a manager receives a fixed salary and may have sub‑ordinates.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    /** Unparameterized constructor */
    public Manager() {
    }

    /** Getters and Setters */
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
     * Adds a direct subordinate to this manager.
     *
     * @param e the employee to add as a subordinate
     */
    public void addSubordinate(Employee e) {
        if (e != null && !subordinates.contains(e)) {
            subordinates.add(e);
        }
    }

    /**
     * Returns the number of direct subordinate employees.
     *
     * @return count of subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }

    @Override
    public double calculateSalary() {
        return salary;
    }
}

/**
 * SalesPeople class – a salesperson receives a fixed salary plus commission.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage; // expressed as a decimal (e.g., 0.05 for 5%)

    /** Unparameterized constructor */
    public SalesPeople() {
    }

    /** Getters and Setters */
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
     * Returns the total commission earned by this salesperson.
     *
     * @return commission = amountOfSales * commissionPercentage
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
 * Abstract Worker class – common attributes for all workers.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    /** Unparameterized constructor */
    public Worker() {
    }

    /** Getters and Setters */
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
 * ShiftWorker – a worker that may receive holiday premiums.
 */
class ShiftWorker extends Worker {
    private double holidayPremium; // total premium amount for the period

    /** Unparameterized constructor */
    public ShiftWorker() {
    }

    /** Getters and Setters */
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
        return calculateBaseSalary() + calculateHolidayPremium();
    }
}

/**
 * OffShiftWorker – a regular worker without holiday premiums.
 */
class OffShiftWorker extends Worker {

    /** Unparameterized constructor */
    public OffShiftWorker() {
    }

    @Override
    public double calculateSalary() {
        return calculateBaseSalary();
    }
}

/**
 * Department class – contains employees and a manager.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    /** Unparameterized constructor */
    public Department() {
    }

    /** Getters and Setters */
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
        if (e != null && !employees.contains(e)) {
            employees.add(e);
        }
    }

    /**
     * Calculates the average weekly working hours for all workers in this department.
     *
     * @return average weekly working hours; 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        int totalHours = 0;
        int workerCount = 0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                Worker w = (Worker) e;
                totalHours += w.getWeeklyWorkingHour();
                workerCount++;
            }
        }
        if (workerCount == 0) {
            return 0.0;
        }
        return (double) totalHours / workerCount;
    }
}

/**
 * Company class – aggregates employees and departments and provides analytics.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    /** Unparameterized constructor */
    public Company() {
    }

    /** Getters and Setters */
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
     *
     * @return sum of salaries of workers, salespeople and managers
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
     * @return sum of amountOfSales * commissionPercentage for each salesperson
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;
        for (Employee e : employees) {
            if (e instanceof SalesPeople) {
                SalesPeople sp = (SalesPeople) e;
                totalCommission += sp.getTotalCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     *
     * @return sum of holiday premiums for all shift workers; 0 if none exist
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                ShiftWorker sw = (ShiftWorker) e;
                totalPremiums += sw.calculateHolidayPremium();
            }
        }
        return totalPremiums;
    }

    /**
     * Finds the average weekly working hours for all workers in the DELIVERY department.
     *
     * @return average weekly working hours; 0 if there are no workers in the delivery department
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