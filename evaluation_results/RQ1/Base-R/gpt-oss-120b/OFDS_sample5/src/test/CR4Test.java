import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        
        // Assign vehicle to employee and add to company
        john.setAssignedVehicle(vehicle);
        company.addEmployee(john);
        company.addVehicle(vehicle);
        
        // Test: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        // Expected Output: ["John Doe"]
        List<String> expected = new ArrayList<>();
        expected.add("John Doe");
        
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
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        alice.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        bob.setAssignedVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Test: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = new ArrayList<>();
        expected.add("Alice Smith");
        expected.add("Bob Johnson");
        
        assertEquals("Multiple employees with vehicles should return all their names", expected, result);
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
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company (no assignments)
        company.addEmployee(charlie);
        company.addEmployee(david);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Test: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("No drivers assigned should return empty list", expected, result);
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
        
        // Create Vehicles: one assigned, one not assigned, one assigned
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        eva.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // Not assigned to any driver
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        frank.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(eva);
        company.addEmployee(frank);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Test: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = new ArrayList<>();
        expected.add("Eva Green");
        expected.add("Frank Wright");
        
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
        
        // Add employees to company (no vehicles assigned)
        company.addEmployee(grace);
        company.addEmployee(henry);
        
        // Test: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicle();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        
        assertEquals("Employees without vehicles should return empty list", expected, result);
    }
}