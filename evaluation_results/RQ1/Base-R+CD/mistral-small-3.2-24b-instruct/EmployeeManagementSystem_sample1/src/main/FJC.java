import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * Gets the number of direct subordinate employees for the manager.
     *
     * @return the number of direct subordinate employees
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
     * Calculates the total commission for the salesperson.
     *
     * @return the total commission amount
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
     * Calculates the holiday premium for the shift worker.
     *
     * @return the holiday premium amount
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

class OffShiftWorker extends Worker {
    public OffShiftWorker() {
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
     * Calculates the total salary of all employees in the company.
     *
     * @return the total salary of all employees
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalSalary += worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                if (employee instanceof ShiftWorker) {
                    ShiftWorker shiftWorker = (ShiftWorker) employee;
                    totalSalary += shiftWorker.calculateHolidayPremium();
                }
            } else if (employee instanceof SalesPeople) {
                SalesPeople salesPeople = (SalesPeople) employee;
                totalSalary += salesPeople.getSalary() + salesPeople.getTotalCommission();
            } else if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                totalSalary += manager.getSalary();
            }
        }
        return totalSalary;
    }

    /**
     * Calculates the total commission amount for all salespeople in the company.
     *
     * @return the total commission amount
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPeople) {
                SalesPeople salesPeople = (SalesPeople) employee;
                totalCommission += salesPeople.getTotalCommission();
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     *
     * @return the total holiday premiums
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalHolidayPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalHolidayPremiums += shiftWorker.calculateHolidayPremium();
            }
        }
        return totalHolidayPremiums;
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
     * Calculates the average working hours per week for all workers in the delivery department.
     *
     * @return the average working hours per week
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0;
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
        return workerCount == 0 ? 0 : (double) totalHours / workerCount;
    }
}