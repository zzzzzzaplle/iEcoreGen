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

    /** Employees hired by the company. */
    private List<Employee> employees;

    /** Vehicles owned or rented by the company. */
    private List<Vehicle> vehicles;

    /** Creates a new {@code Company} with empty employee and vehicle lists. */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /* --------------------------------------------------------------------- */
    /* Getters and setters                                                   */
    /* --------------------------------------------------------------------- */

    /** @return the company name */
    public String getName() {
        return name;
    }

    /** @param name the company name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return mutable list of employees (modifications affect the company) */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to replace the current list */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /** @return mutable list of vehicles (modifications affect the company) */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /** @param vehicles the list of vehicles to replace the current list */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
    }

    /* --------------------------------------------------------------------- */
    /* Business methods (functional requirements)                           */
    /* --------------------------------------------------------------------- */

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the count of employees whose {@code type} is {@link EmployeeType#FULL_TIME}
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
     * Returns the registration numbers of all vehicles that are driven by
     * part‑time employees in this company.
     *
     * @return a list of registration numbers; empty list if none are found
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e != null
                    && e.getType() == EmployeeType.PART_TIME
                    && e.getDrivenVehicle() != null) {
                result.add(e.getDrivenVehicle().getRegistrationNumber());
            }
        }
        return result;
    }

    /**
     * Counts the number of rented vehicles belonging to this company.
     *
     * @return the count of {@link RentedVehicle} instances in the vehicle list
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
     * Retrieves the names of all employees that are currently driving a vehicle
     * in this company.
     *
     * @return a list of employee names; empty list if no employee is driving a vehicle
     */
    public List<String> getCurrentDriversNames() {
        List<String> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Employee driver = v.getCurrentDriver();
            if (driver != null && driver.getName() != null) {
                result.add(driver.getName());
            }
        }
        return result;
    }

    /* --------------------------------------------------------------------- */
    /* Helper methods for managing bi‑directional associations                */
    /* --------------------------------------------------------------------- */

    /**
     * Adds an employee to the company. If the employee already belongs to
     * another company, no check is performed (the model does not keep a back‑reference).
     *
     * @param employee employee to add; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee must not be null");
        employees.add(employee);
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle vehicle to add; must not be {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle must not be null");
        vehicles.add(vehicle);
    }
}

/**
 * Types of employees in the system.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents an employee of a company.
 */
class Employee {

    /** Employee's name. */
    private String name;

    /** Type of the employee (full‑time / part‑time). */
    private EmployeeType type;

    /** Vehicle that the employee drives, if any. */
    private Vehicle drivenVehicle;

    /** Creates a new {@code Employee} with no initial values. */
    public Employee() {
        // empty constructor
    }

    /* --------------------------------------------------------------------- */
    /* Getters and setters                                                   */
    /* --------------------------------------------------------------------- */

    /** @return employee name */
    public String getName() {
        return name;
    }

    /** @param name employee name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return employee type */
    public EmployeeType getType() {
        return type;
    }

    /** @param type employee type to set */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** @return vehicle driven by this employee, or {@code null} if none */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Assigns a vehicle to this employee as the driven vehicle.
     * The method also sets the vehicle's {@code currentDriver} reference to this employee,
     * keeping the bi‑directional association consistent.
     *
     * @param vehicle vehicle to assign; may be {@code null} to unassign
     */
    public void setDrivenVehicle(Vehicle vehicle) {
        // Break previous association, if any
        if (this.drivenVehicle != null) {
            this.drivenVehicle.setCurrentDriver(null);
        }

        this.drivenVehicle = vehicle;

        if (vehicle != null && vehicle.getCurrentDriver() != this) {
            vehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract representation of a vehicle used for deliveries.
 */
abstract class Vehicle {

    /** Registration number of the vehicle. */
    private String registrationNumber;

    /** Employee currently driving the vehicle, if any. */
    private Employee currentDriver;

    /** Creates a new {@code Vehicle} without initial values. */
    public Vehicle() {
        // empty constructor
    }

    /* --------------------------------------------------------------------- */
    /* Getters and setters                                                   */
    /* --------------------------------------------------------------------- */

    /** @return registration number */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** @param registrationNumber registration number to set */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** @return current driver, or {@code null} if none */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Assigns a driver to this vehicle.
     * The method also updates the driver’s {@code drivenVehicle} reference,
     * keeping the bi‑directional association consistent.
     *
     * @param driver employee to assign as driver; may be {@code null} to unassign
     */
    public void setCurrentDriver(Employee driver) {
        // Break previous association, if any
        if (this.currentDriver != null) {
            this.currentDriver.setDrivenVehicle(null);
        }

        this.currentDriver = driver;

        if (driver != null && driver.getDrivenVehicle() != this) {
            driver.setDrivenVehicle(this);
        }
    }
}

/**
 * Vehicle that is owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** Creates a new owned vehicle with default values. */
    public OwnedVehicle() {
        // empty constructor
    }
}

/**
 * Vehicle that is rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** Creates a new rented vehicle with default values. */
    public RentedVehicle() {
        // empty constructor
    }
}