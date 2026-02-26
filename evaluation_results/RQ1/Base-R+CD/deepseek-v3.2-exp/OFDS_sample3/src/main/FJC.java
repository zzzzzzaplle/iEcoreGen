import java.util.ArrayList;
import java.util.List;

class Company {
    private String name;
    private List<Employee> employees;
    private List<Vehicle> vehicles;

    public Company() {
        employees = new ArrayList<>();
        vehicles = new ArrayList<>();
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
     * Iterates through all employees and counts those with FULL_TIME type.
     *
     * @return the total count of full-time employees in the company
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
     * Checks each vehicle to see if it has a driver and if that driver is part-time.
     *
     * @return a list of registration numbers of vehicles driven by part-time employees.
     *         Returns an empty list if no such vehicles exist.
     */
    public List<String> getPartTimeDriverVehicleRegistrationNumbers() {
        List<String> registrationNumbers = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee driver = vehicle.getCurrentDriver();
            if (driver != null && driver.getType() == EmployeeType.PART_TIME) {
                registrationNumbers.add(vehicle.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    /**
     * Counts the number of rented vehicles in the company.
     * Iterates through all vehicles and counts those that are instances of RentedVehicle.
     *
     * @return the total count of rented vehicles in the company
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
     * Checks each vehicle for a current driver and collects their names.
     *
     * @return a list of names of employees currently driving vehicles.
     *         Returns an empty list if no employees are driving vehicles.
     */
    public List<String> getCurrentDriversNames() {
        List<String> driverNames = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            Employee driver = vehicle.getCurrentDriver();
            if (driver != null) {
                driverNames.add(driver.getName());
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