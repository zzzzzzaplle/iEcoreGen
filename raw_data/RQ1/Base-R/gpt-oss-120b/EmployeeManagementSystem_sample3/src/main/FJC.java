import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for all employees.
 */
 class Employee {

    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;
    private Department department;

    /** Unparameterized constructor */
    public Employee() {
    }

    // -------------------- Getters & Setters --------------------
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
 * Abstract class for workers (shift or off‑shift).
 */
public abstract class Worker extends Employee {

    private double weeklyWorkingHours;
    private double hourlyRate;

    /** Unparameterized constructor */
    public Worker() {
    }

    // -------------------- Getters & Setters --------------------
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
 * Shift workers can only be assigned to the Delivery department.
 * They receive an additional holiday premium.
 */
 class ShiftWorker extends Worker {

    private double holidayPremium; // total premium paid per week for holidays

    /** Unparameterized constructor */
    public ShiftWorker() {
    }

    // -------------------- Getters & Setters --------------------
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }
}

/**
 * Off‑shift workers have permits for weekends and official holidays.
 */
 class OffShiftWorker extends Worker {

    private int weekendPermits;
    private int officialHolidayPermits;

    /** Unparameterized constructor */
    public OffShiftWorker() {
    }

    // -------------------- Getters & Setters --------------------
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
}

/**
 * Sales persons have a fixed salary, sales amount and a commission percentage.
 */
 class SalesPerson extends Employee {

    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage; // e.g., 0.05 for 5 %

    /** Unparameterized constructor */
    public SalesPerson() {
    }

    // -------------------- Getters & Setters --------------------
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
 * Managers receive a fixed salary and have a position title.
 * They also keep a list of direct subordinate employees.
 */
 class Manager extends Employee {

    private double fixedSalary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    /** Unparameterized constructor */
    public Manager() {
    }

    // -------------------- Getters & Setters --------------------
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
     * @param employee the employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    /**
     * Returns the number of direct subordinates of this manager.
     *
     * @return the size of the subordinate list
     */
    public int getDirectSubordinateCount() {
        return subordinates.size();
    }
}

/**
 * Department of the company. Each department has a name and a manager.
 */
 class Department {

    private String name; // "Production", "Control", "Delivery"
    private Manager manager;

    /** Unparameterized constructor */
    public Department() {
    }

    // -------------------- Getters & Setters --------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
}

/**
 * Company class that aggregates all employees and provides the required
 * business calculations.
 */
 class Company {

    private List<Employee> employees = new ArrayList<>();

    /** Unparameterized constructor */
    public Company() {
    }

    // -------------------- Getters & Setters --------------------
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     *
     * <p>Workers' salary = weeklyWorkingHours * hourlyRate + holiday premium (if shift worker).
     * Sales person's salary = fixed salary + amountOfSales * commissionPercentage.
     * Manager's salary = fixed salary.</p>
     *
     * @return the sum of salaries for all employees
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
                SalesPerson sp = (SalesPerson) e;
                total += sp.getFixedSalary() + sp.getAmountOfSales() * sp.getCommissionPercentage();
            } else if (e instanceof Manager) {
                total += ((Manager) e).getFixedSalary();
            }
        }
        return total;
    }

    /**
     * Computes the average weekly working hours of all workers that belong to the
     * Delivery department.
     *
     * @return average working hours, or {@code 0} if there are no such workers
     */
    public double averageWorkingHoursInDelivery() {
        double sumHours = 0.0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker && e.getDepartment() != null
                    && "Delivery".equalsIgnoreCase(e.getDepartment().getName())) {
                sumHours += ((Worker) e).getWeeklyWorkingHours();
                count++;
            }
        }
        return (count == 0) ? 0.0 : sumHours / count;
    }

    /**
     * Determines the total commission amount earned by all salespeople.
     *
     * @return sum of (amountOfSales * commissionPercentage) for every sales person
     */
    public double totalCommission() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof SalesPerson) {
                SalesPerson sp = (SalesPerson) e;
                total += sp.getAmountOfSales() * sp.getCommissionPercentage();
            }
        }
        return total;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers.
     *
     * @return sum of holiday premiums, or {@code 0} if there are no shift workers
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
     * Returns a map that associates each manager with the number of his/her direct
     * subordinate employees.
     *
     * @return a {@code Map} where the key is a {@code Manager} and the value is the count of direct subordinates
     */
    public Map<Manager, Integer> getDirectSubordinateCounts() {
        Map<Manager, Integer> result = new HashMap<>();
        for (Employee e : employees) {
            if (e instanceof Manager) {
                Manager m = (Manager) e;
                result.put(m, m.getDirectSubordinateCount());
            }
        }
        return result;
    }

    /**
     * Convenience method that returns the number of direct subordinates for a
     * specific manager.
     *
     * @param manager the manager whose subordinates are to be counted
     * @return number of direct subordinates, or {@code 0} if the manager is not part of the company
     */
    public int getDirectSubordinateCount(Manager manager) {
        if (employees.contains(manager)) {
            return manager.getDirectSubordinateCount();
        }
        return 0;
    }
}