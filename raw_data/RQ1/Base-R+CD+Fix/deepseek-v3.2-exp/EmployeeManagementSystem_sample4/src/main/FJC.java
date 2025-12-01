import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * Calculates the average working hours per week for all workers in this department
     * @return average working hours per week, or 0 if there are no workers in the department
     */
    public double calculateAverageWorkerWorkingHours() {
        if (employees == null || employees.isEmpty()) {
            return 0.0;
        }
        
        double totalHours = 0;
        int workerCount = 0;
        
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalHours += worker.getWeeklyWorkingHour();
                workerCount++;
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }
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
     * Abstract method to calculate the salary for this employee
     * @return the calculated salary for this employee
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

    /**
     * Gets the number of direct subordinate employees for this manager
     * @return the count of direct subordinate employees
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates != null ? subordinates.size() : 0;
    }

    /**
     * Calculates the salary for this manager (fixed salary)
     * @return the fixed salary of this manager
     */
    @Override
    public double calculateSalary() {
        return salary;
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
     * Calculates the total commission for this salesperson
     * @return the commission amount (amountOfSales * commissionPercentage)
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }

    /**
     * Calculates the total salary for this salesperson (salary + commission)
     * @return the total salary including commission
     */
    @Override
    public double calculateSalary() {
        return salary + getTotalCommission();
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
     * Calculates the base salary for this worker (weeklyWorkingHour * hourlyRates)
     * @return the base salary without any premiums
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
     * Calculates the holiday premium for this shift worker
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }

    /**
     * Calculates the total salary for this shift worker (base salary + holiday premium)
     * @return the total salary including holiday premium
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary() + calculateHolidayPremium();
    }
}

class OffShiftWorker extends Worker {
    public OffShiftWorker() {
    }

    /**
     * Calculates the salary for this off-shift worker (base salary only)
     * @return the base salary without any premiums
     */
    @Override
    public double calculateSalary() {
        return super.calculateSalary();
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

    /**
     * Calculates the total salary of all employees in the company
     * @return the sum of all employees' salaries
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
     * @return the sum of all salespeople's commissions
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
     * @return the sum of all shift workers' holiday premiums, or 0 if no shift workers exist
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0.0;
        boolean hasShiftWorker = false;
        
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalPremiums += shiftWorker.calculateHolidayPremium();
                hasShiftWorker = true;
            }
        }
        
        return hasShiftWorker ? totalPremiums : 0.0;
    }

    /**
     * Finds average working hours per week for all workers in the delivery department
     * @return average working hours per week, or 0 if there are no workers in the delivery department
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        double totalHours = 0;
        int workerCount = 0;
        
        for (Department department : departments) {
            if (department.getType() == DepartmentType.DELIVERY) {
                for (Employee employee : department.getEmployees()) {
                    if (employee instanceof Worker) {
                        Worker worker = (Worker) employee;
                        totalHours += worker.getWeeklyWorkingHour();
                        workerCount++;
                    }
                }
                break;
            }
        }
        
        return workerCount > 0 ? totalHours / workerCount : 0.0;
    }
}