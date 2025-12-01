import java.util.Date;
import java.util.List;
import java.util.ArrayList;

enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
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
    
    /**
     * Calculate the salary for this employee
     * This is an abstract method that must be implemented by subclasses
     * @return the calculated salary for the employee
     */
    public abstract double calculateSalary();
}

class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;
    
    public Manager() {
        subordinates = new ArrayList<>();
    }
    
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
    
    public void addSubordinate(Employee subordinate) {
        subordinates.add(subordinate);
    }
    
    public void removeSubordinate(Employee subordinate) {
        subordinates.remove(subordinate);
    }
    
    /**
     * Calculate the salary for the manager
     * Manager salary is a fixed amount
     * @return the fixed salary of the manager
     */
    @Override
    public double calculateSalary() {
        return salary;
    }
    
    /**
     * Get the number of direct subordinate employees for this manager
     * @return the count of direct subordinate employees
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;
    
    public SalesPeople() {
    }
    
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
     * Calculate the salary for the sales person
     * Sales person salary = fixed salary + (amount of sales * commission percentage)
     * @return the calculated salary including commission
     */
    @Override
    public double calculateSalary() {
        return salary + (amountOfSales * commissionPercentage);
    }
    
    /**
     * Calculate the total commission earned by this sales person
     * @return the commission amount (amount of sales * commission percentage)
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;
    
    public Worker() {
    }
    
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
    
    /**
     * Calculate the base salary for the worker
     * Base worker salary = weekly working hours * hourly rates
     * @return the base salary without any additional premiums
     */
    protected double calculateBaseSalary() {
        return weeklyWorkingHour * hourlyRates;
    }
}

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
     * Calculate the salary for the shift worker
     * Shift worker salary = base salary + holiday premium
     * @return the total salary including holiday premium
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary() + holidayPremium;
    }
    
    /**
     * Calculate the holiday premium for this shift worker
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

class OffShiftWorker extends Worker {
    public OffShiftWorker() {
    }
    
    /**
     * Calculate the salary for the off-shift worker
     * Off-shift worker salary = base salary (no additional premiums)
     * @return the base salary of the off-shift worker
     */
    @Override
    public double calculateSalary() {
        return calculateBaseSalary();
    }
}

class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;
    
    public Department() {
        employees = new ArrayList<>();
    }
    
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
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    /**
     * Calculate average working hours per week for all workers in this department
     * Only considers Worker objects (ShiftWorker and OffShiftWorker)
     * @return the average working hours per week, or 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        if (employees.isEmpty()) {
            return 0.0;
        }
        
        int totalHours = 0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHour();
                workerCount++;
            }
        }
        
        if (workerCount == 0) {
            return 0.0;
        }
        
        return (double) totalHours / workerCount;
    }
    
    /**
     * Get all workers in this department
     * @return list of Worker objects in the department
     */
    public List<Worker> getWorkers() {
        List<Worker> workers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                workers.add((Worker) employee);
            }
        }
        return workers;
    }
    
    /**
     * Get all shift workers in this department
     * @return list of ShiftWorker objects in the department
     */
    public List<ShiftWorker> getShiftWorkers() {
        List<ShiftWorker> shiftWorkers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                shiftWorkers.add((ShiftWorker) employee);
            }
        }
        return shiftWorkers;
    }
}

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
    
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    
    public void addDepartment(Department department) {
        departments.add(department);
    }
    
    public void removeDepartment(Department department) {
        departments.remove(department);
    }
    
    /**
     * Calculate the total salary of all employees in the company
     * Total salary = sum(workers' salary + sales people's salary + managers' salary)
     * @return the total salary amount for all employees
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;
        for (Employee employee : employees) {
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }
    
    /**
     * Find average working hours per week for all workers in the delivery department
     * @return average working hours, or 0 if there are no workers in the delivery department
     */
    public double calculateAverageDeliveryDepartmentWorkingHours() {
        Department deliveryDepartment = findDepartmentByType(DepartmentType.DELIVERY);
        if (deliveryDepartment == null) {
            return 0.0;
        }
        return deliveryDepartment.calculateAverageWorkerWorkingHours();
    }
    
    /**
     * Determine the total commission amount for all salespeople in the company
     * @return sum(amountOfSales * commissionPercentage) for all salespeople
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
     * Calculate total holiday premiums paid to all shift workers in the company
     * @return total holiday premium amount, or 0 if there are no shift workers
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
    
    /**
     * Find a department by its type
     * @param type the department type to search for
     * @return the department with the specified type, or null if not found
     */
    private Department findDepartmentByType(DepartmentType type) {
        for (Department department : departments) {
            if (department.getType() == type) {
                return department;
            }
        }
        return null;
    }
    
    /**
     * Get all managers in the company
     * @return list of Manager objects
     */
    public List<Manager> getManagers() {
        List<Manager> managers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                managers.add((Manager) employee);
            }
        }
        return managers;
    }
}