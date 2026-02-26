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
        company = null;
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create a Company named "Food Express"
        company = new Company("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee john = new Employee("John Doe", EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new Vehicle("ABC123", VehicleType.OWNED);
        vehicle.setDriver(john);
        
        // Add employee and vehicle to company
        company.addEmployee(john);
        company.addVehicle(vehicle);
        
        // Expected Output: ["John Doe"]
        List<String> expected = Arrays.asList("John Doe");
        List<String> actual = company.getNamesOfEmployeesDrivingVehicles();
        
        assertEquals("Should return the single driver's name", expected, actual);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create a Company named "Quick Delivery"
        company = new Company("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee alice = new Employee("Alice Smith", EmployeeType.FULL_TIME);
        Employee bob = new Employee("Bob Johnson", EmployeeType.PART_TIME);
        
        // Create Vehicles: 
        // - Registration "XYZ789" with driver "Alice Smith"
        // - Registration "LMN456" with driver "Bob Johnson"
        Vehicle vehicle1 = new Vehicle("XYZ789", VehicleType.OWNED);
        vehicle1.setDriver(alice);
        
        Vehicle vehicle2 = new Vehicle("LMN456", VehicleType.RENTED);
        vehicle2.setDriver(bob);
        
        // Add employees and vehicles to company
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        List<String> actual = company.getNamesOfEmployeesDrivingVehicles();
        
        // Sort both lists for comparison since order may vary
        Collections.sort(expected);
        Collections.sort(actual);
        
        assertEquals("Should return names of all drivers", expected, actual);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create a Company named "Gourmet Delivery"
        company = new Company("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = new Employee("Charlie Brown", EmployeeType.PART_TIME);
        Employee david = new Employee("David Warner", EmployeeType.FULL_TIME);
        
        // Create Vehicles: 
        // - Registration "DEF321" not assigned to any driver
        // - Registration "JKL654" not assigned to any driver
        Vehicle vehicle1 = new Vehicle("DEF321", VehicleType.OWNED);
        Vehicle vehicle2 = new Vehicle("JKL654", VehicleType.RENTED);
        
        // Add employees and vehicles to company (vehicles have no drivers assigned)
        company.addEmployee(charlie);
        company.addEmployee(david);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: []
        List<String> expected = Arrays.asList();
        List<String> actual = company.getNamesOfEmployeesDrivingVehicles();
        
        assertEquals("Should return empty list when no drivers assigned", expected, actual);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create a Company named "Fast Meals Co."
        company = new Company("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = new Employee("Eva Green", EmployeeType.FULL_TIME);
        Employee frank = new Employee("Frank Wright", EmployeeType.PART_TIME);
        
        // Create Vehicles: 
        // - Registration "RST234" with driver "Eva Green"
        // - Registration "UVW567" not assigned to any driver
        // - Registration "OPQ890" with driver "Frank Wright"
        Vehicle vehicle1 = new Vehicle("RST234", VehicleType.OWNED);
        vehicle1.setDriver(eva);
        
        Vehicle vehicle2 = new Vehicle("UVW567", VehicleType.RENTED);
        // No driver assigned
        
        Vehicle vehicle3 = new Vehicle("OPQ890", VehicleType.OWNED);
        vehicle3.setDriver(frank);
        
        // Add employees and vehicles to company
        company.addEmployee(eva);
        company.addEmployee(frank);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        List<String> actual = company.getNamesOfEmployeesDrivingVehicles();
        
        // Sort both lists for comparison since order may vary
        Collections.sort(expected);
        Collections.sort(actual);
        
        assertEquals("Should return names of drivers from vehicles that have drivers assigned", expected, actual);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create a Company named "Delicious Delivery"
        company = new Company("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee grace = new Employee("Grace Title", EmployeeType.FULL_TIME);
        Employee henry = new Employee("Henry Field", EmployeeType.PART_TIME);
        
        // Add employees to company (no vehicles added)
        company.addEmployee(grace);
        company.addEmployee(henry);
        
        // Expected Output: []
        List<String> expected = Arrays.asList();
        List<String> actual = company.getNamesOfEmployeesDrivingVehicles();
        
        assertEquals("Should return empty list when no vehicles exist", expected, actual);
    }
}