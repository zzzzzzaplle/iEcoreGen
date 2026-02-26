import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a company that owns employees and vehicles for food delivery.
 */
 class Company {

    /** The name of the company. */
    private String name;

    /** List of employees hired by the company. */
    private List<Employee> employees;

    /** List of vehicles owned or rented by the company. */
    private List<Vehicle> vehicles;

    /** Default constructor required for frameworks / testing. */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /** Getter for the company name. */
    public String getName() {
        return name;
    }

    /** Setter for the company name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the list of employees. */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** Setter for the list of employees. */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /** Getter for the list of vehicles. */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /** Setter for the list of vehicles. */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the number of employees whose {@link Employee#getType()} is {@link EmployeeType#FULL_TIME}
     */
    public int getFullTimeEmployeeCount() {
        if (employees == null) {
            return 0;
        }
        int count = 0;
        for (Employee e : employees) {
            if (e != null && EmployeeType.FULL_TIME.equals(e.getType())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles that are driven by part‑time employees.
     *
     * @return a list of registration numbers; the list may be empty if no such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        if (employees == null) {
            return Collections.emptyList();
        }
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
     * @return the number of {@link RentedVehicle} instances in the company's vehicle list
     */
    public int getRentedVehicleCount() {
        if (vehicles == null) {
            return 0;
        }
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
     * @return a list containing the names of drivers; the list may be empty if no vehicle has a driver
     */
    public List<String> getCurrentDriversNames() {
        if (vehicles == null) {
            return Collections.emptyList();
        }
        List<String> driverNames = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v != null && v.getCurrentDriver() != null) {
                driverNames.add(v.getCurrentDriver().getName());
            }
        }
        return driverNames;
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
 * Represents an employee that may be assigned to drive a vehicle.
 */
class Employee {

    /** Employee's full name. */
    private String name;

    /** Type of the employee (part‑time or full‑time). */
    private EmployeeType type;

    /** Vehicle currently driven by the employee; may be {@code null}. */
    private Vehicle drivenVehicle;

    /** Default constructor required for frameworks / testing. */
    public Employee() {
        this.name = "";
        this.type = null;
        this.drivenVehicle = null;
    }

    /** Getter for employee name. */
    public String getName() {
        return name;
    }

    /** Setter for employee name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for employee type. */
    public EmployeeType getType() {
        return type;
    }

    /** Setter for employee type. */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** Getter for the vehicle currently driven by this employee. */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Sets the vehicle driven by this employee and updates the vehicle's {@code currentDriver}
     * reference accordingly to keep the bidirectional association consistent.
     *
     * @param drivenVehicle the vehicle to assign; may be {@code null}
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        // Break existing association if present
        if (this.drivenVehicle != null) {
            this.drivenVehicle.setCurrentDriver(null);
        }

        this.drivenVehicle = drivenVehicle;

        // Establish new association
        if (drivenVehicle != null) {
            drivenVehicle.setCurrentDriver(this);
        }
    }
}

/**
 * Abstract base class for all vehicles used by the company.
 */
abstract class Vehicle {

    /** Unique registration number of the vehicle. */
    private String registrationNumber;

    /** Employee currently driving the vehicle; may be {@code null}. */
    private Employee currentDriver;

    /** Default constructor required for frameworks / testing. */
    public Vehicle() {
        this.registrationNumber = "";
        this.currentDriver = null;
    }

    /** Getter for registration number. */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** Setter for registration number. */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** Getter for the current driver. */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Sets the current driver of the vehicle and updates the driver's {@code drivenVehicle}
     * reference to keep the bidirectional association consistent.
     *
     * @param currentDriver the employee who will drive this vehicle; may be {@code null}
     */
    public void setCurrentDriver(Employee currentDriver) {
        // Break existing association if present
        if (this.currentDriver != null && this.currentDriver.getDrivenVehicle() == this) {
            this.currentDriver.setDrivenVehicle(null);
        }

        this.currentDriver = currentDriver;

        // Establish new association
        if (currentDriver != null && currentDriver.getDrivenVehicle() != this) {
            currentDriver.setDrivenVehicle(this);
        }
    }
}

/**
 * Represents a vehicle owned outright by the company.
 */
class OwnedVehicle extends Vehicle {

    /** Default constructor required for frameworks / testing. */
    public OwnedVehicle() {
        super();
    }

    // Additional owned‑vehicle‑specific attributes could be added here.
}

/**
 * Represents a vehicle that the company rents.
 */
class RentedVehicle extends Vehicle {

    /** Default constructor required for frameworks / testing. */
    public RentedVehicle() {
        super();
    }

    // Additional rented‑vehicle‑specific attributes could be added here.
}