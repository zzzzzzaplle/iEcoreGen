import java.util.ArrayList;
import java.util.List;

class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Constructs a new Company with no name, employees, or vehicles.
     */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     * @return The name of the company.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name The new name of the company.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the company.
     * @return The list of employees.
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees The new list of employees.
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles in the company.
     * @return The list of vehicles.
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles in the company.
     * @param vehicles The new list of vehicles.
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return The number of full-time employees.
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
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     * @return A list of registration numbers of vehicles driven by part-time employees.
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.PART_TIME && employee.getDrivenVehicle() != null) {
                registrationNumbers.add(employee.getDrivenVehicle().getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return The number of rented vehicles.
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
     * Finds the names of all employees who are currently driving a vehicle in the company.
     * @return A list of names of employees who are currently driving a vehicle.
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getCurrentDriver() != null) {
                driverNames.add(vehicle.getCurrentDriver().getName());
            }
        }
        return driverNames;
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
     * Constructs a new Employee with no name, type, or driven vehicle.
     */
    public Employee() {
        this.name = "";
        this.type = null;
        this.drivenVehicle = null;
    }

    /**
     * Gets the name of the employee.
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name The new name of the employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the employee.
     * @return The type of the employee.
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     * @param type The new type of the employee.
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the vehicle driven by the employee.
     * @return The vehicle driven by the employee.
     */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Sets the vehicle driven by the employee.
     * @param drivenVehicle The new vehicle driven by the employee.
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        this.drivenVehicle = drivenVehicle;
    }
}

abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver;

    /**
     * Constructs a new Vehicle with no registration number or current driver.
     */
    public Vehicle() {
        this.registrationNumber = "";
        this.currentDriver = null;
    }

    /**
     * Gets the registration number of the vehicle.
     * @return The registration number of the vehicle.
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle.
     * @param registrationNumber The new registration number of the vehicle.
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the current driver of the vehicle.
     * @return The current driver of the vehicle.
     */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Sets the current driver of the vehicle.
     * @param currentDriver The new current driver of the vehicle.
     */
    public void setCurrentDriver(Employee currentDriver) {
        this.currentDriver = currentDriver;
    }
}

class OwnedVehicle extends Vehicle {
    /**
     * Constructs a new OwnedVehicle with no registration number or current driver.
     */
    public OwnedVehicle() {
        super();
    }
}

class RentedVehicle extends Vehicle {
    /**
     * Constructs a new RentedVehicle with no registration number or current driver.
     */
    public RentedVehicle() {
        super();
    }
}