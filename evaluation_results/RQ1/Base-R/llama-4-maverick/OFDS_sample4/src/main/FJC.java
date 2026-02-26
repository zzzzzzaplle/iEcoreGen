import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * @return the name of the company
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
     * Gets the list of employees in the company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles managed by the company.
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles managed by the company.
     * @param vehicles the list of vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return the count of full-time employees
     */
    public int countFullTimeEmployees() {
        return (int) employees.stream()
                .filter(employee -> employee instanceof FullTimeEmployee)
                .count();
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     * @return a list of registration numbers
     */
    public List<String> getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getDriver() instanceof PartTimeEmployee)
                .map(Vehicle::getRegistrationNumber)
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return the count of rented vehicles
     */
    public int countRentedVehicles() {
        return (int) vehicles.stream()
                .filter(vehicle -> vehicle instanceof RentedVehicle)
                .count();
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company.
     * @return a list of employee names
     */
    public List<String> getNamesOfEmployeesDrivingAVehicle() {
        return vehicles.stream()
                .map(Vehicle::getDriver)
                .filter(driver -> driver != null)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}

/**
 * Represents an employee in the Online Food Delivery System.
 */
abstract class Employee {
    private String name;

    /**
     * Unparameterized constructor for Employee.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return the name of the employee
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
}

/**
 * Represents a full-time employee in the Online Food Delivery System.
 */
class FullTimeEmployee extends Employee {
    /**
     * Unparameterized constructor for FullTimeEmployee.
     */
    public FullTimeEmployee() {
        super();
    }
}

/**
 * Represents a part-time employee in the Online Food Delivery System.
 */
class PartTimeEmployee extends Employee {
    /**
     * Unparameterized constructor for PartTimeEmployee.
     */
    public PartTimeEmployee() {
        super();
    }
}

/**
 * Represents a vehicle in the Online Food Delivery System.
 */
abstract class Vehicle {
    private String registrationNumber;
    private Employee driver;

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
     * Gets the driver of the vehicle.
     * @return the driver
     */
    public Employee getDriver() {
        return driver;
    }

    /**
     * Sets the driver of the vehicle.
     * @param driver the driver to set
     */
    public void setDriver(Employee driver) {
        this.driver = driver;
    }
}

/**
 * Represents an owned vehicle in the Online Food Delivery System.
 */
class OwnedVehicle extends Vehicle {
    /**
     * Unparameterized constructor for OwnedVehicle.
     */
    public OwnedVehicle() {
        super();
    }
}

/**
 * Represents a rented vehicle in the Online Food Delivery System.
 */
class RentedVehicle extends Vehicle {
    /**
     * Unparameterized constructor for RentedVehicle.
     */
    public RentedVehicle() {
        super();
    }
}