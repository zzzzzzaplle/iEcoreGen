import java.util.*;
import java.util.stream.Collectors;

enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

abstract class Employee {
    private DepartmentType department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    public Employee() {
    }

    public DepartmentType getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentType department) {
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
     * Calculates the salary for this employee
     * @return The calculated salary amount
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

    public void addSubordinate(Employee employee) {
        this.subordinates.add(employee);
    }

    public void removeSubordinate(Employee employee) {
        this.subordinates.remove(employee);
    }

    /**
     * Calculates the manager's salary
     * @return The fixed salary amount for the manager
     */
    @Override
    public double calculateSalary() {
        return salary;
    }

    /**
     * Gets the number of direct subordinate employees for this manager
     * @return The count of direct subordinates
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
     * Calculates the salesperson's total salary including commission
     * @return The total salary (fixed salary + commission)
     */
    @Override
    public double calculateSalary() {
        return salary + getTotalCommission();
    }

    /**
     * Calculates the commission amount for this salesperson
     * @return The commission amount (sales amount * commission percentage)
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
     * Calculates the base salary for a worker
     * @return The base salary (weekly working hours * hourly rates)
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
     * Calculates the total salary including holiday premium
     * @return The total salary (base salary + holiday premium)
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
     * Calculates the salary for off-shift worker (base salary only)
     * @return The base salary
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary();
    }
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

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    /**
     * Calculates the average weekly working hours for all workers in this department
     * @return The average working hours per week, or 0 if no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        List<Worker> workers = employees.stream()
                .filter(e -> e instanceof Worker)
                .map(e -> (Worker) e)
                .collect(Collectors.toList());

        if (workers.isEmpty()) {
            return 0.0;
        }

        double totalHours = workers.stream()
                .mapToInt(Worker::getWeeklyWorkingHour)
                .sum();

        return totalHours / workers.size();
    }

    /**
     * Gets all workers in this department
     * @return List of workers in the department
     */
    public List<Worker> getWorkers() {
        return employees.stream()
                .filter(e -> e instanceof Worker)
                .map(e -> (Worker) e)
                .collect(Collectors.toList());
    }

    /**
     * Gets all shift workers in this department
     * @return List of shift workers in the department
     */
    public List<ShiftWorker> getShiftWorkers() {
        return employees.stream()
                .filter(e -> e instanceof ShiftWorker)
                .map(e -> (ShiftWorker) e)
                .collect(Collectors.toList());
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

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    public void removeDepartment(Department department) {
        this.departments.remove(department);
    }

    /**
     * Calculates the total salary of all employees in the company
     * @return The sum of all employees' salaries
     */
    public double calculateTotalEmployeeSalary() {
        return employees.stream()
                .mapToDouble(Employee::calculateSalary)
                .sum();
    }

    /**
     * Calculates the total commission amount for all salespeople in the company
     * @return The sum of all salespeople's commissions
     */
    public double calculateTotalSalesPeopleCommission() {
        return employees.stream()
                .filter(e -> e instanceof SalesPeople)
                .map(e -> (SalesPeople) e)
                .mapToDouble(SalesPeople::getTotalCommission)
                .sum();
    }

    /**
     * Calculates total holiday premiums paid to all shift workers in the company
     * @return The sum of all shift workers' holiday premiums, or 0 if no shift workers
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        List<ShiftWorker> shiftWorkers = employees.stream()
                .filter(e -> e instanceof ShiftWorker)
                .map(e -> (ShiftWorker) e)
                .collect(Collectors.toList());

        if (shiftWorkers.isEmpty()) {
            return 0.0;
        }

        return shiftWorkers.stream()
                .mapToDouble(ShiftWorker::calculateHolidayPremium)
                .sum();
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return The average working hours, or 0 if no workers in delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        Optional<Department> deliveryDept = departments.stream()
                .filter(d -> d.getType() == DepartmentType.DELIVERY)
                .findFirst();

        if (deliveryDept.isPresent()) {
            return deliveryDept.get().calculateAverageWorkerWorkingHours();
        }
        return 0.0;
    }

    /**
     * Gets all managers in the company
     * @return List of managers
     */
    public List<Manager> getManagers() {
        return employees.stream()
                .filter(e -> e instanceof Manager)
                .map(e -> (Manager) e)
                .collect(Collectors.toList());
    }

    /**
     * Gets all salespeople in the company
     * @return List of salespeople
     */
    public List<SalesPeople> getSalesPeople() {
        return employees.stream()
                .filter(e -> e instanceof SalesPeople)
                .map(e -> (SalesPeople) e)
                .collect(Collectors.toList());
    }

    /**
     * Gets all workers in the company
     * @return List of workers
     */
    public List<Worker> getWorkers() {
        return employees.stream()
                .filter(e -> e instanceof Worker)
                .map(e -> (Worker) e)
                .collect(Collectors.toList());
    }

    /**
     * Gets all shift workers in the company
     * @return List of shift workers
     */
    public List<ShiftWorker> getShiftWorkers() {
        return employees.stream()
                .filter(e -> e instanceof ShiftWorker)
                .map(e -> (ShiftWorker) e)
                .collect(Collectors.toList());
    }

    /**
     * Gets all off-shift workers in the company
     * @return List of off-shift workers
     */
    public List<OffShiftWorker> getOffShiftWorkers() {
        return employees.stream()
                .filter(e -> e instanceof OffShiftWorker)
                .map(e -> (OffShiftWorker) e)
                .collect(Collectors.toList());
    }
}