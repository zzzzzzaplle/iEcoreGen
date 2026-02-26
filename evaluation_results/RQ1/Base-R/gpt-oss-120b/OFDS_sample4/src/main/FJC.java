import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that manages employees and vehicles for food delivery.
 */
 class Company {

    /** The company's name. */
    private String name;

    /** Employees hired by the company. */
    private List<Employee> employees = new ArrayList<>();

    /** Vehicles owned or rented by the company. */
    private List<Vehicle> vehicles = new ArrayList<>();

    /** No‑argument constructor required for tests. */
    public Company() {
        // default constructor
    }

    /* ===================== Getters & Setters ===================== */

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

    /* ===================== Helper Methods ===================== */

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

    /* ===================== Functional Requirements ===================== */

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the count of employees whose type is {@link EmployeeType#FULL_TIME}
     */
    public int countFullTimeEmployees() {
        int count = 0;
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part‑time employees.
     * <p>
     * The method iterates over the company's employees, selects those that are
     * part‑time and currently assigned to a vehicle, and collects the vehicle's
     * registration numbers.
     *
     * @return an immutable list of registration numbers; empty if no such vehicles exist
     */
    public List<String> getVehicleRegistrationsDrivenByPartTimeEmployees() {
        List<String> registrations = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.PART_TIME && e.getVehicle() != null) {
                registrations.add(e.getVehicle().getRegistrationNumber());
            }
        }
        return Collections.unmodifiableList(registrations);
    }

    /**
     * Counts the number of rented vehicles owned by the company.
     *
     * @return the count of vehicles whose type is {@link VehicleType#RENTED}
     */
    public int countRentedVehicles() {
        int count = 0;
        for (Vehicle v : vehicles) {
            if (v.getType() == VehicleType.RENTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle.
     *
     * @return an immutable list of employee names; empty if no employee drives a vehicle
     */
    public List<String> getNamesOfEmployeesDrivingVehicle() {
        List<String> names = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getVehicle() != null) {
                names.add(e.getName());
            }
        }
        return Collections.unmodifiableList(names);
    }
}

/**
 * Represents an employee of a food‑delivery company.
 */
class Employee {

    /** Employee's name. */
    private String name;

    /** Type of employment (full‑time or part‑time). */
    private EmployeeType type;

    /** Vehicle currently driven by this employee; may be {@code null}. */
    private Vehicle vehicle;

    /** No‑argument constructor required for tests. */
    public Employee() {
        // default constructor
    }

    /* ===================== Getters & Setters ===================== */

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

    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Assigns a vehicle to this employee. The method also updates the vehicle's
     * driver reference to keep the bidirectional relationship consistent.
     *
     * @param vehicle the vehicle to assign; may be {@code null} to unassign
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        if (vehicle != null && vehicle.getDriver() != this) {
            vehicle.setDriver(this);
        }
    }
}

/**
 * Represents a vehicle used for food delivery.
 */
class Vehicle {

    /** Registration number of the vehicle (e.g., license plate). */
    private String registrationNumber;

    /** Type of vehicle (owned or rented). */
    private VehicleType type;

    /** Employee currently driving this vehicle; may be {@code null}. */
    private Employee driver;

    /** No‑argument constructor required for tests. */
    public Vehicle() {
        // default constructor
    }

    /* ===================== Getters & Setters ===================== */

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

    /**
     * Assigns a driver to this vehicle. The method also updates the driver's
     * vehicle reference to keep the bidirectional relationship consistent.
     *
     * @param driver the employee who will drive the vehicle; may be {@code null} to unassign
     */
    public void setDriver(Employee driver) {
        this.driver = driver;
        if (driver != null && driver.getVehicle() != this) {
            driver.setVehicle(this);
        }
    }
}

/**
 * Enumeration of possible employee employment types.
 */
enum EmployeeType {
    /** Full‑time employee. */
    FULL_TIME,
    /** Part‑time employee. */
    PART_TIME
}

/**
 * Enumeration of possible vehicle ownership types.
 */
enum VehicleType {
    /** Vehicle owned by the company. */
    OWNED,
    /** Vehicle rented by the company. */
    RENTED
}