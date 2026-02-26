import java.util.ArrayList;
import java.util.List;

/**
 * Represents an employee in the food delivery system.
 */
class Employee {
    private String name;
    private String type; // "part-time" or "full-time"
    private Vehicle assignedVehicle;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
        this.name = "";
        this.type = "";
        this.assignedVehicle = null;
    }

    /**
     * Gets the name of the employee.
     *
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the employee.
     *
     * @return the type of the employee ("part-time" or "full-time")
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     *
     * @param type the type to set ("part-time" or "full-time")
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the vehicle assigned to the employee.
     *
     * @return the assigned vehicle
     */
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    /**
     * Sets the vehicle assigned to the employee.
     *
     * @param assignedVehicle the vehicle to assign
     */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    /**
     * Checks if the employee is driving a vehicle.
     *
     * @return true if the employee is driving a vehicle, false otherwise
     */
    public boolean isDriving() {
        return assignedVehicle != null;
    }
}

/**
 * Represents a vehicle in the food delivery system.
 */
class Vehicle {
    private String registrationNumber;
    private String type; // "owned" or "rented"
    private Employee driver;

    /**
     * Default constructor for Vehicle.
     */
    public Vehicle() {
        this.registrationNumber = "";
        this.type = "";
        this.driver = null;
    }

    /**
     * Gets the registration number of the vehicle.
     *
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle.
     *
     * @param registrationNumber the registration number to set
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the type of the vehicle.
     *
     * @return the type of the vehicle ("owned" or "rented")
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the vehicle.
     *
     * @param type the type to set ("owned" or "rented")
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the driver of the vehicle.
     *
     * @return the driver employee
     */
    public Employee getDriver() {
        return driver;
    }

    /**
     * Sets the driver of the vehicle.
     *
     * @param driver the employee to set as driver
     */
    public void setDriver(Employee driver) {
        this.driver = driver;
    }
}

/**
 * Represents a company in the online food delivery system.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     *
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the company.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles in the company.
     *
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles in the company.
     *
     * @param vehicles the list of vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle the vehicle to add
     */
    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    /**
     * Counts the number of full-time employees in the company.
     *
     * @return the count of full-time employees
     */
    public int countFullTimeEmployees() {
        int count = 0;
        for (Employee employee : employees) {
            if ("full-time".equals(employee.getType())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     *
     * @return a list of registration numbers of vehicles driven by part-time employees
     */
    public List<String> getVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if ("part-time".equals(employee.getType()) && employee.isDriving()) {
                registrationNumbers.add(employee.getAssignedVehicle().getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     *
     * @return the count of rented vehicles
     */
    public int countRentedVehicles() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if ("rented".equals(vehicle.getType())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company.
     *
     * @return a list of names of employees who are driving a vehicle
     */
    public List<String> getDrivingEmployees() {
        List<String> drivingEmployeeNames = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isDriving()) {
                drivingEmployeeNames.add(employee.getName());
            }
        }
        return drivingEmployeeNames;
    }
}