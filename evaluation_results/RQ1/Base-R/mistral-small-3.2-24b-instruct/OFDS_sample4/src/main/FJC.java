import java.util.ArrayList;
import java.util.List;

/**
 * Represents a company in the Online Food Delivery System.
 */
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
     * @return the list of employees in the company
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * Sets the list of employees for the company.
     * @param employees the list of employees to set
     */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * @return the list of vehicles in the company
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets the list of vehicles for the company.
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
            if (employee.getType().equals(EmployeeType.FULL_TIME)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the registration numbers of all vehicles driven by part-time employees.
     * @return a list of registration numbers of vehicles driven by part-time employees
     */
    public List<String> findVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getType().equals(EmployeeType.PART_TIME) && employee.getAssignedVehicle() != null) {
                registrationNumbers.add(employee.getAssignedVehicle().getRegistrationNumber());
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
            if (vehicle.getType().equals(VehicleType.RENTED)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle.
     * @return a list of names of employees who are driving a vehicle
     */
    public List<String> findEmployeesDrivingVehicles() {
        List<String> employeeNames = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAssignedVehicle() != null) {
                employeeNames.add(employee.getName());
            }
        }
        return employeeNames;
    }
}

/**
 * Represents an employee in the Online Food Delivery System.
 */
 class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle assignedVehicle;

    /**
     * Constructs an empty Employee with no name, type, or assigned vehicle.
     */
    public Employee() {
        this.name = "";
        this.type = EmployeeType.PART_TIME;
        this.assignedVehicle = null;
    }

    /**
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
     * @return the type of the employee
     */
    public EmployeeType getType() {
        return type;
    }

    /**
     * Sets the type of the employee.
     * @param type the type to set
     */
    public void setType(EmployeeType type) {
        this.type = type;
    }

    /**
     * @return the vehicle assigned to the employee
     */
    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    /**
     * Sets the vehicle assigned to the employee.
     * @param assignedVehicle the vehicle to set
     */
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}

/**
 * Represents a vehicle in the Online Food Delivery System.
 */
 class Vehicle {
    private String registrationNumber;
    private VehicleType type;

    /**
     * Constructs an empty Vehicle with no registration number or type.
     */
    public Vehicle() {
        this.registrationNumber = "";
        this.type = VehicleType.OWNED;
    }

    /**
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

    /**
     * @return the type of the vehicle
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Sets the type of the vehicle.
     * @param type the type to set
     */
    public void setType(VehicleType type) {
        this.type = type;
    }
}

/**
 * Enumeration representing the type of an employee.
 */
 enum EmployeeType {
    FULL_TIME,
    PART_TIME
}

/**
 * Enumeration representing the type of a vehicle.
 */
 enum VehicleType {
    OWNED,
    RENTED
}