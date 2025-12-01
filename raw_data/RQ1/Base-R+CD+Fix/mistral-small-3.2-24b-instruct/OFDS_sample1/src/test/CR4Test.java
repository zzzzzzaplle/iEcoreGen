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
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Single employee with vehicle should return their name", expected, result);
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
        
        // Create Vehicles: 
        // - Registration "XYZ789" with driver "Alice Smith"
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        // - Registration "LMN456" with driver "Bob Johnson"
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(alice, bob));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Multiple employees with vehicles should return all driver names", expected, result);
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
        
        // Create Vehicles: 
        // - Registration "DEF321" not assigned to any driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        // - Registration "JKL654" not assigned to any driver
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company (employees have no driven vehicles)
        company.getEmployees().addAll(Arrays.asList(charlie, david));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Expected Output: []
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("No drivers assigned to vehicles should return empty list", expected, result);
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
        
        // Create Vehicles: 
        // - Registration "RST234" with driver "Eva Green"
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        eva.setDrivenVehicle(vehicle1);
        
        // - Registration "UVW567" not assigned to any driver
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        
        // - Registration "OPQ890" with driver "Frank Wright"
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        frank.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(eva, frank));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Mixed vehicles state should return only employees with assigned vehicles", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        // Add employees to company (no vehicles added)
        company.getEmployees().addAll(Arrays.asList(grace, henry));
        
        // Expected Output: []
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("Employees without vehicles should return empty list", expected, result);
    }
}