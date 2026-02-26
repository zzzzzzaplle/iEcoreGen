import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents an Employee in the company
 */
class Employee {
    private String name;
    private Date dateOfBirth;
    private String socialInsuranceNumber;
    private Department department;

    public Employee() {
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

/**
 * Represents a Worker employee
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
     * Calculates the salary for this worker
     * @return the calculated salary for this worker
     */
    public double calculateSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
}

/**
 * Represents a Shift Worker employee
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
     * Calculates the salary for this shift worker including holiday premiums
     * @return the calculated salary including holiday premiums
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + holidayPremium;
    }
}

/**
 * Represents a NonShift Worker employee
 */
class NonShiftWorker extends Worker {
    private boolean weekendPermit;
    private boolean officialHolidayPermit;

    public NonShiftWorker() {
    }

    public boolean isWeekendPermit() {
        return weekendPermit;
    }

    public void setWeekendPermit(boolean weekendPermit) {
        this.weekendPermit = weekendPermit;
    }

    public boolean isOfficialHolidayPermit() {
        return officialHolidayPermit;
    }

    public void setOfficialHolidayPermit(boolean officialHolidayPermit) {
        this.officialHolidayPermit = officialHolidayPermit;
    }
}

/**
 * Represents a Sales Person employee
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
     * @return the calculated salary including commission
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the commission amount for this sales person
     * @return the calculated commission amount
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }
}

/**
 * Represents a Manager employee
 */
class Manager extends Employee {
    private double fixedSalary;
    private String position;
    private List<Employee> subordinates;

    public Manager() {
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

    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Calculates the salary for this manager
     * @return the fixed salary of the manager
     */
    public double calculateSalary() {
        return fixedSalary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager
     * @return the count of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents a Department in the company
 */
class Department {
    private String name;
    private Manager manager;
    private List<Employee> employees;

    public Department() {
        this.employees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
}

/**
 * Represents the Company with employees and departments
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    public Company() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return the total salary of all employees
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
     * Finds average working hours per week for all workers in the delivery department
     * @return the average working hours per week, or 0 if no workers in delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0.0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker && 
                employee.getDepartment() != null && 
                "Delivery".equals(employee.getDepartment().getName())) {
                totalHours += ((Worker) employee).getWeeklyWorkingHours();
                workerCount++;
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }

    /**
     * Determines the total commission amount for all salespeople in the company
     * @return the total commission amount for all salespeople
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
     * @return the total holiday premiums, or 0 if no shift workers in delivery department
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasShiftWorkersInDelivery = false;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                if (employee.getDepartment() != null && "Delivery".equals(employee.getDepartment().getName())) {
                    hasShiftWorkersInDelivery = true;
                }
                totalPremiums += ((ShiftWorker) employee).getHolidayPremium();
            }
        }
        
        return hasShiftWorkersInDelivery ? totalPremiums : 0.0;
    }

    /**
     * Gets the number of direct subordinate employees for each manager
     * @return a list of strings containing manager names and their subordinate counts
     */
    public List<String> getSubordinateCountsForManagers() {
        List<String> results = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                results.add(manager.getName() + ": " + manager.getNumberOfSubordinates() + " subordinates");
            }
        }
        return results;
    }
}