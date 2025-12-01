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
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName("John Doe");
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        OwnedVehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        employee.setVehicle(vehicle);
        
        // Add employee to company
        company.setEmployees(Arrays.asList(employee));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify: Expected Output: ["John Doe"]
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        FullTimeEmployee employee1 = new FullTimeEmployee();
        employee1.setName("Alice Smith");
        
        PartTimeEmployee employee2 = new PartTimeEmployee();
        employee2.setName("Bob Johnson");
        
        // Create Vehicles with drivers
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        employee1.setVehicle(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        employee2.setVehicle(vehicle2);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify: Expected Output: ["Alice Smith", "Bob Johnson"]
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        PartTimeEmployee employee1 = new PartTimeEmployee();
        employee1.setName("Charlie Brown");
        
        FullTimeEmployee employee2 = new FullTimeEmployee();
        employee2.setName("David Warner");
        
        // Create Vehicles not assigned to any driver
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees (without vehicles) to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        FullTimeEmployee employee1 = new FullTimeEmployee();
        employee1.setName("Eva Green");
        
        PartTimeEmployee employee2 = new PartTimeEmployee();
        employee2.setName("Frank Wright");
        
        // Create Vehicles with mixed assignment state
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        employee1.setVehicle(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // vehicle2 not assigned to any employee
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        employee2.setVehicle(vehicle3);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify: Expected Output: ["Eva Green", "Frank Wright"]
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles
        FullTimeEmployee employee1 = new FullTimeEmployee();
        employee1.setName("Grace Title");
        
        PartTimeEmployee employee2 = new PartTimeEmployee();
        employee2.setName("Henry Field");
        
        // Add employees (without vehicles) to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Execute: Retrieve current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
}