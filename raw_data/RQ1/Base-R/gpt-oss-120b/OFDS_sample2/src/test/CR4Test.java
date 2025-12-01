import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        FullTimeEmployee john = new FullTimeEmployee();
        john.setName("John Doe");
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        OwnedVehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        
        // Assign vehicle to John
        john.setVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.addEmployee(john);
        company.addVehicle(vehicle);
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        FullTimeEmployee alice = new FullTimeEmployee();
        alice.setName("Alice Smith");
        
        PartTimeEmployee bob = new PartTimeEmployee();
        bob.setName("Bob Johnson");
        
        // Create Vehicles: 
        // - Registration "XYZ789" with driver "Alice Smith"
        // - Registration "LMN456" with driver "Bob Johnson"
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        alice.setVehicle(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        bob.setVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        PartTimeEmployee charlie = new PartTimeEmployee();
        charlie.setName("Charlie Brown");
        
        FullTimeEmployee david = new FullTimeEmployee();
        david.setName("David Warner");
        
        // Create Vehicles: 
        // - Registration "DEF321" not assigned to any driver
        // - Registration "JKL654" not assigned to any driver
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company (no assignments)
        company.addEmployee(charlie);
        company.addEmployee(david);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: []
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        FullTimeEmployee eva = new FullTimeEmployee();
        eva.setName("Eva Green");
        
        PartTimeEmployee frank = new PartTimeEmployee();
        frank.setName("Frank Wright");
        
        // Create Vehicles: 
        // - Registration "RST234" with driver "Eva Green"
        // - Registration "UVW567" not assigned to any driver
        // - Registration "OPQ890" with driver "Frank Wright"
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        eva.setVehicle(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned to vehicle2
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        frank.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(eva);
        company.addEmployee(frank);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        FullTimeEmployee grace = new FullTimeEmployee();
        grace.setName("Grace Title");
        
        PartTimeEmployee henry = new PartTimeEmployee();
        henry.setName("Henry Field");
        
        // Add employees to company (no vehicles added)
        company.addEmployee(grace);
        company.addEmployee(henry);
        
        // Expected Output: []
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        assertTrue(result.isEmpty());
    }
}