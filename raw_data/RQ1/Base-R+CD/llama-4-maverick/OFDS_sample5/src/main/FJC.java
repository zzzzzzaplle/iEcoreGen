import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum representing the type of an employee.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents a vehicle in the Online Food Delivery System.
 */
abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver;

    /**
     * Unparameterized constructor for Vehicle.
     */
    public Vehicle() {}

    /**
     * Gets the registration number of the vehicle.
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle.
     * @param registrationNumber the registration number to set
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the current driver of the vehicle.
     * @return the current driver
     */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Sets the current driver of the vehicle.
     * @param currentDriver the current driver to set
     */
    public void setCurrentDriver(Employee currentDriver) {
        this.currentDriver = currentDriver;
    }
}

/**
 * Represents an owned vehicle in the Online Food Delivery System.
 */
class OwnedVehicle extends Vehicle {
    /**
     * Unparameterized constructor for OwnedVehicle.
     */
    public OwnedVehicle() {}
}

/**
 * Represents a rented vehicle in the Online Food Delivery System.
 */
class RentedVehicle extends Vehicle {
    /**
     * Unparameterized constructor for RentedVehicle.
     */
    public RentedVehicle() {}
}

/**
 * Represents an employee in the Online Food Delivery System.
 */
class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle drivenVehicle;

    /**
     * Unparameterized constructor for Employee.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the employee.
     * @return the type
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     * @param type the type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the vehicle driven by the employee.
     * @return the driven vehicle
     */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Sets the vehicle driven by the employee.
     * @param drivenVehicle the driven vehicle to set
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        this.drivenVehicle = drivenVehicle;
    }
}

/**
 * Represents a company in the Online Food Delivery System.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Unparameterized constructor for Company.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the employees of the company.
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the employees of the company.
     * @param employees the employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the vehicles of the company.
     * @return the vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the vehicles of the company.
     * @param vehicles the vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return the count of full-time employees
     */
    public int getFullTimeEmployeeCount() {
        return (int) employees.stream().filter(employee -> employee.getType() == EmployeeType.FULL_TIME).count();
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     * @return a list of registration numbers
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        return employees.stream()
                .filter(employee -> employee.getType() == EmployeeType.PART_TIME && employee.getDrivenVehicle() != null)
                .map(employee -> employee.getDrivenVehicle().getRegistrationNumber())
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return the count of rented vehicles
     */
    public int getRentedVehicleCount() {
        return (int) vehicles.stream().filter(vehicle -> vehicle instanceof RentedVehicle).count();
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company.
     * @return a list of employee names
     */
    public List<String> getCurrentDriversNames() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentDriver() != null)
                .map(vehicle -> vehicle.getCurrentDriver().getName())
                .collect(Collectors.toList());
    }
}