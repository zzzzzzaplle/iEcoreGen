import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company in the Online Food Delivery System (OFDS).
 */
class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Default constructor to initialize a Company object.
     */
    public Company() {
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Gets the name of the company.
     * @return the name of the company
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the company.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of employees in the company.
     * @return the list of employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees in the company.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Gets the list of vehicles managed by the company.
     * @return the list of vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles managed by the company.
     * @param vehicles the list of vehicles to set
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    /**
     * Counts the number of full-time employees in the company.
     * @return the count of full-time employees
     */
    public int countFullTimeEmployees() {
        int count = 0;
        for (Employee employee : employees) {
            if (employee instanceof FullTimeEmployee) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees in the company.
     * @return a list of registration numbers
     */
    public List<String> getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof PartTimeEmployee && employee.getVehicle() != null) {
                registrationNumbers.add(employee.getVehicle().getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     * @return the count of rented vehicles
     */
    public int countRentedVehicles() {
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
     * @return a list of employee names
     */
    public List<String> getNamesOfEmployeesDrivingAVehicle() {
        List<String> employeeNames = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getVehicle() != null) {
                employeeNames.add(employee.getName());
            }
        }
        return employeeNames;
    }
}

/**
 * Represents an employee in the Online Food Delivery System (OFDS).
 */
abstract class Employee {
    private String name;
    private Vehicle vehicle;

    /**
     * Default constructor to initialize an Employee object.
     */
    public Employee() {}

    /**
     * Gets the name of the employee.
     * @return the name of the employee
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the vehicle assigned to the employee.
     * @return the vehicle assigned to the employee
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets the vehicle assigned to the employee.
     * @param vehicle the vehicle to set
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}

/**
 * Represents a full-time employee in the Online Food Delivery System (OFDS).
 */
class FullTimeEmployee extends Employee {
    /**
     * Default constructor to initialize a FullTimeEmployee object.
     */
    public FullTimeEmployee() {}
}

/**
 * Represents a part-time employee in the Online Food Delivery System (OFDS).
 */
class PartTimeEmployee extends Employee {
    /**
     * Default constructor to initialize a PartTimeEmployee object.
     */
    public PartTimeEmployee() {}
}

/**
 * Represents a vehicle in the Online Food Delivery System (OFDS).
 */
abstract class Vehicle {
    private String registrationNumber;

    /**
     * Default constructor to initialize a Vehicle object.
     */
    public Vehicle() {}

    /**
     * Gets the registration number of the vehicle.
     * @return the registration number of the vehicle
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the registration number of the vehicle.
     * @param registrationNumber the registration number to set
     */
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}

/**
 * Represents an owned vehicle in the Online Food Delivery System (OFDS).
 */
class OwnedVehicle extends Vehicle {
    /**
     * Default constructor to initialize an OwnedVehicle object.
     */
    public OwnedVehicle() {}
}

/**
 * Represents a rented vehicle in the Online Food Delivery System (OFDS).
 */
class RentedVehicle extends Vehicle {
    /**
     * Default constructor to initialize a RentedVehicle object.
     */
    public RentedVehicle() {}
}