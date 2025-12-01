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
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName("John Doe");
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        employee.setVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.setEmployees(Arrays.asList(employee));
        company.setVehicles(Arrays.asList(vehicle));
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Single employee with vehicle should return their name", expected, result);
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
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        alice.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        bob.setVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Multiple employees with vehicles should return all driver names", expected, result);
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
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company (employees have no vehicles assigned)
        company.setEmployees(Arrays.asList(charlie, david));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: []
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = Arrays.asList();
        
        assertEquals("No drivers assigned to vehicles should return empty list", expected, result);
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
        
        // Create Vehicles with mixed assignment state
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        eva.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // vehicle2 not assigned to any driver
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        frank.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(eva, frank));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Mixed vehicle assignment should return only drivers with assigned vehicles", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        FullTimeEmployee grace = new FullTimeEmployee();
        grace.setName("Grace Title");
        
        PartTimeEmployee henry = new PartTimeEmployee();
        henry.setName("Henry Field");
        
        // Add employees to company (no vehicles assigned to employees)
        company.setEmployees(Arrays.asList(grace, henry));
        
        // Expected Output: []
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = Arrays.asList();
        
        assertEquals("Employees without vehicles should return empty list", expected, result);
    }
}