import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company in the Online Food Delivery System (OFDS)
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
     * @param name the company name to set
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
     * @param employees the list of employees to set
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
     * @param vehicles the list of vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company
     * @return the count of full-time employees
     */
    public int countFullTimeEmployees() {
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company
     * @return list of registration numbers of vehicles driven by part-time employees, empty list if no vehicles
     */
    public List<String> findVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee driver = vehicle.getDriver();
            if (driver != null && driver.getType() == EmployeeType.PART_TIME) {
                registrationNumbers.add(vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company
     * @return the count of rented vehicles
     */
    public int countRentedVehicles() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType() == VehicleType.RENTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company
     * @return list of names of employees currently driving vehicles, empty list if no employee driving a vehicle
     */
    public List<String> findEmployeesDrivingVehicles() {
        List<String> employeeNames = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee driver = vehicle.getDriver();
            if (driver != null) {
                employeeNames.add(driver.getName());
            }
        }
        return employeeNames;
    }
}

/**
 * Represents an employee in the Online Food Delivery System (OFDS)
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
     * Gets the employee name
     * @return the employee name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the employee name
     * @param name the employee name to set
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
     * @param type the employee type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the assigned vehicle
     * @return the assigned vehicle, null if no vehicle assigned
     */
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    /**
     * Sets the assigned vehicle
     * @param assignedVehicle the vehicle to assign to this employee
     */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}

/**
 * Represents a vehicle in the Online Food Delivery System (OFDS)
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
     * Gets the vehicle registration number
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the vehicle registration number
     * @param registrationNumber the registration number to set
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
     * @param type the vehicle type to set
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * Gets the driver assigned to this vehicle
     * @return the driver assigned to this vehicle, null if no driver assigned
     */
    public Employee getDriver() {
        return driver;
    }

    /**
     * Sets the driver for this vehicle
     * @param driver the driver to assign to this vehicle
     */
    public void setDriver(Employee driver) {
        this.driver = driver;
    }
}

/**
 * Enum representing the type of employee
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Enum representing the type of vehicle
 */
enum VehicleType {
    OWNED,
    RENTED
}