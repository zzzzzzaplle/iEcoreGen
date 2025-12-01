import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Enumeration of possible employee employment types.
 */
enum EmployeeType {
    FULL_TIME,
    PART_TIME
}

/**
 * Enumeration of possible vehicle ownership types.
 */
enum VehicleType {
    OWNED,
    RENTED
}

/**
 * Represents an employee of a food delivery company.
 */
class Employee {
    private String name;
    private EmployeeType type;

    /** Unparameterized constructor */
    public Employee() {
        // Default values can be set later via setters
    }

    /** Parameterized constructor for convenience */
    public Employee(String name, EmployeeType type) {
        this.name = name;
        this.type = type;
    }

    /** @return the employee's name */
    public String getName() {
        return name;
    }

    /** @param name the employee's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the employee's type (FULL_TIME / PART_TIME) */
    public EmployeeType getType() {
        return type;
    }

    /** @param type the employee's type to set */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Employee{name='" + name + '\'' + ", type=" + type + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name) && type == employee.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}

/**
 * Represents a vehicle used for food delivery.
 */
class Vehicle {
    private String registrationNumber;
    private VehicleType type;
    private Employee driver; // may be null if no driver assigned

    /** Unparameterized constructor */
    public Vehicle() {
        // Default values can be set later via setters
    }

    /** Parameterized constructor for convenience */
    public Vehicle(String registrationNumber, VehicleType type) {
        this.registrationNumber = registrationNumber;
        this.type = type;
    }

    /** @return the vehicle's registration number */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** @param registrationNumber the registration number to set */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** @return the vehicle's type (OWNED / RENTED) */
    public VehicleType getType() {
        return type;
    }

    /** @param type the vehicle's type to set */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /** @return the driver assigned to this vehicle, may be {@code null} */
    public Employee getDriver() {
        return driver;
    }

    /** @param driver the driver to assign to this vehicle (may be {@code null}) */
    public void setDriver(Employee driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "Vehicle{registrationNumber='" + registrationNumber + '\'' +
                ", type=" + type +
                ", driver=" + (driver != null ? driver.getName() : "none") + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(registrationNumber, vehicle.registrationNumber) &&
                type == vehicle.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, type);
    }
}

/**
 * Represents a food delivery company that manages employees and vehicles.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /** Unparameterized constructor */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /** Parameterized constructor for convenience */
    public Company(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /** @return the company's name */
    public String getName() {
        return name;
    }

    /** @param name the company's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return mutable list of employees belonging to the company */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to set (replaces current list) */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /** @return mutable list of vehicles belonging to the company */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /** @param vehicles the list of vehicles to set (replaces current list) */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
    }

    /**
     * Counts the number of full‑time employees in this company.
     *
     * @return the count of employees whose {@link Employee#getType()} is {@link EmployeeType#FULL_TIME}
     */
    public int countFullTimeEmployees() {
        int count = 0;
        for (Employee e : employees) {
            if (e != null && e.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retrieves the registration numbers of all vehicles that are driven by part‑time employees.
     *
     * @return a list of registration numbers; the list is empty if no such vehicles exist
     */
    public List<String> getVehicleRegistrationsDrivenByPartTimeEmployees() {
        List<String> registrations = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Employee driver = v.getDriver();
            if (driver != null && driver.getType() == EmployeeType.PART_TIME) {
                registrations.add(v.getRegistrationNumber());
            }
        }
        return registrations;
    }

    /**
     * Counts the number of rented vehicles owned by the company.
     *
     * @return the count of vehicles whose {@link Vehicle#getType()} is {@link VehicleType#RENTED}
     */
    public int countRentedVehicles() {
        int count = 0;
        for (Vehicle v : vehicles) {
            if (v != null && v.getType() == VehicleType.RENTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently assigned as drivers to any vehicle.
     *
     * @return a list containing distinct employee names; the list is empty if no employee is driving a vehicle
     */
    public List<String> getNamesOfEmployeesDrivingVehicles() {
        List<String> names = new ArrayList<>();
        for (Vehicle v : vehicles) {
            Employee driver = v.getDriver();
            if (driver != null && driver.getName() != null && !names.contains(driver.getName())) {
                names.add(driver.getName());
            }
        }
        return names;
    }

    /**
     * Utility method to add an employee to the company.
     *
     * @param employee the employee to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code employee} is {@code null}
     */
    public void addEmployee(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        employees.add(employee);
    }

    /**
     * Utility method to add a vehicle to the company.
     *
     * @param vehicle the vehicle to add; must not be {@code null}
     * @throws IllegalArgumentException if {@code vehicle} is {@code null}
     */
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        vehicles.add(vehicle);
    }

    @Override
    public String toString() {
        return "Company{name='" + name + '\'' +
                ", employees=" + employees +
                ", vehicles=" + vehicles + '}';
    }
}