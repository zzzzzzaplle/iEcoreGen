import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base abstract class for all employees.
 */
abstract class Employee {
    private DepartmentType department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    public Employee() {
    }

    // Getters and Setters
    public DepartmentType getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentType department) {
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
 * Manager class – a type of employee that receives a fixed salary and may have subordinates.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    public Manager() {
    }

    // Getters and Setters
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
     * @return the count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }

    /**
     * Adds a subordinate to this manager.
     *
     * @param e the employee to add as a subordinate
     */
    public void addSubordinate(Employee e) {
        if (e != null) {
            subordinates.add(e);
        }
    }
}

/**
 * SalesPeople class – a type of employee that receives a fixed salary plus commission.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {
    }

    // Getters and Setters
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
     * Calculates the commission amount for this salesperson.
     *
     * @return commission = amountOfSales * commissionPercentage
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Abstract worker class – common attributes for all workers.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    public Worker() {
    }

    // Getters and Setters
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
     * Calculates the basic salary (without any premiums) for a worker.
     *
     * @return weeklyWorkingHour * hourlyRates
     */
    public double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

/**
 * Shift worker – a worker that receives a holiday premium.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
    }

    // Getters and Setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Returns the holiday premium for this shift worker.
     *
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Off‑shift worker – a normal worker without holiday premium.
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
    }

    // No additional fields
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
 * Department class – groups employees and is managed by a manager.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    public Department() {
    }

    // Getters and Setters
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
     * Calculates the average weekly working hours of all workers belonging to this department.
     *
     * @return average weekly hours, or 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        int totalHours = 0;
        int count = 0;
        for (Employee e : employees) {
            if (e instanceof Worker) {
                totalHours += ((Worker) e).getWeeklyWorkingHour();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalHours / count;
    }
}

/**
 * Company class – contains all employees and departments and provides aggregate calculations.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    public Company() {
    }

    // Getters and Setters
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
     *
     * @return sum of salaries of workers, salespeople and managers
     */
    public double calculateTotalEmployeeSalary() {
        double total = 0.0;
        for (Employee e : employees) {
            if (e instanceof ShiftWorker) {
                ShiftWorker sw = (ShiftWorker) e;
                total += sw.calculateBaseSalary() + sw.calculateHolidayPremium();
            } else if (e instanceof OffShiftWorker) {
                OffShiftWorker ow = (OffShiftWorker) e;
                total += ow.calculateBaseSalary();
            } else if (e instanceof SalesPeople) {
                SalesPeople sp = (SalesPeople) e;
                total += sp.getSalary() + sp.getTotalCommission();
            } else if (e instanceof Manager) {
                total += ((Manager) e).getSalary();
            }
        }
        return total;
    }

    /**
     * Finds the average weekly working hours for all workers that belong to the DELIVERY department.
     *
     * @return average hours, or 0 if there are no such workers
     */
    public double calculateAverageWorkingHoursInDelivery() {
        int totalHours = 0;
        int count = 0;
        for (Employee e : employees) {
            if (e.getDepartment() == DepartmentType.DELIVERY && e instanceof Worker) {
                totalHours += ((Worker) e).getWeeklyWorkingHour();
                count++;
            }
        }
        return count == 0 ? 0.0 : (double) totalHours / count;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     *
     * @return sum of (amountOfSales * commissionPercentage) for every SalesPeople instance
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
     * @return sum of holiday premiums, or 0 if there are no shift workers
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
}

/**
 * Helper methods added to Employee to store its department type.
 */
abstract class EmployeeHelper extends Employee {
    // This subclass exists only to add a department field of enum type.
}

/**
 * Extending the original Employee to hold a DepartmentType instead of a raw String.
 */
abstract class EmployeeWithDept extends Employee {
    // The original description used a String, but the design model uses an enum.
}

/**
 * Utility class to bridge the department field in Employee.
 */
class DepartmentUtil {
    /**
     * Sets the department type for an employee.
     *
     * @param e   the employee whose department is to be set
     * @param dt  the department type
     */
    public static void setDepartment(Employee e, DepartmentType dt) {
        // Since the original Employee class stores a String, we store the enum's name.
        // This method is a placeholder; in a real system, the field would be of type DepartmentType.
        // For the purpose of this generated code, we cast and set via reflection.
        try {
            java.lang.reflect.Field f = Employee.class.getDeclaredField("department");
            f.setAccessible(true);
            f.set(e, dt.name());
        } catch (Exception ex) {
            throw new RuntimeException("Unable to set department", ex);
        }
    }

    /**
     * Retrieves the department type of an employee.
     *
     * @param e the employee
     * @return the department type, or null if it cannot be determined
     */
    public static DepartmentType getDepartment(Employee e) {
        try {
            java.lang.reflect.Field f = Employee.class.getDeclaredField("department");
            f.setAccessible(true);
            String name = (String) f.get(e);
            return DepartmentType.valueOf(name);
        } catch (Exception ex) {
            throw new RuntimeException("Unable to get department", ex);
        }
    }
}