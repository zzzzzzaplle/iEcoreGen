import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Employee() {}

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

    public Manager() {}

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

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @param employees List of all employees in the company.
     * @return Number of direct subordinate employees.
     */
    public int getDirectSubordinateEmployeesCount(List<Employee> employees) {
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(this.getDepartment()) && !(employee instanceof Manager)) {
                count++;
            }
        }
        return count;
    }
}

class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    public SalesPeople() {}

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
     * @return Total commission amount.
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage;
    }
}

abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    public Worker() {}

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

    public ShiftWorker() {}

    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for the shift worker.
     * @return Holiday premium amount.
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

class OffShiftWorker extends Worker {
    public OffShiftWorker() {}
}

class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees = new ArrayList<>();

    public Department() {}

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

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Calculates the average working hours per week for all workers in the delivery department.
     * @return Average working hours per week.
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0;
        }

        int totalHours = 0;
        int workerCount = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                totalHours += ((Worker) employee).getWeeklyWorkingHour();
                workerCount++;
            }
        }

        return workerCount == 0 ? 0 : (double) totalHours / workerCount;
    }
}

class Company {
    private List<Employee> employees = new ArrayList<>();
    private List<Department> departments = new ArrayList<>();

    public Company() {}

    public List<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return Total salary of all employees.
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                double workerSalary = worker.getWeeklyWorkingHour() * worker.getHourlyRates();
                if (employee instanceof ShiftWorker) {
                    workerSalary += ((ShiftWorker) employee).calculateHolidayPremium();
                }
                totalSalary += workerSalary;
            } else if (employee instanceof SalesPeople) {
                totalSalary += ((SalesPeople) employee).getSalary() + ((SalesPeople) employee).getTotalCommission();
            } else if (employee instanceof Manager) {
                totalSalary += ((Manager) employee).getSalary();
            }
        }

        return totalSalary;
    }

    /**
     * Calculates the total commission amount for all salespeople in the company.
     * @return Total commission amount.
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0;

        for (Employee employee : employees) {
            if (employee instanceof SalesPeople) {
                totalCommission += ((SalesPeople) employee).getTotalCommission();
            }
        }

        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return Total holiday premiums.
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalPremiums = 0;

        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                totalPremiums += ((ShiftWorker) employee).calculateHolidayPremium();
            }
        }

        return totalPremiums;
    }
}