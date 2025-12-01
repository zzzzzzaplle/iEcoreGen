import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company.
 */
abstract class Employee {
    private String name;
    private String dateOfBirth;
    private String socialInsuranceNumber;
    private String department;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}

/**
 * Represents a worker employee.
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;

    public Worker() {
        super();
    }

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

    /**
     * Calculates the salary of the worker.
     * For shift workers, includes holiday premiums.
     * 
     * @return the calculated salary
     */
    public double calculateSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
}

/**
 * Represents an off-shift worker.
 */
class OffShiftWorker extends Worker {
    private boolean weekendPermit;
    private boolean holidayPermit;

    public OffShiftWorker() {
        super();
    }

    public boolean isWeekendPermit() {
        return weekendPermit;
    }

    public void setWeekendPermit(boolean weekendPermit) {
        this.weekendPermit = weekendPermit;
    }

    public boolean isHolidayPermit() {
        return holidayPermit;
    }

    public void setHolidayPermit(boolean holidayPermit) {
        this.holidayPermit = holidayPermit;
    }
}

/**
 * Represents a shift worker.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
        super();
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the salary of the shift worker including holiday premiums.
     * 
     * @return the calculated salary including holiday premiums
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + holidayPremium;
    }
}

/**
 * Represents a sales person employee.
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPerson() {
        super();
    }

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

    /**
     * Calculates the salary of the sales person.
     * Salary = fixed salary + (amount of sales * commission percentage)
     * 
     * @return the calculated salary
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage / 100);
    }

    /**
     * Calculates the commission amount for this sales person.
     * 
     * @return the commission amount
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage / 100;
    }
}

/**
 * Represents a manager employee.
 */
class Manager extends Employee {
    private double fixedSalary;
    private String position;
    private List<Employee> subordinates;

    public Manager() {
        super();
        this.subordinates = new ArrayList<>();
    }

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
     * Calculates the salary of the manager.
     * 
     * @return the fixed salary
     */
    public double calculateSalary() {
        return fixedSalary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager.
     * 
     * @return the count of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }

    /**
     * Adds a subordinate to this manager.
     * 
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }
}

/**
 * Represents the company with employees.
 */
class Company {
    private List<Employee> employees;

    public Company() {
        this.employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Calculates the total salary of all employees in the company.
     * Total salary = sum(workers' salary + sales people's salary + managers' salary)
     * 
     * @return the total salary of all employees
     */
    public double calculateTotalSalary() {
        double totalSalary = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                totalSalary += ((Worker) employee).calculateSalary();
            } else if (employee instanceof SalesPerson) {
                totalSalary += ((SalesPerson) employee).calculateSalary();
            } else if (employee instanceof Manager) {
                totalSalary += ((Manager) employee).calculateSalary();
            }
        }

        return totalSalary;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department.
     * 
     * @return the average working hours, or 0 if there are no workers in the delivery department
     */
    public double findAverageWorkingHoursInDelivery() {
        double totalHours = 0;
        int workerCount = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker && "delivery".equalsIgnoreCase(employee.getDepartment())) {
                totalHours += ((Worker) employee).getWeeklyWorkingHours();
                workerCount++;
            }
        }

        if (workerCount == 0) {
            return 0;
        }

        return totalHours / workerCount;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * 
     * @return the total commission amount
     */
    public double calculateTotalCommission() {
        double totalCommission = 0;

        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                totalCommission += ((SalesPerson) employee).calculateCommission();
            }
        }

        return totalCommission;
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company.
     * 
     * @return the total holiday premiums, or 0 if there are no shift workers
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0;

        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) employee).getHolidayPremium();
            }
        }

        return totalPremiums;
    }
}