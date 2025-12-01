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
    public void testCase1_SingleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create company, employee, and assign vehicle
        company.setName("Food Express");
        
        Employee johnDoe = new Employee();
        johnDoe.setName("John Doe");
        johnDoe.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle = new OwnedVehicle("ABC123");
        vehicle.setCurrentDriver(johnDoe);
        johnDoe.setDrivenVehicle(vehicle);
        
        company.getEmployees().add(johnDoe);
        company.getVehicles().add(vehicle);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertEquals(Arrays.asList("John Doe"), result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create company, employees, and assign vehicles
        company.setName("Quick Delivery");
        
        Employee aliceSmith = new Employee();
        aliceSmith.setName("Alice Smith");
        aliceSmith.setType(EmployeeType.FULL_TIME);
        
        Employee bobJohnson = new Employee();
        bobJohnson.setName("Bob Johnson");
        bobJohnson.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle("XYZ789");
        vehicle1.setCurrentDriver(aliceSmith);
        aliceSmith.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle("LMN456");
        vehicle2.setCurrentDriver(bobJohnson);
        bobJohnson.setDrivenVehicle(vehicle2);
        
        company.getEmployees().addAll(Arrays.asList(aliceSmith, bobJohnson));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create company, employees, and vehicles without assigning drivers
        company.setName("Gourmet Delivery");
        
        Employee charlieBrown = new Employee();
        charlieBrown.setName("Charlie Brown");
        charlieBrown.setType(EmployeeType.PART_TIME);
        
        Employee davidWarner = new Employee();
        davidWarner.setName("David Warner");
        davidWarner.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle("DEF321");
        Vehicle vehicle2 = new RentedVehicle("JKL654");
        
        company.getEmployees().addAll(Arrays.asList(charlieBrown, davidWarner));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - empty list
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create company, employees, and mix of vehicles with and without drivers
        company.setName("Fast Meals Co.");
        
        Employee evaGreen = new Employee();
        evaGreen.setName("Eva Green");
        evaGreen.setType(EmployeeType.FULL_TIME);
        
        Employee frankWright = new Employee();
        frankWright.setName("Frank Wright");
        frankWright.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle("RST234");
        vehicle1.setCurrentDriver(evaGreen);
        evaGreen.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle("UVW567");
        // vehicle2 not assigned to any driver
        
        Vehicle vehicle3 = new OwnedVehicle("OPQ890");
        vehicle3.setCurrentDriver(frankWright);
        frankWright.setDrivenVehicle(vehicle3);
        
        company.getEmployees().addAll(Arrays.asList(evaGreen, frankWright));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create company and employees without assigning any vehicles
        company.setName("Delicious Delivery");
        
        Employee graceTitle = new Employee();
        graceTitle.setName("Grace Title");
        graceTitle.setType(EmployeeType.FULL_TIME);
        
        Employee henryField = new Employee();
        henryField.setName("Henry Field");
        henryField.setType(EmployeeType.PART_TIME);
        
        company.getEmployees().addAll(Arrays.asList(graceTitle, henryField));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - empty list
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
}