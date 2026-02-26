import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a food‑delivery company. It maintains collections of employees
 * and vehicles and provides several query operations required by the
 * functional specifications.
 */
 class Company {

    /** Name of the company. */
    private String name;

    /** Employees that work for the company. */
    private List<Employee> employees = new ArrayList<>();

    /** Vehicles owned or rented by the company. */
    private List<Vehicle> vehicles = new ArrayList<>();

    /** No‑argument constructor required by the task. */
    public Company() {
    }

    /** Constructor with name (convenient for client code). */
    public Company(String name) {
        this.name = name;
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

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
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
    }

    /* --------------------------------------------------------------------- */
    /* Functional requirement methods                                         */
    /* --------------------------------------------------------------------- */

    /**
     * Counts the number of full‑time employees that belong to this company.
     *
     * @return the number of employees whose {@link Employee#getType()} is
     *         {@link EmployeeType#FULL_TIME}
     */
    public int getFullTimeEmployeeCount() {
        int count = 0;
        for (Employee e : employees) {
            if (e != null && e.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the registration numbers of all vehicles driven by part‑time
     * employees in this company.
     *
     * @return an immutable list of registration numbers; the list is empty if
     *         no part‑time employee drives a vehicle
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrations = new ArrayList<>();
        for (Employee e : employees) {
            if (e != null
                    && e.getType() == EmployeeType.PART_TIME
                    && e.getDrivenVehicle() != null) {
                registrations.add(e.getDrivenVehicle().getRegistrationNumber());
            }
        }
        return Collections.unmodifiableList(registrations);
    }

    /**
     * Counts the number of rented vehicles owned by the company.
     *
     * @return the number of {@link RentedVehicle} instances present in the
     *         company's vehicle collection
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
     * Retrieves the names of all employees who are currently driving a
     * vehicle in the company.
     *
     * @return an immutable list of employee names; the list is empty if no
     *         vehicle has a driver
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Employee driver = v.getCurrentDriver();
            if (driver != null && driver.getName() != null) {
                driverNames.add(driver.getName());
            }
        }
        return Collections.unmodifiableList(driverNames);
    }

    /* --------------------------------------------------------------------- */
    /* Helper methods (optional but convenient)                              */
    /* --------------------------------------------------------------------- */

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee must not be null");
        employees.add(employee);
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle the vehicle to add; must not be {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle must not be null");
        vehicles.add(vehicle);
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
 * Represents an employee that may be assigned to drive a vehicle.
 */
class Employee {

    /** Name of the employee. */
    private String name;

    /** Employment type (part‑time or full‑time). */
    private EmployeeType type;

    /** Vehicle currently driven by the employee; can be {@code null}. */
    private Vehicle drivenVehicle;

    /** No‑argument constructor required by the task. */
    public Employee() {
    }

    /** Convenience constructor. */
    public Employee(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

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

    /**
     * Assigns a vehicle to this employee as the one they drive.
     * The relationship is kept bi‑directional: the vehicle's
     * {@link Vehicle#setCurrentDriver(Employee)} method is also called.
     *
     * @param vehicle the vehicle to assign; may be {@code null} to remove
     *                the assignment
     */
    public void setDrivenVehicle(Vehicle vehicle) {
        // Break previous association, if any
        if (this.drivenVehicle != null && this.drivenVehicle.getCurrentDriver() == this) {
            this.drivenVehicle.setCurrentDriver(null);
        }
        this.drivenVehicle = vehicle;
        if (vehicle != null && vehicle.getCurrentDriver() != this) {
            vehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract base class for all vehicles.
 */
abstract class Vehicle {

    /** Registration number of the vehicle. */
    private String registrationNumber;

    /** Employee currently driving the vehicle; may be {@code null}. */
    private Employee currentDriver;

    /** No‑argument constructor required by the task. */
    public Vehicle() {
    }

    /** Convenience constructor. */
    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Assigns the driver of this vehicle.
     * The relationship is kept bi‑directional: the employee's
     * {@link Employee#setDrivenVehicle(Vehicle)} method is also called.
     *
     * @param driver the employee who will drive this vehicle; may be {@code null}
     *               to indicate that the vehicle has no driver
     */
    public void setCurrentDriver(Employee driver) {
        // Break previous association, if any
        if (this.currentDriver != null && this.currentDriver.getDrivenVehicle() == this) {
            this.currentDriver.setDrivenVehicle(null);
        }
        this.currentDriver = driver;
        if (driver != null && driver.getDrivenVehicle() != this) {
            driver.setDrivenVehicle(this);
        }
    }
}

/**
 * Represents a vehicle that is owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** No‑argument constructor required by the task. */
    public OwnedVehicle() {
        super();
    }

    /** Constructor with registration number. */
    public OwnedVehicle(String registrationNumber) {
        super(registrationNumber);
    }
}

/**
 * Represents a vehicle that is rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** No‑argument constructor required by the task. */
    public RentedVehicle() {
        super();
    }

    /** Constructor with registration number. */
    public RentedVehicle(String registrationNumber) {
        super(registrationNumber);
    }
}