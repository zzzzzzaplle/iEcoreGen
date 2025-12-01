import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company
 */
class Employee {
    private String department;
    private String name;
    private String dateOfBirth;
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
}

/**
 * Represents a worker employee
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;
    private boolean isShiftWorker;
    private double holidayPremium;

    public Worker() {
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

    public boolean isShiftWorker() {
        return isShiftWorker;
    }

    public void setShiftWorker(boolean shiftWorker) {
        isShiftWorker = shiftWorker;
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the salary for this worker
     * @return total salary including base pay and holiday premium if applicable
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
 * Represents a non-shift worker (regular worker without shift duties)
 */
class NonShiftWorker extends Worker {
    private boolean weekendPermit;
    private boolean holidayPermit;

    public NonShiftWorker() {
        setShiftWorker(false);
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
 * Represents a shift worker
 */
class ShiftWorker extends Worker {
    public ShiftWorker() {
        setShiftWorker(true);
    }
}

/**
 * Represents a sales person employee
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPerson() {
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
     * Calculates the salary for this sales person
     * @return total salary including fixed salary and commission
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the commission amount for this sales person
     * @return commission amount (amountOfSales * commissionPercentage)
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a manager employee
 */
class Manager extends Employee {
    private double fixedSalary;
    private String position;
    private List<Employee> subordinates;

    public Manager() {
        subordinates = new ArrayList<>();
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
     * Adds a subordinate to this manager
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Removes a subordinate from this manager
     * @param employee the employee to remove from subordinates
     */
    public void removeSubordinate(Employee employee) {
        subordinates.remove(employee);
    }

    /**
     * Calculates the salary for this manager
     * @return fixed salary of the manager
     */
    public double calculateSalary() {
        return fixedSalary;
    }
}

/**
 * Main company class that manages all employees and provides business operations
 */
class Company {
    private List<Employee> employees;

    public Company() {
        employees = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the company
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Removes an employee from the company
     * @param employee the employee to remove
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return sum of all employees' salaries (workers + sales people + managers)
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
     * Finds the average working hours per week for all workers in the delivery department
     * @return average working hours per week, or 0 if there are no workers in delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0.0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker && "delivery".equalsIgnoreCase(employee.getDepartment())) {
                totalHours += ((Worker) employee).getWeeklyWorkingHours();
                workerCount++;
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }

    /**
     * Determines the total commission amount for all salespeople in the company
     * @return sum of commission amounts for all salespeople
     */
    public double calculateTotalCommission() {
        double totalCommission = 0.0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                totalCommission += ((SalesPerson) employee).calculateCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return total holiday premiums, or 0 if there are no shift workers in delivery department
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasShiftWorkersInDelivery = false;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker && ((Worker) employee).isShiftWorker()) {
                if ("delivery".equalsIgnoreCase(employee.getDepartment())) {
                    totalPremiums += ((Worker) employee).getHolidayPremium();
                    hasShiftWorkersInDelivery = true;
                }
            }
        }
        
        return hasShiftWorkersInDelivery ? totalPremiums : 0.0;
    }

    /**
     * Gets the number of direct subordinate employees for each manager
     * @return a list of strings containing manager names and their subordinate counts
     */
    public List<String> getManagerSubordinateCounts() {
        List<String> results = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                results.add(manager.getName() + ": " + manager.getSubordinates().size() + " subordinates");
            }
        }
        return results;
    }
}