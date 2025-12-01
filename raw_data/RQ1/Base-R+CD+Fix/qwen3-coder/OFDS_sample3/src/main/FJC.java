import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company in the Online Food Delivery System.
 * A company has a name, employees, and vehicles for food delivery.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Constructs a new Company with empty lists of employees and vehicles.
     */
    public Company() {
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
     * Counts the number of full-time employees in the company.
     *
     * @return the count of full-time employees
     */
    public int getFullTimeEmployeeCount() {
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     *
     * @return a list of registration numbers of vehicles driven by part-time employees,
     *         or an empty list if no such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee driver = vehicle.getCurrentDriver();
            if (driver != null && driver.getType() == EmployeeType.PART_TIME) {
                registrationNumbers.add(vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     *
     * @return the count of rented vehicles
     */
    public int getRentedVehicleCount() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof RentedVehicle) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company.
     *
     * @return a list of names of employees who are currently driving a vehicle,
     *         or an empty list if no employee is driving a vehicle
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDrivenVehicle() != null) {
                driverNames.add(employee.getName());
            }
        }
        return driverNames;
    }
}

/**
 * Enum representing the type of an employee.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents an employee in the Online Food Delivery System.
 * An employee can be either part-time or full-time and may drive a vehicle.
 */
class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle drivenVehicle;

    /**
     * Constructs a new Employee with no assigned vehicle.
     */
    public Employee() {
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
     * Gets the type of the employee (PART_TIME or FULL_TIME).
     *
     * @return the type of the employee
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     *
     * @param type the type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the vehicle driven by the employee.
     *
     * @return the driven vehicle, or null if not driving any vehicle
     */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Sets the vehicle driven by the employee.
     *
     * @param drivenVehicle the vehicle to set
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        this.drivenVehicle = drivenVehicle;
    }
}

/**
 * Abstract class representing a vehicle in the Online Food Delivery System.
 * A vehicle has a registration number and may have a current driver.
 */
abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver;

    /**
     * Constructs a new Vehicle with no current driver.
     */
    public Vehicle() {
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
     * Gets the current driver of the vehicle.
     *
     * @return the current driver, or null if no driver is assigned
     */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Sets the current driver of the vehicle.
     *
     * @param currentDriver the driver to set
     */
    public void setCurrentDriver(Employee currentDriver) {
        this.currentDriver = currentDriver;
    }
}

/**
 * Represents an owned vehicle in the Online Food Delivery System.
 * This is a concrete implementation of the Vehicle class.
 */
class OwnedVehicle extends Vehicle {
    /**
     * Constructs a new OwnedVehicle.
     */
    public OwnedVehicle() {
        super();
    }
}

/**
 * Represents a rented vehicle in the Online Food Delivery System.
 * This is a concrete implementation of the Vehicle class.
 */
class RentedVehicle extends Vehicle {
    /**
     * Constructs a new RentedVehicle.
     */
    public RentedVehicle() {
        super();
    }
}