import java.util.*;

/**
 * Represents an employee in the company
 */
class Employee {
    protected String name;
    protected String department;
    protected Date dateOfBirth;
    protected String socialInsuranceNumber;

    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }
}

/**
 * Represents a worker employee
 */
class Worker extends Employee {
    protected int weeklyWorkingHours;
    protected double hourlyRate;
    protected boolean isShiftWorker;
    protected boolean hasWeekendPermit;
    protected boolean hasHolidayPermit;
    protected double holidayPremium;

    public Worker() {
        super();
    }

    public int getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(int weeklyWorkingHours) {
        this.weeklyWorkingHours = weeklyWorkingHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public boolean isShiftWorker() {
        return isShiftWorker;
    }

    public void setShiftWorker(boolean shiftWorker) {
        isShiftWorker = shiftWorker;
    }

    public boolean isHasWeekendPermit() {
        return hasWeekendPermit;
    }

    public void setHasWeekendPermit(boolean hasWeekendPermit) {
        this.hasWeekendPermit = hasWeekendPermit;
    }

    public boolean isHasHolidayPermit() {
        return hasHolidayPermit;
    }

    public void setHasHolidayPermit(boolean hasHolidayPermit) {
        this.hasHolidayPermit = hasHolidayPermit;
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculate worker's salary based on working hours, hourly rate and holiday premiums
     * @return worker's total salary
     */
    public double calculateSalary() {
        double baseSalary = weeklyWorkingHours * hourlyRate;
        if (isShiftWorker) {
            return baseSalary + holidayPremium;
        }
        return baseSalary;
    }
}

/**
 * Represents a sales person employee
 */
class SalesPerson extends Employee {
    protected double fixedSalary;
    protected double amountOfSales;
    protected double commissionPercentage;

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
     * Calculate salesperson's salary based on fixed salary and commission from sales
     * @return salesperson's total salary
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }
}

/**
 * Represents a manager employee
 */
class Manager extends Employee {
    protected double fixedSalary;
    protected String position;
    protected List<Employee> subordinates;

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
     * Add a subordinate to this manager
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Get the number of direct subordinate employees for this manager
     * @return the count of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }

    /**
     * Calculate manager's salary based on fixed salary
     * @return manager's total salary
     */
    public double calculateSalary() {
        return fixedSalary;
    }
}

/**
 * Represents the company with employees
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
     * Add an employee to the company
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Calculate the total salary of all employees in the company
     * Total salary = sum(workers' salary + sales people's salary + managers' salary)
     * Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers]
     * Sales people's salary = salary + amountOfSales * commissionPercentage
     * Managers' salary = salary
     * @return total salary of all employees
     */
    public double calculateTotalSalary() {
        double totalSalary = 0.0;

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
     * Find average working hours per week for all workers in the delivery department
     * Return 0 if there are no workers in the delivery department
     * @return average working hours per week for delivery department workers
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        int totalHours = 0;
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

        return (double) totalHours / workerCount;
    }

    /**
     * Determine the total commission amount for all salespeople in the company
     * Sum(amountOfSales * commissionPercentage) for all salespeople
     * @return total commission amount for all salespeople
     */
    public double calculateTotalCommission() {
        double totalCommission = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                SalesPerson salesPerson = (SalesPerson) employee;
                totalCommission += salesPerson.getAmountOfSales() * salesPerson.getCommissionPercentage();
            }
        }

        return totalCommission;
    }

    /**
     * Calculate total holiday premiums paid to all shift workers in the company
     * Return 0 if there are no shift workers in the delivery department
     * @return total holiday premiums paid to shift workers
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                if (worker.isShiftWorker()) {
                    totalPremiums += worker.getHolidayPremium();
                }
            }
        }

        return totalPremiums;
    }
}