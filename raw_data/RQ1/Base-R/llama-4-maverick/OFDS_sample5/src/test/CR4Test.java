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
        FullTimeEmployee john = new FullTimeEmployee();
        john.setName("John Doe");
        
        // Create Vehicle with registration "ABC123" assigned to "John Doe"
        OwnedVehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(john);
        
        // Add employee and vehicle to company
        company.setEmployees(Arrays.asList(john));
        company.setVehicles(Arrays.asList(vehicle));
        
        // Execute method under test
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify expected output
        assertEquals("Should return single driver name", Arrays.asList("John Doe"), result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        FullTimeEmployee alice = new FullTimeEmployee();
        alice.setName("Alice Smith");
        
        PartTimeEmployee bob = new PartTimeEmployee();
        bob.setName("Bob Johnson");
        
        // Create Vehicles with assigned drivers
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(alice);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(bob);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify expected output
        assertEquals("Should return both driver names", 
                     Arrays.asList("Alice Smith", "Bob Johnson"), result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        PartTimeEmployee charlie = new PartTimeEmployee();
        charlie.setName("Charlie Brown");
        
        FullTimeEmployee david = new FullTimeEmployee();
        david.setName("David Warner");
        
        // Create Vehicles without assigned drivers
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        // No driver assigned
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        // No driver assigned
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(charlie, david));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify expected output
        assertTrue("Should return empty list when no vehicles have drivers", result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        FullTimeEmployee eva = new FullTimeEmployee();
        eva.setName("Eva Green");
        
        PartTimeEmployee frank = new PartTimeEmployee();
        frank.setName("Frank Wright");
        
        // Create Vehicles: one with driver, one without, one with driver
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(eva);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(frank);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(eva, frank));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify expected output
        assertEquals("Should return only names of employees driving vehicles", 
                     Arrays.asList("Eva Green", "Frank Wright"), result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles
        FullTimeEmployee grace = new FullTimeEmployee();
        grace.setName("Grace Title");
        
        PartTimeEmployee henry = new PartTimeEmployee();
        henry.setName("Henry Field");
        
        // Add employees to company (no vehicles)
        company.setEmployees(Arrays.asList(grace, henry));
        company.setVehicles(Arrays.asList()); // Empty vehicles list
        
        // Execute method under test
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify expected output
        assertTrue("Should return empty list when no vehicles exist", result.isEmpty());
    }
}