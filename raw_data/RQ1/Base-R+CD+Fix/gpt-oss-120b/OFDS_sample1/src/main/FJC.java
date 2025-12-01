import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a food‑delivery company.
 */
 class Company {

    /** Company name */
    private String name;

    /** Employees working for the company */
    private List<Employee> employees;

    /** Vehicles owned or rented by the company */
    private List<Vehicle> vehicles;

    /** Unparameterized constructor required for tests */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /** -------------------- Getters & Setters -------------------- */

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

    /** -------------------- Functional Requirements -------------------- */

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the number of employees whose {@code type} is {@link EmployeeType#FULL_TIME}
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
     * Returns the registration numbers of all vehicles that are driven by part‑time employees.
     * If no such vehicle exists an empty list is returned.
     *
     * @return a list of registration numbers (may be empty but never {@code null})
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
     * @return the number of {@link RentedVehicle} instances in the {@code vehicles} collection
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
     * Retrieves the names of all employees that are currently driving a vehicle.
     * If no employee is driving a vehicle an empty list is returned.
     *
     * @return a list of employee names (may be empty but never {@code null})
     */
    public List<String> getCurrentDriversNames() {
        List<String> drivers = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Employee driver = v.getCurrentDriver();
            if (driver != null && driver.getName() != null) {
                drivers.add(driver.getName());
            }
        }
        return drivers;
    }
}

/**
 * Enumeration for employee types.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents an employee of a company.
 */
class Employee {

    /** Employee name */
    private String name;

    /** Employee type (full‑time / part‑time) */
    private EmployeeType type;

    /** Vehicle currently driven by the employee (may be {@code null}) */
    private Vehicle drivenVehicle;

    /** Unparameterized constructor required for tests */
    public Employee() {
        this.name = "";
        this.type = EmployeeType.PART_TIME;
        this.drivenVehicle = null;
    }

    /** -------------------- Getters & Setters -------------------- */

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
        // Keep the bidirectional link consistent if a vehicle is supplied
        if (drivenVehicle != null && drivenVehicle.getCurrentDriver() != this) {
            drivenVehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract representation of a vehicle.
 */
abstract class Vehicle {

    /** Registration number of the vehicle */
    private String registrationNumber;

    /** Employee currently driving the vehicle (may be {@code null}) */
    private Employee currentDriver;

    /** Unparameterized constructor required for tests */
    public Vehicle() {
        this.registrationNumber = "";
        this.currentDriver = null;
    }

    /** -------------------- Getters & Setters -------------------- */

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
        // Keep the bidirectional link consistent if an employee is supplied
        if (currentDriver != null && currentDriver.getDrivenVehicle() != this) {
            currentDriver.setDrivenVehicle(this);
        }
    }
}

/**
 * A vehicle owned by the company.
 */
class OwnedVehicle extends Vehicle {

    /** Unparameterized constructor required for tests */
    public OwnedVehicle() {
        super();
    }
}

/**
 * A vehicle rented by the company.
 */
class RentedVehicle extends Vehicle {

    /** Unparameterized constructor required for tests */
    public RentedVehicle() {
        super();
    }
}