import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an employee in the Online Food Delivery System
 */
class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle assignedVehicle;

    /**
     * Default constructor
     */
    public Employee() {
    }

    /**
     * Gets the employee's name
     * @return the employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee's name
     * @param name the employee's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employee type
     * @return the employee type
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the employee type
     * @param type the employee type
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the vehicle assigned to this employee
     * @return the assigned vehicle, or null if no vehicle is assigned
     */
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    /**
     * Sets the vehicle assigned to this employee
     * @param assignedVehicle the vehicle to assign
     */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    /**
     * Checks if the employee is currently assigned to drive a vehicle
     * @return true if the employee is driving a vehicle, false otherwise
     */
    public boolean isDrivingVehicle() {
        return assignedVehicle != null;
    }

    /**
     * Checks if the employee is a full-time employee
     * @return true if the employee is full-time, false otherwise
     */
    public boolean isFullTime() {
        return type == EmployeeType.FULL_TIME;
    }

    /**
     * Checks if the employee is a part-time employee
     * @return true if the employee is part-time, false otherwise
     */
    public boolean isPartTime() {
        return type == EmployeeType.PART_TIME;
    }
}

/**
 * Represents the type of employment for an employee
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents a vehicle in the Online Food Delivery System
 */
class Vehicle {
    private String registrationNumber;
    private VehicleType type;
    private Employee driver;

    /**
     * Default constructor
     */
    public Vehicle() {
    }

    /**
     * Gets the vehicle's registration number
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the vehicle's registration number
     * @param registrationNumber the registration number
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the vehicle type
     * @return the vehicle type
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Sets the vehicle type
     * @param type the vehicle type
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * Gets the driver assigned to this vehicle
     * @return the assigned driver, or null if no driver is assigned
     */
    public Employee getDriver() {
        return driver;
    }

    /**
     * Sets the driver assigned to this vehicle
     * @param driver the driver to assign
     */
    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    /**
     * Checks if the vehicle is currently being driven by an employee
     * @return true if the vehicle has a driver assigned, false otherwise
     */
    public boolean isBeingDriven() {
        return driver != null;
    }

    /**
     * Checks if the vehicle is an owned vehicle
     * @return true if the vehicle is owned, false otherwise
     */
    public boolean isOwned() {
        return type == VehicleType.OWNED;
    }

    /**
     * Checks if the vehicle is a rented vehicle
     * @return true if the vehicle is rented, false otherwise
     */
    public boolean isRented() {
        return type == VehicleType.RENTED;
    }
}

/**
 * Represents the type of vehicle ownership
 */
enum VehicleType {
    OWNED,
    RENTED
}

/**
 * Represents a company in the Online Food Delivery System
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Default constructor
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Gets the company name
     * @return the company name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the company name
     * @param name the company name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees
     * @param employees the list of employees
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles
     * @param vehicles the list of vehicles
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Adds an employee to the company
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
        }
    }

    /**
     * Adds a vehicle to the company
     * @param vehicle the vehicle to add
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null && !vehicles.contains(vehicle)) {
            vehicles.add(vehicle);
        }
    }

    /**
     * Counts the number of full-time employees in the company
     * @return the count of full-time employees
     */
    public int countFullTimeEmployees() {
        return (int) employees.stream()
                .filter(Employee::isFullTime)
                .count();
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company
     * @return a list of registration numbers of vehicles driven by part-time employees, 
     *         returns empty list if no vehicles are found
     */
    public List<String> findVehiclesDrivenByPartTimeEmployees() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.isBeingDriven() && vehicle.getDriver().isPartTime())
                .map(Vehicle::getRegistrationNumber)
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of rented vehicles in the company
     * @return the count of rented vehicles
     */
    public int countRentedVehicles() {
        return (int) vehicles.stream()
                .filter(Vehicle::isRented)
                .count();
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company
     * @return a list of names of employees who are currently driving vehicles,
     *         returns empty list if no employees are driving vehicles
     */
    public List<String> findEmployeesDrivingVehicles() {
        return employees.stream()
                .filter(Employee::isDrivingVehicle)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}