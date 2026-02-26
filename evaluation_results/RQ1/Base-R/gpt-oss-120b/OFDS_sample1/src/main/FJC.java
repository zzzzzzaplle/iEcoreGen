import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that manages employees and vehicles for food delivery.
 */
 class Company {

    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /** 
     * Creates an empty {@code Company} instance.
     * The internal collections are initialised to empty {@code ArrayList}s.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /* -------------------------------------------------
     *  Getters and Setters
     * ------------------------------------------------- */

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

    /* -------------------------------------------------
     *  Helper methods for managing the domain model
     * ------------------------------------------------- */

    /**
     * Adds an {@code Employee} to the company.
     *
     * @param employee the employee to add; must not be {@code null}
     */
    public void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "employee must not be null");
        this.employees.add(employee);
    }

    /**
     * Adds a {@code Vehicle} to the company.
     *
     * @param vehicle the vehicle to add; must not be {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        Objects.requireNonNull(vehicle, "vehicle must not be null");
        this.vehicles.add(vehicle);
    }

    /**
     * Assigns a driver to a vehicle. The relationship is kept bidirectional:
     * the employee's {@code assignedVehicle} reference and the vehicle's
     * {@code driver} reference are both updated.
     *
     * @param employee the employee who will become the driver; must not be {@code null}
     * @param vehicle  the vehicle to be driven; must not be {@code null}
     * @throws IllegalArgumentException if the employee already drives another vehicle
     *                                  or the vehicle already has a driver.
     */
    public void assignDriver(Employee employee, Vehicle vehicle) {
        Objects.requireNonNull(employee, "employee must not be null");
        Objects.requireNonNull(vehicle, "vehicle must not be null");

        if (employee.getAssignedVehicle() != null) {
            throw new IllegalArgumentException("Employee already assigned to a vehicle");
        }
        if (vehicle.getDriver() != null) {
            throw new IllegalArgumentException("Vehicle already has a driver");
        }

        employee.setAssignedVehicle(vehicle);
        vehicle.setDriver(employee);
    }

    /* -------------------------------------------------
     *  Functional requirements
     * ------------------------------------------------- */

    /**
     * Counts how many full‑time employees work for this company.
     *
     * @return the number of employees whose type is {@link EmployeeType#FULL_TIME}
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
     * Retrieves the registration numbers of all vehicles that are currently driven
     * by part‑time employees of this company.
     *
     * @return a {@link List} of registration numbers; the list is empty if no such
     *         vehicles exist.
     */
    public List<String> getVehicleRegistrationsDrivenByPartTimeEmployees() {
        List<String> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.PART_TIME && e.getAssignedVehicle() != null) {
                result.add(e.getAssignedVehicle().getRegistrationNumber());
            }
        }
        return result;
    }

    /**
     * Counts the number of rented vehicles owned (or managed) by this company.
     *
     * @return the number of vehicles whose type is {@link VehicleType#RENTED}
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
     * @return a {@link List} of employee names; the list is empty if no employee
     *         is driving a vehicle.
     */
    public List<String> getNamesOfEmployeesDrivingVehicles() {
        List<String> result = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getAssignedVehicle() != null) {
                result.add(e.getName());
            }
        }
        return result;
    }
}

/**
 * Represents an employee of a delivery company.
 */
class Employee {

    private String name;
    private EmployeeType type;
    private Vehicle assignedVehicle; // null if not driving any vehicle

    /** Creates an empty {@code Employee} instance. */
    public Employee() {
        // No‑arg constructor required by the specification
    }

    /* -------------------------------------------------
     *  Getters and Setters
     * ------------------------------------------------- */

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

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}

/**
 * Represents a vehicle used for food delivery.
 */
class Vehicle {

    private String registrationNumber;
    private VehicleType type;
    private Employee driver; // null if no driver assigned

    /** Creates an empty {@code Vehicle} instance. */
    public Vehicle() {
        // No‑arg constructor required by the specification
    }

    /* -------------------------------------------------
     *  Getters and Setters
     * ------------------------------------------------- */

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

    public void setDriver(Employee driver) {
        this.driver = driver;
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
 * Enumeration of possible vehicle ownership types.
 */
enum VehicleType {
    OWNED,
    RENTED
}