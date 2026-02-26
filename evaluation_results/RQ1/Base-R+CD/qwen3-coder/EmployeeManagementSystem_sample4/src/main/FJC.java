import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract class representing an employee in the company
 */
abstract class Employee {
    protected String department;
    protected String name;
    protected Date birthDate;
    protected String socialInsuranceNumber;

    public Employee() {
        this.department = "";
        this.name = "";
        this.birthDate = new Date();
        this.socialInsuranceNumber = "";
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
 * Class representing a manager employee
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;

    public Manager() {
        super();
        this.salary = 0.0;
        this.position = "";
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
     * Get the number of direct subordinate employees for this manager
     * 
     * @return the count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return this.subordinates.size();
    }
}

/**
 * Class representing a sales person employee
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {
        super();
        this.salary = 0.0;
        this.amountOfSales = 0.0;
        this.commissionPercentage = 0.0;
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
     * Calculate the total commission for this sales person
     * 
     * @return the total commission amount (amountOfSales * commissionPercentage)
     */
    public double getTotalCommission() {
        return this.amountOfSales * this.commissionPercentage;
    }
}

/**
 * Abstract class representing a worker employee
 */
abstract class Worker extends Employee {
    protected int weeklyWorkingHour;
    protected double hourlyRates;

    public Worker() {
        super();
        this.weeklyWorkingHour = 0;
        this.hourlyRates = 0.0;
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
 * Class representing a shift worker
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
        super();
        this.holidayPremium = 0.0;
    }

    // Getters and setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculate the holiday premium for this shift worker
     * 
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return this.holidayPremium;
    }
}

/**
 * Class representing an off-shift worker
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
        super();
    }
}

/**
 * Class representing the company with all employees
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

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
     * Calculate the total salary of all employees in the company
     * Total salary = sum(workers' salary + sales people's salary + managers' salary)
     * Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers]
     * Sales people's salary = salary + amountOfSales * commissionPercentage
     * Managers' salary = salary
     * 
     * @return the total salary of all employees
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;

        for (Employee employee : this.employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                double workerSalary = worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                
                if (employee instanceof ShiftWorker) {
                    ShiftWorker shiftWorker = (ShiftWorker) employee;
                    workerSalary += shiftWorker.calculateHolidayPremium();
                }
                
                totalSalary += workerSalary;
            } else if (employee instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) employee;
                double salesSalary = salesPerson.getSalary() + 
                                   (salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage());
                totalSalary += salesSalary;
            } else if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                totalSalary += manager.getSalary();
            }
        }

        return totalSalary;
    }

    /**
     * Calculate the total commission amount for all salespeople in the company
     * Sum(amountOfSales * commissionPercentage) for all salespeople
     * 
     * @return the total commission amount for all salespeople
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;

        for (Employee employee : this.employees) {
            if (employee instanceof SalesPeople) {
                SalesPeople salesPerson = (SalesPeople) employee;
                totalCommission += salesPerson.getTotalCommission();
            }
        }

        return totalCommission;
    }

    /**
     * Calculate total holiday premiums paid to all shift workers in the company
     * Return 0 if there are no shift workers in the delivery department
     * 
     * @return the total holiday premiums for shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;

        for (Employee employee : this.employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.calculateHolidayPremium();
            }
        }

        return totalPremiums;
    }
}

/**
 * Enum representing department types
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Class representing a department in the company
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;

    public Department() {
        this.type = DepartmentType.PRODUCTION;
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
     * Calculate the average working hours per week for all workers in the delivery department
     * Return 0 if there are no workers in the delivery department
     * 
     * @return the average working hours per week for delivery department workers
     */
    public double calculateAverageWorkerWorkingHours() {
        if (this.type != DepartmentType.DELIVERY) {
            return 0.0;
        }

        int totalWorkingHours = 0;
        int workerCount = 0;

        for (Employee employee : this.employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalWorkingHours += worker.getWeeklyWorkingHour();
                workerCount++;
            }
        }

        if (workerCount == 0) {
            return 0.0;
        }

        return (double) totalWorkingHours / workerCount;
    }
}