import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of company departments.
 */
enum Department {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Base class representing a generic employee.
 */
 class Employee {
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;
    private Department department;

    /** No‑argument constructor */
    public Employee() {
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

/**
 * Abstract class for workers (both shift and non‑shift).
 */
public abstract class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;

    /** No‑argument constructor */
    public Worker() {
        super();
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
}

/**
 * Shift workers work only in the DELIVERY department and receive a holiday premium.
 */
 class ShiftWorker extends Worker {
    private double holidayPremium; // premium paid for working on holidays (per week)

    /** No‑argument constructor */
    public ShiftWorker() {
        super();
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
}

/**
 * Non‑shift workers (off‑shift) may hold permits for weekends and official holidays.
 */
 class OffShiftWorker extends Worker {
    private boolean weekendPermit;
    private boolean holidayPermit;

    /** No‑argument constructor */
    public OffShiftWorker() {
        super();
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
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
}

/**
 * Sales people have a fixed salary plus commission based on sales.
 */
 class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage; // expressed as a decimal (e.g., 0.05 for 5%)

    /** No‑argument constructor */
    public SalesPerson() {
        super();
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
    public double getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(double fixedSalary) {
        this.fixedSalary = fixedSalary;
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
}

/**
 * Managers receive a fixed salary and can have direct subordinates.
 */
 class Manager extends Employee {
    private double fixedSalary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    /** No‑argument constructor */
    public Manager() {
        super();
    }

    // -------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------
    public double getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(double fixedSalary) {
        this.fixedSalary = fixedSalary;
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
     * @return number of direct subordinates
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates == null ? 0 : subordinates.size();
    }
}

/**
 * The Company class aggregates all employees and provides business‑level calculations.
 */
 class Company {
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor */
    public Company() {
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

    /**
     * Adds an employee to the company's employee list.
     *
     * @param employee the employee to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code employee} is {@code null}
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }
        employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * <p>
     * Total salary = sum(workers' salary + sales people's salary + managers' salary).
     * <ul>
     *   <li>Workers' salary = weeklyWorkingHours * hourlyRate + holidayPremium (for shift workers).</li>
     *   <li>Sales people's salary = fixedSalary + amountOfSales * commissionPercentage.</li>
     *   <li>Managers' salary = fixedSalary.</li>
     * </ul>
     *
     * @return the total salary paid to all employees
     */
    public double calculateTotalSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                Worker w = (Worker) e;
                double salary = w.getWeeklyWorkingHours() * w.getHourlyRate();
                if (w instanceof ShiftWorker) {
                    salary += ((ShiftWorker) w).getHolidayPremium();
                }
                total += salary;
            } else if (e instanceof SalesPerson) {
                SalesPerson s = (SalesPerson) e;
                total += s.getFixedSalary() + s.getAmountOfSales() * s.getCommissionPercentage();
            } else if (e instanceof Manager) {
                total += ((Manager) e).getFixedSalary();
            }
        }
        return total;
    }

    /**
     * Computes the average weekly working hours of all workers that belong to the DELIVERY department.
     *
     * @return average weekly working hours, or {@code 0} if there are no such workers
     */
    public double averageWorkingHoursInDelivery() {
        double sumHours = 0.0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker && e.getDepartment() == Department.DELIVERY) {
                sumHours += ((Worker) e).getWeeklyWorkingHours();
                count++;
            }
        }
        return count == 0 ? 0.0 : sumHours / count;
    }

    /**
     * Determines the total commission amount earned by all salespeople in the company.
     *
     * @return sum of (amountOfSales * commissionPercentage) for all salespeople
     */
    public double totalCommissionAmount() {
        double totalCommission = 0.0;
        for (Employee e : employees) {
            if (e instanceof SalesPerson) {
                SalesPerson s = (SalesPerson) e;
                totalCommission += s.getAmountOfSales() * s.getCommissionPercentage();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     *
     * @return total holiday premiums; {@code 0} if there are no shift workers
     */
    public double totalHolidayPremiums() {
        double totalPremium = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                totalPremium += ((ShiftWorker) e).getHolidayPremium();
            }
        }
        return totalPremium;
    }
}