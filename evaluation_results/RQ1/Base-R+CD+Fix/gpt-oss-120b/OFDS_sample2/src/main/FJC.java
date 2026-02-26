import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Default constructor for Company class
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Counts the number of full-time employees in the company
     * @return the count of full-time employees
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
     * Finds registration numbers of all vehicles driven by part-time employees
     * @return list of registration numbers, empty list if no vehicles found
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
     * Counts the number of rented vehicles in the company
     * @return the count of rented vehicles
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
     * Finds names of all employees currently driving a vehicle
     * @return list of employee names, empty list if no employees are driving
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDrivenVehicle() != null) {
                driverNames.add(employee.getName());
            }
        }
        return driverNames;
    }

    // Getters and setters
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
     * Default constructor for Employee class
     */
    public Employee() {
    }

    // Getters and setters
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
     * Default constructor for Vehicle class
     */
    public Vehicle() {
    }

    // Getters and setters
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
     * Default constructor for OwnedVehicle class
     */
    public OwnedVehicle() {
    }
}

class RentedVehicle extends Vehicle {
    /**
     * Default constructor for RentedVehicle class
     */
    public RentedVehicle() {
    }
}