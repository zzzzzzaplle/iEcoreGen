import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

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
        // SetUp: Create company with single employee assigned to a vehicle
        company.setName("Food Express");
        
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        
        employee.setAssignedVehicle(vehicle);
        
        company.setEmployees(Arrays.asList(employee));
        company.setVehicles(Arrays.asList(vehicle));
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = Arrays.asList("John Doe");
        assertEquals("Should return single employee name when one employee has a vehicle", expected, result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create company with multiple employees and vehicles
        company.setName("Quick Delivery");
        
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        
        employee1.setAssignedVehicle(vehicle1);
        employee2.setAssignedVehicle(vehicle2);
        
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals("Should return all employee names when multiple employees have vehicles", expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create company with employees but no vehicles assigned
        company.setName("Gourmet Delivery");
        
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Employees are not assigned any vehicles
        employee1.setAssignedVehicle(null);
        employee2.setAssignedVehicle(null);
        
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no employees have assigned vehicles", expected, result);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create company with some vehicles assigned and some not
        company.setName("Fast Meals Co.");
        
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        
        employee1.setAssignedVehicle(vehicle1);
        employee2.setAssignedVehicle(vehicle3);
        // vehicle2 is not assigned to any employee
        
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals("Should return only employee names that have assigned vehicles", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create company with employees but no vehicles at all
        company.setName("Delicious Delivery");
        
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        
        // No vehicles created and none assigned to employees
        employee1.setAssignedVehicle(null);
        employee2.setAssignedVehicle(null);
        
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList()); // Empty vehicle list
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when employees have no vehicles assigned", expected, result);
    }
}