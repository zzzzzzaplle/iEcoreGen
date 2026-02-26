import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a food‑delivery company.
 */
 class Company {

    /** Name of the company. */
    private String name;

    /** Employees that work for the company. */
    private List<Employee> employees;

    /** Vehicles owned or rented by the company. */
    private List<Vehicle> vehicles;

    /** No‑argument constructor required for test instantiation. */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /* -----------------------------------------------------------
     *  Getters and Setters
     * ----------------------------------------------------------- */

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

    /* -----------------------------------------------------------
     *  Helper methods for managing collections (optional but useful)
     * ----------------------------------------------------------- */

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        this.employees.add(employee);
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle the vehicle to add; must not be {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle cannot be null");
        this.vehicles.add(vehicle);
    }

    /* -----------------------------------------------------------
     *  Functional requirements
     * ----------------------------------------------------------- */

    /**
     * Counts the number of full‑time employees working for this company.
     *
     * @return the number of employees whose {@code type} is {@link EmployeeType#FULL_TIME}
     */
    public int getFullTimeEmployeeCount() {
        int count = 0;
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the registration numbers of all vehicles that are driven by
     * part‑time employees of this company.
     *
     * @return a list of registration numbers; the list is empty if no such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.PART_TIME && e.getDrivenVehicle() != null) {
                registrationNumbers.add(e.getDrivenVehicle().getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles owned by this company.
     *
     * @return the number of {@link RentedVehicle} instances in the company's vehicle list
     */
    public int getRentedVehicleCount() {
        int count = 0;
        for (Vehicle v : vehicles) {
            if (v instanceof RentedVehicle) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the names of all employees who are currently driving a vehicle.
     *
     * @return a list of employee names; the list is empty if no employee is driving a vehicle
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getDrivenVehicle() != null) {
                driverNames.add(e.getName());
            }
        }
        return driverNames;
    }
}

/**
 * Enumeration of possible employee employment types.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents an employee of a food‑delivery company.
 */
class Employee {

    /** Employee's name. */
    private String name;

    /** Employment type (part‑time or full‑time). */
    private EmployeeType type;

    /** Vehicle currently driven by the employee (may be {@code null}). */
    private Vehicle drivenVehicle;

    /** No‑argument constructor required for test instantiation. */
    public Employee() {
        // fields left null/empty intentionally
    }

    /* -----------------------------------------------------------
     *  Getters and Setters
     * ----------------------------------------------------------- */

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

    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    public void setDrivenVehicle(Vehicle drivenVehicle) {
        this.drivenVehicle = drivenVehicle;
        // Keep the bidirectional relationship consistent
        if (drivenVehicle != null) {
            drivenVehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract base class for vehicles used by the delivery system.
 */
abstract class Vehicle {

    /** Unique registration number of the vehicle. */
    private String registrationNumber;

    /** Employee currently driving this vehicle (may be {@code null}). */
    private Employee currentDriver;

    /** No‑argument constructor required for test instantiation. */
    public Vehicle() {
        // fields left null/empty intentionally
    }

    /* -----------------------------------------------------------
     *  Getters and Setters
     * ----------------------------------------------------------- */

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Employee getCurrentDriver() {
        return currentDriver;
    }

    public void setCurrentDriver(Employee currentDriver) {
        this.currentDriver = currentDriver;
        // Keep the bidirectional relationship consistent
        if (currentDriver != null && currentDriver.getDrivenVehicle() != this) {
            currentDriver.setDrivenVehicle(this);
        }
    }
}

/**
 * Represents a vehicle owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** No‑argument constructor required for test instantiation. */
    public OwnedVehicle() {
        super();
    }

    // No additional fields or behaviour required for the current domain.
}

/**
 * Represents a vehicle rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** No‑argument constructor required for test instantiation. */
    public RentedVehicle() {
        super();
    }

    // No additional fields or behaviour required for the current domain.
}