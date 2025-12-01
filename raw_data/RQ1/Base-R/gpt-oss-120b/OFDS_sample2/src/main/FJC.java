import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a company that manages employees and vehicles for food delivery.
 */
 class Company {

    private String name;
    private final List<Employee> employees = new ArrayList<>();
    private final List<Vehicle> vehicles = new ArrayList<>();

    /** Unparameterized constructor */
    public Company() {
    }

    /** Getter for the company name */
    public String getName() {
        return name;
    }

    /** Setter for the company name */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns an unmodifiable view of the employee list */
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    /** Returns an unmodifiable view of the vehicle list */
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee cannot be null");
        employees.add(employee);
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle the vehicle to add; must not be {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle cannot be null");
        vehicles.add(vehicle);
    }

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the number of {@link FullTimeEmployee} instances associated with the company
     */
    public int countFullTimeEmployees() {
        return (int) employees.stream()
                .filter(e -> e instanceof FullTimeEmployee)
                .count();
    }

    /**
     * Retrieves the registration numbers of all vehicles that are driven by part‑time employees.
     *
     * @return a list of registration numbers; the list is empty if no such vehicles exist
     */
    public List<String> getVehicleRegistrationsDrivenByPartTimeEmployees() {
        return vehicles.stream()
                .filter(v -> v.getDriver() instanceof PartTimeEmployee)
                .map(Vehicle::getRegistrationNumber)
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of rented vehicles owned by the company.
     *
     * @return the number of {@link RentedVehicle} instances in the company's fleet
     */
    public int countRentedVehicles() {
        return (int) vehicles.stream()
                .filter(v -> v instanceof RentedVehicle)
                .count();
    }

    /**
     * Finds the names of all employees who are currently assigned as drivers of a vehicle.
     *
     * @return a list containing the names of employees driving a vehicle; empty if none
     */
    public List<String> getNamesOfEmployeesDrivingVehicle() {
        return employees.stream()
                .filter(e -> e.getVehicle() != null)
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}

/**
 * Abstract base class for employees.
 */
abstract class Employee {

    private String name;
    private Vehicle vehicle; // vehicle this employee drives (may be null)

    /** Unparameterized constructor */
    public Employee() {
    }

    /** Getter for employee name */
    public String getName() {
        return name;
    }

    /** Setter for employee name */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the vehicle assigned to the employee */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Assigns a vehicle to this employee. Also updates the vehicle's driver reference.
     *
     * @param vehicle the vehicle to assign; may be {@code null} to unassign
     */
    public void setVehicle(Vehicle vehicle) {
        // detach from previous vehicle, if any
        if (this.vehicle != null) {
            this.vehicle.setDriver(null);
        }
        this.vehicle = vehicle;
        if (vehicle != null) {
            vehicle.setDriver(this);
        }
    }
}

/**
 * Represents a part‑time employee.
 */
class PartTimeEmployee extends Employee {

    /** Unparameterized constructor */
    public PartTimeEmployee() {
        super();
    }
}

/**
 * Represents a full‑time employee.
 */
class FullTimeEmployee extends Employee {

    /** Unparameterized constructor */
    public FullTimeEmployee() {
        super();
    }
}

/**
 * Abstract base class for vehicles.
 */
abstract class Vehicle {

    private String registrationNumber;
    private Employee driver; // employee currently driving this vehicle (may be null)

    /** Unparameterized constructor */
    public Vehicle() {
    }

    /** Getter for registration number */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** Setter for registration number */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** Getter for the driver of this vehicle */
    public Employee getDriver() {
        return driver;
    }

    /**
     * Assigns a driver to this vehicle. Also updates the employee's vehicle reference.
     *
     * @param driver the employee to set as driver; may be {@code null} to unassign
     */
    public void setDriver(Employee driver) {
        // detach from previous driver, if any
        if (this.driver != null && this.driver.getVehicle() == this) {
            this.driver.setVehicle(null);
        }
        this.driver = driver;
        if (driver != null && driver.getVehicle() != this) {
            driver.setVehicle(this);
        }
    }
}

/**
 * Represents a vehicle owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** Unparameterized constructor */
    public OwnedVehicle() {
        super();
    }
}

/**
 * Represents a vehicle rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** Unparameterized constructor */
    public RentedVehicle() {
        super();
    }
}