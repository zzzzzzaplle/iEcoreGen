import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Online Food Delivery System company
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
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public List<Vehicle> getVehicles() {
        return vehicles;
    }
    
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
    
    /**
     * Counts the number of full-time employees in the company
     * @return the number of full-time employees
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
     * Finds the registration numbers of all vehicles driven by part-time employees
     * @return list of registration numbers of vehicles driven by part-time employees, empty list if no vehicles
     */
    public List<String> getVehiclesDrivenByPartTimeEmployees() {
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
     * @return the number of rented vehicles
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
     * Finds the names of all employees who are currently driving a vehicle
     * @return list of names of employees currently driving vehicles, empty list if no employee driving a vehicle
     */
    public List<String> getEmployeesDrivingVehicles() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee driver = vehicle.getDriver();
            if (driver != null) {
                driverNames.add(driver.getName());
            }
        }
        return driverNames;
    }
}

/**
 * Represents an employee in the food delivery system
 */
class Employee {
    private String name;
    private EmployeeType type;
    
    /**
     * Default constructor
     */
    public Employee() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public EmployeeType getType() {
        return type;
    }
    
    public void setType(EmployeeType type) {
        this.type = type;
    }
}

/**
 * Represents a vehicle in the food delivery system
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
    
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    
    public VehicleType getType() {
        return type;
    }
    
    public void setType(VehicleType type) {
        this.type = type;
    }
    
    public Employee getDriver() {
        return driver;
    }
    
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