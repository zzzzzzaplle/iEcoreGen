import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an abstract employee in the company.
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    public Employee() {
    }

    public Employee(String department, String name, Date birthDate, String socialInsuranceNumber) {
        this.department = department;
        this.name = name;
        this.birthDate = birthDate;
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    // Getters and Setters
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
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private String position;

    public Manager() {
    }

    public Manager(String department, String name, Date birthDate, String socialInsuranceNumber, double salary, String position) {
        super(department, name, birthDate, socialInsuranceNumber);
        this.salary = salary;
        this.position = position;
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

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @param company The company object containing all employees.
     * @return The number of direct subordinate employees.
     */
    public int getDirectSubordinateEmployeesCount(Company company) {
        List<Employee> employees = company.getEmployees();
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(this.getDepartment()) && !(employee instanceof Manager)) {
                count++;
            }
        }
        return count;
    }
}

/**
 * Represents salespeople in the company.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {
    }

    public SalesPeople(String department, String name, Date birthDate, String socialInsuranceNumber, double salary, double amountOfSales, double commissionPercentage) {
        super(department, name, birthDate, socialInsuranceNumber);
        this.salary = salary;
        this.amountOfSales = amountOfSales;
        this.commissionPercentage = commissionPercentage;
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
     * Calculates the total commission for the salesperson.
     * @return The total commission.
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents an abstract worker in the company.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    public Worker() {
    }

    public Worker(String department, String name, Date birthDate, String socialInsuranceNumber, int weeklyWorkingHour, double hourlyRates) {
        super(department, name, birthDate, socialInsuranceNumber);
        this.weeklyWorkingHour = weeklyWorkingHour;
        this.hourlyRates = hourlyRates;
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
}

/**
 * Represents a shift worker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
    }

    public ShiftWorker(String department, String name, Date birthDate, String socialInsuranceNumber, int weeklyWorkingHour, double hourlyRates, double holidayPremium) {
        super(department, name, birthDate, socialInsuranceNumber, weeklyWorkingHour, hourlyRates);
        this.holidayPremium = holidayPremium;
    }

    // Getters and Setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for the shift worker.
     * @return The holiday premium.
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Represents an off-shift worker in the company.
 */
class OffShiftWorker extends Worker {
    public OffShiftWorker() {
    }

    public OffShiftWorker(String department, String name, Date birthDate, String socialInsuranceNumber, int weeklyWorkingHour, double hourlyRates) {
        super(department, name, birthDate, socialInsuranceNumber, weeklyWorkingHour, hourlyRates);
    }
}

/**
 * Represents a company with employees and departments.
 */
class Company {
    private List<Employee> employees;

    public Company() {
        this.employees = new ArrayList<>();
    }

    public Company(List<Employee> employees) {
        this.employees = employees;
    }

    // Getters and Setters
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return The total salary.
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalSalary += worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                if (employee instanceof ShiftWorker) {
                    ShiftWorker shiftWorker = (ShiftWorker) employee;
                    totalSalary += shiftWorker.calculateHolidayPremium();
                }
            } else if (employee instanceof SalesPeople) {
                SalesPeople salesPeople = (SalesPeople) employee;
                totalSalary += salesPeople.getSalary() + salesPeople.getTotalCommission();
            } else if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                totalSalary += manager.getSalary();
            }
        }
        return totalSalary;
    }

    /**
     * Calculates the total commission for all salespeople in the company.
     * @return The total commission.
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPeople) {
                SalesPeople salesPeople = (SalesPeople) employee;
                totalCommission += salesPeople.getTotalCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return The total holiday premiums.
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalHolidayPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalHolidayPremiums += shiftWorker.calculateHolidayPremium();
            }
        }
        return totalHolidayPremiums;
    }
}

/**
 * Represents the type of department in the company.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Represents a department in the company.
 */
class Department {
    private DepartmentType type;

    public Department() {
    }

    public Department(DepartmentType type) {
        this.type = type;
    }

    // Getters and Setters
    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    /**
     * Calculates the average working hours per week for all workers in the delivery department.
     * @param company The company object containing all employees.
     * @return The average working hours per week.
     */
    public double calculateAverageWorkerWorkingHours(Company company) {
        if (this.type != DepartmentType.DELIVERY) {
            return 0;
        }
        List<Employee> employees = company.getEmployees();
        int totalHours = 0;
        int count = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker && employee.getDepartment().equals(this.type.toString())) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHour();
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return (double) totalHours / count;
    }
}