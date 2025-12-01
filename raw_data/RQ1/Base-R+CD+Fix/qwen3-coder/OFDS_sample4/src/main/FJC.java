import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company in the Online Food Delivery System.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Default constructor for Company.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Getter for company name.
     *
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for company name.
     *
     * @param name the name to set for the company
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for employees list.
     *
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Setter for employees list.
     *
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Getter for vehicles list.
     *
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Setter for vehicles list.
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
     * Finds the registration numbers of all vehicles driven by part-time employees.
     *
     * @return a list of registration numbers of vehicles driven by part-time employees,
     *         or an empty list if none are found
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.PART_TIME && employee.getDrivenVehicle() != null) {
                registrationNumbers.add(employee.getDrivenVehicle().getRegistrationNumber());
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
     * Finds the names of all employees who are currently driving a vehicle.
     *
     * @return a list of names of employees driving vehicles, or an empty list if none are found
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
 */
class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle drivenVehicle;

    /**
     * Default constructor for Employee.
     */
    public Employee() {
    }

    /**
     * Getter for employee name.
     *
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for employee name.
     *
     * @param name the name to set for the employee
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for employee type.
     *
     * @return the type of the employee
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Setter for employee type.
     *
     * @param type the type to set for the employee
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Getter for the vehicle driven by the employee.
     *
     * @return the vehicle driven by the employee, or null if none
     */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Setter for the vehicle driven by the employee.
     *
     * @param drivenVehicle the vehicle to set as driven by the employee
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        this.drivenVehicle = drivenVehicle;
    }
}

/**
 * Abstract class representing a vehicle in the Online Food Delivery System.
 */
abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver;

    /**
     * Default constructor for Vehicle.
     */
    public Vehicle() {
    }

    /**
     * Getter for vehicle registration number.
     *
     * @return the registration number of the vehicle
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Setter for vehicle registration number.
     *
     * @param registrationNumber the registration number to set for the vehicle
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Getter for the current driver of the vehicle.
     *
     * @return the employee currently driving the vehicle, or null if none
     */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Setter for the current driver of the vehicle.
     *
     * @param currentDriver the employee to set as the current driver
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
     * Default constructor for OwnedVehicle.
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
     * Default constructor for RentedVehicle.
     */
    public RentedVehicle() {
        super();
    }
}