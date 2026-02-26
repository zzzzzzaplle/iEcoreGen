import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Enum representing the possible department types.
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
    }

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
 * Abstract class for workers (both shift and off‑shift).
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    public Worker() {
    }

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
 * Shift worker – works only in the DELIVERY department and receives a holiday premium.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Returns the holiday premium paid to this shift worker.
     *
     * @return holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Off‑shift worker – does not receive holiday premiums.
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
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
    }

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
     * Calculates the total commission earned by this sales person.
     *
     * @return total commission = amountOfSales * commissionPercentage
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Manager employee.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    public Manager() {
    }

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
        return subordinates.size();
    }
}

/**
 * Department class containing employees and a manager.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

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
     * Calculates the average weekly working hours of all workers in this department.
     * If the department is not DELIVERY or contains no workers, returns 0.
     *
     * @return average weekly working hours for workers in the department, or 0 if none
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0.0;
        }
        int totalHours = 0;
        int workerCount = 0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                Worker w = (Worker) e;
                totalHours += w.getWeeklyWorkingHour();
                workerCount++;
            }
        }
        return workerCount == 0 ? 0.0 : ((double) totalHours) / workerCount;
    }
}

/**
 * Company class aggregating employees and departments.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    public Company() {
    }

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
     * <ul>
     *   <li>Workers: weeklyWorkingHour * hourlyRates (+ holiday premium for shift workers)</li>
     *   <li>SalesPeople: salary + amountOfSales * commissionPercentage</li>
     *   <li>Managers: salary</li>
     * </ul>
     *
     * @return total salary amount for all employees
     */
    public double calculateTotalEmployeeSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                ShiftWorker sw = (ShiftWorker) e;
                total += sw.getWeeklyWorkingHour() * sw.getHourlyRates()
                        + sw.calculateHolidayPremium();
            } else if (e instanceof Worker) {
                Worker w = (Worker) e;
                total += w.getWeeklyWorkingHour() * w.getHourlyRates();
            } else if (e instanceof SalesPeople) {
                SalesPeople sp = (SalesPeople) e;
                total += sp.getSalary() + sp.getAmountOfSales() * sp.getCommissionPercentage();
            } else if (e instanceof Manager) {
                Manager m = (Manager) e;
                total += m.getSalary();
            }
        }
        return total;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     *
     * @return sum of commissions for all salespeople
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

    /**
     * Finds the average working hours per week for all workers in the DELIVERY department.
     * Returns 0 if there are no workers in that department.
     *
     * @return average weekly working hours for delivery workers, or 0 if none
     */
    public double calculateAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0.0;
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
        return workerCount == 0 ? 0.0 : totalHours / workerCount;
    }
}