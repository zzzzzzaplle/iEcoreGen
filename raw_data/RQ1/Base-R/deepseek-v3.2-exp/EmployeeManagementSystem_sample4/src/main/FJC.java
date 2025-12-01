import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;
    private String dateOfBirth;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}

/**
 * Represents a department in the company.
 */
class Department {
    private String name;
    private Manager manager;
    private List<Employee> employees;

    public Department() {
        employees = new ArrayList<>();
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
}

/**
 * Represents a worker employee.
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;
    private boolean isShiftWorker;
    private double holidayPremiums;

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

    public double getHolidayPremiums() {
        return holidayPremiums;
    }

    public void setHolidayPremiums(double holidayPremiums) {
        this.holidayPremiums = holidayPremiums;
    }

    /**
     * Calculates the salary for this worker.
     * Workers' salary = weeklyWorkingHour * hourlyRates + [holiday premiums for shift workers]
     *
     * @return the calculated salary for this worker
     */
    public double calculateSalary() {
        double baseSalary = weeklyWorkingHours * hourlyRate;
        if (isShiftWorker) {
            baseSalary += holidayPremiums;
        }
        return baseSalary;
    }
}

/**
 * Represents a non-shift worker employee.
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
 * Represents a shift worker employee.
 */
class ShiftWorker extends Worker {
    public ShiftWorker() {
        setShiftWorker(true);
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
     * Calculates the salary for this sales person.
     * Sales people's salary = salary + amountOfSales * commissionPercentage
     *
     * @return the calculated salary for this sales person
     */
    public double calculateSalary() {
        return fixedSalary + (amountOfSales * commissionPercentage);
    }

    /**
     * Calculates the commission amount for this sales person.
     *
     * @return the commission amount (amountOfSales * commissionPercentage)
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
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
     * Calculates the salary for this manager.
     * Managers' salary = salary
     *
     * @return the fixed salary of this manager
     */
    public double calculateSalary() {
        return fixedSalary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager.
     *
     * @return the number of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company with employees and departments.
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    public Company() {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
    }

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
     * Total salary = sum(workers' salary + sales people's salary + managers' salary)
     *
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
     * Finds average working hours per week for all workers in the delivery department.
     * Returns 0 if there are no workers in the delivery department.
     *
     * @return the average working hours per week for delivery department workers
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0.0;
        int workerCount = 0;
        
        for (Department department : departments) {
            if ("Delivery".equalsIgnoreCase(department.getName())) {
                for (Employee employee : department.getEmployees()) {
                    if (employee instanceof Worker) {
                        totalHours += ((Worker) employee).getWeeklyWorkingHours();
                        workerCount++;
                    }
                }
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * Sum(amountOfSales * commissionPercentage) for all salespeople.
     *
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
     * Calculates total holiday premiums paid to all shift workers in the company.
     * Returns 0 if there are no shift workers in the delivery department.
     *
     * @return the total holiday premiums for all shift workers
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasShiftWorkersInDelivery = false;
        
        for (Department department : departments) {
            if ("Delivery".equalsIgnoreCase(department.getName())) {
                for (Employee employee : department.getEmployees()) {
                    if (employee instanceof Worker && ((Worker) employee).isShiftWorker()) {
                        totalPremiums += ((Worker) employee).getHolidayPremiums();
                        hasShiftWorkersInDelivery = true;
                    }
                }
            }
        }
        
        return hasShiftWorkersInDelivery ? totalPremiums : 0.0;
    }

    /**
     * Gets the number of direct subordinate employees for each manager.
     * This method prints the results to console as specified in the requirements.
     */
    public void printManagerSubordinateCounts() {
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                System.out.println("Manager " + manager.getName() + " has " + 
                                 manager.getNumberOfSubordinates() + " direct subordinates.");
            }
        }
    }
}

/**
 * Represents specific department types in the company.
 */
class ProductionDepartment extends Department {
    public ProductionDepartment() {
        setName("Production");
    }
}

class ControlDepartment extends Department {
    public ControlDepartment() {
        setName("Control");
    }
}

class DeliveryDepartment extends Department {
    public DeliveryDepartment() {
        setName("Delivery");
    }
}