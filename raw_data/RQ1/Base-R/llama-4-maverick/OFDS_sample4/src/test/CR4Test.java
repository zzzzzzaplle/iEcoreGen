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
        FullTimeEmployee john = new FullTimeEmployee();
        john.setName("John Doe");
        
        // Create Vehicle with registration "ABC123" assigned to "John Doe" as driver
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(john);
        
        // Add employee and vehicle to company
        company.getEmployees().add(john);
        company.getVehicles().add(vehicle);
        
        // Expected Output: ["John Doe"]
        List<String> expected = Arrays.asList("John Doe");
        List<String> actual = company.getNamesOfEmployeesDrivingAVehicle();
        
        assertEquals("Should return names of employees currently driving vehicles", expected, actual);
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
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(alice);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(bob);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(alice, bob));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        List<String> actual = company.getNamesOfEmployeesDrivingAVehicle();
        
        assertEquals("Should return names of all employees currently driving vehicles", expected, actual);
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
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        // No driver assigned
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        // No driver assigned
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(charlie, david));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: []
        List<String> expected = Arrays.asList();
        List<String> actual = company.getNamesOfEmployeesDrivingAVehicle();
        
        assertEquals("Should return empty list when no vehicles have drivers", expected, actual);
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
        
        // Create Vehicles with mixed driver assignments
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(eva);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(frank);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(eva, frank));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        List<String> actual = company.getNamesOfEmployeesDrivingAVehicle();
        
        assertEquals("Should return names of employees driving vehicles, ignoring vehicles without drivers", expected, actual);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        FullTimeEmployee grace = new FullTimeEmployee();
        grace.setName("Grace Title");
        
        PartTimeEmployee henry = new PartTimeEmployee();
        henry.setName("Henry Field");
        
        // Add employees to company (no vehicles added)
        company.getEmployees().addAll(Arrays.asList(grace, henry));
        
        // Expected Output: []
        List<String> expected = Arrays.asList();
        List<String> actual = company.getNamesOfEmployeesDrivingAVehicle();
        
        assertEquals("Should return empty list when company has no vehicles", expected, actual);
    }
}