import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that manages employees and vehicles for food delivery.
 */
 class Company {

    /** The name of the company. */
    private String name;

    /** The list of employees working for the company. */
    private List<Employee> employees;

    /** The list of vehicles owned or rented by the company. */
    private List<Vehicle> vehicles;

    /**
     * Creates a new {@code Company} instance with empty employee and vehicle lists.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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

    // -----------------------------------------------------------------------
    // Business methods (Functional Requirements)
    // -----------------------------------------------------------------------

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the number of employees whose type is {@link EmployeeType#FULL_TIME}
     */
    public int getFullTimeEmployeeCount() {
        int count = 0;
        for (Employee e : employees) {
            if (e != null && EmployeeType.FULL_TIME.equals(e.getType())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the registration numbers of all vehicles that are driven by
     * part‑time employees in this company.
     *
     * @return a {@link List} of registration numbers; the list is empty if no
     *         such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrations = new ArrayList<>();
        for (Employee e : employees) {
            if (e != null
                    && EmployeeType.PART_TIME.equals(e.getType())
                    && e.getDrivenVehicle() != null) {
                registrations.add(e.getDrivenVehicle().getRegistrationNumber());
            }
        }
        return registrations;
    }

    /**
     * Counts the number of rented vehicles owned by this company.
     *
     * @return the number of {@link RentedVehicle} instances in the vehicle list
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
     * Retrieves the names of all employees who are currently driving a vehicle
     * in this company.
     *
     * @return a {@link List} of employee names; the list is empty if no employee
     *         is currently driving a vehicle
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Employee driver = v.getCurrentDriver();
            if (driver != null && driver.getName() != null) {
                driverNames.add(driver.getName());
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
 * Represents an employee of a company.
 */
class Employee {

    /** The employee's name. */
    private String name;

    /** The employee's type (part‑time or full‑time). */
    private EmployeeType type;

    /**
     * The vehicle that this employee is currently driving (may be {@code null}).
     * This reference is kept in sync with {@link Vehicle#getCurrentDriver()}
     * when both sides are set via the provided setters.
     */
    private Vehicle drivenVehicle;

    /**
     * Creates a new {@code Employee} instance.
     */
    public Employee() {
        // default constructor
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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
     * Assigns a vehicle to this employee as the driven vehicle. Also updates the
     * vehicle's {@code currentDriver} reference to point back to this employee.
     *
     * @param drivenVehicle the vehicle to assign; may be {@code null} to indicate
     *                      that the employee is not driving any vehicle
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        // Break previous association if any
        if (this.drivenVehicle != null) {
            this.drivenVehicle.setCurrentDriver(null);
        }
        this.drivenVehicle = drivenVehicle;
        if (drivenVehicle != null) {
            drivenVehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract representation of a vehicle used for food delivery.
 */
abstract class Vehicle {

    /** The registration number of the vehicle. */
    private String registrationNumber;

    /**
     * The employee currently driving this vehicle (may be {@code null}).
     * This reference is kept in sync with {@link Employee#getDrivenVehicle()}
     * when both sides are set via the provided setters.
     */
    private Employee currentDriver;

    /**
     * Creates a new {@code Vehicle} instance.
     */
    public Vehicle() {
        // default constructor
    }

    // -----------------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------------

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
     * Sets the current driver of this vehicle. Also updates the driver's
     * {@code drivenVehicle} reference to point back to this vehicle.
     *
     * @param currentDriver the employee who will drive the vehicle; may be
     *                      {@code null} to indicate no driver
     */
    public void setCurrentDriver(Employee currentDriver) {
        // Break previous association if any
        if (this.currentDriver != null && this.currentDriver.getDrivenVehicle() == this) {
            this.currentDriver.setDrivenVehicle(null);
        }
        this.currentDriver = currentDriver;
        if (currentDriver != null && currentDriver.getDrivenVehicle() != this) {
            currentDriver.setDrivenVehicle(this);
        }
    }
}

/**
 * Represents a vehicle that is owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /**
     * Creates a new {@code OwnedVehicle} instance.
     */
    public OwnedVehicle() {
        super();
    }
}

/**
 * Represents a vehicle that is rented by the company.
 */
class RentedVehicle extends Vehicle {

    /**
     * Creates a new {@code RentedVehicle} instance.
     */
    public RentedVehicle() {
        super();
    }
}