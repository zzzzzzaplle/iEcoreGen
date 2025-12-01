import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // SetUp: Create company and employees
        company.setName("Food Express");
        
        // Create part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType("part-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType("part-time");
        
        // Create full-time employees
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType("full-time");
        
        Employee employee4 = new Employee();
        employee4.setName("Diana");
        employee4.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(employee1);
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(employee2);
        employee2.setVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(employee3);
        employee3.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Check expected output
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Registration numbers should match expected list", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create company with only full-time employees
        company.setName("Quick Delivery");
        
        // Create full-time employees
        Employee employee1 = new Employee();
        employee1.setName("Ethan");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Fiona");
        employee2.setType("full-time");
        
        Employee employee3 = new Employee();
        employee3.setName("George");
        employee3.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(employee1);
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(employee2);
        employee2.setVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Should return empty list
        assertTrue("Result should be empty when no part-time employees drive vehicles", result.isEmpty());
        assertEquals("Registration numbers should be empty list", 0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create company with mixed employees and vehicles
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType("part-time");
        
        // Create full-time employees
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType("full-time");
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType("full-time");
        
        Employee employee4 = new Employee();
        employee4.setName("Kara");
        employee4.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(employee1);
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(employee2);
        employee2.setVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(employee3);
        employee3.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Should return only the part-time employee's vehicle
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Registration numbers should match expected list", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create company with multiple part-time drivers
        company.setName("City Foods");
        
        // Create part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Lily");
        employee1.setType("part-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Mike");
        employee2.setType("part-time");
        
        // Create full-time employee
        Employee employee3 = new Employee();
        employee3.setName("Nina");
        employee3.setType("full-time");
        
        // Create vehicles (note: test case says 2 vehicles but shows 3 vehicles in setup)
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(employee1);
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(employee2);
        employee2.setVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(employee3);
        employee3.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Should return both part-time employees' vehicles
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Registration numbers should match expected list", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create company with vehicles having no drivers
        company.setName("Rapid Deliveries");
        
        // Create part-time employees (but no vehicles assigned to them)
        Employee employee1 = new Employee();
        employee1.setName("Olivia");
        employee1.setType("part-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Paul");
        employee2.setType("part-time");
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Should return empty list since no vehicles are driven by part-time employees
        assertTrue("Result should be empty when no vehicles are driven by part-time employees", result.isEmpty());
        assertEquals("Registration numbers should be empty list", 0, result.size());
    }
}