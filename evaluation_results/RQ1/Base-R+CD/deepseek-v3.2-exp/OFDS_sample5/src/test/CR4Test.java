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
        company.setEmployees(Arrays.asList(john));
        company.setVehicles(Arrays.asList(vehicle));
        
        // Execute method and verify result
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Single employee with vehicle should return that employee's name", 
                     expected, result);
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
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify result
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Multiple employees with vehicles should return all driver names", 
                     expected, result);
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
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(charlie, david));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify result
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("No current drivers should return empty list", 
                     expected, result);
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
        
        // Create Vehicles with mixed assignment states
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        eva.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setCurrentDriver(null);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        frank.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(eva, frank));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify result
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Mixed vehicles state should return only drivers with assigned vehicles", 
                     expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        grace.setDrivenVehicle(null);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        henry.setDrivenVehicle(null);
        
        // Add employees to company (no vehicles)
        company.setEmployees(Arrays.asList(grace, henry));
        company.setVehicles(Arrays.asList()); // Empty vehicles list
        
        // Execute method and verify result
        List<String> result = company.getCurrentDriversNames();
        List<String> expected = Arrays.asList();
        
        assertEquals("Employees without vehicles should return empty list", 
                     expected, result);
    }
}