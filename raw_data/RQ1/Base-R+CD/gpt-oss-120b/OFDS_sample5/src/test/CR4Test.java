import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_singleEmployeeWithVehicle() {
        // Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee john = new Employee("John Doe", EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new OwnedVehicle("ABC123");
        vehicle.setCurrentDriver(john);
        
        // Add vehicle to company
        company.addVehicle(vehicle);
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: ["John Doe"]
        assertEquals(1, result.size());
        assertTrue(result.contains("John Doe"));
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee alice = new Employee("Alice Smith", EmployeeType.FULL_TIME);
        Employee bob = new Employee("Bob Johnson", EmployeeType.PART_TIME);
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle("XYZ789");
        vehicle1.setCurrentDriver(alice);
        
        Vehicle vehicle2 = new OwnedVehicle("LMN456");
        vehicle2.setCurrentDriver(bob);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = new Employee("Charlie Brown", EmployeeType.PART_TIME);
        Employee david = new Employee("David Warner", EmployeeType.FULL_TIME);
        
        // Create Vehicles without drivers
        Vehicle vehicle1 = new OwnedVehicle("DEF321");
        Vehicle vehicle2 = new OwnedVehicle("JKL654");
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = new Employee("Eva Green", EmployeeType.FULL_TIME);
        Employee frank = new Employee("Frank Wright", EmployeeType.PART_TIME);
        
        // Create Vehicles - some with drivers, some without
        Vehicle vehicle1 = new OwnedVehicle("RST234");
        vehicle1.setCurrentDriver(eva);
        
        Vehicle vehicle2 = new OwnedVehicle("UVW567"); // No driver
        
        Vehicle vehicle3 = new OwnedVehicle("OPQ890");
        vehicle3.setCurrentDriver(frank);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee grace = new Employee("Grace Title", EmployeeType.FULL_TIME);
        Employee henry = new Employee("Henry Field", EmployeeType.PART_TIME);
        
        // Add employees to company (but don't assign them to vehicles)
        company.addEmployee(grace);
        company.addEmployee(henry);
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: []
        assertEquals(0, result.size());
    }
}