import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of the possible departments in the company.
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

    public Employee() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

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
    private List<Employee> subordinates = new ArrayList<>();

    public Manager() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

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
     * Returns the number of direct subordinate employees for this manager.
     *
     * @return count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates == null ? 0 : subordinates.size();
    }
}

/**
 * Sales person employee.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

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
     * @return commission amount = amountOfSales * commissionPercentage
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

    public Worker() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

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
 * Shift worker – works in the delivery department and receives a holiday premium.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Returns the holiday premium for this shift worker.
     *
     * @return holiday premium value
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Off‑shift worker – works regular shifts (no holiday premium).
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
        // no‑arg constructor
    }
}

/**
 * Department class. Holds a collection of employees and knows its type.
 */
class Department {
    private DepartmentType type;
    private List<Employee> employees = new ArrayList<>();

    public Department() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Calculates the average weekly working hours of all workers in this department.
     * If there are no workers, the method returns 0.
     *
     * @return average weekly working hours, or 0 if none
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

        return workerCount == 0 ? 0.0 : (double) totalHours / workerCount;
    }
}

/**
 * Company class – aggregates employees and departments and provides
 * the required business calculations.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    public Company() {
        // no‑arg constructor
    }

    /* ---------- Getters & Setters ---------- */

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
     * <p>
     * Workers' salary = weeklyWorkingHour * hourlyRates + holiday premium (if shift worker).<br>
     * Sales people salary = base salary + amountOfSales * commissionPercentage.<br>
     * Managers salary = base salary.
     *
     * @return total salary of all employees
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
     * Returns the average weekly working hours for all workers belonging to the
     * delivery department. If there are no such workers, returns 0.
     *
     * @return average working hours in delivery department, or 0 if none
     */
    public double calculateAverageWorkerWorkingHoursInDelivery() {
        Department deliveryDept = null;
        for (Department d : departments) {
            if (d.getType() == DepartmentType.DELIVERY) {
                deliveryDept = d;
                break;
            }
        }
        if (deliveryDept == null) {
            return 0.0;
        }
        return deliveryDept.calculateAverageWorkerWorkingHours();
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     *
     * @return total commission summed across all salespeople
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
     * @return sum of holiday premiums for all shift workers, or 0 if none
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