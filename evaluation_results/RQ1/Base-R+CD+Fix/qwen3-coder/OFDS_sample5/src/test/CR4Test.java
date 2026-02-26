import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(john);
        john.setDrivenVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.getEmployees().add(john);
        company.getVehicles().add(vehicle);
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: ["John Doe"]
        assertEquals("Should contain exactly one driver", 1, result.size());
        assertEquals("John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
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
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(alice, bob));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: ["Alice Smith", "Bob Johnson"]
        assertEquals("Should contain exactly two drivers", 2, result.size());
        assertTrue("Should contain Alice Smith", result.contains("Alice Smith"));
        assertTrue("Should contain Bob Johnson", result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
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
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: []
        assertTrue("Should be empty when no drivers assigned", result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with mixed assignment state
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        eva.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        frank.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(eva, frank));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: ["Eva Green", "Frank Wright"]
        assertEquals("Should contain exactly two drivers", 2, result.size());
        assertTrue("Should contain Eva Green", result.contains("Eva Green"));
        assertTrue("Should contain Frank Wright", result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        // No vehicles created or assigned to employees
        
        // Add employees to company
        company.getEmployees().addAll(Arrays.asList(grace, henry));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: []
        assertTrue("Should be empty when employees have no vehicles", result.isEmpty());
    }
}