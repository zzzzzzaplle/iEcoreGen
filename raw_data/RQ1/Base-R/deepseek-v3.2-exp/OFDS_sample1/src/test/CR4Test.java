import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

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
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(john);
        
        // Add employee and vehicle to company
        company.getEmployees().add(john);
        company.getVehicles().add(vehicle);
        
        // Execute method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Should return single employee driving vehicle", expected, result);
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
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(alice);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(bob);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(alice, bob));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Should return both employees driving vehicles", expected, result);
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
        
        // Create Vehicles without drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setDriver(null);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(charlie, david));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList();
        
        assertEquals("Should return empty list when no vehicles have drivers", expected, result);
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
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(eva);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setDriver(null);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(frank);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(eva, frank));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Should return only employees driving vehicles", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        // Add employees to company (no vehicles added)
        company.getEmployees().addAll(Arrays.asList(grace, henry));
        
        // Execute method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList();
        
        assertEquals("Should return empty list when company has no vehicles", expected, result);
    }
}