import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a food‑delivery company.
 */
 class Company {

    /** Name of the company. */
    private String name;

    /** Employees that work for the company. */
    private List<Employee> employees = new ArrayList<>();

    /** Vehicles owned or rented by the company. */
    private List<Vehicle> vehicles = new ArrayList<>();

    /** Default constructor. */
    public Company() {
    }

    /** Constructor with name. */
    public Company(String name) {
        this.name = name;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Functional requirement methods
    // -------------------------------------------------------------------------

    /**
     * Counts the number of full‑time employees belonging to this company.
     *
     * @return the count of employees whose {@link Employee#getType()} is
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
     * Retrieves the registration numbers of all vehicles that are driven by
     * part‑time employees of this company.
     *
     * @return a list of registration numbers; the list is empty when no such
     *         vehicles exist
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
     * Counts the number of rented vehicles owned by this company.
     *
     * @return the number of {@link RentedVehicle} instances contained in the
     *         company's vehicle list
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
     * Retrieves the names of all employees who are currently assigned as drivers
     * to a vehicle in this company.
     *
     * @return a list of employee names; empty when no vehicle has a driver
     */
    public List<String> getCurrentDriversNames() {
        List<String> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v != null && v.getCurrentDriver() != null) {
                result.add(v.getCurrentDriver().getName());
            }
        }
        return result;
    }

    // -------------------------------------------------------------------------
    // Convenience methods for managing collections
    // -------------------------------------------------------------------------

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add; ignored if {@code null}
     */
    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle the vehicle to add; ignored if {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
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
 * Represents an employee of a delivery company.
 */
class Employee {

    /** Employee's full name. */
    private String name;

    /** Employment type (part‑time or full‑time). */
    private EmployeeType type;

    /** Vehicle currently driven by the employee; may be {@code null}. */
    private Vehicle drivenVehicle;

    /** Default constructor. */
    public Employee() {
    }

    /** Constructor with all fields (except drivenVehicle which can be set later). */
    public Employee(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
     * Assigns a vehicle to this employee as the driven vehicle.
     * Also updates the vehicle's {@code currentDriver} reference to this employee.
     *
     * @param drivenVehicle the vehicle to assign; may be {@code null}
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        // Break old association if present
        if (this.drivenVehicle != null) {
            this.drivenVehicle.setCurrentDriver(null);
        }

        this.drivenVehicle = drivenVehicle;

        // Establish new bidirectional link
        if (drivenVehicle != null && drivenVehicle.getCurrentDriver() != this) {
            drivenVehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract base class for vehicles used by the delivery company.
 */
abstract class Vehicle {

    /** Registration number of the vehicle (unique identifier). */
    private String registrationNumber;

    /** Employee currently driving the vehicle; may be {@code null}. */
    private Employee currentDriver;

    /** Default constructor. */
    public Vehicle() {
    }

    /** Constructor with registration number. */
    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    // -------------------------------------------------------------------------
    // Getters and Setters
    // -------------------------------------------------------------------------

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
     * Sets the current driver of this vehicle.
     * Also updates the driver’s {@code drivenVehicle} reference to this vehicle.
     *
     * @param currentDriver the employee to set as driver; may be {@code null}
     */
    public void setCurrentDriver(Employee currentDriver) {
        // Break old association if present
        if (this.currentDriver != null) {
            this.currentDriver.setDrivenVehicle(null);
        }

        this.currentDriver = currentDriver;

        // Establish new bidirectional link
        if (currentDriver != null && currentDriver.getDrivenVehicle() != this) {
            currentDriver.setDrivenVehicle(this);
        }
    }
}

/**
 * Represents a vehicle owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** Default constructor. */
    public OwnedVehicle() {
        super();
    }

    /** Constructor with registration number. */
    public OwnedVehicle(String registrationNumber) {
        super(registrationNumber);
    }
}

/**
 * Represents a vehicle rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** Default constructor. */
    public RentedVehicle() {
        super();
    }

    /** Constructor with registration number. */
    public RentedVehicle(String registrationNumber) {
        super(registrationNumber);
    }
}