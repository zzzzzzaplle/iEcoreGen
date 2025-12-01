import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_singleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setType("full-time");
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(employee);
        employee.setAssignedVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.addEmployee(employee);
        company.addVehicle(vehicle);
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getDrivingEmployeeNames();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Single employee driving should return correct name", expected, result);
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType("part-time");
        
        // Create Vehicles: 
        // - Registration "XYZ789" with driver "Alice Smith"
        // - Registration "LMN456" with driver "Bob Johnson"
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(employee1);
        employee1.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(employee2);
        employee2.setAssignedVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getDrivingEmployeeNames();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Multiple employees driving should return all names", expected, result);
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType("part-time");
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType("full-time");
        
        // Create Vehicles: 
        // - Registration "DEF321" not assigned to any driver
        // - Registration "JKL654" not assigned to any driver
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: []
        List<String> result = company.getDrivingEmployeeNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("No drivers assigned should return empty list", expected, result);
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType("part-time");
        
        // Create Vehicles: 
        // - Registration "RST234" with driver "Eva Green"
        // - Registration "UVW567" not assigned to any driver
        // - Registration "OPQ890" with driver "Frank Wright"
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(employee1);
        employee1.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(employee2);
        employee2.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getDrivingEmployeeNames();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Mixed vehicle assignments should return only driving employees", expected, result);
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType("part-time");
        
        // Add employees to company (no vehicles added)
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Expected Output: []
        List<String> result = company.getDrivingEmployeeNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("Employees without vehicles should return empty list", expected, result);
    }
}