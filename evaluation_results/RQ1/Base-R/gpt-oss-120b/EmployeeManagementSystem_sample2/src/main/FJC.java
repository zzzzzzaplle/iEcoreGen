import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a department of the company.
 */
 class Department {

    private String name;
    private Manager manager; // each department is controlled by a manager

    /** Unparameterized constructor */
    public Department() {
    }

    /** @return the name of the department */
    public String getName() {
        return name;
    }

    /** @param name the department name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the manager that controls the department */
    public Manager getManager() {
        return manager;
    }

    /** @param manager the manager to set for this department */
    public void setManager(Manager manager) {
        this.manager = manager;
    }
}

/**
 * Base class for all employees.
 */
public abstract class Employee {

    private Department department;
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;

    /** Unparameterized constructor */
    public Employee() {
    }

    /** @return the department where the employee works */
    public Department getDepartment() {
        return department;
    }

    /** @param department the department to set */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /** @return the employee's full name */
    public String getName() {
        return name;
    }

    /** @param name the employee's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the employee's date of birth */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /** @param dateOfBirth the date of birth to set */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /** @return the social insurance number */
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    /** @param socialInsuranceNumber the social insurance number to set */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Calculates the monthly salary of the employee.
     *
     * @return the salary amount
     */
    public abstract double calculateSalary();
}

/**
 * Abstract class for all workers (shift and off‑shift).
 */
public abstract class Worker extends Employee {

    private double weeklyWorkingHours;
    private double hourlyRate;

    /** Unparameterized constructor */
    public Worker() {
    }

    /** @return weekly working hours */
    public double getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    /** @param weeklyWorkingHours weekly hours to set */
    public void setWeeklyWorkingHours(double weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    /** @return hourly rate */
    public double getHourlyRate() {
        return hourlyRate;
    }

    /** @param hourlyRate hourly rate to set */
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    /**
     * Base salary for a worker (without any premium).
     *
     * @return salary = weeklyWorkingHours * hourlyRate
     */
    protected double baseSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
}

/**
 * Shift workers are allowed only in the delivery department and receive a holiday premium.
 */
 class ShiftWorker extends Worker {

    private double holidayPremium; // extra amount paid for holiday work

    /** Unparameterized constructor */
    public ShiftWorker() {
    }

    /** @return the holiday premium amount */
    public double getHolidayPremium() {
        return holidayPremium;
    }

    /** @param holidayPremium the holiday premium to set */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the salary for a shift worker.
     *
     * @return base salary plus holiday premium
     */
    @Override
    public double calculateSalary() {
        return baseSalary() + holidayPremium;
    }
}

/**
 * Off‑shift workers have weekend and official holiday permits.
 */
 class OffShiftWorker extends Worker {

    private int weekendPermits;
    private int officialHolidayPermits;

    /** Unparameterized constructor */
    public OffShiftWorker() {
    }

    /** @return number of weekend permits */
    public int getWeekendPermits() {
        return weekendPermits;
    }

    /** @param weekendPermits number of weekend permits to set */
    public void setWeekendPermits(int weekendPermits) {
        this.weekendPermits = weekendPermits;
    }

    /** @return number of official holiday permits */
    public int getOfficialHolidayPermits() {
        return officialHolidayPermits;
    }

    /** @param officialHolidayPermits number of official holiday permits to set */
    public void setOfficialHolidayPermits(int officialHolidayPermits) {
        this.officialHolidayPermits = officialHolidayPermits;
    }

    /**
     * Calculates the salary for an off‑shift worker.
     *
     * @return base salary (no holiday premium)
     */
    @Override
    public double calculateSalary() {
        return baseSalary();
    }
}

/**
 * Sales person employee.
 */
 class SalesPerson extends Employee {

    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage; // expressed as a fraction, e.g., 0.05 for 5%

    /** Unparameterized constructor */
    public SalesPerson() {
    }

    /** @return the fixed salary */
    public double getFixedSalary() {
        return fixedSalary;
    }

    /** @param fixedSalary the fixed salary to set */
    public void setFixedSalary(double fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    /** @return the total amount of sales made */
    public double getAmountOfSales() {
        return amountOfSales;
    }

    /** @param amountOfSales the amount of sales to set */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    /** @return commission percentage (e.g., 0.07 for 7%) */
    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    /** @param commissionPercentage commission percentage to set */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the salary for a sales person.
     *
     * @return fixed salary + (amountOfSales * commissionPercentage)
     */
    @Override
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }
}

/**
 * Manager employee.
 */
 class Manager extends Employee {

    private double fixedSalary;
    private String position; // e.g., "Production Manager"
    private List<Employee> subordinates = new ArrayList<>();

    /** Unparameterized constructor */
    public Manager() {
    }

    /** @return the fixed salary */
    public double getFixedSalary() {
        return fixedSalary;
    }

    /** @param fixedSalary the fixed salary to set */
    public void setFixedSalary(double fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    /** @return the manager's position */
    public String getPosition() {
        return position;
    }

    /** @param position the manager's position to set */
    public void setPosition(String position) {
        this.position = position;
    }

    /** @return the list of direct subordinates */
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    /** @param subordinates the subordinates list to set */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
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

    /**
     * Returns the number of direct subordinates.
     *
     * @return size of the subordinates list
     */
    public int getDirectSubordinateCount() {
        return subordinates.size();
    }

    /**
     * Calculates the salary for a manager.
     *
     * @return fixed salary (no commission or premium)
     */
    @Override
    public double calculateSalary() {
        return fixedSalary;
    }
}

/**
 * Company class that aggregates all employees and provides the required calculations.
 */
 class Company {

    private List<Employee> employees = new ArrayList<>();

    /** Unparameterized constructor */
    public Company() {
    }

    /** @return the list of all employees */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the employee list to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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
     * Calculates the total salary of all employees in the company.
     *
     * @return sum of salaries of workers, sales persons, and managers
     */
    public double calculateTotalSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            total += e.calculateSalary();
        }
        return total;
    }

    /**
     * Computes the average weekly working hours for all workers that belong to the
     * delivery department. Returns 0 if there are no such workers.
     *
     * @return average weekly working hours (0 if none)
     */
    public double averageWorkingHoursInDelivery() {
        double sum = 0.0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                Department d = e.getDepartment();
                if (d != null && "delivery".equalsIgnoreCase(d.getName())) {
                    sum += ((Worker) e).getWeeklyWorkingHours();
                    count++;
                }
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    /**
     * Determines the total commission amount for all sales people in the company.
     *
     * @return sum of (amountOfSales * commissionPercentage) for every SalesPerson
     */
    public double totalCommissionAmount() {
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
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * Returns 0 if there are no shift workers.
     *
     * @return sum of holiday premiums for all ShiftWorker instances
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
     * Returns a map that contains each manager as a key and the number of their direct
     * subordinate employees as the value.
     *
     * @return map of manager → direct subordinate count
     */
    public Map<Manager, Integer> getDirectSubordinateCountForManagers() {
        Map<Manager, Integer> result = new HashMap<>();
        for (Employee e : employees) {
            if (e instanceof Manager) {
                Manager m = (Manager) e;
                result.put(m, m.getDirectSubordinateCount());
            }
        }
        return result;
    }
}