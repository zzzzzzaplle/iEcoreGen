import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an Employee in the company management system
 */
class Employee {
    private String department;
    private String name;
    private Date dateOfBirth;
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
 * Represents a Worker employee type
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;

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

    /**
     * Calculates the base salary for a worker
     * @return base salary calculated as weekly working hours multiplied by hourly rate
     */
    public double calculateBaseSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
}

/**
 * Represents a NonShiftWorker with weekend and holiday permits
 */
class NonShiftWorker extends Worker {
    private boolean hasWeekendPermit;
    private boolean hasHolidayPermit;

    public NonShiftWorker() {
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
}

/**
 * Represents a ShiftWorker who receives holiday premiums
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    public ShiftWorker() {
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the total salary for a shift worker including holiday premium
     * @return total salary including base salary and holiday premium
     */
    public double calculateSalary() {
        return calculateBaseSalary() + holidayPremium;
    }
}

/**
 * Represents a SalesPerson employee type
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
     * Calculates the total salary for a sales person
     * @return total salary including fixed salary and commission
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the commission amount for a sales person
     * @return commission amount calculated as amount of sales multiplied by commission percentage
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a Manager employee type with position and subordinates
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
     * Adds a subordinate to the manager's list of subordinates
     * @param employee the employee to add as subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Calculates the salary for a manager
     * @return fixed salary of the manager
     */
    public double calculateSalary() {
        return fixedSalary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager
     * @return number of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company with employee management functionality
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
     * Calculates the total salary of all employees in the company
     * @return total salary sum of all employees
     */
    public double calculateTotalSalary() {
        double totalSalary = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                if (employee instanceof ShiftWorker) {
                    ShiftWorker shiftWorker = (ShiftWorker) employee;
                    totalSalary += shiftWorker.calculateSalary();
                } else {
                    totalSalary += worker.calculateBaseSalary();
                }
            } else if (employee instanceof SalesPerson) {
                SalesPerson salesPerson = (SalesPerson) employee;
                totalSalary += salesPerson.calculateSalary();
            } else if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                totalSalary += manager.calculateSalary();
            }
        }
        
        return totalSalary;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return average working hours per week, returns 0 if no workers in delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0.0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker && "delivery".equalsIgnoreCase(employee.getDepartment())) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHours();
                workerCount++;
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }

    /**
     * Determines the total commission amount for all salespeople in the company
     * @return total commission amount across all salespeople
     */
    public double calculateTotalCommissionForSalespeople() {
        double totalCommission = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                SalesPerson salesPerson = (SalesPerson) employee;
                totalCommission += salesPerson.calculateCommission();
            }
        }
        
        return totalCommission;
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return total holiday premiums, returns 0 if no shift workers in delivery department
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasDeliveryShiftWorker = false;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                if ("delivery".equalsIgnoreCase(employee.getDepartment())) {
                    totalPremiums += shiftWorker.getHolidayPremium();
                    hasDeliveryShiftWorker = true;
                }
            }
        }
        
        return hasDeliveryShiftWorker ? totalPremiums : 0.0;
    }

    /**
     * Gets the number of direct subordinate employees for each manager
     * @return list of manager names with their subordinate counts
     */
    public List<String> getManagerSubordinateCounts() {
        List<String> managerCounts = new ArrayList<>();
        
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                managerCounts.add(manager.getName() + ": " + manager.getNumberOfSubordinates() + " subordinates");
            }
        }
        
        return managerCounts;
    }
}