import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
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
        
        // Add employee and vehicle to company
        company.setEmployees(Arrays.asList(john));
        company.setVehicles(Arrays.asList(vehicle));
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Should return driver name when single employee drives vehicle", 
                     expected, result);
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
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(alice);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Should return all driver names when multiple employees drive vehicles", 
                     expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees without vehicles
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles without drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(charlie, david));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: []
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("Should return empty list when no vehicles have drivers", 
                     expected, result);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with mixed driver assignments
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setCurrentDriver(null); // No driver
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(eva, frank));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Should return only driver names for vehicles with assigned drivers", 
                     expected, result);
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
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        // Add employees to company (no vehicles)
        company.setEmployees(Arrays.asList(grace, henry));
        company.setVehicles(Arrays.asList()); // Empty vehicles list
        
        // Expected Output: []
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("Should return empty list when company has no vehicles", 
                     expected, result);
    }
}