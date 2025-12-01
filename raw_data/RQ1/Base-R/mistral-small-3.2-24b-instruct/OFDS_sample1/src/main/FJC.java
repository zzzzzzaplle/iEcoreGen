import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the Online Food Delivery System.
 */
class Employee {
    private String name;
    private boolean isFullTime;

    /**
     * Constructs a new Employee with default values.
     */
    public Employee() {
        this.name = "";
        this.isFullTime = false;
    }

    /**
     * Constructs a new Employee with the specified name and employment type.
     * @param name The name of the employee.
     * @param isFullTime True if the employee is full-time, false otherwise.
     */
    public Employee(String name, boolean isFullTime) {
        this.name = name;
        this.isFullTime = isFullTime;
    }

    /**
     * Gets the name of the employee.
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if the employee is full-time.
     * @return True if the employee is full-time, false otherwise.
     */
    public boolean isFullTime() {
        return isFullTime;
    }

    /**
     * Sets the employment type of the employee.
     * @param fullTime True if the employee is full-time, false otherwise.
     */
    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }
}

/**
 * Represents a vehicle in the Online Food Delivery System.
 */
class Vehicle {
    private String registrationNumber;
    private boolean isRented;

    /**
     * Constructs a new Vehicle with default values.
     */
    public Vehicle() {
        this.registrationNumber = "";
        this.isRented = false;
    }

    /**
     * Constructs a new Vehicle with the specified registration number and ownership status.
     * @param registrationNumber The registration number of the vehicle.
     * @param isRented True if the vehicle is rented, false otherwise.
     */
    public Vehicle(String registrationNumber, boolean isRented) {
        this.registrationNumber = registrationNumber;
        this.isRented = isRented;
    }

    /**
     * Gets the registration number of the vehicle.
     * @return The registration number of the vehicle.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle.
     * @param registrationNumber The registration number to set.
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Checks if the vehicle is rented.
     * @return True if the vehicle is rented, false otherwise.
     */
    public boolean isRented() {
        return isRented;
    }

    /**
     * Sets the ownership status of the vehicle.
     * @param rented True if the vehicle is rented, false otherwise.
     */
    public void setRented(boolean rented) {
        isRented = rented;
    }
}

/**
 * Represents a company in the Online Food Delivery System.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;
    private List<Employee> drivers;

    /**
     * Constructs a new Company with default values.
     */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.drivers = new ArrayList<>();
    }

    /**
     * Constructs a new Company with the specified name.
     * @param name The name of the company.
     */
    public Company(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.drivers = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     * @return The name of the company.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the company.
     * @return The list of employees.
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees The list of employees to set.
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles in the company.
     * @return The list of vehicles.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles in the company.
     * @param vehicles The list of vehicles to set.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Gets the list of drivers in the company.
     * @return The list of drivers.
     */
    public List<Employee> getDrivers() {
        return drivers;
    }

    /**
     * Sets the list of drivers in the company.
     * @param drivers The list of drivers to set.
     */
    public void setDrivers(List<Employee> drivers) {
        this.drivers = drivers;
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return The number of full-time employees.
     */
    public int countFullTimeEmployees() {
        return (int) employees.stream().filter(Employee::isFullTime).count();
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     * @return A list of registration numbers of vehicles driven by part-time employees.
     */
    public List<String> findVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee driver : drivers) {
            if (!driver.isFullTime()) {
                for (Vehicle vehicle : vehicles) {
                    // Assuming there is a way to associate drivers with vehicles, this is a simplified approach
                    // In a real scenario, you might have a driver-vehicle assignment mechanism
                    registrationNumbers.add(vehicle.getRegistrationNumber());
                }
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return The number of rented vehicles.
     */
    public int countRentedVehicles() {
        return (int) vehicles.stream().filter(Vehicle::isRented).count();
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company.
     * @return A list of names of employees who are driving a vehicle.
     */
    public List<String> findEmployeesDrivingVehicles() {
        List<String> employeeNames = new ArrayList<>();
        for (Employee driver : drivers) {
            employeeNames.add(driver.getName());
        }
        return employeeNames;
    }
}