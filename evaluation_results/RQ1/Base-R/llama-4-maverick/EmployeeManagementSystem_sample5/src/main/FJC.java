import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    private String department;
    private String name;
    private String dateOfBirth;
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
     * Gets the date of birth of the employee.
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of the employee.
     * @param dateOfBirth the date of birth to set
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the social insurance number of the employee.
     * @return the social insurance number
     */
    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    /**
     * Sets the social insurance number of the employee.
     * @param socialInsuranceNumber the social insurance number to set
     */
    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    /**
     * Calculates the salary of the employee.
     * @return the salary
     */
    public abstract double calculateSalary();
}

/**
 * Represents a worker in the company.
 */
abstract class Worker extends Employee {
    private double weeklyWorkingHour;
    private double hourlyRates;

    /**
     * Unparameterized constructor for Worker.
     */
    public Worker() {}

    /**
     * Gets the weekly working hours of the worker.
     * @return the weekly working hours
     */
    public double getWeeklyWorkingHour() {
        return weeklyWorkingHour;
    }

    /**
     * Sets the weekly working hours of the worker.
     * @param weeklyWorkingHour the weekly working hours to set
     */
    public void setWeeklyWorkingHour(double weeklyWorkingHour) {
        this.weeklyWorkingHour = weeklyWorkingHour;
    }

    /**
     * Gets the hourly rates of the worker.
     * @return the hourly rates
     */
    public double getHourlyRates() {
        return hourlyRates;
    }

    /**
     * Sets the hourly rates of the worker.
     * @param hourlyRates the hourly rates to set
     */
    public void setHourlyRates(double hourlyRates) {
        this.hourlyRates = hourlyRates;
    }

    /**
     * Calculates the salary of the worker.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return weeklyWorkingHour * hourlyRates + calculateHolidayPremiums();
    }

    /**
     * Calculates the holiday premiums for the worker.
     * @return the holiday premiums
     */
    protected abstract double calculateHolidayPremiums();
}

/**
 * Represents a shift worker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremiums;

    /**
     * Unparameterized constructor for ShiftWorker.
     */
    public ShiftWorker() {}

    /**
     * Gets the holiday premiums of the shift worker.
     * @return the holiday premiums
     */
    public double getHolidayPremiums() {
        return holidayPremiums;
    }

    /**
     * Sets the holiday premiums of the shift worker.
     * @param holidayPremiums the holiday premiums to set
     */
    public void setHolidayPremiums(double holidayPremiums) {
        this.holidayPremiums = holidayPremiums;
    }

    /**
     * Calculates the holiday premiums for the shift worker.
     * @return the holiday premiums
     */
    @Override
    protected double calculateHolidayPremiums() {
        return holidayPremiums;
    }
}

/**
 * Represents a non-shift worker in the company.
 */
class NonShiftWorker extends Worker {
    /**
     * Unparameterized constructor for NonShiftWorker.
     */
    public NonShiftWorker() {}

    /**
     * Calculates the holiday premiums for the non-shift worker.
     * @return the holiday premiums (always 0)
     */
    @Override
    protected double calculateHolidayPremiums() {
        return 0;
    }
}

/**
 * Represents a salesperson in the company.
 */
class SalesPerson extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Unparameterized constructor for SalesPerson.
     */
    public SalesPerson() {}

    /**
     * Gets the salary of the salesperson.
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the salesperson.
     * @param salary the salary to set
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Gets the amount of sales made by the salesperson.
     * @return the amount of sales
     */
    public double getAmountOfSales() {
        return amountOfSales;
    }

    /**
     * Sets the amount of sales made by the salesperson.
     * @param amountOfSales the amount of sales to set
     */
    public void setAmountOfSales(double amountOfSales) {
        this.amountOfSales = amountOfSales;
    }

    /**
     * Gets the commission percentage of the salesperson.
     * @return the commission percentage
     */
    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    /**
     * Sets the commission percentage of the salesperson.
     * @param commissionPercentage the commission percentage to set
     */
    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    /**
     * Calculates the salary of the salesperson.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return salary + amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private List<Employee> subordinates;
    private String position;

    /**
     * Unparameterized constructor for Manager.
     */
    public Manager() {
        this.subordinates = new ArrayList<>();
    }

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
     * Gets the subordinates of the manager.
     * @return the subordinates
     */
    public List<Employee> getSubordinates() {
        return subordinates;
    }

    /**
     * Adds a subordinate to the manager.
     * @param subordinate the subordinate to add
     */
    public void addSubordinate(Employee subordinate) {
        this.subordinates.add(subordinate);
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
     * Calculates the salary of the manager.
     * @return the salary
     */
    @Override
    public double calculateSalary() {
        return salary;
    }

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @return the number of subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company.
 */
class Company {
    private List<Employee> employees;

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {
        this.employees = new ArrayList<>();
    }

    /**
     * Adds an employee to the company.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return the total salary
     */
    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }

    /**
     * Finds the average working hours per week for all workers in the delivery department.
     * @return the average working hours
     */
    public double averageWorkingHoursInDeliveryDepartment() {
        double totalWorkingHours = 0;
        int workerCount = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker && employee.getDepartment().equals("delivery")) {
                totalWorkingHours += ((Worker) employee).getWeeklyWorkingHour();
                workerCount++;
            }
        }
        return workerCount == 0 ? 0 : totalWorkingHours / workerCount;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * @return the total commission amount
     */
    public double totalCommissionAmount() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                SalesPerson salesPerson = (SalesPerson) employee;
                totalCommission += salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return the total holiday premiums
     */
    public double totalHolidayPremiums() {
        double totalPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) employee).getHolidayPremiums();
            }
        }
        return totalPremiums;
    }

    /**
     * Gets the number of direct subordinate employees for each manager.
     * @return a list of manager subordinate counts
     */
    public List<Integer> getManagerSubordinateCounts() {
        List<Integer> subordinateCounts = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                subordinateCounts.add(((Manager) employee).getNumberOfSubordinates());
            }
        }
        return subordinateCounts;
    }
}