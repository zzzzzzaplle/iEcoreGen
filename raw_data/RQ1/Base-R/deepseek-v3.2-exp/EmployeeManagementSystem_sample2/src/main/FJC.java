import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the company
 */
class Employee {
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;
    private Department department;

    /**
     * Default constructor
     */
    public Employee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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
 * Represents a department in the company
 */
class Department {
    private String name;
    private Manager manager;
    private List<Employee> employees;

    /**
     * Default constructor
     */
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

    /**
     * Adds an employee to the department
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
    }
}

/**
 * Represents a worker employee
 */
class Worker extends Employee {
    private double weeklyWorkingHours;
    private double hourlyRate;

    /**
     * Default constructor
     */
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
     * Calculates the base salary for the worker
     * @return base salary calculated as weekly working hours multiplied by hourly rate
     */
    public double calculateBaseSalary() {
        return weeklyWorkingHours * hourlyRate;
    }
}

/**
 * Represents a non-shift worker with weekend and holiday permits
 */
class NonShiftWorker extends Worker {
    private boolean hasWeekendPermit;
    private boolean hasHolidayPermit;

    /**
     * Default constructor
     */
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

    /**
     * Calculates the total salary for non-shift worker
     * @return total salary including base salary (no holiday premiums)
     */
    public double calculateTotalSalary() {
        return calculateBaseSalary();
    }
}

/**
 * Represents a shift worker with holiday premiums
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Default constructor
     */
    public ShiftWorker() {
    }

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the total salary for shift worker
     * @return total salary including base salary and holiday premiums
     */
    public double calculateTotalSalary() {
        return calculateBaseSalary() + holidayPremium;
    }
}

/**
 * Represents a sales person employee
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor
     */
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
     * Calculates the commission amount for sales person
     * @return commission amount calculated as amount of sales multiplied by commission percentage
     */
    public double calculateCommission() {
        return amountOfSales * commissionPercentage;
    }

    /**
     * Calculates the total salary for sales person
     * @return total salary including fixed salary and commission
     */
    public double calculateTotalSalary() {
        return fixedSalary + calculateCommission();
    }
}

/**
 * Represents a manager employee with position and subordinates
 */
class Manager extends Employee {
    private String position;
    private double fixedSalary;
    private List<Employee> subordinates;

    /**
     * Default constructor
     */
    public Manager() {
        this.subordinates = new ArrayList<>();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getFixedSalary() {
        return fixedSalary;
    }

    public void setFixedSalary(double fixedSalary) {
        this.fixedSalary = fixedSalary;
    }

    public List<Employee> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<Employee> subordinates) {
        this.subordinates = subordinates;
    }

    /**
     * Adds a subordinate to the manager
     * @param subordinate the subordinate employee to add
     */
    public void addSubordinate(Employee subordinate) {
        if (subordinates == null) {
            subordinates = new ArrayList<>();
        }
        subordinates.add(subordinate);
    }

    /**
     * Calculates the total salary for manager
     * @return fixed salary of the manager
     */
    public double calculateTotalSalary() {
        return fixedSalary;
    }

    /**
     * Gets the number of direct subordinate employees
     * @return number of direct subordinates
     */
    public int getNumberOfSubordinates() {
        return subordinates != null ? subordinates.size() : 0;
    }
}

/**
 * Represents the company with employees and departments
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    /**
     * Default constructor
     */
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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Adds an employee to the company
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employees == null) {
            employees = new ArrayList<>();
        }
        employees.add(employee);
    }

    /**
     * Adds a department to the company
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return total salary calculated as sum of all employees' salaries
     */
    public double calculateTotalSalary() {
        double totalSalary = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                if (worker instanceof ShiftWorker) {
                    totalSalary += ((ShiftWorker) worker).calculateTotalSalary();
                } else if (worker instanceof NonShiftWorker) {
                    totalSalary += ((NonShiftWorker) worker).calculateTotalSalary();
                } else {
                    totalSalary += worker.calculateBaseSalary();
                }
            } else if (employee instanceof SalesPerson) {
                totalSalary += ((SalesPerson) employee).calculateTotalSalary();
            } else if (employee instanceof Manager) {
                totalSalary += ((Manager) employee).calculateTotalSalary();
            }
        }
        
        return totalSalary;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return average working hours per week for delivery department workers, or 0 if no workers in delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0.0;
        int workerCount = 0;
        
        for (Department department : departments) {
            if ("Delivery".equalsIgnoreCase(department.getName())) {
                for (Employee employee : department.getEmployees()) {
                    if (employee instanceof Worker) {
                        Worker worker = (Worker) employee;
                        totalHours += worker.getWeeklyWorkingHours();
                        workerCount++;
                    }
                }
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }

    /**
     * Determines the total commission amount for all salespeople in the company
     * @return total commission amount calculated as sum of all salespeople's commissions
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
     * @return total holiday premiums for all shift workers, or 0 if no shift workers exist
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0.0;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.getHolidayPremium();
            }
        }
        
        return totalPremiums;
    }

    /**
     * Gets the number of direct subordinate employees for each manager
     * @return list of manager names with their subordinate counts
     */
    public List<String> getManagerSubordinateCounts() {
        List<String> results = new ArrayList<>();
        
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                results.add(manager.getName() + ": " + manager.getNumberOfSubordinates() + " subordinates");
            }
        }
        
        return results;
    }

    /**
     * Gets the delivery department from the company
     * @return the delivery department or null if not found
     */
    public Department getDeliveryDepartment() {
        for (Department department : departments) {
            if ("Delivery".equalsIgnoreCase(department.getName())) {
                return department;
            }
        }
        return null;
    }
}