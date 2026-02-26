import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // SetUp: Create company and employees/vehicles as specified
        
        // Create company
        company.setName("Food Express");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType("part-time");
        
        // Create full-time employees
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType("full-time");
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(emp1);
        emp1.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(emp2);
        emp2.setAssignedVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(emp3);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute the method under test
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // SetUp: Create company with only full-time employees
        
        company.setName("Quick Delivery");
        
        // Create full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setType("full-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setType("full-time");
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(emp1);
        emp1.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(emp2);
        emp2.setAssignedVehicle(vehicle2);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute the method under test
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output (empty list)
        assertTrue("Should return empty list when no part-time employees exist", 
                   result.isEmpty());
        assertEquals("Size should be 0 when no part-time employees exist", 
                     0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // SetUp: Create company with mixed employee types and vehicles
        
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType("part-time");
        
        // Create full-time employees
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType("full-time");
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType("full-time");
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(emp1);
        emp1.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(emp2);
        emp2.setAssignedVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(emp3);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute the method under test
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Should return registration number of vehicle driven by part-time employee", 
                     expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // SetUp: Create company with multiple part-time drivers and vehicles
        
        company.setName("City Foods");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType("part-time");
        
        // Create full-time employee
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(emp1);
        emp1.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(emp2);
        emp2.setAssignedVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(emp3);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute the method under test
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // SetUp: Create company with vehicles that have no drivers assigned
        
        company.setName("Rapid Deliveries");
        
        // Create part-time employees (but no vehicles assigned to them)
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setType("part-time");
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute the method under test
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output (empty list)
        assertTrue("Should return empty list when no vehicles are driven by part-time employees", 
                   result.isEmpty());
        assertEquals("Size should be 0 when no vehicles have part-time drivers", 
                     0, result.size());
    }
}