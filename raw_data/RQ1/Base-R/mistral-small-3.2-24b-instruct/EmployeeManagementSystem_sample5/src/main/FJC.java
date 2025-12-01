import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an employee in the company.
 */
class Employee {
    private String name;
    private LocalDate dateOfBirth;
    private String socialInsuranceNumber;
    private Department department;

    /**
     * Constructs an empty Employee object.
     */
    public Employee() {
    }

    // Getters and Setters
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
 * Represents a worker in the company.
 */
class Worker extends Employee {
    private int weeklyWorkingHours;
    private double hourlyRate;
    private boolean isShiftWorker;

    /**
     * Constructs an empty Worker object.
     */
    public Worker() {
        super();
    }

    // Getters and Setters
    public int getWeeklyWorkingHours() {
        return weeklyWorkingHours;
    }

    public void setWeeklyWorkingHours(int weeklyWorkingHours) {
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

    /**
     * Calculates the salary of the worker.
     * @return The calculated salary.
     */
    public double calculateSalary() {
        double salary = weeklyWorkingHours * hourlyRate;
        if (isShiftWorker && getDepartment() == Department.DELIVERY) {
            salary += calculateHolidayPremiums();
        }
        return salary;
    }

    /**
     * Calculates the holiday premiums for the shift worker.
     * @return The calculated holiday premiums.
     */
    public double calculateHolidayPremiums() {
        // Assuming a fixed premium for simplicity
        return 100.0;
    }
}

/**
 * Represents a salesperson in the company.
 */
class SalesPerson extends Employee {
    private double fixedSalary;
    private double amountOfSales;
    private double commissionPercentage;

    /**
     * Constructs an empty SalesPerson object.
     */
    public SalesPerson() {
        super();
    }

    // Getters and Setters
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
     * Calculates the salary of the salesperson.
     * @return The calculated salary.
     */
    public double calculateSalary() {
        return fixedSalary + amountOfSales * (commissionPercentage / 100);
    }
}

/**
 * Represents a manager in the company.
 */
class Manager extends Employee {
    private String position;
    private List<Employee> subordinates;

    /**
     * Constructs an empty Manager object.
     */
    public Manager() {
        super();
        this.subordinates = new ArrayList<>();
    }

    // Getters and Setters
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
     * @param employee The employee to add as a subordinate.
     */
    public void addSubordinate(Employee employee) {
        subordinates.add(employee);
    }

    /**
     * Calculates the salary of the manager.
     * @return The calculated salary.
     */
    public double calculateSalary() {
        return 0; // Assuming fixed salary is set elsewhere
    }

    /**
     * Gets the number of direct subordinate employees for the manager.
     * @return The number of direct subordinate employees.
     */
    public int getNumberOfDirectSubordinates() {
        return subordinates.size();
    }
}

/**
 * Represents the company.
 */
class Company {
    private List<Employee> employees;

    /**
     * Constructs an empty Company object.
     */
    public Company() {
        this.employees = new ArrayList<>();
    }

    // Getters and Setters
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Adds an employee to the company.
     * @param employee The employee to add.
     */
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    /**
     * Calculates the total salary of all employees in the company.
     * @return The total salary.
     */
    public double calculateTotalSalary() {
        double totalSalary = 0;
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
     * Finds the average working hours per week for all workers in the delivery department.
     * @return The average working hours per week.
     */
    public double findAverageWorkingHoursInDeliveryDepartment() {
        List<Worker> deliveryWorkers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof Worker && ((Worker) employee).getDepartment() == Department.DELIVERY) {
                deliveryWorkers.add((Worker) employee);
            }
        }
        if (deliveryWorkers.isEmpty()) {
            return 0;
        }
        int totalHours = 0;
        for (Worker worker : deliveryWorkers) {
            totalHours += worker.getWeeklyWorkingHours();
        }
        return (double) totalHours / deliveryWorkers.size();
    }

    /**
     * Determines the total commission amount for all salespeople in the company.
     * @return The total commission amount.
     */
    public double calculateTotalCommission() {
        double totalCommission = 0;
        for (Employee employee : employees) {
            if (employee instanceof SalesPerson) {
                SalesPerson salesPerson = (SalesPerson) employee;
                totalCommission += salesPerson.getAmountOfSales() * (salesPerson.getCommissionPercentage() / 100);
            }
        }
        return totalCommission;
    }

    /**
     * Calculates the total holiday premiums paid to all shift workers in the company.
     * @return The total holiday premiums.
     */
    public double calculateTotalHolidayPremiums() {
        double totalPremiums = 0;
        for (Employee employee : employees) {
            if (employee instanceof Worker && ((Worker) employee).isShiftWorker()) {
                totalPremiums += ((Worker) employee).calculateHolidayPremiums();
            }
        }
        return totalPremiums;
    }

    /**
     * Gets the number of direct subordinate employees for each manager.
     * @return A map of managers to their number of direct subordinate employees.
     */
    public Map<Manager, Integer> getNumberOfDirectSubordinatesForEachManager() {
        Map<Manager, Integer> subordinateCount = new HashMap<>();
        for (Employee employee : employees) {
            if (employee instanceof Manager) {
                Manager manager = (Manager) employee;
                subordinateCount.put(manager, manager.getNumberOfDirectSubordinates());
            }
        }
        return subordinateCount;
    }
}

/**
 * Represents the departments in the company.
 */
enum Department {
    PRODUCTION,
    CONTROL,
    DELIVERY
}