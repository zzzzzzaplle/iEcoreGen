import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
     * Calculates the salary for this employee based on their specific type
     * @return The calculated salary for this employee
     */
    public abstract double calculateSalary();
}

class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;

    public Manager() {
        this.subordinates = new ArrayList<>();
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

    /**
     * Calculates the manager's salary (fixed salary)
     * @return The fixed salary of the manager
     */
    @Override
    public double calculateSalary() {
        return salary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager
     * @return The count of direct subordinate employees
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
     * Calculates the salesperson's salary (fixed salary plus commission)
     * @return The total salary including commission
     */
    @Override
    public double calculateSalary() {
        return salary + getTotalCommission();
    }

    /**
     * Calculates the commission amount for this salesperson
     * @return The commission amount (amountOfSales * commissionPercentage)
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
     * Calculates the base salary for workers (weeklyWorkingHour * hourlyRates)
     * @return The base salary without any premiums
     */
    @Override
    public double calculateSalary() {
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
     * Calculates the shift worker's salary including holiday premium
     * @return Total salary (base salary + holiday premium)
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + calculateHolidayPremium();
    }

    /**
     * Calculates the holiday premium for this shift worker
     * @return The holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

class OffShiftWorker extends Worker {
    public OffShiftWorker() {
    }

    /**
     * Calculates the off-shift worker's salary (base salary only)
     * @return The base salary without any premiums
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary();
    }
}

enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;

    public Department() {
        this.employees = new ArrayList<>();
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

    /**
     * Calculates the average working hours per week for all workers in this department
     * @return The average working hours, or 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        List<Worker> workers = getWorkersInDepartment();
        if (workers.isEmpty()) {
            return 0.0;
        }
        
        int totalHours = 0;
        for (Worker worker : workers) {
            totalHours += worker.getWeeklyWorkingHour();
        }
        
        return (double) totalHours / workers.size();
    }

    /**
     * Gets all workers in this department
     * @return List of workers in the department
     */
    private List<Worker> getWorkersInDepartment() {
        List<Worker> workers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                workers.add((Worker) employee);
            }
        }
        return workers;
    }
}

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

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return The sum of all employees' salaries
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;
        for (Employee employee : employees) {
            totalSalary += employee.calculateSalary();
        }
        return totalSalary;
    }

    /**
     * Calculates the total commission amount for all salespeople in the company
     * @return The sum of commission amounts for all salespeople
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
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return The sum of holiday premiums for all shift workers, or 0 if there are no shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasShiftWorkers = false;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker && "DELIVERY".equals(employee.getDepartment())) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.calculateHolidayPremium();
                hasShiftWorkers = true;
            }
        }
        
        return hasShiftWorkers ? totalPremiums : 0.0;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return The average working hours, or 0 if there are no workers in the delivery department
     */
    public double calculateAverageDeliveryDepartmentWorkingHours() {
        Department deliveryDepartment = findDepartmentByType(DepartmentType.DELIVERY);
        if (deliveryDepartment != null) {
            return deliveryDepartment.calculateAverageWorkerWorkingHours();
        }
        return 0.0;
    }

    /**
     * Finds a department by its type
     * @param type The department type to search for
     * @return The department with the specified type, or null if not found
     */
    private Department findDepartmentByType(DepartmentType type) {
        for (Department department : departments) {
            if (department.getType() == type) {
                return department;
            }
        }
        return null;
    }
}