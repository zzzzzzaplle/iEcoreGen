import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that manages employees and vehicles for food delivery.
 */
 class Company {

    /** Name of the company */
    private String name;

    /** List of employees working for the company */
    private List<Employee> employees = new ArrayList<>();

    /** List of vehicles owned or rented by the company */
    private List<Vehicle> vehicles = new ArrayList<>();

    /** No‑argument constructor */
    public Company() {
        // default constructor
    }

    /** Constructor with name */
    public Company(String name) {
        this.name = name;
    }

    /* ==================== Getters & Setters ==================== */

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

    /* ==================== Business Methods ==================== */

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the number of employees whose {@link Employee#getType()} is {@link EmployeeType#FULL_TIME}
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
     * Retrieves the registration numbers of all vehicles that are driven by part‑time employees.
     *
     * @return a {@link List} of registration numbers; empty if no such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e != null
                    && EmployeeType.PART_TIME.equals(e.getType())
                    && e.getDrivenVehicle() != null) {
                result.add(e.getDrivenVehicle().getRegistrationNumber());
            }
        }
        return result;
    }

    /**
     * Counts the number of rented vehicles belonging to this company.
     *
     * @return the number of vehicles that are instances of {@link RentedVehicle}
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
     * Finds the names of all employees who are currently driving a vehicle.
     *
     * @return a {@link List} of employee names; empty if no employee is currently driving a vehicle
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

    /* ==================== Helper Methods ==================== */

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add; ignored if null
     */
    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }

    /**
     * Adds a vehicle to the company.
     *
     * @param vehicle the vehicle to add; ignored if null
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }
}

/**
 * Enumeration of possible employee types.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents an employee of a food‑delivery company.
 */
class Employee {

    /** Employee name */
    private String name;

    /** Employee type (full‑time or part‑time) */
    private EmployeeType type;

    /** Vehicle currently driven by this employee (may be null) */
    private Vehicle drivenVehicle;

    /** No‑argument constructor */
    public Employee() {
        // default constructor
    }

    /** Constructor with name and type */
    public Employee(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    /* ==================== Getters & Setters ==================== */

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
     * Also updates the vehicle's {@code currentDriver} reference.
     *
     * @param drivenVehicle the vehicle to assign; may be null to unassign
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        // detach old vehicle if any
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
 * Abstract base class for vehicles used by the delivery system.
 */
abstract class Vehicle {

    /** Registration number of the vehicle */
    private String registrationNumber;

    /** Employee currently driving this vehicle (may be null) */
    private Employee currentDriver;

    /** No‑argument constructor */
    protected Vehicle() {
        // default constructor
    }

    /** Constructor with registration number */
    protected Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /* ==================== Getters & Setters ==================== */

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
     * Also updates the employee's {@code drivenVehicle} reference to keep both sides consistent.
     *
     * @param currentDriver the employee who will drive this vehicle; may be null to unassign
     */
    public void setCurrentDriver(Employee currentDriver) {
        // detach previous driver if any
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
 * Represents a vehicle owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** No‑argument constructor */
    public OwnedVehicle() {
        super();
    }

    /** Constructor with registration number */
    public OwnedVehicle(String registrationNumber) {
        super(registrationNumber);
    }
}

/**
 * Represents a vehicle rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** No‑argument constructor */
    public RentedVehicle() {
        super();
    }

    /** Constructor with registration number */
    public RentedVehicle(String registrationNumber) {
        super(registrationNumber);
    }
}