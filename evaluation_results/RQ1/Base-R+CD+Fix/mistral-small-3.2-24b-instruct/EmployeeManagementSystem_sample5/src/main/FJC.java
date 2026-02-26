import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract class representing an Employee in the company.
 */
abstract class Employee {
    private String department;
    private String name;
    private Date birthDate;
    private String socialInsuranceNumber;

    /**
     * Default constructor.
     */
    public Employee() {
    }

    /**
     * Parameterized constructor.
     * @param department the department of the employee
     * @param name the name of the employee
     * @param birthDate the birth date of the employee
     * @param socialInsuranceNumber the social insurance number of the employee
     */
    public Employee(String department, String name, Date birthDate, String socialInsuranceNumber) {
        this.department = department;
        this.name = name;
        this.birthDate = birthDate;
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    // Getters and setters
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

/**
 * Class representing a Manager in the company.
 */
class Manager extends Employee {
    private double salary;
    private String position;
    private List<Employee> subordinates;

    /**
     * Default constructor.
     */
    public Manager() {
        subordinates = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     * @param department the department of the manager
     * @param name the name of the manager
     * @param birthDate the birth date of the manager
     * @param socialInsuranceNumber the social insurance number of the manager
     * @param salary the salary of the manager
     * @param position the position of the manager
     */
    public Manager(String department, String name, Date birthDate, String socialInsuranceNumber, double salary, String position) {
        super(department, name, birthDate, socialInsuranceNumber);
        this.salary = salary;
        this.position = position;
        subordinates = new ArrayList<>();
    }

    // Getters and setters
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
     * Adds a subordinate to the manager.
     * @param employee the employee to add as a subordinate
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Gets the number of direct subordinate employees.
     * @return the number of direct subordinate employees
     */
    public int getDirectSubordinateEmployeesCount() {
        return subordinates.size();
    }
}

/**
 * Class representing SalesPeople in the company.
 */
class SalesPeople extends Employee {
    private double salary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Default constructor.
     */
    public SalesPeople() {
    }

    /**
     * Parameterized constructor.
     * @param department the department of the sales person
     * @param name the name of the sales person
     * @param birthDate the birth date of the sales person
     * @param socialInsuranceNumber the social insurance number of the sales person
     * @param salary the salary of the sales person
     * @param amountOfSales the amount of sales made by the sales person
     * @param commissionPercentage the commission percentage of the sales person
     */
    public SalesPeople(String department, String name, Date birthDate, String socialInsuranceNumber, double salary, double amountOfSales, double commissionPercentage) {
        super(department, name, birthDate, socialInsuranceNumber);
        this.salary = salary;
        this.amountOfSales = amountOfSales;
        this.commissionPercentage = commissionPercentage;
    }

    // Getters and setters
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
     * Calculates the total commission for the sales person.
     * @return the total commission
     */
    public double getTotalCommission() {
        return amountOfSales * commissionPercentage / 100;
    }
}

/**
 * Abstract class representing a Worker in the company.
 */
abstract class Worker extends Employee {
    private int weeklyWorkingHour;
    private double hourlyRates;

    /**
     * Default constructor.
     */
    public Worker() {
    }

    /**
     * Parameterized constructor.
     * @param department the department of the worker
     * @param name the name of the worker
     * @param birthDate the birth date of the worker
     * @param socialInsuranceNumber the social insurance number of the worker
     * @param weeklyWorkingHour the weekly working hours of the worker
     * @param hourlyRates the hourly rates of the worker
     */
    public Worker(String department, String name, Date birthDate, String socialInsuranceNumber, int weeklyWorkingHour, double hourlyRates) {
        super(department, name, birthDate, socialInsuranceNumber);
        this.weeklyWorkingHour = weeklyWorkingHour;
        this.hourlyRates = hourlyRates;
    }

    // Getters and setters
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

/**
 * Class representing a ShiftWorker in the company.
 */
class ShiftWorker extends Worker {
    private double holidayPremium;

    /**
     * Default constructor.
     */
    public ShiftWorker() {
    }

    /**
     * Parameterized constructor.
     * @param department the department of the shift worker
     * @param name the name of the shift worker
     * @param birthDate the birth date of the shift worker
     * @param socialInsuranceNumber the social insurance number of the shift worker
     * @param weeklyWorkingHour the weekly working hours of the shift worker
     * @param hourlyRates the hourly rates of the shift worker
     * @param holidayPremium the holiday premium of the shift worker
     */
    public ShiftWorker(String department, String name, Date birthDate, String socialInsuranceNumber, int weeklyWorkingHour, double hourlyRates, double holidayPremium) {
        super(department, name, birthDate, socialInsuranceNumber, weeklyWorkingHour, hourlyRates);
        this.holidayPremium = holidayPremium;
    }

    // Getters and setters
    public double getHolidayPremium() {
        return holidayPremium;
    }

    public void setHolidayPremium(double holidayPremium) {
        this.holidayPremium = holidayPremium;
    }

    /**
     * Calculates the holiday premium for the shift worker.
     * @return the holiday premium
     */
    public double calculateHolidayPremium() {
        return holidayPremium;
    }
}

/**
 * Class representing an OffShiftWorker in the company.
 */
class OffShiftWorker extends Worker {
    /**
     * Default constructor.
     */
    public OffShiftWorker() {
    }

    /**
     * Parameterized constructor.
     * @param department the department of the off-shift worker
     * @param name the name of the off-shift worker
     * @param birthDate the birth date of the off-shift worker
     * @param socialInsuranceNumber the social insurance number of the off-shift worker
     * @param weeklyWorkingHour the weekly working hours of the off-shift worker
     * @param hourlyRates the hourly rates of the off-shift worker
     */
    public OffShiftWorker(String department, String name, Date birthDate, String socialInsuranceNumber, int weeklyWorkingHour, double hourlyRates) {
        super(department, name, birthDate, socialInsuranceNumber, weeklyWorkingHour, hourlyRates);
    }
}

/**
 * Class representing a Company.
 */
class Company {
    private List<Employee> employees;
    private List<Department> departments;

    /**
     * Default constructor.
     */
    public Company() {
        employees = new ArrayList<>();
        departments = new ArrayList<>();
    }

    // Getters and setters
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
     * Adds an employee to the company.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Adds a department to the company.
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        departments.add(department);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return the total salary
     */
    public double calculateTotalEmployeeSalary() {
        double totalSalary = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                double workerSalary = worker.getWeeklyWorkingHour() * worker.getHourlyRates();

                if (employee instanceof ShiftWorker) {
                    ShiftWorker shiftWorker = (ShiftWorker) employee;
                    workerSalary += shiftWorker.calculateHolidayPremium();
                }

                totalSalary += workerSalary;
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
     * @return the total commission amount
     */
    public double calculateTotalSalesPeopleCommission() {
        double totalCommission = 0.0;

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
     * @return the total holiday premiums
     */
    public double calculateTotalShiftWorkerHolidayPremiums() {
        double totalHolidayPremiums = 0.0;

        for (Employee employee : employees) {
            if (employee instanceof ShiftWorker) {
                ShiftWorker shiftWorker = (ShiftWorker) employee;
                totalHolidayPremiums += shiftWorker.calculateHolidayPremium();
            }
        }

        return totalHolidayPremiums;
    }
}

/**
 * Enum representing the types of departments in the company.
 */
enum DepartmentType {
    PRODUCTION,
    CONTROL,
    DELIVERY
}

/**
 * Class representing a Department in the company.
 */
class Department {
    private DepartmentType type;
    private Manager manager;
    private List<Employee> employees;

    /**
     * Default constructor.
     */
    public Department() {
        employees = new ArrayList<>();
    }

    /**
     * Parameterized constructor.
     * @param type the type of the department
     */
    public Department(DepartmentType type) {
        this.type = type;
        employees = new ArrayList<>();
    }

    // Getters and setters
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
     * Adds an employee to the department.
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Calculates the average working hours per week for all workers in the delivery department.
     * @return the average working hours per week
     */
    public double calculateAverageWorkerWorkingHours() {
        if (type != DepartmentType.DELIVERY) {
            return 0.0;
        }

        int totalWorkingHours = 0;
        int workerCount = 0;

        for (Employee employee : employees) {
            if (employee instanceof Worker) {
                Worker worker = (Worker) employee;
                totalWorkingHours += worker.getWeeklyWorkingHour();
                workerCount++;
            }
        }

        if (workerCount == 0) {
            return 0.0;
        }

        return (double) totalWorkingHours / workerCount;
    }
}