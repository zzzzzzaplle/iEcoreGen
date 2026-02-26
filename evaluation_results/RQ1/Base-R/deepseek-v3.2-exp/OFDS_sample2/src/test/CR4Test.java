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
        // Set up company with single employee assigned to vehicle
        company.setName("Food Express");
        
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(john);
        
        company.setEmployees(Arrays.asList(john));
        company.setVehicles(Arrays.asList(vehicle1));
        
        // Execute method under test
        List<String> result = company.getEmployeesDrivingVehicles();
        
        // Verify expected output
        assertEquals("Should contain exactly one driver name", 1, result.size());
        assertEquals("Driver name should be John Doe", "John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Set up company with multiple employees and vehicles
        company.setName("Quick Delivery");
        
        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(alice);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(bob);
        
        company.setEmployees(Arrays.asList(alice, bob));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getEmployeesDrivingVehicles();
        
        // Verify expected output
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain Alice Smith", result.contains("Alice Smith"));
        assertTrue("Should contain Bob Johnson", result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Set up company with vehicles but no drivers assigned
        company.setName("Gourmet Delivery");
        
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setDriver(null);
        
        company.setEmployees(Arrays.asList(charlie, david));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getEmployeesDrivingVehicles();
        
        // Verify expected output
        assertTrue("Should return empty list when no drivers assigned", result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Set up company with mixed vehicles state (some with drivers, some without)
        company.setName("Fast Meals Co.");
        
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(eva);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setDriver(null);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(frank);
        
        company.setEmployees(Arrays.asList(eva, frank));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getEmployeesDrivingVehicles();
        
        // Verify expected output
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain Eva Green", result.contains("Eva Green"));
        assertTrue("Should contain Frank Wright", result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Set up company with employees but no vehicles assigned
        company.setName("Delicious Delivery");
        
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        company.setEmployees(Arrays.asList(grace, henry));
        company.setVehicles(Arrays.asList()); // No vehicles
        
        // Execute method under test
        List<String> result = company.getEmployeesDrivingVehicles();
        
        // Verify expected output
        assertTrue("Should return empty list when no vehicles exist", result.isEmpty());
    }
}