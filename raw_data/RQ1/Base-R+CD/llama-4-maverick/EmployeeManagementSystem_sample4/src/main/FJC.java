import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Enum representing different department types in the company.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    /**
     * Unparameterized constructor for Employee.
     */
    public Employee() {}

    /**
     * Gets the department of the employee.
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Sets the department of the employee.
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Gets the name of the employee.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the birth date of the employee.
     * @return the birthDate
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birth date of the employee.
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the social insurance number of the employee.
     * @return the socialInsuranceNumber
     */
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    /**
     * Sets the social insurance number of the employee.
     * @param socialInsuranceNumber the socialInsuranceNumber to set
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates = new ArrayList<>();

    /**
     * Unparameterized constructor for Manager.
     */
    public Manager() {}

    /**
     * Gets the salary of the manager.
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the manager.
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the position of the manager.
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position of the manager.
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Gets the subordinates of the manager.
     * @return the subordinates
     */
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    /**
     * Sets the subordinates of the manager.
     * @param subordinates the subordinates to set
     */
    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @return the number of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

/**
 * Represents a sales person in the company.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Unparameterized constructor for SalesPeople.
     */
    public SalesPeople() {}

    /**
     * Gets the salary of the sales person.
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the sales person.
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the amount of sales made by the sales person.
     * @return the amountOfSales
     */
    public double getAmountOfSales() {
        return amountOfSales;
    }

    /**
     * Sets the amount of sales made by the sales person.
     * @param amountOfSales the amountOfSales to set
     */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    /**
     * Gets the commission percentage of the sales person.
     * @return the commissionPercentage
     */
    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    /**
     * Sets the commission percentage of the sales person.
     * @param commissionPercentage the commissionPercentage to set
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the total commission for the sales person.
     * @return the total commission
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a worker in the company.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    /**
     * Unparameterized constructor for Worker.
     */
    public Worker() {}

    /**
     * Gets the weekly working hours of the worker.
     * @return the weeklyWorkingHour
     */
    public int getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    /**
     * Sets the weekly working hours of the worker.
     * @param weeklyWorkingHour the weeklyWorkingHour to set
     */
    public void setWeeklyWorkingHour(int weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    /**
     * Gets the hourly rates of the worker.
     * @return the hourlyRates
     */
    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rates of the worker.
     * @param hourlyRates the hourlyRates to set
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }
}

/**
 * Represents a shift worker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Unparameterized constructor for ShiftWorker.
     */
    public ShiftWorker() {}

    /**
     * Gets the holiday premium of the shift worker.
     * @return the holidayPremium
     */
    public double getHolidayPremium() {
        return holidayPremium;
    }

    /**
     * Sets the holiday premium of the shift worker.
     * @param holidayPremium the holidayPremium to set
     */
    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for the shift worker.
     * @return the holiday premium
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Represents an off-shift worker in the company.
 */
class OffShiftWorker extends Worker {
    /**
     * Unparameterized constructor for OffShiftWorker.
     */
    public OffShiftWorker() {}
}

/**
 * Represents a department in the company.
 */
class Department {
    private DepartmentType type;
    private List<Employee> employees = new ArrayList<>();
    private Manager manager;

    /**
     * Unparameterized constructor for Department.
     */
    public Department() {}

    /**
     * Gets the type of the department.
     * @return the type
     */
    public DepartmentType getType() {
        return type;
    }

    /**
     * Sets the type of the department.
     * @param type the type to set
     */
    public void setType(DepartmentType type) {
        this.type = type;
    }

    /**
     * Gets the employees in the department.
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees in the department.
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the manager of the department.
     * @return the manager
     */
    public Manager getManager() {
        return manager;
    }

    /**
     * Sets the manager of the department.
     * @param manager the manager to set
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * Calculates the average working hours per week for all workers in the department.
     * @return the average working hours
     */
    public double calculateAverageWorkerWorkingHours() {
        double totalHours = 0;
        int workerCount = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                totalHours += ((Worker) employee).getWeeklyWorkingHour();
                workerCount++;
            }
        }
        return workerCount == 0 ? 0 : totalHours / workerCount;
    }
}

/**
 * Represents the company.
 */
class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {}

    /**
     * Gets the employees in the company.
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees in the company.
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the departments in the company.
     * @return the departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * Sets the departments in the company.
     * @param departments the departments to set
     */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return the total salary
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                totalSalary += ((Worker) employee).getWeeklyWorkingHour() * ((Worker) employee).getHourlyRates();
                if (employee instanceof ShiftWorker) {
                    totalSalary += ((ShiftWorker) employee).calculateHolidayPremium();
                }
            } else if (employee instanceof SalesPeople) {
                totalSalary += ((SalesPeople) employee).getSalary() + ((SalesPeople) employee).getTotalCommission();
            } else if (employee instanceof Manager) {
                totalSalary += ((Manager) employee).getSalary();
            }
        }
        return totalSalary;
    }

    /**
     * Calculates the total commission amount for all sales people in the company.
     * @return the total commission
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPeople) {
                totalCommission += ((SalesPeople) employee).getTotalCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return the total holiday premiums
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) employee).calculateHolidayPremium();
            }
        }
        return totalPremiums;
    }
}