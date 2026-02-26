import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the Online Food Delivery System.
 * Employees can be either part-time or full-time.
 */
class Employee {
    private String name;
    private boolean isFullTime;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
    }

    /**
     * Gets the name of the employee.
     * @return the employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the employee's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the employee is full-time.
     * @return true if the employee is full-time, false otherwise
     */
    public boolean isFullTime() {
        return isFullTime;
    }

    /**
     * Sets the employment type of the employee.
     * @param isFullTime true for full-time, false for part-time
     */
    public void setFullTime(boolean isFullTime) {
        this.isFullTime = isFullTime;
    }
}

/**
 * Represents a vehicle in the Online Food Delivery System.
 * Vehicles can be either owned or rented.
 */
class Vehicle {
    private String registrationNumber;
    private boolean isRented;
    private Employee driver;

    /**
     * Default constructor for Vehicle.
     */
    public Vehicle() {
    }

    /**
     * Gets the registration number of the vehicle.
     * @return the vehicle's registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle.
     * @param registrationNumber the vehicle's registration number
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Checks if the vehicle is rented.
     * @return true if the vehicle is rented, false if owned
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the rental status of the vehicle.
     * @param isRented true for rented, false for owned
     */
    public void setRented(boolean isRented) {
        this.isRented = isRented;
    }

    /**
     * Gets the driver assigned to this vehicle.
     * @return the assigned driver, or null if no driver is assigned
     */
    public Employee getDriver() {
        return driver;
    }

    /**
     * Assigns a driver to this vehicle.
     * @param driver the employee to assign as driver
     */
    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    /**
     * Checks if the vehicle currently has a driver assigned.
     * @return true if a driver is assigned, false otherwise
     */
    public boolean hasDriver() {
        return driver != null;
    }
}

/**
 * Represents a company in the Online Food Delivery System.
 * Companies have employees and vehicles for food delivery.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Default constructor for Company.
     * Initializes empty lists for employees and vehicles.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     * @return the company's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name the company's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees the list of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles in the company.
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles in the company.
     * @param vehicles the list of vehicles
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return the count of full-time employees
     */
    public int countFullTimeEmployees() {
        int count = 0;
        for (Employee employee : employees) {
            if (employee.isFullTime()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees.
     * @return a list of registration numbers, or an empty list if no vehicles are driven by part-time employees
     */
    public List<String> findVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.hasDriver() && !vehicle.getDriver().isFullTime()) {
                registrationNumbers.add(vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return the count of rented vehicles
     */
    public int countRentedVehicles() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isRented()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle.
     * @return a list of employee names, or an empty list if no employees are driving vehicles
     */
    public List<String> findEmployeesDrivingVehicles() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.hasDriver()) {
                driverNames.add(vehicle.getDriver().getName());
            }
        }
        return driverNames;
    }
}