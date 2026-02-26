import java.util.ArrayList;
import java.util.List;

class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Constructs an empty Company with no name, employees, or vehicles.
     */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return The count of full-time employees.
     */
    public int getFullTimeEmployeeCount() {
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.FULL_TIME) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees.
     * @return A list of registration numbers of vehicles driven by part-time employees.
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee currentDriver = vehicle.getCurrentDriver();
            if (currentDriver != null && currentDriver.getType() == EmployeeType.PART_TIME) {
                registrationNumbers.add(vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return The count of rented vehicles.
     */
    public int getRentedVehicleCount() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof RentedVehicle) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle.
     * @return A list of names of employees who are currently driving a vehicle.
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee currentDriver = vehicle.getCurrentDriver();
            if (currentDriver != null) {
                driverNames.add(currentDriver.getName());
            }
        }
        return driverNames;
    }

    // Getters and Setters
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
}

enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle drivenVehicle;

    /**
     * Constructs an empty Employee with no name, type, or driven vehicle.
     */
    public Employee() {
        this.name = "";
        this.type = null;
        this.drivenVehicle = null;
    }

    // Getters and Setters
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
    }
}

abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver;

    /**
     * Constructs an empty Vehicle with no registration number or current driver.
     */
    public Vehicle() {
        this.registrationNumber = "";
        this.currentDriver = null;
    }

    // Getters and Setters
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
    }
}

class OwnedVehicle extends Vehicle {
    /**
     * Constructs an empty OwnedVehicle with no registration number or current driver.
     */
    public OwnedVehicle() {
        super();
    }
}

class RentedVehicle extends Vehicle {
    /**
     * Constructs an empty RentedVehicle with no registration number or current driver.
     */
    public RentedVehicle() {
        super();
    }
}