import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that owns employees and vehicles for food delivery.
 */
 class Company {

    /** The name of the company. */
    private String name;

    /** All employees that work for the company. */
    private List<Employee> employees = new ArrayList<>();

    /** All vehicles that belong to the company. */
    private List<Vehicle> vehicles = new ArrayList<>();

    /** No‑argument constructor required for tests. */
    public Company() {
    }

    /** Returns the company name. */
    public String getName() {
        return name;
    }

    /** Sets the company name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the mutable list of employees. */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** Sets the employee list (replaces current list). */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /** Returns the mutable list of vehicles. */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /** Sets the vehicle list (replaces current list). */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
    }

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

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the count of employees whose type is {@link EmployeeType#FULL_TIME}
     */
    public int getFullTimeEmployeeCount() {
        int count = 0;
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles that are driven by
     * part‑time employees in this company.
     *
     * @return a list of registration numbers; empty if no such vehicles exist
     */
    public List<String> getVehicleRegistrationsDrivenByPartTimeEmployees() {
        List<String> registrations = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getType() == EmployeeType.PART_TIME && e.getAssignedVehicle() != null) {
                registrations.add(e.getAssignedVehicle().getRegistrationNumber());
            }
        }
        return registrations;
    }

    /**
     * Counts the number of rented vehicles owned by this company.
     *
     * @return the count of vehicles whose type is {@link VehicleType#RENTED}
     */
    public int getRentedVehicleCount() {
        int count = 0;
        for (Vehicle v : vehicles) {
            if (v.getType() == VehicleType.RENTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in
     * this company.
     *
     * @return a list of employee names; empty if no employee is driving a vehicle
     */
    public List<String> getNamesOfEmployeesDrivingVehicle() {
        List<String> names = new ArrayList<>();
        for (Employee e : employees) {
            if (e.getAssignedVehicle() != null) {
                names.add(e.getName());
            }
        }
        return names;
    }

    /**
     * Convenience method to assign a vehicle to an employee. The relationship
     * is stored only in the employee (the vehicle does not keep a reference to
     * its driver to avoid circular serialization issues). The method also
     * ensures that the employee and vehicle belong to this company.
     *
     * @param employee the employee to receive the vehicle; must belong to this company
     * @param vehicle  the vehicle to assign; must belong to this company
     * @throws IllegalArgumentException if either argument does not belong to this company
     */
    public void assignVehicleToEmployee(Employee employee, Vehicle vehicle) {
        if (!employees.contains(employee)) {
            throw new IllegalArgumentException("Employee does not belong to this company.");
        }
        if (!vehicles.contains(vehicle)) {
            throw new IllegalArgumentException("Vehicle does not belong to this company.");
        }
        employee.setAssignedVehicle(vehicle);
    }
}

/**
 * Represents an employee of a food‑delivery company.
 */
class Employee {

    /** Employee's full name. */
    private String name;

    /** The type of employee (full‑time or part‑time). */
    private EmployeeType type;

    /** The vehicle currently assigned to this employee; may be {@code null}. */
    private Vehicle assignedVehicle;

    /** No‑argument constructor required for tests. */
    public Employee() {
    }

    /** Returns the employee's name. */
    public String getName() {
        return name;
    }

    /** Sets the employee's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the employee's type. */
    public EmployeeType getType() {
        return type;
    }

    /** Sets the employee's type. */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** Returns the vehicle assigned to this employee, or {@code null} if none. */
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    /** Sets the vehicle assigned to this employee. */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}

/**
 * Represents a vehicle used for food delivery.
 */
class Vehicle {

    /** Unique registration number of the vehicle. */
    private String registrationNumber;

    /** The type of the vehicle (owned or rented). */
    private VehicleType type;

    /** No‑argument constructor required for tests. */
    public Vehicle() {
    }

    /** Returns the registration number. */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** Sets the registration number. */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** Returns the vehicle type. */
    public VehicleType getType() {
        return type;
    }

    /** Sets the vehicle type. */
    public void setType(VehicleType type) {
        this.type = type;
    }
}

/**
 * Enumeration of possible employee categories.
 */
enum EmployeeType {
    /** Employee works part‑time. */
    PART_TIME,
    /** Employee works full‑time. */
    FULL_TIME
}

/**
 * Enumeration of possible vehicle categories.
 */
enum VehicleType {
    /** Vehicle is owned by the company. */
    OWNED,
    /** Vehicle is rented. */
    RENTED
}