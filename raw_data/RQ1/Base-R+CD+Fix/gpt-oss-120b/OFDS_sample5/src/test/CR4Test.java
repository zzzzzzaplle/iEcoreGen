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
        // SetUp: Create Company, Employee, and Vehicle with driver assignment
        company = new Company("Food Express");
        
        Employee johnDoe = new Employee("John Doe", EmployeeType.FULL_TIME);
        Vehicle vehicle = new OwnedVehicle("ABC123");
        
        // Assign vehicle to employee (bi-directional relationship)
        johnDoe.setDrivenVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.addEmployee(johnDoe);
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
        company = new Company("Quick Delivery");
        
        Employee aliceSmith = new Employee("Alice Smith", EmployeeType.FULL_TIME);
        Employee bobJohnson = new Employee("Bob Johnson", EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle("XYZ789");
        Vehicle vehicle2 = new OwnedVehicle("LMN456");
        
        // Assign vehicles to employees
        aliceSmith.setDrivenVehicle(vehicle1);
        bobJohnson.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(aliceSmith);
        company.addEmployee(bobJohnson);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - should contain both names (order may vary)
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain Alice Smith", result.contains("Alice Smith"));
        assertTrue("Should contain Bob Johnson", result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company, Employees, and Vehicles without driver assignments
        company = new Company("Gourmet Delivery");
        
        Employee charlieBrown = new Employee("Charlie Brown", EmployeeType.PART_TIME);
        Employee davidWarner = new Employee("David Warner", EmployeeType.FULL_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle("DEF321");
        Vehicle vehicle2 = new OwnedVehicle("JKL654");
        
        // Do NOT assign vehicles to employees (vehicles have no drivers)
        
        // Add employees and vehicles to company
        company.addEmployee(charlieBrown);
        company.addEmployee(davidWarner);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - empty list
        assertTrue("Should return empty list when no vehicles have drivers", result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company, Employees, and Vehicles with mixed driver assignments
        company = new Company("Fast Meals Co.");
        
        Employee evaGreen = new Employee("Eva Green", EmployeeType.FULL_TIME);
        Employee frankWright = new Employee("Frank Wright", EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new OwnedVehicle("RST234");
        Vehicle vehicle2 = new OwnedVehicle("UVW567");
        Vehicle vehicle3 = new OwnedVehicle("OPQ890");
        
        // Assign vehicles to some employees, leave one vehicle without driver
        evaGreen.setDrivenVehicle(vehicle1);
        frankWright.setDrivenVehicle(vehicle3);
        // vehicle2 remains without driver
        
        // Add employees and vehicles to company
        company.addEmployee(evaGreen);
        company.addEmployee(frankWright);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - should contain both driver names (order may vary)
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain Eva Green", result.contains("Eva Green"));
        assertTrue("Should contain Frank Wright", result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company and Employees without any vehicle assignments
        company = new Company("Delicious Delivery");
        
        Employee graceTitle = new Employee("Grace Title", EmployeeType.FULL_TIME);
        Employee henryField = new Employee("Henry Field", EmployeeType.PART_TIME);
        
        // Employees are not assigned any vehicles
        
        // Add employees to company (no vehicles added to company)
        company.addEmployee(graceTitle);
        company.addEmployee(henryField);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output - empty list
        assertTrue("Should return empty list when no vehicles exist in company", result.isEmpty());
    }
}