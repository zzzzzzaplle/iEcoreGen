import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum EmployeeType {
    PART_TIME,
    FULL_TIME
}

abstract class Vehicle {
    private String registrationNumber;
    private Employee currentDriver;

    public Vehicle() {
    }

    /**
     * Gets the registration number of the vehicle
     * @return the registration number
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle
     * @param registrationNumber the registration number to set
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    /**
     * Gets the current driver of the vehicle
     * @return the current driver, or null if no driver is assigned
     */
    public Employee getCurrentDriver() {
        return currentDriver;
    }

    /**
     * Sets the current driver of the vehicle
     * @param currentDriver the employee to set as current driver
     */
    public void setCurrentDriver(Employee currentDriver) {
        this.currentDriver = currentDriver;
    }
}

class OwnedVehicle extends Vehicle {
    public OwnedVehicle() {
    }
}

class RentedVehicle extends Vehicle {
    public RentedVehicle() {
    }
}

class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle drivenVehicle;

    public Employee() {
    }

    /**
     * Gets the name of the employee
     * @return the employee's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the employee
     * @return the employee type (PART_TIME or FULL_TIME)
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of the employee
     * @param type the employee type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * Gets the vehicle currently driven by the employee
     * @return the driven vehicle, or null if no vehicle is assigned
     */
    public Vehicle getDrivenVehicle() {
        return drivenVehicle;
    }

    /**
     * Sets the vehicle currently driven by the employee
     * @param drivenVehicle the vehicle to set as driven by this employee
     */
    public void setDrivenVehicle(Vehicle drivenVehicle) {
        this.drivenVehicle = drivenVehicle;
    }
}

class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    public Company() {
        employees = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    /**
     * Gets the name of the company
     * @return the company name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the company
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles in the company
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles in the company
     * @param vehicles the list of vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company
     * @return the count of full-time employees
     */
    public int getFullTimeEmployeeCount() {
        return (int) employees.stream()
                .filter(employee -> employee.getType() == EmployeeType.FULL_TIME)
                .count();
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company
     * @return a list of registration numbers, or empty list if no vehicles are driven by part-time employees
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentDriver() != null && 
                                  vehicle.getCurrentDriver().getType() == EmployeeType.PART_TIME)
                .map(Vehicle::getRegistrationNumber)
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of rented vehicles in the company
     * @return the count of rented vehicles
     */
    public int getRentedVehicleCount() {
        return (int) vehicles.stream()
                .filter(vehicle -> vehicle instanceof RentedVehicle)
                .count();
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company
     * @return a list of employee names, or empty list if no employees are currently driving vehicles
     */
    public List<String> getCurrentDriversNames() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentDriver() != null)
                .map(vehicle -> vehicle.getCurrentDriver().getName())
                .distinct()
                .collect(Collectors.toList());
    }
}