import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR4Test {
    
    private Company company;
    private Employee employee;
    private Vehicle vehicle;
    
    @Before
    public void setUp() {
        // Reset objects before each test
        company = null;
        employee = null;
        vehicle = null;
    }
    
    @Test
    public void testCase1_singleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create company, employee, and vehicle with driver assignment
        company = new Company();
        company.setName("Food Express");
        
        employee = new Employee();
        employee.setName("John Doe");
        employee.setType("full-time");
        
        vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        
        // Assign vehicle to employee and employee to vehicle
        employee.setAssignedVehicle(vehicle);
        vehicle.setDriver(employee);
        
        // Add employee and vehicle to company
        company.addEmployee(employee);
        company.addVehicle(vehicle);
        
        // Execute method under test
        List<String> result = company.getDrivingEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("John Doe");
        assertEquals("Should return single employee driving a vehicle", expected, result);
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create company, multiple employees and vehicles with driver assignments
        company = new Company();
        company.setName("Quick Delivery");
        
        // Create first employee and vehicle
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType("full-time");
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        
        // Assign vehicle to employee and employee to vehicle
        employee1.setAssignedVehicle(vehicle1);
        vehicle1.setDriver(employee1);
        
        // Create second employee and vehicle
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType("part-time");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        
        // Assign vehicle to employee and employee to vehicle
        employee2.setAssignedVehicle(vehicle2);
        vehicle2.setDriver(employee2);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getDrivingEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals("Should return all employees driving vehicles", expected, result);
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create company with employees and vehicles but no driver assignments
        company = new Company();
        company.setName("Gourmet Delivery");
        
        // Create employees without vehicle assignments
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType("part-time");
        // No vehicle assigned
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType("full-time");
        // No vehicle assigned
        
        // Create vehicles without driver assignments
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        // No driver assigned
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        // No driver assigned
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getDrivingEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no employees are driving", expected, result);
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create company with some vehicles assigned to drivers and some not
        company = new Company();
        company.setName("Fast Meals Co.");
        
        // Create employees
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType("part-time");
        
        // Create vehicles with mixed assignment states
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        
        // Assign vehicles to appropriate employees
        employee1.setAssignedVehicle(vehicle1);
        vehicle1.setDriver(employee1);
        
        employee2.setAssignedVehicle(vehicle3);
        vehicle3.setDriver(employee2);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute method under test
        List<String> result = company.getDrivingEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals("Should return only employees who are driving vehicles", expected, result);
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create company with employees but no vehicle assignments
        company = new Company();
        company.setName("Delicious Delivery");
        
        // Create employees without vehicle assignments
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType("full-time");
        // No vehicle assigned
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType("part-time");
        // No vehicle assigned
        
        // Add employees to company (no vehicles added since none are assigned)
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Execute method under test
        List<String> result = company.getDrivingEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no employees have vehicles", expected, result);
    }
}