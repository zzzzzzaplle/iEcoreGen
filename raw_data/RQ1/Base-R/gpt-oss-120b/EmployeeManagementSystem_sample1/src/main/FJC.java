import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration of possible company departments.
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
    private Department department;
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;

    /** Default (no‑arg) constructor. */
    public Employee() {
    }

    // ----- getters & setters -------------------------------------------------
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

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Calculates the monthly salary for this employee.
     * Sub‑classes override this method with the appropriate formula.
     *
     * @return the calculated salary (default 0.0)
     */
    public double calculateSalary() {
        return 0.0;
    }
}

/**
 * Abstract class representing a generic worker.
 */
abstract class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;

    /** Default (no‑arg) constructor. */
    public Worker() {
    }

    // ----- getters & setters -------------------------------------------------
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
     * Calculates the salary for a generic worker (without any premiums).
     *
     * @return weeklyWorkingHours * hourlyRate
     */
    @Override
    public double calculateSalary() {
        return getWeeklyWorkingHours() * getHourlyRate();
    }
}

/**
 * Shift workers work only in the DELIVERY department and receive a holiday premium.
 */
class ShiftWorker extends Worker {
    private double holidayPremium; // total premium amount for the period

    /** Default (no‑arg) constructor. */
    public ShiftWorker() {
    }

    // ----- getters & setters -------------------------------------------------
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Salary for a shift worker = base salary + holiday premium.
     *
     * @return base salary (weeklyHours * hourlyRate) + holidayPremium
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + getHolidayPremium();
    }
}

/**
 * Off‑shift workers may have permits for weekends and official holidays.
 */
class OffShiftWorker extends Worker {
    private int weekendPermits;
    private int officialHolidayPermits;

    /** Default (no‑arg) constructor. */
    public OffShiftWorker() {
    }

    // ----- getters & setters -------------------------------------------------
    public int getWeekendPermits() {
        return weekendPermits;
    }

    public void setWeekendPermits(int weekendPermits) {
        this.weekendPermits = weekendPermits;
    }

    public int getOfficialHolidayPermits() {
        return officialHolidayPermits;
    }

    public void setOfficialHolidayPermits(int officialHolidayPermits) {
        this.officialHolidayPermits = officialHolidayPermits;
    }

    // Salary calculation uses the default implementation from Worker.
}

/**
 * SalesPerson employees receive a fixed salary plus commission.
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage; // expressed as a decimal (e.g., 0.05 for 5%)

    /** Default (no‑arg) constructor. */
    public SalesPerson() {
    }

    // ----- getters & setters -------------------------------------------------
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

    /**
     * Salary for a salesperson = fixed salary + commission.
     *
     * @return fixedSalary + (amountOfSales * commissionPercentage)
     */
    @Override
    public double calculateSalary() {
        return getFixedSalary() + (getAmountOfSales() * getCommissionPercentage());
    }

    /**
     * Calculates only the commission part of the salary.
     *
     * @return amountOfSales * commissionPercentage
     */
    public double calculateCommission() {
        return getAmountOfSales() * getCommissionPercentage();
    }
}

/**
 * Manager employees have a fixed salary, a position title, and a list of direct subordinates.
 */
class Manager extends Employee {
    private double fixedSalary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    /** Default (no‑arg) constructor. */
    public Manager() {
    }

    // ----- getters & setters -------------------------------------------------
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
     * Adds a direct subordinate to this manager.
     *
     * @param e the employee to be added as a subordinate
     */
    public void addSubordinate(Employee e) {
        subordinates.add(e);
    }

    /**
     * Salary for a manager = fixed salary.
     *
     * @return fixedSalary
     */
    @Override
    public double calculateSalary() {
        return getFixedSalary();
    }

    /**
     * Returns the number of direct subordinate employees.
     *
     * @return size of the subordinates list
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates.size();
    }
}

/**
 * Company class aggregates all employees and provides the required business calculations.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();

    /** Default (no‑arg) constructor. */
    public Company() {
    }

    // ----- getters & setters -------------------------------------------------
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the company.
     *
     * @param e employee to be added
     */
    public void addEmployee(Employee e) {
        employees.add(e);
    }

    /**
     * Calculates the total salary of all employees in the company.
     *
     * @return sum of salaries for workers, salespeople and managers
     */
    public double calculateTotalSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            total += e.calculateSalary();
        }
        return total;
    }

    /**
     * Computes the average weekly working hours for all workers (shift or off‑shift)
     * that belong to the DELIVERY department.
     *
     * @return average working hours, or 0 if no such workers exist
     */
    public double averageWorkingHoursForDeliveryWorkers() {
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
     * @return sum of (amountOfSales * commissionPercentage) for every salesperson
     */
    public double totalCommissionAmount() {
        double totalCommission = 0.0;
        for (Employee e : employees) {
            if (e instanceof SalesPerson) {
                totalCommission += ((SalesPerson) e).calculateCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers.
     *
     * @return sum of holidayPremium for each shift worker, or 0 if none exist
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

    /**
     * Retrieves a map that links each manager to the number of their direct subordinates.
     *
     * @return map where key = manager instance, value = count of direct subordinates
     */
    public Map<Manager, Integer> getSubordinateCounts() {
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