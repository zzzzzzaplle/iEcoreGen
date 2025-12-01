import java.util.ArrayList;
import java.util.List;

 class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    /**
     * Constructs a new Company with no name, no employees, and no vehicles.
     */
    public Company() {
        this.name = "";
        this.employees = new ArrayList<>();
        this.vehicles = new ArrayList<>();
    }

    /**
     * Counts the number of full-time employees in the company.
     *
     * @return the number of full-time employees
     */
    public int countFullTimeEmployees() {
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
     *
     * @return a list of registration numbers of vehicles driven by part-time employees
     */
    public List<String> findVehiclesDrivenByPartTimeEmployees() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getType() == EmployeeType.PART_TIME) {
                Vehicle vehicle = employee.getAssignedVehicle();
                if (vehicle != null) {
                    registrationNumbers.add(vehicle.getRegistrationNumber());
                }
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     *
     * @return the number of rented vehicles
     */
    public int countRentedVehicles() {
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getType() == VehicleType.RENTED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the names of all employees who are currently driving a vehicle in the company.
     *
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

 class Employee {
    private String name;
    private EmployeeType type;
    private Vehicle assignedVehicle;

    /**
     * Constructs a new Employee with no name, no type, and no assigned vehicle.
     */
    public Employee() {
        this.name = "";
        this.type = null;
        this.assignedVehicle = null;
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

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}

 class Vehicle {
    private String registrationNumber;
    private VehicleType type;

    /**
     * Constructs a new Vehicle with no registration number and no type.
     */
    public Vehicle() {
        this.registrationNumber = "";
        this.type = null;
    }

    // Getters and Setters
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }
}

 enum EmployeeType {
    FULL_TIME,
    PART_TIME
}

 enum VehicleType {
    OWNED,
    RENTED
}