import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Types of employees that can work for a company.
 */
enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

/**
 * Represents an employee of a company.
 */
class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle drivenVehicle; // the vehicle this employee drives, if any

    /** Unparameterized constructor. */
    public Employee() {
        // fields are initialized to default values (null)
    }

    /** @return the employee's name */
    public String getName() {
        return name;
    }

    /** @param name the employee's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the employee's type */
    public EmployeeType getType() {
        return type;
    }

    /** @param type the employee's type to set */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /** @return the vehicle this employee is currently driving, or {@code null} if none */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Assigns a vehicle to this employee as the driven vehicle.
     * Also updates the vehicle's {@code currentDriver} reference so that the
     * bidirectional association stays consistent.
     *
     * @param vehicle the vehicle to assign; may be {@code null} to remove the assignment
     */
    public void setDrivenVehicle(Vehicle vehicle) {
        // Break old association, if any
        if (this.drivenVehicle != null) {
            Vehicle oldVehicle = this.drivenVehicle;
            this.drivenVehicle = null;
            if (oldVehicle.getCurrentDriver() == this) {
                oldVehicle.setCurrentDriver(null);
            }
        }

        // Establish new association, if vehicle is not null
        if (vehicle != null) {
            this.drivenVehicle = vehicle;
            if (vehicle.getCurrentDriver() != this) {
                vehicle.setCurrentDriver(this);
            }
        }
    }
}

/**
 * Abstract representation of a vehicle owned or rented by a company.
 */
abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver; // the employee currently driving this vehicle, if any

    /** Unparameterized constructor. */
    public Vehicle() {
        // fields are initialized to default values (null)
    }

    /** @return the registration number of the vehicle */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /** @param registrationNumber the registration number to set */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /** @return the employee currently driving this vehicle, or {@code null} if none */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Assigns a driver to this vehicle.
     * Also updates the employee's {@code drivenVehicle} reference so that the
     * bidirectional association stays consistent.
     *
     * @param employee the employee to assign as driver; may be {@code null} to remove the driver
     */
    public void setCurrentDriver(Employee employee) {
        // Break old association, if any
        if (this.currentDriver != null) {
            Employee oldDriver = this.currentDriver;
            this.currentDriver = null;
            if (oldDriver.getDrivenVehicle() == this) {
                oldDriver.setDrivenVehicle(null);
            }
        }

        // Establish new association, if employee is not null
        if (employee != null) {
            this.currentDriver = employee;
            if (employee.getDrivenVehicle() != this) {
                employee.setDrivenVehicle(this);
            }
        }
    }
}

/**
 * Represents a vehicle that is owned by the company.
 */
class OwnedVehicle extends Vehicle {
    /** Unparameterized constructor. */
    public OwnedVehicle() {
        super();
    }
}

/**
 * Represents a vehicle that is rented by the company.
 */
class RentedVehicle extends Vehicle {
    /** Unparameterized constructor. */
    public RentedVehicle() {
        super();
    }
}

/**
 * Represents a company that manages employees and vehicles for food delivery.
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /** Unparameterized constructor that initializes the employee and vehicle lists. */
    public Company() {
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

    /** @return the list of employees working for the company */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** @param employees the list of employees to set */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    /** @return the list of vehicles owned or rented by the company */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /** @param vehicles the list of vehicles to set */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
    }

    /**
     * Counts the number of full‑time employees in the company.
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
     * Retrieves the registration numbers of all vehicles that are driven by
     * part‑time employees.
     *
     * @return a list of registration numbers; the list is empty if no such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrations = new ArrayList<>();
        for (Employee e : employees) {
            if (e != null && e.getType() == EmployeeType.PART_TIME) {
                Vehicle v = e.getDrivenVehicle();
                if (v != null && v.getRegistrationNumber() != null) {
                    registrations.add(v.getRegistrationNumber());
                }
            }
        }
        return registrations;
    }

    /**
     * Counts the number of rented vehicles owned by the company.
     *
     * @return the count of {@link RentedVehicle} instances in the company's vehicle list
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
     * Retrieves the names of all employees who are currently driving a vehicle.
     *
     * @return a list of employee names; the list is empty if no employee is driving a vehicle
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