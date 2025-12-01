import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create Company "Food Express"
        company.setName("Food Express");
        
        // Create Employee "John Doe" of type Full-Time
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicle with registration "ABC123" assigned to "John Doe"
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(john);
        
        // Set employee's driven vehicle
        john.setDrivenVehicle(vehicle);
        
        // Add employee and vehicle to company
        List<Employee> employees = new ArrayList<>();
        employees.add(john);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        company.setVehicles(vehicles);
        
        // Expected Output: ["John Doe"]
        List<String> expected = new ArrayList<>();
        expected.add("John Doe");
        
        List<String> actual = company.getCurrentDriversNames();
        assertEquals("Should return single driver name", expected, actual);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with assigned drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(alice);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        
        // Set employees' driven vehicles
        alice.setDrivenVehicle(vehicle1);
        bob.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = new ArrayList<>();
        expected.add("Alice Smith");
        expected.add("Bob Johnson");
        
        List<String> actual = company.getCurrentDriversNames();
        assertEquals("Should return both driver names", expected, actual);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles without assigned drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(charlie);
        employees.add(david);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        List<String> actual = company.getCurrentDriversNames();
        assertEquals("Should return empty list when no drivers assigned", expected, actual);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles: one with driver, one without, one with driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setCurrentDriver(null);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        
        // Set employees' driven vehicles
        eva.setDrivenVehicle(vehicle1);
        frank.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(eva);
        employees.add(frank);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = new ArrayList<>();
        expected.add("Eva Green");
        expected.add("Frank Wright");
        
        List<String> actual = company.getCurrentDriversNames();
        assertEquals("Should return only drivers who are assigned to vehicles", expected, actual);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        grace.setDrivenVehicle(null);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        henry.setDrivenVehicle(null);
        
        // Add employees to company (no vehicles added)
        List<Employee> employees = new ArrayList<>();
        employees.add(grace);
        employees.add(henry);
        company.setEmployees(employees);
        
        // Set empty vehicle list
        company.setVehicles(new ArrayList<>());
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        List<String> actual = company.getCurrentDriversNames();
        assertEquals("Should return empty list when no vehicles exist", expected, actual);
    }
}