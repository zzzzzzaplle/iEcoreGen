import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumerates the possible departments of the company.
 */
enum Department {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Base class for every employee in the company.
 */
class Employee {
    /** Department where the employee works. */
    private Department department;
    /** Full name of the employee. */
    private String name;
    /** Date of birth. */
    private LocalDate dateOfBirth;
    /** Social Insurance Number (SIN). */
    private String sin;

    /** No‑argument constructor required by the specification. */
    public Employee() {
    }

    /* ---------- Getters & Setters ---------- */

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

    public String getSin() {
        return sin;
    }

    public void setSin(String sin) {
        this.sin = sin;
    }

    /**
     * Returns the monthly salary of the employee.
     * Sub‑classes must override this method.
     *
     * @return salary amount (default 0.0)
     */
    public double getSalary() {
        return 0.0;
    }
}

/**
 * Abstract class that groups common data for all workers.
 */
abstract class Worker extends Employee {
    /** Weekly working hours. */
    private double weeklyWorkingHours;
    /** Hourly rate. */
    private double hourlyRate;

    public Worker() {
    }

    /* ---------- Getters & Setters ---------- */

    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Base salary for a worker (without any premiums).
     *
     * @return weeklyWorkingHours * hourlyRate
     */
    protected double baseSalary() {
        return getWeeklyWorkingHours() * getHourlyRate();
    }
}

/**
 * Represents a non‑shift worker (may have weekend/holiday permits).
 */
class NonShiftWorker extends Worker {
    /** Whether the worker has a weekend permit. */
    private boolean weekendPermit;
    /** Whether the worker has an official holiday permit. */
    private boolean holidayPermit;

    public NonShiftWorker() {
    }

    /* ---------- Getters & Setters ---------- */

    public boolean isWeekendPermit() {
        return weekendPermit;
    }

    public void setWeekendPermit(boolean weekendPermit) {
        this.weekendPermit = weekendPermit;
    }

    public boolean isHolidayPermit() {
        return holidayPermit;
    }

    public void setHolidayPermit(boolean holidayPermit) {
        this.holidayPermit = holidayPermit;
    }

    @Override
    public double getSalary() {
        // Non‑shift workers receive only the base salary.
        return baseSalary();
    }
}

/**
 * Represents a shift worker who receives a holiday premium.
 * Shift workers are allowed only in the DELIVERY department.
 */
class ShiftWorker extends Worker {
    /** Premium paid for working on holidays (per week). */
    private double holidayPremium;

    public ShiftWorker() {
    }

    /* ---------- Getters & Setters ---------- */

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    @Override
    public void setDepartment(Department department) {
        // Enforce rule: shift workers can only be in DELIVERY.
        if (department != Department.DELIVERY) {
            throw new IllegalArgumentException(
                "Shift workers may only be assigned to the DELIVERY department.");
        }
        super.setDepartment(department);
    }

    @Override
    public double getSalary() {
        // Salary = base + holiday premium.
        return baseSalary() + getHolidayPremium();
    }
}

/**
 * Represents a sales person who receives a fixed salary plus commissions.
 */
class SalesPerson extends Employee {
    /** Fixed monthly salary. */
    private double salary;
    /** Total amount of sales made. */
    private double amountOfSales;
    /** Commission percentage (e.g., 0.05 for 5%). */
    private double commissionPercentage;

    public SalesPerson() {
    }

    /* ---------- Getters & Setters ---------- */

    public double getSalaryBase() {
        return salary;
    }

    public void setSalaryBase(double salary) {
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
     * Returns the total monthly compensation for the sales person.
     *
     * @return fixed salary + (amountOfSales * commissionPercentage)
     */
    @Override
    public double getSalary() {
        return getSalaryBase() + (getAmountOfSales() * getCommissionPercentage());
    }

    /**
     * Calculates the commission amount earned by this sales person.
     *
     * @return amountOfSales * commissionPercentage
     */
    public double commissionEarned() {
        return getAmountOfSales() * getCommissionPercentage();
    }
}

/**
 * Represents a manager who receives a fixed salary and may have direct subordinates.
 */
class Manager extends Employee {
    /** Fixed monthly salary. */
    private double salary;
    /** Position title (e.g., "Production Manager"). */
    private String position;
    /** Direct subordinate employees. */
    private List<Employee> subordinates = new ArrayList<>();

    public Manager() {
    }

    /* ---------- Getters & Setters ---------- */

    public double getSalaryBase() {
        return salary;
    }

    public void setSalaryBase(double salary) {
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

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = new ArrayList<>(subordinates);
    }

    /**
     * Adds a direct subordinate to this manager.
     *
     * @param e employee to be added as a subordinate
     */
    public void addSubordinate(Employee e) {
        if (e != null) {
            subordinates.add(e);
        }
    }

    @Override
    public double getSalary() {
        return getSalaryBase();
    }

    /**
     * Returns the number of direct subordinates.
     *
     * @return size of the subordinate list
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates.size();
    }
}

/**
 * Central class that stores all employees and provides the required
 * analytical operations.
 */
class Company {
    /** All employees of the company. */
    private List<Employee> employees = new ArrayList<>();

    public Company() {
    }

    /* ---------- Getters & Setters ---------- */

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }

    /**
     * Adds an employee to the company.
     *
     * @param e employee to be added
     */
    public void addEmployee(Employee e) {
        if (e != null) {
            employees.add(e);
        }
    }

    /**
     * Calculates the total salary of all employees in the company.
     * Total salary = sum(workers' salary + sales people's salary + managers' salary).
     *
     * @return total salary amount
     */
    public double calculateTotalSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            total += e.getSalary();
        }
        return total;
    }

    /**
     * Computes the average weekly working hours for all workers that belong
     * to the DELIVERY department. Returns 0 if there are no such workers.
     *
     * @return average weekly working hours (0 if none)
     */
    public double averageWorkingHoursInDelivery() {
        double sum = 0.0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker && e.getDepartment() == Department.DELIVERY) {
                sum += ((Worker) e).getWeeklyWorkingHours();
                count++;
            }
        }
        return (count == 0) ? 0.0 : sum / count;
    }

    /**
     * Determines the total commission amount earned by all salespeople.
     * Sum(amountOfSales * commissionPercentage) for all salespeople.
     *
     * @return total commission amount
     */
    public double totalCommissionAmount() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof SalesPerson) {
                total += ((SalesPerson) e).commissionEarned();
            }
        }
        return total;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers.
     * Returns 0 if there are no shift workers.
     *
     * @return total holiday premiums
     */
    public double totalHolidayPremiums() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                total += ((ShiftWorker) e).getHolidayPremium();
            }
        }
        return total;
    }

    /**
     * Produces a mapping from each manager to the number of their direct
     * subordinate employees.
     *
     * @return map where key = manager, value = number of direct subordinates
     */
    public Map<Manager, Integer> getNumberOfDirectSubordinatesPerManager() {
        Map<Manager, Integer> result = new HashMap<>();
        for (Employee e : employees) {
            if (e instanceof Manager) {
                Manager m = (Manager) e;
                result.put(m, m.getNumberOfDirectSubordinates());
            }
        }
        return result;
    }
}