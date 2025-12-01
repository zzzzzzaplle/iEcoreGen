import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract class representing an employee in the company.
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
    }

    // Getters and setters
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
 * Class representing a manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;

    /**
     * Default constructor for Manager.
     */
    public Manager() {
        this.subordinates = new ArrayList<>();
    }

    // Getters and setters
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
     * Gets the number of direct subordinate employees for this manager.
     *
     * @return the count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

/**
 * Class representing a salesperson in the company.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor for SalesPeople.
     */
    public SalesPeople() {
    }

    // Getters and setters
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
     * Calculates the total commission for this salesperson.
     *
     * @return the total commission amount (amountOfSales * commissionPercentage)
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Abstract class representing a worker in the company.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    /**
     * Default constructor for Worker.
     */
    public Worker() {
    }

    // Getters and setters
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
 * Class representing a shift worker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Default constructor for ShiftWorker.
     */
    public ShiftWorker() {
    }

    // Getters and setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for this shift worker.
     *
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Class representing an off-shift worker in the company.
 */
class OffShiftWorker extends Worker {
    /**
     * Default constructor for OffShiftWorker.
     */
    public OffShiftWorker() {
    }
}

/**
 * Class representing the company with its employees and departments.
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    // Getters and setters
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
     * Total salary = sum(workers' salary + sales people's salary + managers' salary).
     * Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers].
     * Sales people's salary = salary + amountOfSales * commissionPercentage.
     * Managers' salary = salary.
     *
     * @return the total salary of all employees
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                double workerSalary = worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                
                if (worker instanceof ShiftWorker) {
                    ShiftWorker shiftWorker = (ShiftWorker) worker;
                    workerSalary += shiftWorker.calculateHolidayPremium();
                }
                
                totalSalary += workerSalary;
            } else if (employee instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) employee;
                totalSalary += salesPerson.getSalary() + 
                              (salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage());
            } else if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                totalSalary += manager.getSalary();
            }
        }

        return totalSalary;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * Sum(amountOfSales * commissionPercentage) for all salespeople.
     *
     * @return the total commission amount for all salespeople
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) employee;
                totalCommission += salesPerson.getTotalCommission();
            }
        }

        return totalCommission;
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company.
     * Return 0 if there are no shift workers in the delivery department.
     *
     * @return the total holiday premiums for shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.calculateHolidayPremium();
            }
        }

        return totalPremiums;
    }
}

/**
 * Enum representing the different department types in the company.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Class representing a department in the company.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;

    /**
     * Default constructor for Department.
     */
    public Department() {
        this.employees = new ArrayList<>();
    }

    // Getters and setters
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
     * Calculates the average working hours per week for all workers in the delivery department.
     * Return 0 if there are no workers in the delivery department.
     *
     * @return the average working hours per week for delivery department workers
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0.0;
        }

        int totalHours = 0;
        int workerCount = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHour();
                workerCount++;
            }
        }

        if (workerCount == 0) {
            return 0.0;
        }

        return (double) totalHours / workerCount;
    }
}