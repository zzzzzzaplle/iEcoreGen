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
        
        // Create Employee "John Doe" (Full-Time)
        Employee john = new Employee();
        john.setName("John Doe");
        john.setFullTime(true);
        
        // Create Vehicle "ABC123" assigned to John Doe
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(john);
        
        // Add employee and vehicle to company
        List<Employee> employees = new ArrayList<>();
        employees.add(john);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        company.setVehicles(vehicles);
        
        // Expected Output: ["John Doe"]
        List<String> result = company.findEmployeesDrivingVehicles();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setFullTime(true);
        
        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setFullTime(false);
        
        // Create Vehicles with assigned drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(alice);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(bob);
        
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
        List<String> result = company.findEmployeesDrivingVehicles();
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setFullTime(false);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setFullTime(true);
        
        // Create Vehicles without assigned drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        // No driver assigned
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        // No driver assigned
        
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
        List<String> result = company.findEmployeesDrivingVehicles();
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setFullTime(true);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setFullTime(false);
        
        // Create Vehicles with mixed driver assignments
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(eva);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(frank);
        
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
        List<String> result = company.findEmployeesDrivingVehicles();
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setFullTime(true);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setFullTime(false);
        
        // No vehicles created for this company
        
        // Add employees to company (no vehicles)
        List<Employee> employees = new ArrayList<>();
        employees.add(grace);
        employees.add(henry);
        company.setEmployees(employees);
        
        // Empty vehicle list
        company.setVehicles(new ArrayList<Vehicle>());
        
        // Expected Output: []
        List<String> result = company.findEmployeesDrivingVehicles();
        assertTrue(result.isEmpty());
    }
}