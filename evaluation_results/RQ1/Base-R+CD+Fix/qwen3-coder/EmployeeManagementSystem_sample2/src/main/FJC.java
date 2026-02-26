import java.util.*;

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

    // Getter and Setter methods
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
 * Class representing a manager who can have subordinates
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

    // Getter and Setter methods
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
     * Gets the number of direct subordinate employees for this manager
     * @return the count of direct subordinates
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

/**
 * Class representing sales people who earn commissions
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

    // Getter and Setter methods
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
     * Calculates the total commission for this salesperson
     * @return the total commission amount (amountOfSales * commissionPercentage)
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Abstract class representing a worker
 */
abstract class Worker extends Employee {
    protected int weeklyWorkingHour;
    protected double hourlyRates;

    public Worker() {
        super();
        this.weeklyWorkingHour = 0;
        this.hourlyRates = 0.0;
    }

    // Getter and Setter methods
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
 * Class representing shift workers who receive holiday premiums
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
        super();
        this.holidayPremium = 0.0;
    }

    // Getter and Setter methods
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for this shift worker
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Class representing off-shift workers who have weekend and holiday permits
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
        super();
    }
}

/**
 * Enum representing different department types
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

    // Getter and Setter methods
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
     * Calculates the average working hours per week for all workers in this department
     * @return the average working hours, or 0 if there are no workers
     */
    public double calculateAverageWorkerWorkingHours() {
        List<Worker> workersInDepartment = new ArrayList<>();
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                workersInDepartment.add((Worker) employee);
            }
        }
        
        if (workersInDepartment.isEmpty()) {
            return 0;
        }
        
        int totalHours = 0;
        for (Worker worker : workersInDepartment) {
            totalHours += worker.getWeeklyWorkingHour();
        }
        
        return (double) totalHours / workersInDepartment.size();
    }
}

/**
 * Class representing the company with employees and departments
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    // Getter and Setter methods
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
     * Calculates the total salary of all employees in the company
     * @return the total salary amount
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                double workerSalary = worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                
                if (worker instanceof ShiftWorker) {
                    workerSalary += ((ShiftWorker) worker).calculateHolidayPremium();
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
     * Determines the total commission amount for all salespeople in the company
     * @return the total commission amount
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
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return the total holiday premiums, or 0 if there are no shift workers
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