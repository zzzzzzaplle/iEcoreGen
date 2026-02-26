import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

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

    /**
     * Counts the number of full-time employees in the company.
     * 
     * @return the count of full-time employees
     */
    public int getFullTimeEmployeeCount() {
        return (int) employees.stream()
                .filter(employee -> employee.getType() == EmployeeType.FULL_TIME)
                .count();
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees.
     * 
     * @return a list of registration numbers of vehicles driven by part-time employees, 
     *         or an empty list if no such vehicles exist
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        return vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentDriver() != null && 
                                  vehicle.getCurrentDriver().getType() == EmployeeType.PART_TIME)
                .map(Vehicle::getRegistrationNumber)
                .collect(Collectors.toList());
    }

    /**
     * Counts the number of rented vehicles in the company.
     * 
     * @return the count of rented vehicles
     */
    public int getRentedVehicleCount() {
        return (int) vehicles.stream()
                .filter(vehicle -> vehicle instanceof RentedVehicle)
                .count();
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle.
     * 
     * @return a list of names of employees currently driving vehicles, 
     *         or an empty list if no employee is driving a vehicle
     */
    public List<String> getCurrentDriversNames() {
        return employees.stream()
                .filter(employee -> employee.getDrivenVehicle() != null)
                .map(Employee::getName)
                .collect(Collectors.toList());
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

    public Employee() {
    }

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

    public Vehicle() {
    }

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
    public OwnedVehicle() {
    }
}

class RentedVehicle extends Vehicle {
    public RentedVehicle() {
    }
}