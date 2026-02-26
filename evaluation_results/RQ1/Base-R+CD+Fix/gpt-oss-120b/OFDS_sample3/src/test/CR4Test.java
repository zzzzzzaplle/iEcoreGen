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
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create Company, Employee, and Vehicle with driver assignment
        company.setName("Food Express");
        
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        
        // Assign John as driver to the vehicle
        john.setDrivenVehicle(vehicle);
        
        company.addEmployee(john);
        company.addVehicle(vehicle);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertEquals("Should contain exactly one driver name", 1, result.size());
        assertEquals("Driver name should be John Doe", "John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company, multiple Employees and Vehicles with driver assignments
        company.setName("Quick Delivery");
        
        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        
        // Assign drivers to vehicles
        alice.setDrivenVehicle(vehicle1);
        bob.setDrivenVehicle(vehicle2);
        
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - order may vary, so check size and contains
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain Alice Smith", result.contains("Alice Smith"));
        assertTrue("Should contain Bob Johnson", result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company, Employees, and Vehicles without driver assignments
        company.setName("Gourmet Delivery");
        
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // No driver assignments
        company.addEmployee(charlie);
        company.addEmployee(david);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertTrue("Should return empty list when no drivers assigned", result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company, Employees, and mix of vehicles with and without drivers
        company.setName("Fast Meals Co.");
        
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        
        // Assign drivers to some vehicles
        eva.setDrivenVehicle(vehicle1);
        frank.setDrivenVehicle(vehicle3);
        // vehicle2 remains without driver
        
        company.addEmployee(eva);
        company.addEmployee(frank);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - order may vary, so check size and contains
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain Eva Green", result.contains("Eva Green"));
        assertTrue("Should contain Frank Wright", result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company and Employees without vehicle assignments
        company.setName("Delicious Delivery");
        
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        // No vehicles created or assigned
        company.addEmployee(grace);
        company.addEmployee(henry);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertTrue("Should return empty list when no vehicles exist", result.isEmpty());
    }
}